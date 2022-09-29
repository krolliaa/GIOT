package com.kk.service.edu.controller.api;

import com.kk.common.result.ResultData;
import com.kk.service.edu.pojo.Course;
import com.kk.service.edu.pojo.dto.CourseDto;
import com.kk.service.edu.pojo.vo.ChapterReturnVo;
import com.kk.service.edu.pojo.vo.WebCourseQueryVo;
import com.kk.service.edu.pojo.vo.WebCourseReturnVo;
import com.kk.service.edu.service.ChapterService;
import com.kk.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin
@Api(description = "用户页面课程管理接口")
@RestController
@RequestMapping("/api/edu/course")
public class ApiCourseController {
    @Autowired
    private CourseService courseService;

    @ApiOperation("课程列表")
    @GetMapping("/list")
    public ResultData list(@ApiParam(value = "查询对象", required = false) WebCourseQueryVo webCourseQueryVo) {
        List<Course> courseList = courseService.webSelectList(webCourseQueryVo);
        return ResultData.ok().data("courseList", courseList);
    }

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("根据ID查询课程")
    @GetMapping("/get/{courseId}")
    public ResultData getById(@ApiParam(value = "课程ID", required = true) @PathVariable String courseId) {
        //查询课程信息和讲师信息
        WebCourseReturnVo webCourseReturnVo = courseService.selectWebCourseReturnVoById(courseId);
        //查询当前课程的章节信息
        List<ChapterReturnVo> chapterReturnVoList = chapterService.chapterReturnVoList(courseId);
        return ResultData.ok().data("course", webCourseReturnVo).data("chapterList", chapterReturnVoList);
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("/inner/get-course-dto/{courseId}")
    public CourseDto getCourseDtoByCourseId(@ApiParam(value = "课程ID", required = true) @PathVariable(value = "courseId") String courseId) {
        return courseService.getCourseDtoByCourseId(courseId);
    }
}
