package com.yusys.geek;

import java.util.HashMap;
import java.util.Map;

/**
 * 入口类
 */
public class DataCount {

    private Map<String,Integer> count = null;

    private synchronized void countAdd(String key, int addValue){
        count.put(key,count.get(key)+addValue);
    }
    /**
     * 入口方法
     * @param rpcNum 接口数量
     * @param timeOut 接口超时时间（秒）
     * @param costTime 最大处理时间(秒)
     * @return 返回结果为逗号隔开的两个数字（超时接口数和成功接口返回值汇总）
     */
    public String process(int rpcNum,final int timeOut,final int costTime) {
        long s = System.currentTimeMillis();
        count = new HashMap<String, Integer>();
        count.put("outCount",0);
        count.put("succCount",0);
        for (int i=0;i<rpcNum;i++){
            new Thread(new Runnable() {
                public void run() {
                    try {
                        long start = System.currentTimeMillis();
                        RPC.call(costTime);
                        long end = System.currentTimeMillis();
                        long time = (end-start)/1000;
                        if (time > timeOut){
                            countAdd("outCount",1);
                        }else {
                            countAdd("succCount",1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        while (Thread.activeCount() > 1){
        }
        System.out.println("统计用时："+(System.currentTimeMillis()-s)/1000 + "秒");
        String result = count.get("outCount")+","+count.get("succCount");
        System.out.println("最后结果："+result);
        return result;
    }

    public static void main(String[] args){
        DataCount dc = new DataCount();
//        for(int i=0;i<10;i++){
            dc.process(92,2,5);
            System.out.println("调用次数："+RPC.getCount());//其中p1,p2,p3随机生成
//        }

    }
}
