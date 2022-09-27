package com.kk.service.trade.mapper;

import com.kk.service.trade.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author kk
 * @since 2022-09-27
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
