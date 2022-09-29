package com.kk.service.edu.controller.admin;

import com.kk.common.result.ResultData;
import com.kk.service.edu.mapper.VideoMapper;
import com.kk.service.edu.pojo.Chapter;
import com.kk.service.edu.pojo.vo.ChapterReturnVo;
import com.kk.service.edu.pojo.vo.VideoReturnVo;
import com.kk.service.edu.service.ChapterService;
import com.kk.service.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Api(value = "章节管理", description = "章节管理接口")
//@CrossOrigin
@RestController
@RequestMapping("/admin/edu/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    /**
     * 添加章节接口
     *
     * @param chapter
     * @return
     */
    @ApiOperation(value = "添加章节接口")
    @PostMapping(value = "/save")
    public ResultData save(@ApiParam(value = "章节对象", required = true) @RequestBody Chapter chapter) {
        boolean result = chapterService.save(chapter);
        if (result) return ResultData.ok().message("添加成功");
        else return ResultData.error().message("添加错误");
    }

    /**
     * 根据 id 查询章节接口
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据 id 查询章节接口")
    @GetMapping(value = "/get/{id}")
    public ResultData getById(@ApiParam(value = "章节 id", required = true) @PathVariable(value = "id") String id) {
        Chapter chapter = chapterService.getById(id);
        if (chapter != null) return ResultData.ok().message("查询成功").data("item", chapter);
        else return ResultData.error().message("查询错误");
    }

    /**
     * 更新章节接口
     *
     * @param chapter
     * @return
     */
    @ApiOperation(value = "更新章节接口")
    @PutMapping(value = "/update")
    public ResultData update(@ApiParam(value = "需要更新的章节对象", required = true) @RequestBody Chapter chapter) {
        boolean result = chapterService.updateById(chapter);
        if (result) return ResultData.ok().message("修改成功");
        else return ResultData.error().message("修改错误");
    }

    /**
     * 删除章节接口
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除章节接口")
    @DeleteMapping(value = "/remove/{id}")
    public ResultData delete(@ApiParam(value = "需要删除的章节 id", required = true) @PathVariable(value = "id") String id) {
        boolean result = chapterService.removeChapterById(id);
        if (result) return ResultData.ok().message("删除成功");
        else return ResultData.error().message("删除错误");
    }

    @Autowired
    private VideoService videoService;

    /**
     * 章节列表接口
     *
     * @return
     */
    @ApiOperation(value = "章节列表接口")
    @GetMapping(value = "/nested-list/{courseId}")
    public ResultData list(@ApiParam(value = "课程 ID", required = true) @PathVariable(value = "courseId") String courseId) {
        List<ChapterReturnVo> chapterReturnVoList = chapterService.chapterReturnVoList(courseId);
        if (chapterReturnVoList != null) return ResultData.ok().message("查询成功").data("items", chapterReturnVoList);
        else return ResultData.error().message("查询错误");
    }
}
