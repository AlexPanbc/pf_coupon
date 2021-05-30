package com.coupon.utils;


/**
 * 静态常量类
 *
 * @author jiangtao3
 */
public class Constants {

    public static final String PROJECT_NAME = "pay.coupon.com";

    /**
     * 缓存时间1天
     */
    public static final Long REDIS_CACHE_TIMES = 86400000L;

    /**
     * 单个文件上传大小20M
     */
    public static final Integer MAX_FILE_SIZE = 20 * 1024 * 1024;

    /**
     * 总数据上传大小100M
     */
    public static final Integer MAX_REQUEST_SIZE = 100 * 1024 * 1024;

    // HTTP AUTHORIZATION 头名称
    public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";
    public static final String AUTHORIZATION_PREFIX_BEARER = "Bearer ";
    public static final String AUTHORIZATION_PREFIX_ADMIN = "Admin ";
    public static final String AUTHORIZATION_PREFIX_SYS = "Sys ";

    public static final int KEYSIZE = 2048;
    //keysize=1024时，分段不能大于117 ；keysize>=2048时，分段不能大于keySize/8+128；
    public static final int ENSEGMENTSIZE = 245;
    //等于keySize/8
    public static final int DESEGMENTSIZE = 256;
    public static final String RSA = "RSA";
    public static final int RADIX = 16;

    public static final String RSA_PRIVATEKEY = "privateKey";

    // Cookie TOKEN
    public static final String COOKIE_TOKEN_USER = "user_token";
    public static final String COOKIE_TOKEN_ADMIN = "admin_token";
    public static final String COOKIE_TOKEN_SYS = "sys_token";

    /**
     * 请求类型
     */
    public final static String CONTENT_TYPE_TEXT = "Text";
    public final static String CONTENT_TYPE_TEXT_PLAIN = "text/plain";
    public final static String CONTENT_TYPE_JSON = "application/json";
    public final static String CONTENT_TYPE_JAVASCRIPT = "application/javascript";
    public final static String CONTENT_TYPE_XML_APPLICATION = "application/xml";
    public final static String CONTENT_TYPE_XML_TEXT = "text/xml";
    public final static String CONTENT_TYPE_HTML = "text/html";
    public final static String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    /**
     * 匹配 *.yuelvhui.com | *.zhiding365.com 的正则
     */
    public final static String DOMAIN_PATTERN = "(^http(s)?://((\\w|-)+\\.)+((yuelvhui\\.com)))";

    /**
     * 跨域option请求缓存时间,20天
     */
    public final static int CORS_OGIGIN_SECONDS = 1728000;

    public final static String ENCODING_UTF8 = "UTF-8";

    public final static String ENCODING_GBK = "GBK";

    public final static String LOGIN_REGEX = "^(?![_.\\-])(?!.*[_.\\-]{2})[a-zA-Z0-9_.\\-]+(?<![_.\\-])$";

    /**
     * 默认的货币类型
     */
    public final static String DEFAULT_FEE_TYPE = "CNY";

    /**
     * 券模板固定前缀
     */
    public final static String COUPON_TEMPLATE_NO_PREFIX = "1";

    /**
     * 券批次固定前缀
     */
    public final static String COUPON_BATCH_NO_PREFIX = "2";

    /**
     * 数据中心编号
     */
    public final static long MAKE_NUMBER_DATACENTER_ID = 1;

    /**
     * 机器编号
     */
    public final static long MAKE_NUMBER_MACHINE_ID = 2;

    public final static String JSON_SUCCESS_OUT_STRING = "{\"returnCode\":\"SUCCESS\"}";

    public final static String SUCCESS = "SUCCESS";

    //bmp、png、jpeg、jpg、gif
    public static final String BMP = "bmp";
    public static final String PNG = "png";
    public static final String JPEG = "jpeg";
    public static final String JPG = "jpg";
    public static final String GIF = "gif";

    //领取数量
    public final static String BATCH_GRANT_NUM = "batchGrantNum_";
    //使用数量
    public final static String BATCH_USED_NUM = "batchUsedNum_";
    //库存数量
    public final static String BATCH_STOCK_NUM = "batchStockNum_";
    //领取数量
    public final static String TEMPLATE_GRANT_NUM = "templateGrantNum_";
    //使用数量
    public final static String TEMPLATE_USED_NUM = "templateUsedNum_";
    //库存数量
    public final static String TEMPLATE_STOCK_NUM = "templateStockNum_";

    public final static String TEMPLATE = Constants.PROJECT_NAME + "_template_";

    public final static String BATCH = Constants.PROJECT_NAME + "_batch_";
}
