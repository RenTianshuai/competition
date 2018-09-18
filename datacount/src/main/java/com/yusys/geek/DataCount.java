package com.yusys.geek;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 入口类
 */
public class DataCount {

    private Map<String,Integer> count = null;

    private synchronized void countAdd(String key, int addValue){
        count.put(key,count.get(key)+addValue);
    }
    private AtomicInteger outCount = null;
    private AtomicInteger succCount = null;
    /**
     * 入口方法
     * @param rpcNum 接口数量
     * @param timeOut 接口超时时间（秒）
     * @param costTime 最大处理时间(秒)
     * @return 返回结果为逗号隔开的两个数字（超时接口数和成功接口返回值汇总）
     *
     * 问题思考：如何让主线程等待子线程完成再结束？
     *      1.子线程.join()，会再join的时间点等待，造成无法启动多子线程并发。
     *      2.主线程.sleep(ms)，睡眠时间无法确定，很主观。
     *      3.依靠业务逻辑判断阻止主线程结束。
     *      4.使用java.util.concurrent.CountDownLatch，CountDownLatch相当于一计数器，每次调用减1，到0为止，建一次用一次，不可更改。
     *      5.使用java.util.concurrent.CyclicBarrier，CyclicBarrier可重置。
     */
    public String process(int rpcNum,final int timeOut,final int costTime) {
        final CountDownLatch latch = new CountDownLatch(rpcNum);//1等待多线程完成
//        final CyclicBarrier barrier = new CyclicBarrier(rpcNum+1);//2同步屏障
        long s = System.currentTimeMillis();
        count = new HashMap<String, Integer>();
        count.put("outCount",0);
        count.put("succCount",0);
        outCount = new AtomicInteger(0);
        succCount = new AtomicInteger(0);
        for (int i=0;i<rpcNum;i++){
            new Thread(new Runnable() {
                public void run() {
                    try {
                        long start = System.currentTimeMillis();
                        RPC.call(costTime);
                        long end = System.currentTimeMillis();
                        long time = (end-start)/1000;
                        if (time > timeOut){
//                            countAdd("outCount",1);
                            outCount.incrementAndGet();
                        }else {
//                            countAdd("succCount",1);
                            succCount.incrementAndGet();
                        }
//                        System.out.println(Thread.currentThread().getName());
                        latch.countDown();
//                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        try {
            latch.await();
//            barrier.await();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
//        catch (BrokenBarrierException e) {
//            e.printStackTrace();
//        }
//        System.out.println(Thread.currentThread().getName());
        System.out.println("统计用时："+(System.currentTimeMillis()-s)/1000 + "秒");
        String result = outCount.get()+","+succCount.get();
        System.out.println("最后结果："+result);
        return result;
    }

    public static void main(String[] args){
        DataCount dc = new DataCount();
        Random r = new Random();
        for(int i=0;i<10;i++){
            int c = r.nextInt(100);
            System.out.println("第"+(i+1)+"组："+c);
            dc.process(c,2,5);
            System.out.println("RPC调用总次数："+RPC.getCount());//其中p1,p2,p3随机生成
        }

    }
}
