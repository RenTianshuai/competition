package com.yusys.utils;

public class StringUtil {

    // 创建返回服务器数据
    public static String getReturnData(String path) {
        String result = String.valueOf(path.length());
        while (result.length() < 6) {
            result = "0" + result;
        }
        result += path;
        return result;
    }
}
