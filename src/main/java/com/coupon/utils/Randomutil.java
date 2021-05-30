package com.coupon.utils;

public class Randomutil {

    // 获取6位随机验证码
    public static String getRandom() {
        String num = "";
        for (int i = 0; i < 6; i++) {
            num = num + String.valueOf((int) Math.floor(Math.random() * 9 + 1));
        }
        return num;
    }

    /**
     * 补位字符串
     */
    private static final String e = "LJNHGTVC";

    /**
     * 自定义进制（选择你想要的进制数，不能重复且最好不要0、1这些容易混淆的字符）
     */
    private static final char[] r = new char[]{'Q', 'W', 'E', '8', 'S', '2', 'D', 'Z', '9', '7', 'P', '5', 'K', '3', 'M', 'U', 'F', 'R', '4', 'Y', '6', 'B', 'X'};

    /**
     * 定义一个字符用来补全邀请码长度（该字符前面是计算出来的邀请码，后面是用来补全用的）
     */
    private static final char b = 'A';

    /**
     * 进制长度
     */
    private static final int binLen = r.length;


//    /**
//     * 根据ID生成随机码
//     * 因为同一个id生成的邀请码不唯一，所以暂时舍弃掉
//     * @param id ID
//     * @return 随机码
//     */
//    public static String toSerialCode(long id) {
//        char[] buf=new char[32];
//        int charPos=32;
//
//        while((id / binLen) > 0) {
//            int ind=(int)(id % binLen);
//            buf[--charPos]=r[ind];
//            id /= binLen;
//        }
//        buf[--charPos]=r[(int)(id % binLen)];
//        String str=new String(buf, charPos, (32 - charPos));
//        // 不够长度的自动随机补全
//        if(str.length() < s) {
//            StringBuilder sb=new StringBuilder();
//            sb.append(b);
//            Random rnd=new Random();
//            for(int i=1; i < s - str.length(); i++) {
//                sb.append(r[rnd.nextInt(binLen)]);
//            }
//            str+=sb.toString();
//        }
//        return str;
//    }

    /**
     * 根据ID生成六位随机码
     *
     * @param id     ID
     * @param length 邀请码长度
     * @return 随机码
     */
    public static String toSerialCode(long id, int length) {
        char[] buf = new char[32];
        int charPos = 32;

        while ((id / binLen) > 0) {
            int ind = (int) (id % binLen);
            buf[--charPos] = r[ind];
            id /= binLen;
        }
        buf[--charPos] = r[(int) (id % binLen)];
        String str = new String(buf, charPos, (32 - charPos));
        // 不够长度的自动补全
        if (str.length() < length) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.subSequence(0, length - str.length()));
            str += sb.toString();
        }
        return transRadix(str,36,10);
    }

    /**
     * 根据随机码生成ID
     *
     * @param code 随机码
     * @return ID
     */
    public static long codeToId(String code) {
        char chs[] = code.toCharArray();
        long res = 0L;
        for (int i = 0; i < chs.length; i++) {
            int ind = 0;
            for (int j = 0; j < binLen; j++) {
                if (chs[i] == r[j]) {
                    ind = j;
                    break;
                }
            }
            if (chs[i] == b) {
                break;
            }
            if (i > 0) {
                res = res * binLen + ind;
            } else {
                res = ind;
            }
        }
        return res;
    }

    //设置字符数组
    //可以添加任意不重复字符，提高能转换的进制的上限
    public static char chs[] = new char[36];
    static {
        for(int i = 0; i < 10 ; i++) {
            chs[i] = (char)('0' + i);
        }
        for(int i = 10; i < chs.length; i++) {
            chs[i] = (char)('A' + (i - 10));
        }
    }

    /**
     * 转换方法
     * @param num       元数据字符串
     * @param fromRadix 元数据的进制类型
     * @param toRadix   目标进制类型
     * @return
     */
    public static String transRadix(String num, int fromRadix, int toRadix) {
        int number = Integer.valueOf(num, fromRadix);
        StringBuilder sb = new StringBuilder();
        while (number != 0) {
            sb.append(chs[number%toRadix]);
            number = number / toRadix;
        }
        return sb.reverse().toString();
    }
}
