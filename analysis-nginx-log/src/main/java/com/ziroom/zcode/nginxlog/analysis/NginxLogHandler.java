package com.ziroom.zcode.nginxlog.analysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by sence on 2015/6/28.
 */
public class NginxLogHandler {

    /**
     *
     */
    public void handlerNginxLog(String filePath) throws IOException, ExecutionException, InterruptedException {
        long t1 = System.currentTimeMillis();
        File file = new File(filePath);
        long length = file.length();
        int block = 4;
        long blockSize = 0;
        if(length < block){
            block = 1;
            blockSize = length;
        }else {
            blockSize = (long) Math.ceil(length / block);
        }
        AnalysisIPLoader ipLoader = new AnalysisIPLoader();
        Map<String,String> map = ipLoader.loadAnalysisIP("E:\\nginxlog\\ip.txt");
        ExecutorService executorService = Executors.newFixedThreadPool(block);
        Future<LogHalfPack>[] futures = new Future[block];
        for (int i=0;i<block;i++){
            long startSize = i*blockSize;
            long endSize = (i+1)*blockSize;
            if(i == block-1){
                endSize = length;
            }
            futures[i] = executorService.submit(new NginxLogReaderWoker(startSize, endSize, filePath, map));
        }
        List<LogHalfPack> logHalfPacks = new ArrayList<LogHalfPack>();
        for (int i = 0;i<block;i++){
            LogHalfPack pack = futures[i].get();
            logHalfPacks.add(pack);
        }
    }

}
