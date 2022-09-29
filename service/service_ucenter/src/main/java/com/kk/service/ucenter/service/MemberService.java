package com.kk.service.ucenter.service;

import com.kk.service.ucenter.pojo.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kk.service.ucenter.pojo.dto.MemberDto;
import com.kk.service.ucenter.pojo.vo.LoginVo;
import com.kk.service.ucenter.pojo.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author kk
 * @since 2022-09-25
 */
public interface MemberService extends IService<Member> {
    public abstract void register(RegisterVo registerVo);
    public abstract String login(LoginVo loginVo);
    public abstract Member getByOpenid(String openid);
    public abstract MemberDto getMemberDtoByMemberId(String id);
    public abstract Long countRegisterNum(String day);
}
