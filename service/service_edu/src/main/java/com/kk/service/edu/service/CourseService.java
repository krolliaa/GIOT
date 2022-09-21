package com.kk.service.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.service.edu.pojo.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kk.service.edu.pojo.form.CourseInfoForm;
import com.kk.service.edu.pojo.vo.CoursePublishVo;
import com.kk.service.edu.pojo.vo.CourseQueryVo;
import com.kk.service.edu.pojo.vo.CourseReturnVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
public interface CourseService extends IService<Course> {
    public abstract String saveCourseInfo(CourseInfoForm courseInfoForm);
    public abstract Boolean publishCourseById(String id);
    public abstract CourseInfoForm getCourseInfoById(String id);
    public abstract CoursePublishVo getCoursePublishById(String id);
    public abstract String updateCourseInfo(CourseInfoForm courseInfoForm);
    public abstract Page<CourseReturnVo> selectPageByCourseQueryVo(Long current, Long size, CourseQueryVo courseQueryVo);
    public abstract boolean removeCourseById(String id);
}
