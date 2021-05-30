package com.coupon.enums.group;

public enum ActivityStatusEnum {

    NO_OPEN(1, "未开启"),
    YES_OPEN(2, "已开启"),
    FORBIDDEN(3, "已禁用"),
    LOSE(4, "已失效");

    private Integer status;
    private String desc;

    private ActivityStatusEnum(Integer status, String desc) {
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

