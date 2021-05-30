package com.coupon.enums.group;

public enum OpenStatusEnum {

    NO_OPEN(1, "待开团"),
    YES_OPEN(2, "已开团"),
    FORBIDDEN(3, "拼团成功"),
    LOSE(4, "拼团失败");

    private Integer status;
    private String desc;

    private OpenStatusEnum(Integer status, String desc) {
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

