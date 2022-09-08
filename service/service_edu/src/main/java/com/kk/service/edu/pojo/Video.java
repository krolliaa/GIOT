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
 * 课程视频
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("edu_video")
@ApiModel(value = "Video对象", description = "课程视频")
public class Video extends BasePojo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("课程ID")
    @TableField("course_id")
    private String courseId;

    @ApiModelProperty("章节ID")
    @TableField("chapter_id")
    private String chapterId;

    @ApiModelProperty("节点名称")
    @TableField("title")
    private String title;

    @ApiModelProperty("云端视频资源")
    @TableField("video_source_id")
    private String videoSourceId;

    @ApiModelProperty("原始文件名称")
    @TableField("video_original_name")
    private String videoOriginalName;

    @ApiModelProperty("排序字段")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("播放次数")
    @TableField("play_count")
    private Long playCount;

    @ApiModelProperty("是否可以试听：0收费 1免费")
    @TableField("is_free")
    private Integer isFree;

    @ApiModelProperty("视频时长（秒）")
    @TableField("duration")
    private Float duration;

    @ApiModelProperty("状态")
    @TableField("status")
    private String status;

    @ApiModelProperty("视频源文件大小（字节）")
    @TableField("size")
    private Long size;

    @ApiModelProperty("乐观锁")
    @TableField("version")
    private Long version;


}
