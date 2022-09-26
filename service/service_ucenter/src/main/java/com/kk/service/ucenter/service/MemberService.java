package com.kk.service.ucenter.service;

import com.kk.service.ucenter.pojo.Member;
import com.baomidou.mybatisplus.extension.service.IService;
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
}
