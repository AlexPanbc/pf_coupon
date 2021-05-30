package com.coupon.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

/**
 * @program: pf_coupon
 * @description:
 * @author: Mr.Wang
 * @create: 2019-12-19 21:52
 **/
@Component
public class MyMetaObjectHandle implements MetaObjectHandler{

    @Override
    public void insertFill(MetaObject metaObject) {
        //这里需要写实体类的属性值
        boolean bool=metaObject.hasSetter("createTime");
        Object obj=getFieldValByName("createTime",metaObject);
        if(!ObjectUtils.isEmpty(obj)&&bool){
            setFieldValByName("createTime", LocalDateTime.now(),metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
