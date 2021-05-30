package com.coupon.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil {

    /**
     * 保存数据到文件
     *
     * @param text   数据
     * @param path   文件路径
     * @param append 是否追加
     * @throws IOException
     */
    public static void saveText2File(String text, String path, Boolean append) throws IOException {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter writer = new FileWriter(file, append);
        BufferedWriter bufferWritter = new BufferedWriter(writer);
        bufferWritter.write(text);
        bufferWritter.close();
    }

    /**
     * 读取文件中的数据
     *
     * @param fileName
     * @param bool     是否全部
     * @return
     * @throws IOException
     */
    public static String readFileByLines(String fileName, boolean bool) throws IOException {
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
            return "";
        }

        BufferedReader reader = null;

        reader = new BufferedReader(new FileReader(file));
        String res = "";
        String tempString = null;
        if (!bool) {
            res = reader.readLine();
        } else {
            while ((tempString = reader.readLine()) != null) {
                res += tempString;
            }
        }
        reader.close();
        return res;
    }

    public static List<String> readCommaLine(String fileName) {
        List<String> list = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            return list;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                list.addAll(Arrays.asList(tempString.split(",")));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e1) {

                }
            }
        }
        return list;
    }

    //用这个
    public static List<String> readFileByLines(String fileName) {

        return readFileByLines(fileName,"GBK");
    }

    public static List<String> readFileByLines(String fileName,String charsetName) {

        List<String> list = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) {

            return list;
        }

        InputStreamReader read = null;
        BufferedReader reader = null;
        try {

            read = new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1);
            reader = new BufferedReader(read);

            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {

                byte[] bs = tempString.getBytes(StandardCharsets.ISO_8859_1);
                String data = new String(bs, charsetName);
                list.add(data);
            }
            read.close();
            reader.close();
        } catch (IOException e) {

            e.printStackTrace();
        } finally {

            if (reader != null) {

                try {

                    read.close();
                    reader.close();
                } catch (Exception e1) {

                }
            }
        }
        return list;
    }

    public static String filterEndsWith(String path) {

        if (!path.endsWith("/")) {

            path += "/";
        }

        return path;
    }
}
