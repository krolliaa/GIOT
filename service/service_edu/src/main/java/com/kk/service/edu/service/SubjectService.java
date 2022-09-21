package com.kk.service.edu.service;

import com.kk.common.result.ResultData;
import com.kk.service.edu.pojo.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kk.service.edu.pojo.vo.SubjectListVo;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
public interface SubjectService extends IService<Subject> {
    public abstract void batchImport(InputStream inputStream);
    public abstract List<SubjectListVo> selectNestedList(String id);
    public abstract List<Subject> getSubjectParentListBySubjectName(String title);
}