package com.kk.service.cms.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.kk.service.base.model.BasePojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 广告位置
 * </p>
 *
 * @author kk
 * @since 2022-09-23
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("cms_ad_position")
@ApiModel(value = "AdPosition对象", description = "广告位置")
public class AdPosition extends BasePojo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("广告位置")
    @TableField("position")
    private String position;
}
