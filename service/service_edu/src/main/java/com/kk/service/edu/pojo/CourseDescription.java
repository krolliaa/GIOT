package com.kk.service.edu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kk.service.base.model.BasePojo;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程简介
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("edu_course_description")
@ApiModel(value = "CourseDescription对象", description = "课程简介")
public class CourseDescription extends BasePojo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程简介")
    @TableField("description")
    private String description;


}
