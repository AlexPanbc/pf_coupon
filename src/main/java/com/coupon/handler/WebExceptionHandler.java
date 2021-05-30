package com.coupon.handler;

import com.google.common.collect.Lists;
import com.yuelvhui.util.exception.AuthException;
import com.yuelvhui.util.exception.ExceptionField;
import com.yuelvhui.util.exception.ExceptionResponse;
import com.yuelvhui.util.exception.ServiceException;
import com.coupon.utils.ErrorCode;
import com.coupon.utils.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * 异常拦截器，以JSON格式输出
 */
@ControllerAdvice
public class WebExceptionHandler extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    private HttpHeaders createHttpHeadersInstance() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

    /**
     * 业务异常拦截器
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    ResponseEntity<ResultMessage> serviceExceptionHandler(HttpServletRequest request, HttpServletResponse response, ServiceException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex);
        ResultMessage resultMessage = new ResultMessage(ex);
        return new ResponseEntity<>(resultMessage, createHttpHeadersInstance(), HttpStatus.OK);
    }

    /**
     * 身份认证错误拦截器
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(AuthException.class)
    ResponseEntity<ExceptionResponse> authExceptionHandler(HttpServletRequest request, HttpServletResponse response, AuthException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ex);
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        if (ErrorCode.TokenIsNotEmpty.getCode().equals(ex.getErrorCode())) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(exceptionResponse, createHttpHeadersInstance(), httpStatus);
    }

    /**
     * http请求method不受支持
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    ResponseEntity<ExceptionResponse> httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, HttpServletResponse response, HttpRequestMethodNotSupportedException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCode.ServiceException.getCode(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, createHttpHeadersInstance(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 不支持的MediaType
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    ResponseEntity<ExceptionResponse> httpMediaTypeNotSupportedExceptionHandler(HttpServletRequest request, HttpServletResponse response, HttpMediaTypeNotSupportedException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCode.ServiceException.getCode(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, createHttpHeadersInstance(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 不可接受的传入参数
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageConversionException.class)
    ResponseEntity<ExceptionResponse> httpMessageConversionExceptionHandler(HttpServletRequest request, HttpServletResponse response, HttpMessageConversionException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCode.ServiceException.getCode(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, createHttpHeadersInstance(), HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * 验证器执行错误拦截器
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ExceptionResponse> validationExceptionHandler(HttpServletRequest request, HttpServletResponse response, ValidationException ex) {
        logger.error(ex.getMessage(), ex);

        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCode.ParamIsFailure.getCode(), ex.getMessage());
        return new ResponseEntity<>(exceptionResponse, createHttpHeadersInstance(), HttpStatus.BAD_REQUEST);
    }

    private List<ExceptionField> buildExceptionFields(HttpServletRequest request, List<FieldError> fieldErrors) {
        List<ExceptionField> exceptionFields = Lists.newArrayList();
        for (FieldError error : fieldErrors) {
            String fieldName = error.getField();
            Object fieldValue = error.getRejectedValue();
            String objectName = error.getObjectName();
            String message = error.getDefaultMessage();
            String location;
            if (null != request.getHeader(fieldName)) {
                location = "header";
            } else {
                location = "body";
            }

            ExceptionField exceptionField = new ExceptionField(objectName, "invalidParameter", fieldName, "parameter", error.getDefaultMessage());

            exceptionFields.add(exceptionField);
        }
        return exceptionFields;
    }

    /**
     * 验证器验证错误拦截器
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    ResponseEntity<ExceptionResponse> bindExceptionHandler(HttpServletRequest request, HttpServletResponse response, BindException ex) {
        List<ExceptionField> exceptionFields = buildExceptionFields(request, ex.getBindingResult().getFieldErrors());
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCode.ParamIsFailure.getCode(), "Param is errors", null, exceptionFields);
        return new ResponseEntity<>(exceptionResponse, createHttpHeadersInstance(), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ExceptionResponse> methodArgumentNotValidExceptionHandler(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException ex) {
        List<ExceptionField> exceptionFields = buildExceptionFields(request, ex.getBindingResult().getFieldErrors());
        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCode.ParamIsFailure.getCode(), "Param is errors", null, exceptionFields);
        return new ResponseEntity<>(exceptionResponse, createHttpHeadersInstance(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 未知错误拦截器
     *
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    ResponseEntity<ExceptionResponse> unknownExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        logger.error(ex.getMessage(), ex);

        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCode.ServiceException.getCode(), "Service is errors");
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(exceptionResponse, createHttpHeadersInstance(), httpStatus);
    }
}
