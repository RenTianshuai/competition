package com.yusys.analysis;

import com.yusys.socket.MyServerSocket;
import com.yusys.utils.FileUtils;

import java.io.File;
import java.io.FileReader;


/**
 * 一个大于2的整数N，他可能等于比它小的若干个整数（大于等于2并且不等于自己）乘积。
 * 如果存在这样的连续整数，将他们输出，如果没有则输出-1。
 * <p>
 * 例： 整数60，60=3*4*5。所以输出[3 4 5]
 */
public class Analysis {

    private String inputFilePath = "/home/jike/intdiv/input.txt";
    private String outputFilePath = "";

    public static void main(String[] args) {
        String filePath = "C:\\Users\\XiewzPc\\Desktop\\大赛\\competition\\integerAnalysis\\imput.txt";
        String input = FileUtils.readFromFile(filePath);

        System.out.println(input);

        if (input == null) {
            return;
        }
        new Analysis().analysis(input);
//        boolean succ = FileUtils.saveToFile("123456", filePath, null);


    }

    /**
     * 程序运行主方法
     */
    public void start(){
        MyServerSocket myServer = new MyServerSocket();
        inputFilePath = myServer.getInputFilePath();
        outputFilePath = inputFilePath.replace("input.txt","85/output.txt");
        String inputData = FileUtils.readFromFile(inputFilePath);
        Analysis analysis = new Analysis();
        analysis.analysis(inputData);

        // 发送结果给服务器

    }

    /**
     * 一个大于2的整数N，他可能等于比它小的若干个整数（大于等于2并且不等于自己）乘积。
     * 如果存在这样的连续整数，将他们输出，如果没有则输出-1。
     */
    public String analysis(String inputData) {
        String result = null;

        String[] inputs = inputData.split("\n");
        for (int i = 0; i < inputs.length; i++) {
            String singleRes = calculateSingle(inputs[i]);
            FileUtils.writeLine(outputFilePath, singleRes + "\n");
        }

        return result;
    }

    /**
     * 计算单个数字的组合
     *
     * @param integerNum 一个大于2的整数
     * @return
     */
    public String calculateSingle(String integerNum) {
        StringBuffer resultStr = new StringBuffer();
        String res = null;
        Long integer = 0L;
        try {
            integer = new Long(integerNum);
        }catch (Exception e){
            e.printStackTrace();
            return "输入格式错误！";
        }
        long maxCalNum = integer / 2;
        boolean isNotFirst = false;

        // 从2开始寻找数字
        A:
        for (int i = 2; i < maxCalNum; i++) {
            res = "["; //当做临时字符串使用
            long calResVal = 1; // 计算结果
            for (int j = i; j < maxCalNum; j++) {
                calResVal = calResVal * j;

                // 计算逻辑控制部分,连续两个数据相乘已经大于它就没必要计算了
                if (calResVal > integer && j == i + 1) {
                    break A;
                }

                // 返回字符串操作
                res += j;
                // 如果找到对应的值就将结果拼入字符串
                if (calResVal == integer) {
                    res += "]";
                    if (isNotFirst) {
                        resultStr.append(",");
                    }
                    resultStr.append(res);
                    isNotFirst = true;
                    break;

                } else if (calResVal > integer) { // 计算的值超过预期值就清空临时字符串跳出
                    res = null;
                    break;
                } else {
                    res += " ";//小于预期值就拼空格
                }
            }
        }

        if (resultStr.length() == 0) {
            return "-1";
        }
        return resultStr.toString();
    }


}
