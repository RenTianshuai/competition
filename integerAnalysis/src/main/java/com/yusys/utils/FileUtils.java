package com.yusys.utils;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.server.ExportException;

public class FileUtils {

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
        File file = new File(filePath);// 指定要读取的文件
        FileReader reader = null;// 获取该文件的输入流
        char[] bb = new char[1024];// 用来保存每次读取到的字符
        StringBuilder str = new StringBuilder();// 用来将每次读取到的字符拼接，当然使用StringBuffer类更好
        int n;// 每次读取到的字符长度
        try {
            while ((n = reader.read(bb)) != -1) {
                str.append(bb,0,n);
            }
            reader = new FileReader(file);
            reader.close();// 关闭输入流，释放连接
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        System.out.println(str);
        return str.toString();

    }

    /**
     * 从后面追加内容
     * @param fileName
     * @param content
     * @return
     */
    public static Boolean writeLine(String fileName,String content){
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
