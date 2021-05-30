package com.coupon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 优惠券表
 * </p>
 *
 * @author lcx
 * @since 2021-01-11
 */
@Data
@TableName("coupon")
public class CouponEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 券模板编码
     */
    private String templateCode;

    /**
     * 批次编码
     */
    private String batchCode;

    /**
     * 优惠券编码
     */
    private String couponCode;

    /**
     * 优惠券条形码
     */
    private String barcode;

    /**
     * 优惠券二维码
     */
    private String qrcode;

    /**
     * 券类型(1-满减券,2-现金券,3-折扣券)
     */
    private Integer couponType;

    /**
     * 券名称
     */
    private String couponName;

    /**
     * 面额
     */
    private Integer denomination;

    /**
     * 使用门槛
     */
    private Integer useThreshold;

    /**
     * 折扣(75-->7.5折扣)
     */
    private Integer discount;

    /**
     * 最高优惠
     */
    private Integer highestDiscount;

    /**
     * 状态:1-未开始,2-未使用,3-已使用,4-已过期,5-已冻结,6-已转赠,7-强制下线
     */
    private Integer status;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 用户编码
     */
    private String memberId;

    /**
     * 用户手机号
     */
    private String memberMobile;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 是否可以赠送   0否 1是
     */
    private Integer donate;

    /**
     * 是否已赠送   0未赠 1已赠
     */
    private Integer given;

    /**
     * 操作人
     */
    private String modifier;

    /**
     * 操作时间
     */
    private String modified;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 适用渠道
     */
    private String channelCode;

    /**
     * 使用渠道
     */
    private String usedChannel;

    /**
     * 优惠券领取时间
     */
    private LocalDateTime collectionTime;

    /**
     * 优惠券使用时间
     */
    private LocalDateTime usedTime;

}
