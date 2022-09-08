package com.kk.service.edu.mapper;

import com.kk.service.edu.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 评论 Mapper 接口
 * </p>
 *
 * @author kk
 * @since 2022-09-09
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
