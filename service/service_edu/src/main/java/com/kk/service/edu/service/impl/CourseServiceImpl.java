package com.kk.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.common.result.ResultData;
import com.kk.feign_api.client.OSSClient;
import com.kk.service.edu.mapper.*;
import com.kk.service.edu.pojo.*;
import com.kk.service.edu.pojo.dto.CourseDto;
import com.kk.service.edu.pojo.form.CourseInfoForm;
import com.kk.service.edu.pojo.vo.*;
import com.kk.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CourseCollectMapper courseCollectMapper;

    @Autowired
    private OSSClient ossClient;

    @Override
    // 因为涉及到多个表的存储，所以需要使用到事务
    @Transactional(rollbackFor = {Exception.class})
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        Course course = new Course();
        course.setStatus(Course.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoForm, course);
        courseMapper.insert(course);
        // 存储课程详情信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescriptionMapper.insert(courseDescription);
        return course.getId();
    }

    @Override
    public Boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        return this.updateById(course);
    }

    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        Course course = courseMapper.selectById(id);
        if (course == null) return null;
        CourseDescription courseDescription = courseDescriptionMapper.selectById(id);
        if (courseDescription == null) return null;
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course, courseInfoForm);
        courseInfoForm.setDescription(courseDescription.getDescription());
        return courseInfoForm;
    }

    @Override
    public CoursePublishVo getCoursePublishById(String id) {
        return courseMapper.selectCoursePublishById(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public String updateCourseInfo(CourseInfoForm courseInfoForm) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        courseMapper.updateById(course);
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescriptionMapper.updateById(courseDescription);
        return course.getId();
    }

    @Override
    public Page<CourseReturnVo> selectPageByCourseQueryVo(Long current, Long size, CourseQueryVo courseQueryVo) {
        QueryWrapper<CourseReturnVo> queryWrapper = new QueryWrapper<>();
        String title = "";
        String subjectId = "";
        String subjectParentId = "";
        String teacherId = "";
        if (courseQueryVo != null) {
            // 多表查询后续使用 ${ew.customSqlSegment} 自定义结果，这里的 column 稍微特殊一些
            title = courseQueryVo.getTitle();
            if (Strings.isNotBlank(title)) {
                queryWrapper.like("course.title", title);
            }
            subjectParentId = courseQueryVo.getSubjectParentId();
            if (Strings.isNotBlank(subjectId)) {
                queryWrapper.eq("course.subject_id", subjectId);
            }
            subjectId = courseQueryVo.getSubjectId();
            if (Strings.isNotBlank(subjectParentId)) {
                queryWrapper.eq("course.subject_parent_id", subjectParentId);
            }
            teacherId = courseQueryVo.getTeacherId();
            if (Strings.isNotBlank(teacherId)) {
                queryWrapper.eq("course.teacher_id", teacherId);
            }
        }
        Page<CourseReturnVo> page = new Page<>(current, size);
        List<CourseReturnVo> courseReturnVoList = courseMapper.selectPageByCourseQueryVo(page, queryWrapper);
        page.setRecords(courseReturnVoList);
        return page;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean removeCourseById(String id) {
        // 删除封面
        Course course = baseMapper.selectById(id);
        if (course != null) {
            String cover = course.getCover();
            if (Strings.isNotBlank(cover)) {
                ossClient.deleteFile(cover);
            }
        }
        // 删除视频、大纲、评论、详情、收藏
        //收藏信息：course_collect
        QueryWrapper<CourseCollect> courseCollectQueryWrapper = new QueryWrapper<>();
        courseCollectQueryWrapper.eq("course_id", id);
        courseCollectMapper.delete(courseCollectQueryWrapper);

        //评论信息：comment
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("course_id", id);
        commentMapper.delete(commentQueryWrapper);

        //课时信息：video
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        videoMapper.delete(videoQueryWrapper);

        //章节信息：chapter
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        chapterMapper.delete(chapterQueryWrapper);

        //课程详情：course_description
        courseDescriptionMapper.deleteById(id);
        return courseMapper.deleteById(id) != 0 ? true : false;
    }

    @Override
    public List<Course> webSelectList(WebCourseQueryVo webCourseQueryVo) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        //查询已发布的课程
        queryWrapper.eq("status", Course.COURSE_NORMAL);
        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", webCourseQueryVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectId())) {
            queryWrapper.eq("subject_id", webCourseQueryVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(webCourseQueryVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(webCourseQueryVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(webCourseQueryVo.getPriceSort())) {
            if ("1".equals(webCourseQueryVo.getPriceSort())) queryWrapper.orderByDesc("price");
            else queryWrapper.orderByAsc("price");
        }
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public WebCourseReturnVo selectWebCourseReturnVoById(String courseId) {
        // 每次点击都会增加一次课程浏览数
        Course course = courseMapper.selectById(courseId);
        course.setViewCount(course.getViewCount() + 1);
        courseMapper.updateById(course);
        return courseMapper.selectWebCourseReturnVoById(courseId);
    }

    @Override
    @Cacheable(value = "index", key = "'selectHotCourse'")
    public List<Course> selectHot() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view_count");
        queryWrapper.last("limit 8");
        return courseMapper.selectList(queryWrapper);
    }

    @Override
    public CourseDto getCourseDtoByCourseId(String id) {
        //因为涉及到讲师姓名这里要做多表查询，所以需要自定义 Mapper SQL 语句
        return courseMapper.getCourseDtoByCourseId(id);
    }
}
