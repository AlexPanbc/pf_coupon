package com.coupon.controller.groupwork;


import com.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lsw
 * @Description 团购
 * @Date 2021/3/8 3:08 下午
 * @return
 **/
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private CouponService couponService;

    @GetMapping("/get")
    public void get() {
        couponService.get();
    }

}

