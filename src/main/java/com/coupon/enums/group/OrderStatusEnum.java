package com.coupon.enums.group;

public enum OrderStatusEnum {

    NO_PAY(1, "待支付"),
    YES_PAY(2, "已支付"),
    CANCLE_PAY(3, "已取消"),
    RETURN_PAY(4, "已退单"),
    PORTION_PAY(5, "部分退单");

    private Integer status;
    private String desc;

    private OrderStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

