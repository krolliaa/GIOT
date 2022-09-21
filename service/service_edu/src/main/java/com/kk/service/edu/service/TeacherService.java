package com.kk.service.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.service.edu.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kk.service.edu.pojo.vo.TeacherQueryVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
public interface TeacherService extends IService<Teacher> {
    public abstract Page<Teacher> selectPage(Long current, Long size, TeacherQueryVo teacherQueryVo);
    public abstract List<Map<String, Object>> selectNameListByKey(String key);
}
