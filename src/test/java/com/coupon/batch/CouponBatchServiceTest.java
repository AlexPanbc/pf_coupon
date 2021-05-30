//package com.coupon.batch;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.coupon.dto.*;
//import com.coupon.enums.CouponStatusEnum;
//import com.coupon.enums.GenerationMethodEnum;
//import com.coupon.enums.ValidityTypeEnum;
//import com.coupon.generate.GenerateNumberManager;
//import com.coupon.job.WaittingCouponBatchListener;
//import com.coupon.mongo.entity.CouponMongo;
//import com.coupon.service.ConfigChannelService;
//import com.coupon.service.CouponBatchService;
//import com.coupon.utils.Constants;
//import com.coupon.utils.Pager;
//import com.mongodb.client.result.UpdateResult;
//import com.yuelvhui.util.http.HttpRequest;
//import com.yuelvhui.util.http.HttpResponse;
//import org.assertj.core.util.Lists;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.time.LocalDateTime;
//import java.util.List;
//
///**
// * @program: pf_coupon
// * @description:
// * @author: lcx
// * @create: 2019-07-05 18:36
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CouponBatchServiceTest {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private CouponBatchService couponBatchService;
//    @Autowired
//    private ConfigChannelService configChannelService;
//    @Resource(name = "GenerateCouponTempletNumManagerImpl")
//    private GenerateNumberManager generateNumberManager;
//    @Autowired
//    private WaittingCouponBatchListener waittingCouponBatchListener;
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Test
//    public void testUpdate(){
//        UpdateResult updateResult = mongoTemplate.updateMulti(new Query(new Criteria().andOperator(
//                Criteria.where("batch_code").is("200037013003"),
//                Criteria.where("status").is(CouponStatusEnum.OFFLINE.getStatus()))),
//                new Update().set("status", CouponStatusEnum.UNUSED.getStatus()), CouponMongo.class);
//        System.out.println("执行结束");
//    }
//
//    @Test
//    public void testCreateCoupon(){
//        CouponCreateDto couponCreateDto = new CouponCreateDto();
//        couponCreateDto.setBatchCode("200037013003");
//        couponCreateDto.setStock((long) 100);
//        waittingCouponBatchListener.createCoupon(couponCreateDto);
//    }
//
//    @Test
//    public void testCode(){
//        for (int i = 1; i< 1000;i++) {
//            String s = generateNumberManager.nextNumberString(Constants.COUPON_TEMPLATE_NO_PREFIX);
//            System.out.println(s);
//        }
//    }
//
//    @Test
//    public void list() {
//
//        List<ConfigChannelDto> list= configChannelService.channelList();
//        logger.info("结果" + JSON.toJSONString(list));
//    }
//
//    @Test
//    public void testSync(){
//        SyncCouponDto syncCouponDto = new SyncCouponDto();
//        System.out.println(JSONObject.toJSONString(syncCouponDto));
//        try {
//            HttpResponse response = HttpRequest.post("http://10.168.1.228:8380/coupon/operation")
//                    .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
//                    .body(JSONObject.toJSONString(syncCouponDto))
//                    .execute();
//            String result = new String(response.bodyBytes(), "UTF-8");
//            System.out.println(result);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            logger.error("回调业务端返回结果异常" + ex.getMessage(), ex);
//        }
//    }
//
//    @Test
//    public void pages() {
//        CouponBatchRequestDto dto = new CouponBatchRequestDto();
//        dto.setTemplateCode("000014");
//        dto.setCurPage(1);
//        dto.setPageSize(10);
//        Pager<CouponBatchResponseDto> pages = couponBatchService.pages(dto);
//        logger.info("结果" + JSON.toJSONString(pages));
//    }
//
//    @Test
//    public void create() {
//        List<CouponBatchCreateDto> list = Lists.newArrayList();
//
//        CouponBatchCreateDto dto = new CouponBatchCreateDto();
//        dto.setTemplateCode("1000004");
//        dto.setActivityId("2110000041");
//        dto.setActivityName("活动3");
//        dto.setGenerationMethod(GenerationMethodEnum.NO.getStatus());
//        dto.setTimeType(ValidityTypeEnum.ABSOLUTE_TIME.getStatus());
//        dto.setBatchStartTime(LocalDateTime.of(2021, 1, 25, 0, 0));
//        dto.setBatchEndTime(LocalDateTime.of(2021, 10, 11, 0, 0));
//        dto.setCouponStartTime(LocalDateTime.of(2021, 1, 25, 0, 0));
//        dto.setCouponEndTime(LocalDateTime.of(2021, 11, 11, 0, 0));
//        dto.setModifier("测试");
//        dto.setStockNum(100L);
//        list.add(dto);
//        CouponBatchCreateDto dto1 = new CouponBatchCreateDto();
//        dto1.setTemplateCode("1000004");
//        dto1.setActivityId("2110050031");
//        dto1.setActivityName("活动4");
//        dto1.setGenerationMethod(GenerationMethodEnum.NO.getStatus());
//        dto1.setTimeType(ValidityTypeEnum.RELATIVE_TIME.getStatus());
//        dto1.setBatchStartTime(LocalDateTime.of(2021, 1, 25, 0, 0));
//        dto1.setBatchEndTime(LocalDateTime.of(2021, 10, 11, 0, 0));
//        dto1.setDelayTime(7);
//        dto1.setModifier("测试");
//        dto1.setStockNum(100L);
//        list.add(dto1);
//        logger.info("请求参数" + JSON.toJSONString(list));
//        List<CouponBatchCreateResDto> couponBatchCreateResDtos = couponBatchService.create(list);
//        logger.info("结果" + couponBatchCreateResDtos);
//    }
//
//    @Test
//    public void enable() {
//        CouponBatchStatusDto dto = new CouponBatchStatusDto();
//        dto.setActivityId("");
//        dto.setBatchCode("2000100090011");
//        dto.setModifier("测试人员");
//        dto.setModified("");
//        logger.info("请求参数" + JSON.toJSONString(dto));
//        List<BatchEnableResDto> result = couponBatchService.enable(dto);
//        logger.info("结果" + result);
//    }
//
//    @Test
//    public void disabled() {
//        CouponBatchStatusDto dto = new CouponBatchStatusDto();
//        dto.setActivityId("");
//        dto.setBatchCode("111520280019");
//        dto.setModifier("测试人员");
//        dto.setModified("");
//        logger.info("请求参数" + JSON.toJSONString(dto));
//        Boolean result = couponBatchService.disabled(dto);
//        logger.info("结果" + result);
//    }
//
//    @Test
//    public void remove() {
//        CouponBatchRemoveDto dto = new CouponBatchRemoveDto();
//        dto.setActivityId("");
//        dto.setBatchCode("111520280019");
//        dto.setModifier("测试人员");
//        dto.setModified("");
//        logger.info("请求参数" + JSON.toJSONString(dto));
//        Boolean result = couponBatchService.remove(dto);
//        logger.info("结果" + result);
//    }
//
//    @Test
//    public void offline() {
//        CouponBatchOfflineDto dto = new CouponBatchOfflineDto();
//        dto.setBatchCode("111520280019");
//        dto.setModifier("测试人员");
//        dto.setModified("");
//        logger.info("请求参数" + JSON.toJSONString(dto));
//        Boolean result = couponBatchService.offline(dto);
//        logger.info("结果" + result);
//    }
//}
