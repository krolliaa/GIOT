package com.kk.service.statistic.pojo;

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
 * 网站统计日数据
 * </p>
 *
 * @author kk
 * @since 2022-09-30
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("statistic_daily")
@ApiModel(value = "Daily对象", description = "网站统计日数据")
public class Daily extends BasePojo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("统计日期")
    @TableField("date_calculated")
    private String dateCalculated;

    @ApiModelProperty("注册人数")
    @TableField("register_num")
    private Integer registerNum;

    @ApiModelProperty("登录人数")
    @TableField("login_num")
    private Integer loginNum;

    @ApiModelProperty("每日播放视频数")
    @TableField("video_view_num")
    private Integer videoViewNum;

    @ApiModelProperty("每日新增课程数")
    @TableField("course_num")
    private Integer courseNum;
}
