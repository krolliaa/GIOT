package com.kk.service.edu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherQueryVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //讲师姓名
    private String name;
    //讲师级别
    private Integer level;
    //讲师入驻时间范围
    private String joinDateBegin;
    private String joinDateEnd;
}
