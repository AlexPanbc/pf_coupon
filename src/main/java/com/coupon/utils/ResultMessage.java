package com.coupon.utils;

import com.yuelvhui.util.exception.BaseException;
import com.yuelvhui.util.exception.ExceptionField;
import com.yuelvhui.util.exception.ExceptionResponse;

import java.util.List;

public class ResultMessage extends ExceptionResponse {

    public String resultCode;

    public String resultMessage;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public ResultMessage(String code, String message) {
        super("SUCCESS", "Requested successful");
        setResultCode(code);
        setResultMessage(message);
    }

    public ResultMessage() {
        super("SUCCESS", "Requested successful");
        setResultCode("SUCCESS");
        setResultMessage("Operation is successful");
    }

    public ResultMessage(Object response) {
        super("SUCCESS", "Requested successful", response);
        setResultCode("SUCCESS");
        setResultMessage("Operation is successful");
    }

    public ResultMessage(String resultMessage) {
        super("SUCCESS", "Requested successful");
        setResultCode("FAIL");
        setResultMessage(resultMessage);
    }

    public ResultMessage(String code, String message, Object response) {
        super("SUCCESS", "Requested successful", response);
        setResultCode(code);
        setResultMessage(message);
    }

    public ResultMessage(String code, String message, Object response, List<ExceptionField> errors) {
        super("SUCCESS", "Requested successful", response, errors);
    }

    public ResultMessage(BaseException ex) {
        super("SUCCESS", "Requested successful", null, ex.getErrors());
        setResultCode(ex.getErrorCode());
        setResultMessage(ex.getMessage());
    }

}
