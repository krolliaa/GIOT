package com.kk.service.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class BasePojo {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("创建时间")
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
