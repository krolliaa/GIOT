package com.kk.service.edu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;
    //课程标题
    private String title;
    //课程讲师
    private String teacherId;
    //一级分类
    private String subjectParentId;
    // 二级分类
    private String subjectId;

    @Override
    public String toString() {
        return "CourseQueryVo{" +
                "title='" + title + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", subjectParentId='" + subjectParentId + '\'' +
                ", subjectId='" + subjectId + '\'' +
                '}';
    }
}
