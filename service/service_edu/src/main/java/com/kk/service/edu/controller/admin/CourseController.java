package com.kk.service.edu.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.common.result.ResultData;
import com.kk.service.edu.pojo.Course;
import com.kk.service.edu.pojo.form.CourseInfoForm;
import com.kk.service.edu.pojo.vo.CoursePublishVo;
import com.kk.service.edu.pojo.vo.CourseQueryVo;
import com.kk.service.edu.pojo.vo.CourseReturnVo;
import com.kk.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Api(value = "课程管理", description = "课程管理")
//@CrossOrigin
@RestController
@RequestMapping("/admin/edu/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "分页查询所有课程")
    @GetMapping(value = "/list/{current}/{size}")
    public ResultData pageList(@ApiParam(value = "当前页码", required = true) @PathVariable(value = "current") Long current,
                               @ApiParam(value = "当前页记录数", required = true) @PathVariable(value = "size") Long size,
                               @ApiParam(value = "查询条件") CourseQueryVo courseQueryVo) {
        Page<CourseReturnVo> courseReturnVoPage = courseService.selectPageByCourseQueryVo(current, size, courseQueryVo);
        if (courseReturnVoPage == null) return ResultData.error().message("查询错误");
        long total = courseReturnVoPage.getTotal();
        List<CourseReturnVo> courseReturnVoList = courseReturnVoPage.getRecords();
        return ResultData.ok().data("rows", courseReturnVoList).data("total", total);
    }

    /**
     * 根据 ID 获取课程信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据课程ID获取课程信息")
    @GetMapping(value = "/course-info/{id}")
    public ResultData getCourseInfoById(@ApiParam(value = "课程ID") @PathVariable(value = "id") String id) {
        CourseInfoForm courseInfoForm = courseService.getCourseInfoById(id);
        if (courseInfoForm != null) return ResultData.ok().data("item", courseInfoForm);
        else return ResultData.error().message("课程不存在");
    }

    /**
     * 根据课程 ID 获取课程发布信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据课程ID获取课程发布信息")
    @GetMapping(value = "/course-publish/{id}")
    public ResultData getCoursePublishById(@ApiParam(value = "课程ID") @PathVariable(value = "id") String id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishById(id);
        if (coursePublishVo != null) return ResultData.ok().data("item", coursePublishVo);
        else return ResultData.error().message("课程不存在");
    }

    /**
     * 根据课程 ID 修改状态为未发布
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据课程ID获取课程发布信息")
    @PutMapping(value = "/course-update-status/{id}")
    public ResultData updateStatus(@ApiParam(value = "课程ID") @PathVariable(value = "id") String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_DRAFT);
        boolean success = courseService.updateById(course);
        if (success) return ResultData.ok().message("课程已下线");
        else return ResultData.error().message("课程不存在");
    }

    /**
     * 根据课程 ID 发布课程
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据课程ID发布课程")
    @PutMapping(value = "/course-publish/{id}")
    public ResultData publishCourseById(@ApiParam(value = "课程ID") @PathVariable(value = "id") String id) {
        Boolean publish = courseService.publishCourseById(id);
        if (publish) return ResultData.ok().message("课程发布成功");
        else return ResultData.error().message("课程发布错误");
    }

    /**
     * 添加课程信息
     *
     * @param courseInfoForm
     * @return
     */
    @ApiOperation(value = "添加课程信息")
    @PostMapping(value = "/save-course-info")
    public ResultData saveCourseInfo(@ApiParam(value = "课程信息") @RequestBody CourseInfoForm courseInfoForm) {
        String courseId = courseService.saveCourseInfo(courseInfoForm);
        return ResultData.ok().data("courseId", courseId).message("添加课程成功【未发布】");
    }

    /**
     * 更新课程信息
     *
     * @param courseInfoForm
     * @return
     */
    @ApiOperation(value = "更新课程信息")
    @PutMapping(value = "/update-course-info")
    public ResultData updateCourseInfo(@ApiParam(value = "课程信息") @RequestBody CourseInfoForm courseInfoForm) {
        String courseId = courseService.updateCourseInfo(courseInfoForm);
        return ResultData.ok().data("courseId", courseId).message("修改成功");
    }

    @ApiOperation("根据ID删除课程")
    @DeleteMapping("remove/{id}")
    public ResultData removeById(@ApiParam(value = "课程ID", required = true) @PathVariable String id) {
        boolean result = courseService.removeCourseById(id);
        if (result) {
            return ResultData.ok().message("删除成功");
        } else {
            return ResultData.error().message("数据不存在");
        }
    }
}
