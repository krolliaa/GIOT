package com.kk.service.edu.service.impl;

import com.kk.service.edu.pojo.Video;
import com.kk.service.edu.mapper.VideoMapper;
import com.kk.service.edu.pojo.vo.VideoReturnVo;
import com.kk.service.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
}
