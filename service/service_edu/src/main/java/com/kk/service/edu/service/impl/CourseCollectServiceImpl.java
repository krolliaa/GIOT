package com.kk.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kk.service.edu.pojo.CourseCollect;
import com.kk.service.edu.mapper.CourseCollectMapper;
import com.kk.service.edu.pojo.vo.CourseCollectReturnVo;
import com.kk.service.edu.service.CourseCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 课程收藏 服务实现类
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Service
public class CourseCollectServiceImpl extends ServiceImpl<CourseCollectMapper, CourseCollect> implements CourseCollectService {

    @Override
    public Boolean isCollectThisCourse(String courseId, String memberId) {
        QueryWrapper<CourseCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId).eq("member_id", memberId);
        Long aLong = baseMapper.selectCount(queryWrapper);
        return aLong > 0;
    }

    @Override
    public Boolean addCollect(String courseId, String memberId) {
        if (!isCollectThisCourse(courseId, memberId)) {
            CourseCollect courseCollect = new CourseCollect();
            courseCollect.setCourseId(courseId);
            courseCollect.setMemberId(memberId);
            int insert = baseMapper.insert(courseCollect);
            return insert > 0;
        }
        return false;
    }

    @Override
    public Boolean removeCollect(String courseId, String memberId) {
        if (isCollectThisCourse(courseId, memberId)) {
            QueryWrapper<CourseCollect> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id", courseId).eq("member_id", memberId);
            return this.remove(queryWrapper);
        }
        return false;
    }

    @Override
    public List<CourseCollectReturnVo> getCourseCollectList(String memberId) {
        return baseMapper.getCourseCollectList(memberId);
    }
}
