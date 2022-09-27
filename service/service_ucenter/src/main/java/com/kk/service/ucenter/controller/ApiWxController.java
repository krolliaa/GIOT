package com.kk.service.ucenter.controller;

import com.google.gson.Gson;
import com.kk.common.result.ResultEnum;
import com.kk.common.util.ExceptionUtils;
import com.kk.common.util.HttpClientUtils;
import com.kk.common.util.JWTInfo;
import com.kk.common.util.JWTUtils;
import com.kk.service.base.exception.GiotException;
import com.kk.service.ucenter.pojo.Member;
import com.kk.service.ucenter.service.MemberService;
import com.kk.service.ucenter.util.UCenterProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Api(description = "微信登录相关API")
@Controller
@RequestMapping(value = "/api/ucenter/wx")
@Slf4j
public class ApiWxController {
    @Autowired
    private UCenterProperties uCenterProperties;

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "生成登录的二维码")
    @GetMapping(value = "/login")
    public String generateQrConnect(HttpSession httpSession) {
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //将 redirectUri 编码为 URL 格式
        String redirectUri = "";
        try {
            redirectUri = URLEncoder.encode(uCenterProperties.getRedirectUri(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new GiotException(ResultEnum.URL_ENCODE_ERROR);
        }
        //给 state 设置随机数，放置 CSRF 攻击，并存入 session
        String state = UUID.randomUUID().toString();
        log.info("生成 state = " + state);
        httpSession.setAttribute("wx_open_state", state);
        String qrcodeUrl = String.format(baseUrl, uCenterProperties.getAppId(), redirectUri, state);
        return "redirect:" + qrcodeUrl;
    }

    @ApiOperation(value = "微信登录后的回调")
    @GetMapping(value = "/callback8160")
    public String callback(String code, String state, HttpSession httpSession) {
        //回调被拉起，并获得code和state参数
        System.out.println("callback被调用");
        System.out.println("code = " + code);
        System.out.println("state = " + state);
        if (StringUtils.isBlank(code) || StringUtils.isBlank(state)) {
            log.error("非法回调请求");
            throw new GiotException(ResultEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }
        //比对state即sessionID是否一致，不一致返回错误
        String sessionState = httpSession.getAttribute("wx_open_state").toString();
        if (!state.equals(sessionState)) {
            log.error("非法回调请求");
            throw new GiotException(ResultEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
        }
        //System.out.println(sessionState);
        //结合 code + state 生成 access_token
        //https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        //使用 HttpClients 工具往URL地址中存放参数
        Map<String, String> accessTokenParam = new HashMap<>();
        accessTokenParam.put("appid", uCenterProperties.getAppId());
        accessTokenParam.put("secret", uCenterProperties.getAppSecret());
        accessTokenParam.put("code", code);
        accessTokenParam.put("grant_type", "authorization_code");
        HttpClientUtils httpClientUtils = new HttpClientUtils(accessTokenUrl, accessTokenParam);
        //获取结果，官方文档的正确结果如下：
        /*
            {
                "access_token":"ACCESS_TOKEN",
                "expires_in":7200,
                "refresh_token":"REFRESH_TOKEN",
                "openid":"OPENID",
                "scope":"SCOPE",
                "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
            }
         */
        String result = "";
        try {
            httpClientUtils.get();
            result = httpClientUtils.getContent();
            System.out.println("result" + result);
        } catch (Exception e) {
            log.error("获取access_token失败");
            throw new GiotException(ResultEnum.FETCH_ACCESSTOKEN_FAILD);
        }
        //使用 Gson 将获取到的结果进行一个封装
        Gson gson = new Gson();
        HashMap<String, Object> hashMap = gson.fromJson(result, HashMap.class);
        //若出现 {"errcode":40029,"errmsg":"invalid code"} 则代表响应错误
        Object errcodeObj = hashMap.get("errcode");
        if (errcodeObj != null) {
            //响应错误
            String errmsg = hashMap.get("errmsg").toString();
            Double errcode = (Double) errcodeObj;
            log.error("获取access_token失败 - " + "message: " + errmsg + ", errcode: " + errcode);
            throw new GiotException(ResultEnum.FETCH_ACCESSTOKEN_FAILD);
        }
        //正常，返回 access_token
        /*
            access_token	接口调用凭证
            openid	授权用户唯一标识
         */
        String accessToken = hashMap.get("access_token").toString();
        String openId = hashMap.get("openid").toString();
        log.info("accessToken = " + accessToken);
        log.info("openid = " + openId);
        //获取用户信息，若第一次登录则视为注册，注册完毕后返回 JWTInfo 信息
        //微信登录中可以通过调用：https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID获取个人信息
        //存储时直接根据微信个肉疼信息获取数据即可，返回格式如下：
        /*
            {
                "openid":"OPENID",
                "nickname":"NICKNAME",
                "sex":1,
                "province":"PROVINCE",
                "city":"CITY",
                "country":"COUNTRY",
                "headimgurl": "https://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
                "privilege":[
                    "PRIVILEGE1",
                    "PRIVILEGE2"
                ],
                "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
            }
         */
        Member member = memberService.getByOpenid(openId);
        JWTInfo jwtInfo = new JWTInfo();
        if (member == null) {
            String baseURL = "https://api.weixin.qq.com/sns/userinfo";
            HashMap<String, String> baseURLParamsHashMap = new HashMap<>();
            baseURLParamsHashMap.put("access_token", accessToken);
            baseURLParamsHashMap.put("openid", openId);
            httpClientUtils = new HttpClientUtils(baseURL, baseURLParamsHashMap);
            //发送获取微信用户个人信息
            String resultUserInfo = "";
            try {
                httpClientUtils.get();
                resultUserInfo = httpClientUtils.getContent();
            } catch (Exception e) {
                log.error(ExceptionUtils.getMessage(e));
                throw new GiotException(ResultEnum.FETCH_USERINFO_ERROR);
            }
            System.out.println(resultUserInfo);
            // {"errcode":40003,"errmsg":"invalid openid"}
            HashMap<String, Object> resultUserInfoHashMap = gson.fromJson(resultUserInfo, HashMap.class);
            System.out.println(resultUserInfoHashMap);
            Object errCodeUserInfoObj = resultUserInfoHashMap.get("errcode");
            if (errCodeUserInfoObj != null) {
                log.error("获取用户信息失败" + "，message：" + resultUserInfoHashMap.get("errmsg"));
                throw new GiotException(ResultEnum.FETCH_USERINFO_ERROR);
            }
            member = new Member();
            member.setOpenid(openId);
            member.setNickname(resultUserInfoHashMap.get("nickname").toString());
            member.setAvatar(resultUserInfoHashMap.get("headimgurl").toString());
            Double sex = (Double) resultUserInfoHashMap.get("sex");
            member.setSex(sex.intValue());
            //存储menmber
            memberService.save(member);
        }
        jwtInfo.setId(member.getId());
        jwtInfo.setNickname(member.getNickname());
        jwtInfo.setAvatar(member.getAvatar());
        String JWTToken = JWTUtils.generateJWT(jwtInfo, 1800);
        return "redirect:http://localhost:3333?token=" + JWTToken;
    }
}
