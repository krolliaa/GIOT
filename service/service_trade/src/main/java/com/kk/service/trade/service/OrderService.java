package com.kk.service.trade.service;

import com.kk.service.trade.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author kk
 * @since 2022-09-27
 */
public interface OrderService extends IService<Order> {
    public abstract String saveOrder(String courseId, String memberId);
    public abstract Order getByOrderId(String orderId, String memberId);
    public abstract Boolean isBuyByOrderId(String courseId, String memberId);
    public abstract List<Order> getListByMemberId(String memberId);
    public abstract boolean removeById(String orderId, String memberId);
}
