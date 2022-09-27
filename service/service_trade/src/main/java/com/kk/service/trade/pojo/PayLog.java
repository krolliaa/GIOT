package com.kk.service.trade.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.kk.service.base.model.BasePojo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 支付日志表
 * </p>
 *
 * @author kk
 * @since 2022-09-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("trade_pay_log")
@ApiModel(value = "PayLog对象", description = "支付日志表")
public class PayLog extends BasePojo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单号")
    @TableField("order_no")
    private String orderNo;

    @ApiModelProperty("支付完成时间")
    @TableField("pay_time")
    private Date payTime;

    @ApiModelProperty("支付金额（分）")
    @TableField("total_fee")
    private Long totalFee;

    @ApiModelProperty("交易流水号")
    @TableField("transaction_id")
    private String transactionId;

    @ApiModelProperty("交易状态")
    @TableField("trade_state")
    private String tradeState;

    @ApiModelProperty("支付类型（1：微信 2：支付宝）")
    @TableField("pay_type")
    private Integer payType;

    @ApiModelProperty("其他属性")
    @TableField("attr")
    private String attr;

    @ApiModelProperty("逻辑删除 1（true）已删除， 0（false）未删除")
    @TableField("is_deleted")
    @TableLogic
    private Integer deleted;
}
