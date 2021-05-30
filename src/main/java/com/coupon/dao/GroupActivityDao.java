package com.coupon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coupon.entity.group.GroupActivityEntity;

import javax.annotation.Resource;

@Resource
public interface GroupActivityDao  extends BaseMapper<GroupActivityEntity> {
    GroupActivityEntity getGroup();
}
