package com.yusys;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<String> readFile(String fileName) throws IOException {
        List<String> list = new ArrayList<String>();
        File file = new File(fileName);
        if (file.exists()){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = "";
            while ((line=bufferedReader.readLine())!= null){
                if (line.length()!=0){
                    list.add(line);
                }else {
                    break;
                }
            }
            bufferedReader.close();
        }
        return list;
    }
    public static void writeFile(String fileName, List<String> list) throws IOException {
        File file = new File(fileName);
        if (!file.exists()){
            file.createNewFile();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        for (String l:list){
            bufferedWriter.write(l);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }
}
