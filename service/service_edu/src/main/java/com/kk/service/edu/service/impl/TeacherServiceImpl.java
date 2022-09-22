package com.kk.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.service.edu.mapper.CourseMapper;
import com.kk.service.edu.pojo.Course;
import com.kk.service.edu.pojo.Teacher;
import com.kk.service.edu.mapper.TeacherMapper;
import com.kk.service.edu.pojo.vo.TeacherQueryVo;
import com.kk.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public Page<Teacher> selectPage(Long current, Long size, TeacherQueryVo teacherQueryVo) {
        if (teacherQueryVo == null) return teacherMapper.selectPage(new Page<>(current, size), null);
        String name = teacherQueryVo.getName();
        Integer level = teacherQueryVo.getLevel();
        String joinDateBegin = teacherQueryVo.getJoinDateBegin();
        String joinDateEnd = teacherQueryVo.getJoinDateEnd();
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(name)) queryWrapper.likeRight("name", name);//name不是使用固定值而是模糊搜索并且需要符合最左匹配原则
        if (level != null) queryWrapper.eq("level", level);
        if (StringUtils.isNotBlank(joinDateBegin)) queryWrapper.ge("join_date", joinDateBegin);
        if (StringUtils.isNotBlank(joinDateEnd)) queryWrapper.le("join_date", joinDateEnd);
        queryWrapper.orderByAsc("sort");//按照 sort 字段顺序排序
        return teacherMapper.selectPage(new Page<>(current, size), queryWrapper);
    }

    @Override
    public List<Map<String, Object>> selectNameListByKey(String key) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name");
        queryWrapper.likeRight("name", key);
        List<Map<String, Object>> list = baseMapper.selectMaps(queryWrapper);
        return list;
    }

    @Override
    @Transactional
    public HashMap<String, Object> selectTeacherInfoById(String id) {
        Teacher teacher = teacherMapper.selectById(id);
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", id);
        List<Course> courseList = courseMapper.selectList(queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacher", teacher);
        map.put("courseList", courseList);
        return map;
    }
}
