package com.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coupon.entity.CouponEntity;

import javax.annotation.Resource;

@Resource
public interface CouponDao  extends BaseMapper<CouponEntity> {
    CouponEntity getaaa();
}
