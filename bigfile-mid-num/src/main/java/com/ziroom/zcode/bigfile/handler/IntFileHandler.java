package com.ziroom.zcode.bigfile.handler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by sence on 2015/6/27.
 */
public class IntFileHandler {

    /**
     * pool size
     */
    private int poolSize;

    private BitArray bitArray;

    public IntFileHandler(int poolSize) {
        this.poolSize = poolSize;
        bitArray = new BitArray(Integer.MAX_VALUE);
    }

    /**
     * 计算中位数
     */
    public long sumMidNum(String[] files) throws ExecutionException, InterruptedException {
        long t1 = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
        Future<Boolean>[] futures = new Future[files.length];
        for (int i = 0; i < files.length; i++) {
            futures[i] = executorService.submit(new ReaderIntFileWorker(bitArray, files[i]));
        }
        boolean flag = false;
        for (Future<Boolean> future : futures) {
            flag = future.get();
        }
        if (!flag) {
            return -1;
        }
        executorService.shutdown();
        System.out.println("执行完排序:" + (System.currentTimeMillis() - t1));
        int count = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (bitArray.getBit(i) == 1) {
                count += 1;
            }
        }
        //System.out.println(count);
        int startNum = getStartNum(count);
        int endNum = startNum + 99;
        long sumNum = 0;
        count = 0;
        //System.out.println(startNum + "-" + endNum);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (bitArray.getBit(i) == 1) {
                count += 1;
            }
            //依靠游标计算中位数
            if (count >= startNum && count <= endNum) {
                sumNum += i;
            }
        }
        //System.out.println(System.currentTimeMillis() - t1);
        return sumNum;
    }

    private int getStartNum(int count) {
        double t = count / 2.0;
        t = Math.floor(t);
        return (int) (t - 49);
    }

}
