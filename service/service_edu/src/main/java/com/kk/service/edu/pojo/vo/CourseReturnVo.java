package com.kk.service.edu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseReturnVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String subjectParentTitle;
    private String subjectTitle;
    private String teacherName;
    private Integer lessonNum;
    private String price;
    private String cover;
    private Long buyCount;
    private Long viewCount;
    private String status;
    private String gmtCreate;

    @Override
    public String toString() {
        return "CourseReturnVo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", subjectParentTitle='" + subjectParentTitle + '\'' +
                ", subjectTitle='" + subjectTitle + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", lessonNum=" + lessonNum +
                ", price='" + price + '\'' +
                ", cover='" + cover + '\'' +
                ", buyCount=" + buyCount +
                ", viewCount=" + viewCount +
                ", status='" + status + '\'' +
                ", gmtCreate='" + gmtCreate + '\'' +
                '}';
    }
}
