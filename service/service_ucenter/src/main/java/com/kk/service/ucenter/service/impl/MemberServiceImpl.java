package com.kk.service.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kk.common.result.ResultEnum;
import com.kk.common.util.FormUtils;
import com.kk.common.util.MD5Utils;
import com.kk.service.base.exception.GiotException;
import com.kk.service.ucenter.pojo.Member;
import com.kk.service.ucenter.mapper.MemberMapper;
import com.kk.service.ucenter.pojo.vo.RegisterVo;
import com.kk.service.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author kk
 * @since 2022-09-25
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void register(RegisterVo registerVo) {
        if (registerVo == null) throw new GiotException(ResultEnum.PARAM_ERROR);
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        //校验参数
        if (StringUtils.isEmpty(mobile)
                || !FormUtils.isMobile(mobile)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code)
                || StringUtils.isEmpty(nickname)) {
            throw new GiotException(ResultEnum.PARAM_ERROR);
        }
        //校验验证码
        String checkCode = (String) redisTemplate.opsForValue().get(mobile);
        if (!code.equals(checkCode)) {
            throw new GiotException(ResultEnum.CODE_ERROR);
        }
        //是否已经被注册 ---> 手机号是唯一的
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Integer count = Math.toIntExact(baseMapper.selectCount(queryWrapper));
        if (count > 0) throw new GiotException(ResultEnum.REGISTER_MOBILE_ERROR);
        //校验通过，注册用户
        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5Utils.encrypt(password));
        member.setDisabled(false);
        member.setAvatar("http://mms2.baidu.com/it/u=1387192414,1642993848&fm=253&app=138&f=JPEG&fmt=auto&q=75?w=200&h=200");
        baseMapper.insert(member);
    }
}
