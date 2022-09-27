package com.kk.service.edu.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.service.edu.pojo.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kk.service.edu.pojo.dto.CourseDto;
import com.kk.service.edu.pojo.vo.CoursePublishVo;
import com.kk.service.edu.pojo.vo.CourseReturnVo;
import com.kk.service.edu.pojo.vo.WebCourseReturnVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
public interface CourseMapper extends BaseMapper<Course> {
    //MyBatis-Plus 会自动组装分页参数
    //MyBatis-Plus 会自动封装 QueryWrapper
    public abstract List<CourseReturnVo> selectPageByCourseQueryVo(Page<CourseReturnVo> page,@Param(Constants.WRAPPER) QueryWrapper<CourseReturnVo> queryWrapper);
    public abstract CoursePublishVo selectCoursePublishById(String id);
    public abstract WebCourseReturnVo selectWebCourseReturnVoById(String courseId);
    public abstract CourseDto getCourseDtoByCourseId(String id);
}
