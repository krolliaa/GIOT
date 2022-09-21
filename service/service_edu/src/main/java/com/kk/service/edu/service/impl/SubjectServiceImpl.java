package com.kk.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kk.service.edu.listener.ExcelSubjectListener;
import com.kk.service.edu.pojo.Subject;
import com.kk.service.edu.mapper.SubjectMapper;
import com.kk.service.edu.pojo.excel.ExcelSubjectData;
import com.kk.service.edu.pojo.vo.SubjectListVo;
import com.kk.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public void batchImport(InputStream inputStream) {
        //因为 EasyExcel 提供的监听器是无法被 Spring 管理的，所以如果要使用 SubjectMapper 得直接传递
        EasyExcel.read(inputStream, ExcelSubjectData.class, new ExcelSubjectListener(subjectMapper)).excelType(ExcelTypeEnum.XLS).sheet().doRead();
    }

    @Override
    public List<SubjectListVo> selectNestedList(String id) {
        return subjectMapper.selectNestedList(id);
    }

    @Override
    public List<Subject> getSubjectParentListBySubjectName(String title) {
        return subjectMapper.getSubjectParentListBySubjectName(title);
    }
}
