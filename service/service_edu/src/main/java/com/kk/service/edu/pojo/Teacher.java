package com.kk.service.edu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kk.service.base.model.BasePojo;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 讲师
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("edu_teacher")
@ApiModel(value = "Teacher对象", description = "讲师")
public class Teacher extends BasePojo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("讲师姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("讲师简介")
    @TableField("intro")
    private String intro;

    @ApiModelProperty("讲师资历,一句话说明讲师")
    @TableField("career")
    private String career;

    @ApiModelProperty("头衔 1高级讲师 2首席讲师")
    @TableField("level")
    private Integer level;

    @ApiModelProperty("讲师头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("入驻时间")
    @TableField("join_date")
    private Date joinDate;

    @ApiModelProperty("逻辑删除 1（true）已删除， 0（false）未删除")
    @TableField("is_deleted")
    @TableLogic
    private Integer deleted;


}
