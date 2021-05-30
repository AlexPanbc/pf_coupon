package com.coupon.enums.group;

public enum HeadEnum {

    GIRTH(1, "团长"),
    NO_GIRTH(2, "会员");

    private Integer status;
    private String desc;

    private HeadEnum(Integer status, String desc) {
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

