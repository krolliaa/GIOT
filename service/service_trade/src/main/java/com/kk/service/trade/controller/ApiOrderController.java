package com.kk.service.trade.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.List;


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

    @ApiOperation(value = "新增订单")
    @PostMapping(value = "auth/save/{courseId}")
    public ResultData save(@PathVariable String courseId, HttpServletRequest request) {
        JWTInfo jwtInfo = JWTUtils.getMember(request);
        if (jwtInfo == null) return ResultData.error().message("该操作只允许会员操作！请先登录！");
        //获取到 courseId ，据此创建订单，并且还要传递 member 会员的 id，否则不知道是哪个用户的订单
        //创建好后返回 orderId，前端根据该 orderId 跳转到订单详情页，显示订单详情
        String orderId = orderService.saveOrder(courseId, jwtInfo.getId());
        return ResultData.ok().message("获取订单成功！").data("orderId", orderId);
    }

    @ApiOperation(value = "获取订单")
    @GetMapping(value = "/auth/get/{orderId}")
    public ResultData get(@PathVariable String orderId, HttpServletRequest request) {
        JWTInfo jwtInfo = JWTUtils.getMember(request);
        Order order = orderService.getByOrderId(orderId, jwtInfo.getId());
        return ResultData.ok().data("item", order);
    }

    @ApiOperation(value = "是否购买")
    @GetMapping(value = "/auth/is-buy/{courseId}")
    public ResultData isBuyByCourseId(@PathVariable(value = "courseId") String courseId, HttpServletRequest httpServletRequest) {
        JWTInfo jwtInfo = JWTUtils.getMember(httpServletRequest);
        //如果 Token 失效了则需要重新登陆：
        if (jwtInfo == null) return ResultData.error().message("登录已过期，请重新登录");
        Boolean isBuy = orderService.isBuyByOrderId(courseId, jwtInfo.getId());
        if (isBuy) return ResultData.ok().data("isBuy", isBuy).message("已购该课程，可直接观看噢");
        else return ResultData.error().message("提示：尚未购买该课程，还无法观看噢");
    }

    @ApiOperation(value = "订单列表")
    @GetMapping(value = "/auth/list")
    public ResultData list(HttpServletRequest httpServletRequest) {
        JWTInfo jwtInfo = JWTUtils.getMember(httpServletRequest);
        if (jwtInfo == null) return ResultData.error().message("登录已过期，请重新登录");
        List<Order> orderList = orderService.getListByMemberId(jwtInfo.getId());
        return ResultData.ok().data("items", orderList);
    }

    @ApiOperation(value = "删除订单")
    @DeleteMapping("auth/remove/{orderId}")
    public ResultData remove(@PathVariable String orderId, HttpServletRequest request) {
        JWTInfo jwtInfo = JWTUtils.getMember(request);
        if (jwtInfo == null) return ResultData.error().message("登录已过期，请重新登录");
        boolean result = orderService.removeById(orderId, jwtInfo.getId());
        if (result) return ResultData.ok().message("删除订单成功");
        else return ResultData.error().message("订单不存在");
    }
}