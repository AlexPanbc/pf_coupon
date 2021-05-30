package com.coupon.service.group.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.coupon.dao.CouponDao;
import com.coupon.dao.GroupActivityDao;
import com.coupon.datasources.DataSourceNames;
import com.coupon.datasources.annotation.DataSource;
import com.coupon.entity.CouponEntity;
import com.coupon.entity.group.GroupActivityEntity;
import com.coupon.service.group.GroupActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 拼团活动 服务实现类
 * </p>
 *
 * @author lsw
 * @since 2021-03-08
 */
@Service("GroupActivityService")
public class GroupActivityServiceImpl implements GroupActivityService {

    @Autowired
    private CouponDao couponDao;
    @Autowired
    private GroupActivityDao groupActivityDao;

    @DataSource(name = DataSourceNames.READ)
    public void getaaa() {
        System.out.println(couponDao.getaaa());
        System.out.println(couponDao.selectById(35));
        System.out.println(couponDao.selectOne(new LambdaQueryWrapper<CouponEntity>().eq(CouponEntity::getId, 35)));

    }

    @DataSource(name = DataSourceNames.WRITE)
    public void getg() {
        System.out.println(groupActivityDao.getGroup());
    }
}