package com.nynu.goule.pojo;

import java.util.Date;

/**
 * @author  goule
 * @date  2021/4/1 7:58
 * 订单实体类
 */
public class Order {

    private Integer id;
    private String orderId;
    private String createTime;
    private String price;
    private String isReady;  //商品状态：1：备货中，2：已出库，3：待发货，4：已发货
    private String orderFlow;  //订单流程 1：订货单，2：退货单
    private String orderState;  //订单状态：1：待出库审核，2：快递在路上，3：待收货确认
    private String payment;  //付款状态：1：未收款，2：已付款，3：未退款，4：已退款

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIsReady() {
        return isReady;
    }

    public void setIsReady(String isReady) {
        this.isReady = isReady;
    }

    public String getOrderFlow() {
        return orderFlow;
    }

    public void setOrderFlow(String orderFlow) {
        this.orderFlow = orderFlow;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
