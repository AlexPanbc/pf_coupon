package com.coupon.utils;

import com.google.common.collect.Maps;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

/**
 * @program: pf_coupon
 * @description: Json工具类
 * @author: Mr.Wang
 * @create: 2019-10-14 17:02
 **/
public class BeanUtil {

    public static Map<String, String> beanToMap(Object bean) throws Exception {
        Map<String, String> map = Maps.newHashMap();
        BeanInfo info = Introspector.getBeanInfo(bean.getClass(), Object.class);
        PropertyDescriptor[] pds = info.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            String key = pd.getName();
            String value = String.valueOf(pd.getReadMethod().invoke(bean));
            map.put(key, value);
        }
        return map;
    }

}
