package com.kk.service.edu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebCourseReturnVo {
    private String id;
    private String title;
    private BigDecimal price;
    private String lessonNum;
    private String cover;
    private String buyCount;
    private String viewCount;
    private String description;
    private String teacherId;
    private String teacherName;
    private String teacherAvatar;
    private String teacherIntro;
    private String subjectParentId;
    private String subjectOneTitle;
    private String subjectId;
    private String subjectTwoTitle;
}
