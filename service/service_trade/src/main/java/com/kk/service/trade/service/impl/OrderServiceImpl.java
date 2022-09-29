package com.kk.service.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kk.common.result.ResultEnum;
import com.kk.feign_api.client.EduClient;
import com.kk.feign_api.client.UCenterClient;
import com.kk.feign_api.dto.CourseDto;
import com.kk.feign_api.dto.MemberDto;
import com.kk.service.base.exception.GiotException;
import com.kk.service.trade.pojo.Order;
import com.kk.service.trade.mapper.OrderMapper;
import com.kk.service.trade.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.service.trade.util.OrderNoUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author kk
 * @since 2022-09-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UCenterClient uCenterClient;

    @Override
    public String saveOrder(String courseId, String memberId) {
        //首先判断订单是否存在，如果已经存在了，则直接返回订单 ID
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", memberId);
        Order order = baseMapper.selectOne(queryWrapper);
        if (order != null) return order.getId();
        //根据 courseId 获取订单中的课程信息
        CourseDto courseDto = eduClient.getCourseDtoByCourseId(courseId);
        if (courseDto == null) throw new GiotException(ResultEnum.PARAM_ERROR);
        //根据 memberId 获取订单中的会员信息
        MemberDto memberDto = uCenterClient.getMemberDtoByMemberId(memberId);
        if (memberDto == null) throw new GiotException(ResultEnum.PARAM_ERROR);
        //创建订单
        order = new Order();
        order.setOrderNo(OrderNoUtils.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName(courseDto.getTeacherName());
        order.setTotalFee(courseDto.getPrice().multiply(new BigDecimal(100)));//分
        order.setMemberId(memberId);
        order.setMobile(memberDto.getMobile());
        order.setNickname(memberDto.getNickname());
        order.setStatus(0);//未支付
        order.setPayType(1);//微信支付
        int insert = baseMapper.insert(order);
        if (insert == 0) throw new GiotException(ResultEnum.ORDER_CREATE_ERROR);
        return order.getId();
    }

    @Override
    public Order getByOrderId(String orderId, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        queryWrapper.eq("member_id", memberId);
        Order order = baseMapper.selectOne(queryWrapper);
        return order;
    }

    @Override
    public Boolean isBuyByOrderId(String courseId, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", memberId);
        queryWrapper.eq("status", 1);
        Long aLong = baseMapper.selectCount(queryWrapper);
        return (aLong > 0);
    }

    @Override
    public List<Order> getListByMemberId(String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("member_id", memberId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public boolean removeById(String orderId, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId).eq("member_id", memberId);
        return this.remove(queryWrapper);
    }
}
