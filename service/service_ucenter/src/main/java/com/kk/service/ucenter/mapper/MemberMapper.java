package com.kk.service.ucenter.mapper;

import com.kk.service.ucenter.pojo.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author kk
 * @since 2022-09-25
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

}
