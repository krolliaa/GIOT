package com.kk.service.edu.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.common.result.ResultData;
import com.kk.feign_api.client.OSSClient;
import com.kk.service.edu.pojo.Teacher;
import com.kk.service.edu.pojo.vo.TeacherQueryVo;
import com.kk.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Api(value = "讲师管理接口", description = "讲师管理接口")
@RestController
@RequestMapping("/admin/edu/teacher")
@Slf4j
//@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 获取所有讲师列表接口
     *
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping(value = "/list")
    public ResultData listAll() {
        log.debug("所有讲师列表....................");
        List<Teacher> teachers = teacherService.list();
        if (teachers != null) {
            return ResultData.ok().data("items", teachers).message("获取讲师列表成功");
        } else {
            return ResultData.error().message("数据不存在");
        }
    }

    /**
     * 分页获取讲师列表接口
     *
     * @param current
     * @param size
     * @param teacherQueryVo
     * @return
     */
    @ApiOperation(value = "分页讲师列表")
    @GetMapping(value = "/list/{current}/{size}")
    public ResultData listPage(@ApiParam(value = "当前页码", required = true) @PathVariable(value = "current") Long current,
                               @ApiParam(value = "每页显示条数", required = true) @PathVariable(value = "size") Long size,
                               @ApiParam(value = "讲师列表条件查询对象") TeacherQueryVo teacherQueryVo) {
        Page<Teacher> teacherPage = teacherService.selectPage(current, size, teacherQueryVo);
        List<Teacher> teachers = teacherPage.getRecords();
        long teacherPageTotal = teacherPage.getTotal();
        if (teacherPage != null) {
        }
        return ResultData.ok().message("获取分页讲师列表成功").data("total", teacherPageTotal).data("rows", teachers);
    }

    /**
     * 根据 id 删除讲师接口
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据 id 删除讲师【逻辑删除】", notes = "根据 id 删除讲师【逻辑删除】")
    @DeleteMapping(value = "/remove/{id}")
    public ResultData removeById(@ApiParam(value = "讲师id", required = true) @PathVariable(value = "id") String id) {
        String avatarUrl = teacherService.getById(id).getAvatar();
        Boolean success = teacherService.removeById(id);
        // 使用 OpenFeign 调用 service_oss 中的删除头像接口
        if (success) {
            boolean result = ossClient.deleteFile(avatarUrl).getSuccess();
            String message = "删除成功";
            if (!result) message += "但头像删除错误，请联系管理员！";
            return ResultData.ok().message(message);
        } else {
            return ResultData.error().message("删除失败，数据不存在");
        }
    }

    @ApiOperation(value = "批量删除讲师【逻辑删除】", notes = "批量删除讲师【逻辑删除】")
    @DeleteMapping(value = "/batch-remove")
    public ResultData removeByIds(@ApiParam(value = "讲师idList", required = true) @RequestBody List<String> idList) {
        List<String> avatarUrlList = new ArrayList<>();
        for (String id : idList) avatarUrlList.add(teacherService.getById(id).getAvatar());
        boolean success = teacherService.removeByIds(idList);
        if (success) {
            boolean result = ossClient.deleteFile(avatarUrlList).getSuccess();
            String message = "批量删除成功";
            if (!result) message += "但头像删除错误，请联系管理员！";
            return ResultData.ok().message(message);
        } else {
            return ResultData.error().message("批量删除失败！");
        }
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("/save")
    public ResultData save(@ApiParam(value = "讲师对象", required = true) @RequestBody Teacher teacher) {
        if (!StringUtils.isNotBlank(teacher.getAvatar()))
            teacher.setAvatar("https://giot-file.oss-cn-shenzhen.aliyuncs.com/avator/2022-09-15/default.jpg");
        Boolean success = teacherService.save(teacher);
        if (success) {
            return ResultData.ok().message("保存成功");
        } else {
            return ResultData.error().message("保存失败");
        }
    }

    @ApiOperation(value = "更新讲师")
    @PutMapping(value = "/update")
    public ResultData updateById(@ApiParam(value = "讲师对象", required = true) @RequestBody Teacher teacher) {
        Boolean success = teacherService.updateById(teacher);
        if (success) {
            return ResultData.ok().message("修改成功");
        } else {
            return ResultData.error().message("数据不存在");
        }
    }

    @ApiOperation(value = "根据 id 获取讲师")
    @GetMapping(value = "/{id}")
    public ResultData getById(@ApiParam(value = "讲师ID", required = true) @PathVariable(value = "id") String id) {
        //这里会自动查询没有被逻辑删除的数据，666
        Teacher teacher = teacherService.getById(id);
        if (teacher != null) {
            return ResultData.ok().message("查询成功").data("item", teacher);
        } else {
            return ResultData.error().message("数据不存在");
        }
    }

    @ApiOperation(value = "查询匹配的讲师名称列表")
    @GetMapping(value = "/list/name/{key}")
    public ResultData selectNameListByKey(@ApiParam(value = "查询关键字", required = true) @PathVariable(value = "key") String key) {
        System.out.println(key);
        List<Map<String, Object>> nameList = teacherService.selectNameListByKey(key);
        return ResultData.ok().data("nameList", nameList);
    }

    @Autowired
    private OSSClient ossClient;

    @ApiOperation(value = "测试 OpenFeign 远程调用")
    @GetMapping(value = "/test")
    public ResultData test() {
        return ossClient.test();
    }
}