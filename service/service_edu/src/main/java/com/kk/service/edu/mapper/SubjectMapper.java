package com.kk.service.edu.mapper;

import com.kk.service.edu.pojo.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kk.service.edu.pojo.vo.SubjectListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {
    public abstract List<SubjectListVo> selectNestedList(String id);

    @Select("select * from edu_subject where id in (select parent_id from edu_subject where title = #{title})")
    public abstract List<Subject> getSubjectParentListBySubjectName(String title);
}
