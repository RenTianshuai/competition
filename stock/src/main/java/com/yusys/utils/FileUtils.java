package com.yusys.utils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.rmi.server.ExportException;

public class FileUtils {

    public static boolean createOutputFile(String outputPath){
        String dir = outputPath.replace("output.txt","");
        File file = new File(dir);
        file.mkdirs();
        file = new File(outputPath);
        try {
            boolean newFile = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean saveToFile(String str, String filepath, String encoding) {
        InputStream bis = null;
        try {
            if (encoding == null || "".equals(encoding)) {
                bis = new ByteArrayInputStream(str.getBytes("GBK"));
            } else {
                bis = new ByteArrayInputStream(str.getBytes(encoding));
            }
            OutputStream os = new FileOutputStream(filepath);

            IOUtils.copy(bis, os);
            bis.close();
            os.close();
            System.out.println("保存文件:" + filepath);
            return true;
        } catch (IOException e) {
            System.out.println("保存文件失败:" + filepath);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * DOC 从文件里读取数据.
     *
     * @throws IOException
     */
    public static String readFromFile(String filePath) {

        StringBuilder str = new StringBuilder();// 用来将每次读取到的字符拼接，当然使用StringBuffer类更好
        try {
            //BufferedReader是可以按行读取文件
            FileInputStream inputStream = new FileInputStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String lineStr = null;
            while ((lineStr = bufferedReader.readLine()) != null) {
                str.append(lineStr+"\n");
            }

            inputStream.close();
            bufferedReader.close();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        System.out.println(str);
        return str.toString();

    }

    /**
     * 从后面追加内容
     *
     * @param fileName
     * @param content
     * @return
     */
    public static Boolean writeLine(String fileName, String content) {
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
