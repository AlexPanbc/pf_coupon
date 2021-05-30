package com.coupon.entity.group;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 拼团活动
 * </p>
 *
 * @author lsw
 * @since 2021-04-07
 */
@Data
@TableName("group_activity")
public class GroupActivityEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 业务线：264 好邻好物,265 YCloud,232 大人，251 直订，256 悦掏
     */
    private Integer mchId;

    /**
     * 拼团活动编号
     */
    private String activityCode;

    /**
     * 拼团活动价格
     */
    private Integer activityPrice;

    /**
     * 拼团活动类型 1参团人数，2商品件数，3总金额
     */
    private Integer activityType;

    /**
     * 商品序号
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图片
     */
    private String goodsPic;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 状态：1未启用，2已启用，3已禁用，4已失效
     */
    private Integer status;

    /**
     * 适用业务 1 自营商城
     */
    private Integer suitBusiness;

    /**
     * 官网价格
     */
    private Integer officialPrice;

    /**
     * 单独购买价格
     */
    private Integer separatelyPrice;

    /**
     * 用户购买类型 1全部
     */
    private Integer applyCrowd;

    /**
     * 拼团类型设置和拼团类型联动 比如未满多少笔订单自动退款
     */
    private Integer activityTypeValue;

    /**
     * 拼团成团时间 开团后几个小时内完成拼团
     */
    private Integer cloudsTime;

    /**
     * 拼团机器人设置 剩余几个小时补齐机器人
     */
    private Integer complementRobot;

    /**
     * 每人限购 0不限购，1总限购，2每日限购，3每笔拼团订单限购
     */
    private Integer purchasingType;

    /**
     * 限购数量
     */
    private Integer purchasingNum;

    /**
     * 活动下线是否通知到渠道端 1未通知 2已通知
     */
    private Integer noticeStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Boolean isDel;


}
