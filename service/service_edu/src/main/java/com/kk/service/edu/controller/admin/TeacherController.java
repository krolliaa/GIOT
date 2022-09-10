package com.kk.service.edu.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.common.result.ResultData;
import com.kk.service.edu.pojo.Teacher;
import com.kk.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping(value = "/list")
    public ResultData listAll() {
        List<Teacher> teachers = teacherService.list();
        return ResultData.ok().data("teachers", teachers).message("获取讲师列表成功");
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping(value = "/list/{current}/{size}")
    public ResultData listPage(@ApiParam(value = "当前页码", required = true) @PathVariable(value = "current") Long current,
                               @ApiParam(value = "每页显示条数", required = true) @PathVariable(value = "size") Long size) {
        if (current < 1) current = 1L;
        if (size > 10) size = 10L;
        Page<Teacher> teacherPage = teacherService.page(new Page<Teacher>(current, size));
        List<Teacher> teachers = teacherPage.getRecords();
        long teacherPageTotal = teacherPage.getTotal();
        return ResultData.ok().message("获取分页讲师列表成功").data("total", teacherPageTotal).data("rows", teachers);
    }

    @ApiOperation(value = "根据 id 删除讲师【逻辑删除】", notes = "根据 id 删除讲师【逻辑删除】")
    @DeleteMapping(value = "/remove/{id}")
    public ResultData removeById(@ApiParam(value = "讲师id", required = true) @PathVariable(value = "id") String id) {
        Boolean success = teacherService.removeById(id);
        if (success) {
            return ResultData.ok().message("删除成功");
        } else {
            return ResultData.error().message("删除失败，数据不存在");
        }
    }
}
