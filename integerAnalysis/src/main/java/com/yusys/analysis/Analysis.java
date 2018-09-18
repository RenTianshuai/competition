package com.yusys.analysis;

import com.yusys.socket.ClientSocket;
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


    private String serviceHost = "127.0.0.1";
    private int servicePort = 7701;


    public String getOutputFilePath() {
        return outputFilePath;
    }

    /**
     * 程序运行主方法
     */
    public void start(String inputFilePath) {
//        MyServerSocket myServer = new MyServerSocket();
//        this.inputFilePath = inputFilePath;
        outputFilePath = inputFilePath.replace("input.txt", "85/output.txt");
        String inputData = FileUtils.readFromFile(inputFilePath);
        //开始分析数据并存储
        System.out.println("...开始处理...");
        long start = System.currentTimeMillis();
        analysis(inputData);
        long end = System.currentTimeMillis();
        System.out.println("处理时长约" + (end - start) + "ms");

    }

    // 创建返回服务器数据
    public String getReturnData(String path) {
        String result = String.valueOf(path.length());
        while (result.length() < 6) {
            result = "0" + result;
        }
        result += path;
        return result;
    }

    /**
     * 一个大于2的整数N，他可能等于比它小的若干个整数（大于等于2并且不等于自己）乘积。
     * 如果存在这样的连续整数，将他们输出，如果没有则输出-1。
     */
    public void analysis(String inputData) {
        FileUtils.createOutputFile(outputFilePath);

        String[] inputs = inputData.split("\n");
        for (int i = 0; i < inputs.length; i++) {
//            String singleRes = calculateSingle(inputs[i]);
            String singleRes = calculateSingle2(inputs[i]);
            FileUtils.writeLine(outputFilePath, singleRes + "\n");
        }

    }

    /**
     * 计算单个数字的组合 核心方法
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
        } catch (Exception e) {
            e.printStackTrace();
            return "输入格式错误！";
        }
        long maxCalNum = integer / 2;
        long forjNum = maxCalNum;
        boolean isNotFirst = false;
        long contNum = forjNum; // 记录相乘的数字个数
        long maxI = maxCalNum; // 开始计算的数的最大的值

        // 从2开始寻找数字
        A:
        for (long i = 2L; i < maxCalNum; i++) {
            res = "["; //当做临时字符串使用
            long contSingle = 0; // 单次计数
            long calResVal = 1; // 计算结果
            for (long j = i; j < forjNum; j++) {
                calResVal = calResVal * j;
                contSingle++;

                if (contNum == 1) {
                    break A;
                }

                if (j - i > contNum) {
                    break;
                }

                // 计算逻辑控制部分,连续两个数据相乘已经大于它就没必要计算了
                if (calResVal > integer && j == i + 1) {
                    break A;// 计算结束
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

                    if (contSingle < contNum) {
                        contNum = contSingle;
                    } else
                        contNum--;
                    maxCalNum = integer % i > 0 ? integer / i + 1 : integer / i;

                    break;

                } else if (calResVal > integer) { // 计算的值超过预期值就清空临时字符串跳出
                    res = null;
                    if (contSingle < contNum) {
                        contNum = contSingle;
                    } else contNum--;
                    maxCalNum = integer % i > 0 ? integer / i + 1 : integer / i;
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

    /**
     * 计算单个数字的组合 核心方法
     *
     * @param integerNum 一个大于2的整数
     * @return
     */
    public String calculateSingle2(String integerNum) {
        StringBuffer resultStr = new StringBuffer("[");
        Long integer = 0L;
        try {
            integer = new Long(integerNum);
        } catch (Exception e) {
            e.printStackTrace();
            return "输入格式错误！";
        }
        long maxCalNum = integer / 2;
        long contNum = 0; // 记录相乘的数字个数
        long maxI = maxCalNum; // 开始计算的数的最大的值

        // 分析
        // 2*3*4=24 < 3*3*3=27=3^3  24 = 2.213...^3
        // 2*3*4*5=120,4*5*6=120 --> 120=4.93^3=3.09^4  3个数相乘的中间数为5，4个数的中间数为3（偶数个，左边比右边少一个数）
        // 2*3*4*5*6=720,8*9*10=720 --> 720=3.728^5=8.963^3 3个数的中间数为9， 5个数的中间数为4
        // 2*3*4*5*6*7=5040 --> 5040=4.141^6=8.42^4
        // 得出规律

        long mulResult = 1; // 相乘结果
        // 先确定最大的个数，从2开始乘积得出
        for (long i = 2; i < maxCalNum; i++) {
            resultStr.append(i);
            contNum++;
            mulResult *= i;
            if (mulResult == integer) {
                resultStr.append("]");
                break;
            }
            resultStr.append(" ");
            if (mulResult > integer || mulResult < 0) {
                resultStr = new StringBuffer("[");
                break;
            }
        }

        // 从最大个数-1开始开方 开方的次数不能小于2 得到的值理论上也不能小于2
        contNum--;
        while (true) {
            double middle = Math.pow(integer, 1 / new Double(contNum));
            if (contNum < 2) {
                break;
            }
            if (middle < 2) {
                break;
            }

            //四舍五入 得到中间整数
            long midInt = Math.round(middle);
            long forCont = contNum / 2; // 中间数左右两边分别的个数
            StringBuffer result2 = new StringBuffer("[");
            if (contNum % 2 == 0) { // 偶数个  // 2*3*4*5
                long tempResult = 1;
                for (long i = 1; i <= forCont; i++) { //2
                    long left = midInt - i + 1; // 2
                    long right = midInt + i;
                    tempResult *= left * right;
                    result2.insert(1, left + " ");
                    if (i != 1)
                        result2.append(" ");
                    result2.append(right);
                }
                result2.append("]");
                // 如果结果符合
                if (tempResult == integer) {
                    // 拼字符串
                    if (resultStr.toString().endsWith("]")) {
                        resultStr.append("," + result2);
                    }else
                        resultStr = result2;
                }
            } else { // 奇数个 // 3*4*5
                long tempResult = midInt;
                result2.append(midInt); // 奇数个就先插入中间数
                for (long i = 1; i <= forCont; i++) {
                    long left = midInt - i;
                    long right = midInt + i;
                    tempResult *= left * right;
                    result2.insert(1, left + " ").append(" " + right);
                }

                result2.append("]");
                if (tempResult == integer) {
                    if (resultStr.toString().endsWith("]")) {
                        resultStr.append("," + result2);
                    }else
                        resultStr = result2;
                }
            }

            contNum--;
        }


        if (resultStr.length() == 1) {
            return "-1";
        }
        return resultStr.toString();
    }


}
