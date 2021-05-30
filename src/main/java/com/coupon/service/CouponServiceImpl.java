package com.coupon.service;

import com.coupon.service.group.GroupActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 优惠券表 服务实现类
 * </p>
 *
 * @author lcx
 * @since 2021-01-11
 */
@Service("CouponService")
public class CouponServiceImpl implements CouponService {
    private final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);
    @Autowired
    private GroupActivityService groupActivityService;

    @Override
    public void get() {
        groupActivityService.getaaa();
        groupActivityService.getg();
    }
}
