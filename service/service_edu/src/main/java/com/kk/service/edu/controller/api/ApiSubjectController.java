package com.kk.service.edu.controller.api;

import com.kk.common.result.ResultData;
import com.kk.service.edu.pojo.Subject;
import com.kk.service.edu.service.CourseService;
import com.kk.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@Api(description = "用户页面课程分类管理接口")
//@CrossOrigin
@RestController
@RequestMapping(value = "/api/edu/subject")
@Slf4j
public class ApiSubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "查询课程分类：一二级分类")
    @GetMapping(value = "/nested-list")
    public ResultData nestedList() {
        List<Subject> subjectList = subjectService.list();
        HashSet<Subject> subjectOneList = new HashSet<>();
        HashSet<Subject> subjectTwoList = new HashSet<>();
        for (Subject subject : subjectList) {
            if ("0".equals(subject.getParentId())) subjectOneList.add(subject);
            else subjectTwoList.add(subject);
        }
        return ResultData.ok().data("subjectOneList", subjectOneList).data("subjectTwoList", subjectTwoList);
    }
}
