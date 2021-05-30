package com.coupon.utils;

import com.esotericsoftware.reflectasm.ConstructorAccess;
import com.google.common.collect.Lists;
import com.yuelvhui.util.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: pf_coupon
 * @description: CopyBeanUtils
 * @author: Mr.Wang
 * @create: 2019-10-08 20:57
 **/
public class WrappedBeanCopier {

    private static final Logger logger = LoggerFactory.getLogger(WrappedBeanCopier.class);
    //多线程场景推荐使用 ConcurrentHashMap
    private static final Map<String, BeanCopier> beanCopierCache = new ConcurrentHashMap<>();
    private static final Map<String, ConstructorAccess> constructorAccessCache = new ConcurrentHashMap<>();

    private static void copyProperties(Object source, Object target) {
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    private static BeanCopier getBeanCopier(Class sourceClass, Class targetClass) {
        String beanKey = generateKey(sourceClass, targetClass);
        BeanCopier copier = null;
        if (!beanCopierCache.containsKey(beanKey)) {
            copier = BeanCopier.create(sourceClass, targetClass, false);
            beanCopierCache.put(beanKey, copier);
        } else {
            copier = beanCopierCache.get(beanKey);
        }
        return copier;
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    /**
     * Copy Bean
     *
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source, Class<T> targetClass) {
        T t = null;
        try {
            t = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Create new instance of {} failed: {}", targetClass, e.getMessage());
            throw new ServiceException(ErrorCode.CreateNewInstanceFailed.getCode(), ErrorCode.CreateNewInstanceFailed.getMessage());
        }
        copyProperties(source, t);
        return t;
    }

    /**
     * Copy集合
     *
     * @param sourceList
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> List<T> copyPropertiesOfList(List<?> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        ConstructorAccess<T> constructorAccess = getConstructorAccess(targetClass);
        List<T> resultList = Lists.newArrayList();
        for (Object o : sourceList) {
            T t = null;
            try {
                t = constructorAccess.newInstance();
                copyProperties(o, t);
                resultList.add(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resultList;
    }

    private static <T> ConstructorAccess<T> getConstructorAccess(Class<T> targetClass) {
        ConstructorAccess<T> constructorAccess = constructorAccessCache.get(targetClass.toString());
        if (constructorAccess != null) {
            return constructorAccess;
        }
        try {
            constructorAccess = ConstructorAccess.get(targetClass);
            constructorAccess.newInstance();
            constructorAccessCache.put(targetClass.toString(), constructorAccess);
        } catch (Exception e) {
            logger.error("Create new instance of {} failed: {}", targetClass, e.getMessage());
            throw new ServiceException(ErrorCode.CreateNewInstanceFailed.getCode(), ErrorCode.CreateNewInstanceFailed.getMessage());
        }
        return constructorAccess;
    }

}
