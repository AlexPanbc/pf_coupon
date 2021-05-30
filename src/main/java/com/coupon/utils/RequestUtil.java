package com.coupon.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuelvhui.util.date.CalendarUtil;
import com.yuelvhui.util.http.HttpRequest;
import com.yuelvhui.util.http.HttpResponse;
import com.yuelvhui.util.http.Status;
import com.yuelvhui.util.safety.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class RequestUtil {

    private static final Logger logger = LoggerFactory.getLogger(RequestUtil.class);


    public static <T> T postJsonObjectRequest(String url, String json, String signStr, Class<T> clazz) {
        HttpResponse response = HttpRequest.post(url)
                .header(Constants.HTTP_HEADER_AUTHORIZATION, signStr)
                .charset(Constants.ENCODING_UTF8)
                .contentType(Constants.CONTENT_TYPE_JSON)
                .body(json)
                .execute();
        if (response.getStatus() == Status.HTTP_OK) {
            return JSON.parseObject(new String(response.bodyBytes()), clazz);
        }
        return null;
    }

    public static String postJsonObjectRequest(String url, String json, String signStr) {
        HttpResponse response = HttpRequest.post(url)
                .header(Constants.HTTP_HEADER_AUTHORIZATION, signStr)
                .charset(Constants.ENCODING_UTF8)
                .contentType(Constants.CONTENT_TYPE_JSON)
                .body(json)
                .execute();
        return new String(response.bodyBytes());
    }

    public static String postJsonStringRequest(String url, String json) {
        logger.info("url="+url);
        logger.info("json="+json);
        HttpResponse response = HttpRequest.post(url)
                .charset(Constants.ENCODING_UTF8)
                .contentType(Constants.CONTENT_TYPE_JSON)
                .body(json)
                .execute();
        logger.info("response.body = "+response.body());
        logger.info("response = "+response.toString());
        if (response.getStatus() == Status.HTTP_OK) {
            return new String(response.bodyBytes());
        }
        return null;
    }

    public static String postJsonObjectRequest(String url, Map<String,Object> map) {
        HttpResponse response = HttpRequest.post(url)
                .charset(Constants.ENCODING_UTF8)
                .contentType(Constants.CONTENT_TYPE_FORM)
                .form(map)
                .execute();
        if (response.getStatus() == Status.HTTP_OK) {
            String result = new String(response.bodyBytes());
            JSONObject jsonResult = JSON.parseObject(result);
            if((Integer)jsonResult.get("code") == 200){
                return JSON.toJSONString(jsonResult.get("data"));
            }
        }
        return null;
    }

    public static JSONObject postJsonObject(String url, Map<String,Object> map) {
        HttpResponse response = HttpRequest.post(url)
                .charset(Constants.ENCODING_UTF8)
                .contentType(Constants.CONTENT_TYPE_FORM)
                .form(map)
                .execute();
        if (response.getStatus() == Status.HTTP_OK) {
            String result = new String(response.bodyBytes());
            JSONObject jsonResult = JSON.parseObject(result);
            return jsonResult;
        }
        return null;
    }



}
