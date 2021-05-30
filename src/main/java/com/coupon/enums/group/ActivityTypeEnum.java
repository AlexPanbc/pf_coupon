package com.coupon.enums.group;

public enum ActivityTypeEnum {

    OFFERED_NUM(1, "参团人数"),
    GOODS_NUM(2, "商品件数"),
    MONEY_AMOUNT(3, "总金额");

    private Integer status;
    private String desc;

    private ActivityTypeEnum(Integer status, String desc) {
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

