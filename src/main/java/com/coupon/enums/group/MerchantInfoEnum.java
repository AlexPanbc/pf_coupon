package com.coupon.enums.group;

public enum MerchantInfoEnum {

    HLHW(264l, "好邻好物"),
    YUETAO(256l, "悦淘"),
    YCLOUD(4l, "YCloud");

    private long mchId;
    private String mchName;

    private MerchantInfoEnum(long mchId, String mchName) {
        this.mchId = mchId;
        this.mchName = mchName;
    }

    public long getMchId() {
        return mchId;
    }

    public void setMchId(long mchId) {
        this.mchId = mchId;
    }

    public String getMchName() {
        return mchName;
    }

    public void setMchName(String mchName) {
        this.mchName = mchName;
    }

    public static String getValue(long mchId) {
        for (MerchantInfoEnum ele : values()) {
            if (ele.getMchId() == mchId){
                return ele.getMchName();
            }
        }
        return "未知渠道";
    }
}
