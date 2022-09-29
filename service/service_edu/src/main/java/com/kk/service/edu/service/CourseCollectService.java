package com.kk.service.edu.service;

import com.kk.service.edu.pojo.CourseCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kk.service.edu.pojo.vo.CourseCollectReturnVo;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务类
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
public interface CourseCollectService extends IService<CourseCollect> {
    public abstract Boolean isCollectThisCourse(String courseId, String memberId);
    public abstract Boolean addCollect(String courseId, String memberId);
    public abstract Boolean removeCollect(String courseId, String memberId);
    public abstract List<CourseCollectReturnVo> getCourseCollectList(String memberId);
}
