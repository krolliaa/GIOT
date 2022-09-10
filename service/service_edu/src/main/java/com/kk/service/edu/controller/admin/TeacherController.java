package com.kk.service.edu.controller.admin;

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
@Api(value = "讲师管理接口")
@RestController
@RequestMapping("/admin/edu/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping(value = "/list")
    public List<Teacher> listAll() {
        return teacherService.list();
    }

    @ApiOperation(value = "根据 id 删除讲师【逻辑删除】", notes = "根据 id 删除讲师【逻辑删除】")
    @DeleteMapping(value = "/remove/{id}")
    public Boolean removeById(@ApiParam(value = "讲师id", required = true) @PathVariable(value = "id") String id) {
        return teacherService.removeById(id);
    }
}
