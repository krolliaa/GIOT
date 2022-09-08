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
 * 评论
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("edu_comment")
@ApiModel(value = "Comment对象", description = "评论")
public class Comment extends BasePojo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程id")
    @TableField("course_id")
    private String courseId;

    @ApiModelProperty("讲师id")
    @TableField("teacher_id")
    private String teacherId;

    @ApiModelProperty("会员id")
    @TableField("member_id")
    private String memberId;

    @ApiModelProperty("会员昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("会员头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("评论内容")
    @TableField("content")
    private String content;


}
