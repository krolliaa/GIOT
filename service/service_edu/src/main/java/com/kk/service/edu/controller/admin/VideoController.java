package com.kk.service.edu.controller.admin;

import com.kk.common.result.ResultData;
import com.kk.service.edu.pojo.Video;
import com.kk.service.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Api(value = "课时管理", description = "课时管理接口")
//@CrossOrigin
@RestController
@RequestMapping("/admin/edu/video")
@Slf4j
public class VideoController {
    @Autowired
    private VideoService videoService;

    /**
     * 添加课时
     *
     * @param video
     * @return
     */
    @ApiOperation("新增课时")
    @PostMapping("save")
    public ResultData save(@ApiParam(value = "课时对象", required = true) @RequestBody Video video) {
        System.out.println("调用了新增接口：" + video);
        boolean result = videoService.save(video);
        if (result) return ResultData.ok().message("保存成功");
        else return ResultData.error().message("保存失败");
    }

    /**
     * 根据 ID 查询课时
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询课时")
    @GetMapping("get/{id}")
    public ResultData getById(@ApiParam(value = "课时id", required = true) @PathVariable String id) {
        System.out.println("调用了根据 ID 查询接口：" + id);
        Video video = videoService.getById(id);
        if (video != null) return ResultData.ok().data("item", video);
        else return ResultData.error().message("数据不存在");
    }

    /**
     * 根据 ID 修改课时
     *
     * @param video
     * @return
     */
    @ApiOperation("根据id修改课时")
    @PutMapping("update")
    public ResultData updateById(@ApiParam(value = "课时对象", required = true) @RequestBody Video video) {
        System.out.println("调用了修改接口：" + video);
        boolean result = videoService.updateById(video);
        if (result) return ResultData.ok().message("修改成功");
        else return ResultData.error().message("数据不存在");
    }

    /**
     * 根据 ID 删除课时
     *
     * @param id
     * @return
     */
    @ApiOperation("根据ID删除课时")
    @DeleteMapping("remove/{id}")
    public ResultData removeById(@ApiParam(value = "课时ID", required = true) @PathVariable String id) {
        System.out.println("调用了删除接口：" + id);
        boolean result = videoService.removeById(id);
        if (result) return ResultData.ok().message("删除成功");
        else return ResultData.error().message("数据不存在");
    }
}
