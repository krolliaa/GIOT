package com.kk.service.edu.controller.admin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kk.common.result.ResultData;
import com.kk.common.result.ResultEnum;
import com.kk.common.util.ExceptionUtils;
import com.kk.service.base.exception.GiotException;
import com.kk.service.edu.pojo.Subject;
import com.kk.service.edu.pojo.vo.SubjectListVo;
import com.kk.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
//@CrossOrigin
@RestController
@Api(value = "课程分类管理", description = "课程分类管理")
@RequestMapping("/admin/edu/subject")
@Slf4j
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "Excel批量导入课程类别数据")
    @PostMapping(value = "/import")
    public ResultData batchImport(@ApiParam(value = "Excel 文件") @RequestBody MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            subjectService.batchImport(inputStream);
            return ResultData.ok().message("批量导入成功");
        } catch (Exception e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new GiotException(ResultEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }

    @ApiOperation(value = "级联分类查询")
    @GetMapping(value = "/nested-list")
    public ResultData nestedList() {
        List<SubjectListVo> subjects = subjectService.selectNestedList("0");
        return ResultData.ok().data("items", subjects);
    }


    @ApiOperation(value = "查询一级课程分类")
    @GetMapping(value = "/subjectParentList")
    public ResultData getSubjectParentList() {
        List<Subject> subjectList = subjectService.list(new QueryWrapper<Subject>().eq("parent_id", "0"));
        return ResultData.ok().data("items", subjectList);
    }

    @ApiOperation(value = "查询二级课程分类")
    @GetMapping(value = "/subjectList")
    public ResultData getSubjectList() {
        List<Subject> subjectList = subjectService.list(new QueryWrapper<Subject>().ne("parent_id", "0"));
        HashSet<Subject> subjectHashSet = new HashSet<>();
        for (Subject subject : subjectList) subjectHashSet.add(subject);
        return ResultData.ok().data("items", subjectHashSet);
    }

    @ApiOperation(value = "根据二级课程分类查询一级分类")
    @GetMapping(value = "/subjectParentListBySubjectName/{title}")
    public ResultData getSubjectParentListBySubjectName(@ApiParam(value = "二级标题") @PathVariable(value = "title") String title) {
        System.out.println(title);
        List<Subject> subjectList = subjectService.getSubjectParentListBySubjectName(title);
        System.out.println(subjectList);
        return ResultData.ok().data("items", subjectList);
    }
}
