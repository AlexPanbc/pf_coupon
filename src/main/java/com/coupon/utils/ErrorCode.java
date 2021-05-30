package com.coupon.utils;

public enum ErrorCode {

    ParamIsFailure("10001001", "参数校验失败"),
    ParamIsNotEmpty("10001002", "参数不能为空"),
    ParamFormatNotCorrect("10001003", "参数格式不正确"),
    ParamNotWithinRequireRange("10001004", "参数不在合法区间范围内"),
    CreateNewInstanceFailed("10001005", "创建实例失败"),
    TokenIsNotEmpty("10008001", "登录身份不能为空"),
    TokenVerificationFailure("10008002", "登录身份验证失败"),
    ServiceException("10009001", "系统异常"),
    SystemBusy("10009002", "系统繁忙,请稍后再试"),

    DataIsNotEmpty("10002001", "数据不能为空"),
    DataRemoteCallFailure("10002002", "调用远程接口获取数据失败"),
    DataSignIsFailure("10002003", "参数签名验证失败"),
    DataFailure("10002004", "数据无效"),
    DataStatusFailure("10002005", "业务状态无效"),
    DataRepetition("10002006", "数据重复提交"),

    NotFoundData("13010001", "未找到指定的数据"),
    AddDataFail("13010002", "添加失败"),
    UpdateDataFail("13010003", "修改失败"),
    DeleteDataFail("13010004", "删除失败"),
    SaveDataFail("13010005", "保存失败"),
    AuditFail("13010005", "审核失败"),
    OperationFail("13010005", "操作失败"),

    CouponBatchIsOverdue("16000001", "批次已过期"),
    CouponIsOverdue("16000002", "优惠券已过期");


    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
