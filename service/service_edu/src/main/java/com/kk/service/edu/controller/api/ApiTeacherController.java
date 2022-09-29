package com.kk.service.edu.controller.api;

import com.kk.common.result.ResultData;
import com.kk.service.edu.pojo.Teacher;
import com.kk.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "用户界面讲师接口")
//@CrossOrigin
@RestController
@RequestMapping(value = "/api/edu/teacher")
public class ApiTeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "获取所有讲师")
    @GetMapping(value = "/list")
    public ResultData listAll() {
        List<Teacher> teacherList = teacherService.list();
        return ResultData.ok().message("查询成功").data("items", teacherList);
    }

    @ApiOperation(value = "根据 ID 获取讲师")
    @GetMapping("/get/{id}")
    public ResultData get(@ApiParam(value = "讲师ID", required = true) @PathVariable(value = "id") String id) {
        HashMap<String, Object> map = teacherService.selectTeacherInfoById(id);
        System.out.println(map);
        return ResultData.ok().data(map);
    }
}
