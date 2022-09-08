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
 * 课程收藏
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("edu_course_collect")
@ApiModel(value = "CourseCollect对象", description = "课程收藏")
public class CourseCollect extends BasePojo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程讲师ID")
    @TableField("course_id")
    private String courseId;

    @ApiModelProperty("课程专业ID")
    @TableField("member_id")
    private String memberId;


}
