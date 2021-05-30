package com.coupon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PfCouponApplicationTests {

    volatile  String b ="1";
    @Test
    public void contextLoads() {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, String.valueOf(i));
        }
        final String a = "1";
        map.put(21,"1232");
        map.put(37,"1213");
        map.put(53,"123444");
        map.put(69,"123444");
        map.put(85,"123444");
        map.put(101,"123444");
        System.out.println(map.size());
    }

}
