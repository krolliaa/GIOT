package com.kk.service.edu.mapper;

import com.kk.service.edu.pojo.Chapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kk.service.edu.pojo.vo.ChapterReturnVo;
import com.kk.service.edu.pojo.vo.VideoReturnVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Mapper
public interface ChapterMapper extends BaseMapper<Chapter> {
    @Select("select id, title, sort from edu_chapter where course_id = #{courseId} order by sort, id asc")
    public abstract List<ChapterReturnVo> chapterReturnVoList(String courseId);
}
