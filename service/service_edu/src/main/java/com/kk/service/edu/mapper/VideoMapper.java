package com.kk.service.edu.mapper;

import com.kk.service.edu.pojo.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kk.service.edu.pojo.vo.VideoReturnVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {
    @Select("select id, title, sort, is_free as free, video_source_id from edu_video where chapter_id = #{chapterId} order by sort, id asc")
    public abstract List<VideoReturnVo> videoReturnVoList(String chapterId);
}
