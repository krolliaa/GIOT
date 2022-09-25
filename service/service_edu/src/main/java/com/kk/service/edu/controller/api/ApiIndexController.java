package com.kk.service.edu.controller.api;

import com.kk.common.result.ResultData;
import com.kk.service.edu.pojo.Course;
import com.kk.service.edu.pojo.Teacher;
import com.kk.service.edu.service.CourseService;
import com.kk.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "首页接口")
@CrossOrigin
@RequestMapping("/api/edu/index")
@RestController
public class ApiIndexController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "热门课程和讲师列表")
    @GetMapping(value = "/hot")
    public ResultData getHot() {
        //获取热门课程
        List<Course> courseHot = courseService.selectHot();
        //获取热门讲师
        List<Teacher> teacherHot = teacherService.selectHot();
        return ResultData.ok().data("courseHot", courseHot).data("teacherHot", teacherHot);
    }
}
