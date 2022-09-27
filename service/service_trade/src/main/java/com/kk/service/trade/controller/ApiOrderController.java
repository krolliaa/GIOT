package com.kk.service.trade.controller;

import com.kk.common.result.ResultData;
import com.kk.common.util.JWTInfo;
import com.kk.common.util.JWTUtils;
import com.kk.service.trade.pojo.Order;
import com.kk.service.trade.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author kk
 * @since 2022-09-27
 */
@Api(description = "网站订单管理")
@Slf4j
@CrossOrigin //跨域
@RestController
@RequestMapping("/api/trade/order")
public class ApiOrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("新增订单")
    @PostMapping("auth/save/{courseId}")
    public ResultData save(@PathVariable String courseId, HttpServletRequest request) {
        JWTInfo jwtInfo = JWTUtils.getMember(request);
        if (jwtInfo == null) return ResultData.error().message("该操作只允许会员操作！请先登录！");
        //获取到 courseId ，据此创建订单，并且还要传递 member 会员的 id，否则不知道是哪个用户的订单
        //创建好后返回 orderId，前端根据该 orderId 跳转到订单详情页，显示订单详情
        String orderId = orderService.saveOrder(courseId, jwtInfo.getId());
        return ResultData.ok().message("获取订单成功！").data("orderId", orderId);
    }

    @ApiOperation("获取订单")
    @GetMapping("/auth/get/{orderId}")
    public ResultData get(@PathVariable String orderId, HttpServletRequest request) {
        JWTInfo jwtInfo = JWTUtils.getMember(request);
        Order order = orderService.getByOrderId(orderId, jwtInfo.getId());
        return ResultData.ok().data("item", order);
    }
}