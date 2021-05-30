package com.coupon.enums.group;

public enum PurchasingTypeEnum {

    NO_PURCHASING(0, "不限购"),
    ALL_PURCHASING(1, "总限购"),
    DAY_PURCHASING(2, "每日限购"),
    ORDER_PURCHASING(3, "订单限购");

    private Integer status;
    private String desc;

    private PurchasingTypeEnum(Integer status, String desc) {
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

