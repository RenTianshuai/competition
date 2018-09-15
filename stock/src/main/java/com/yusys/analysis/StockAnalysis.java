package com.yusys.analysis;

import com.yusys.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockAnalysis {
    String outputFilePath;

    public String start(String inputFilePath) {

        outputFilePath = inputFilePath.replace("input.txt", "85/output.txt");
        String inputData = FileUtils.readFromFile(inputFilePath);
        //开始分析数据并存储
        System.out.println("...开始处理...");
        long start = System.currentTimeMillis();
        analysis(inputData);
        long end = System.currentTimeMillis();
        System.out.println("处理时长约" + (end - start) + "ms");
        return outputFilePath;
    }

    private Map<String, List<String>> inputMap = new HashMap<String, List<String>>();
    private Map<String, List<String>> outputMap = new HashMap<String, List<String>>();

    private void analysis(String inputData) {
        FileUtils.createOutputFile(outputFilePath);

        String[] inputs = inputData.split("\n");
        for (int i = 0; i < inputs.length; i++) {
            String[] lineArr = inputs[i].split(",");
            String stockCode = null;
            List list = new ArrayList();
            for (int j = 0; j < lineArr.length; j++) {
                String s = lineArr[j];
                list.add(s);
                if (j == 3) {
                    stockCode = s;
                }

            }
            if (inputMap.containsKey(stockCode)) {
                String singleRes = getResultSingle(inputMap.get(stockCode), list);
                FileUtils.writeLine(outputFilePath, singleRes + "\r\n");

                inputMap.remove(stockCode);
            } else
                inputMap.put(stockCode, list);
//            String singleRes = calculateSingle(inputs[i]);
//            FileUtils.writeLine(outputFilePath, singleRes + "\r\n");
        }


    }

    /**
     * 0 a) 交易时间（4位表示) 0900 - 0925
     * 1 b) 卖出/买入标志(1位数字） 1:卖出 2:买入
     * 2 c) 客户号(10位数字)
     * 3 d) 股票代码(6位数字）
     * 4 e）每股价格(金额格式，精确到小数后3位)
     * 5 f) 股份（数字类型，100的整数倍)
     * <p>
     * 0 a) 股票代码(6位数字）
     * 1 b) 卖出客户号(10位数字)
     * 2 c) 买入客户号(10位数字)
     * 3 d）成交价格(金额格式，精确到小数后3位)
     * 4 f) 成交股份（数字类型，100的整数倍)
     *
     * @param line1
     * @param line2
     * @return
     */
    private String getResultSingle(List<String> line1, List<String> line2) {
        String result = null;
        result = line1.get(3) + ",";// 股票代码

        if ("1".equals(line1.get(1)))// 卖出客户号 买入客户号
            result += line1.get(1) + "," + line2.get(1);
        else
            result += line2.get(1) + "," + line1.get(1);


        return result;

    }
}
