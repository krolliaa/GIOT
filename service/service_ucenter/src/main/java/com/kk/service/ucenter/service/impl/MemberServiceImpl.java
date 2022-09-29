package com.kk.service.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.kk.common.result.ResultEnum;
import com.kk.common.util.FormUtils;
import com.kk.common.util.JWTInfo;
import com.kk.common.util.JWTUtils;
import com.kk.common.util.MD5Utils;
import com.kk.service.base.exception.GiotException;
import com.kk.service.ucenter.pojo.Member;
import com.kk.service.ucenter.mapper.MemberMapper;
import com.kk.service.ucenter.pojo.dto.MemberDto;
import com.kk.service.ucenter.pojo.vo.LoginVo;
import com.kk.service.ucenter.pojo.vo.RegisterVo;
import com.kk.service.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
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

    @Override
    public String login(LoginVo loginVo) {
        if (loginVo == null) throw new GiotException(ResultEnum.LOGIN_ERROR);
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password))
            throw new GiotException(ResultEnum.LOGIN_ERROR);
        //比对数据库
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        queryWrapper.eq("password", MD5Utils.encrypt(password));
        Member member = baseMapper.selectOne(queryWrapper);
        if (member == null) throw new GiotException(ResultEnum.LOGIN_ERROR);
        //是否被禁用，若禁用告知用户
        if (member.getDisabled()) throw new GiotException(ResultEnum.LOGIN_DISABLED_ERROR);
        //验证通过，返回 JWT 字符串，过期时间设定为 30min
        String JWT = JWTUtils.generateJWT(new JWTInfo(member.getId(), member.getNickname(), member.getAvatar()), 1800);
        return JWT;
    }

    @Override
    public Member getByOpenid(String openid) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public MemberDto getMemberDtoByMemberId(String id) {
        Member member = baseMapper.selectById(id);
        MemberDto memberDto = new MemberDto();
        BeanUtils.copyProperties(member, memberDto);
        return memberDto;
    }

    @Override
    public Long countRegisterNum(String day) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("gmt_create", day);
        return baseMapper.selectCount(queryWrapper);
    }
}
