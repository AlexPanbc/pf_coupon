package com.coupon.utils;

import com.yuelvhui.util.exception.ServiceException;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.ServerError;
import java.util.Objects;

/**
 * @create: 2019/11/6
 * @Time:17:13
 * @author: Shangq
 */
public class Base64PicUtil {
    /**
     * 将网络图片进行Base64位编码
     *
     * @param imageUrl
     *            图片的url路径，如http://.....xx.jpg
     * @return
     */
    public static String encodeImgageToBase64(URL imageUrl) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(imageUrl);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
    }

    /**
     * 将本地图片进行Base64位编码
     *
     * @param imageFile
     * @return
     */
    public static String encodeImageToBase64(MultipartFile imageFile) throws IOException {
        BASE64Encoder base64Encoder =new BASE64Encoder();
        String[] suffixArea = Objects.requireNonNull(imageFile.getOriginalFilename()).split("\\.");
        if(Objects.equals(imageFile.getContentType(), "image/jpeg")||Objects.equals(imageFile.getContentType(), "image/jpg")){
            String prefix = "".replace("jpg",suffixArea[suffixArea.length-1]);
            String base64EncoderImg = prefix + base64Encoder.encode(imageFile.getBytes()).replaceAll("[\\s*\t\n\r]", "");
            return base64EncoderImg;
        }else if(imageFile.getContentType().equals("image/png")){
            String prefix = "".replace("png",suffixArea[suffixArea.length-1]);
            String base64EncoderImg = prefix + base64Encoder.encode(imageFile.getBytes()).replaceAll("[\\s*\t\n\r]", "");
            return base64EncoderImg;
        }
        throw new ServiceException(ErrorCode.ParamFormatNotCorrect.getCode(),"图片格式错误，请上传jpg/png格式的图片");
    }

    /**
     * 将Base64位编码的图片进行解码，并保存到指定目录
     *
     * @param base64
     *            base64编码的图片信息
     * @return
     */
    public static void decodeBase64ToImage(String base64, String path,
                                           String imgName) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            FileOutputStream write = new FileOutputStream(new File(path
                    + imgName));
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
