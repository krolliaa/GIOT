package com.kk.service.edu.controller.admin;

import com.kk.service.edu.pojo.Teacher;
import com.kk.service.edu.service.TeacherService;
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
@RestController
@RequestMapping("/admin/edu/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping(value = "/list")
    public List<Teacher> listAll() {
        return teacherService.list();
    }

    @DeleteMapping(value = "/remove/{id}")
    public Boolean removeById(@PathVariable(value = "id") String id) {
        return teacherService.removeById(id);
    }
}
