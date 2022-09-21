package com.kk.service.edu.service;

import com.kk.service.edu.pojo.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kk.service.edu.pojo.vo.ChapterReturnVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
public interface ChapterService extends IService<Chapter> {
    public abstract Boolean removeChapterById(String id);
    public abstract List<ChapterReturnVo> chapterReturnVoList(String courseId);
}
