package com.kk.service.edu.pojo.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectListVo {
    private String id;
    private String title;
    private Integer sort;
    private List<SubjectListVo> children = new ArrayList<>();
}
