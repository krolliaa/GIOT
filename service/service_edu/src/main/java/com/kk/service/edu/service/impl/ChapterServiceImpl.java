package com.kk.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kk.service.edu.mapper.VideoMapper;
import com.kk.service.edu.pojo.Chapter;
import com.kk.service.edu.mapper.ChapterMapper;
import com.kk.service.edu.pojo.vo.ChapterReturnVo;
import com.kk.service.edu.pojo.vo.VideoReturnVo;
import com.kk.service.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.service.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean removeChapterById(String id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("chapter_id", id);
        videoMapper.delete(queryWrapper);
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<ChapterReturnVo> chapterReturnVoList(String courseId) {
        List<ChapterReturnVo> chapterReturnVoList = chapterMapper.chapterReturnVoList(courseId);
        for (ChapterReturnVo chapterReturnVo : chapterReturnVoList) {
            String chapterId = chapterReturnVo.getId();
            List<VideoReturnVo> videoReturnVoList = videoMapper.videoReturnVoList(chapterId);
            chapterReturnVo.setChildren(videoReturnVoList);
        }
        return chapterReturnVoList;
    }
}
