package com.kk.service.edu.service.impl;

import com.kk.service.edu.pojo.Comment;
import com.kk.service.edu.mapper.CommentMapper;
import com.kk.service.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
