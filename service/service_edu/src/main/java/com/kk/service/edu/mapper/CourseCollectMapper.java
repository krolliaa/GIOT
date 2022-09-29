package com.kk.service.edu.mapper;

import com.kk.service.edu.pojo.CourseCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kk.service.edu.pojo.vo.CourseCollectReturnVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课程收藏 Mapper 接口
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Mapper
public interface CourseCollectMapper extends BaseMapper<CourseCollect> {
    public abstract List<CourseCollectReturnVo> getCourseCollectList(String memberId);
}
