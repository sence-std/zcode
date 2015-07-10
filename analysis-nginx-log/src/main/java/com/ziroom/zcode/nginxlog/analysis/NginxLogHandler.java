package com.ziroom.zcode.nginxlog.analysis;

import com.ziroom.zcode.common.util.ByteWrap;
import com.ziroom.zcode.common.util.Check;

import java.io.File;
import java.io.FileWriter;
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
    public void handlerNginxLog(String filePath,int blockNum,String ipPath,String resultPath) throws IOException, ExecutionException, InterruptedException {
        File file = new File(filePath);
        long length = file.length();
        int block = blockNum;
        long blockSize = 0;
        if(length < block){
            block = 1;
            blockSize = length;
        }else {
            blockSize = (long) Math.ceil(length / block);
        }
        AnalysisIPLoader ipLoader = new AnalysisIPLoader();
        Map<String,String> map = ipLoader.loadAnalysisIP(ipPath);
        ExecutorService executorService = Executors.newFixedThreadPool(block);
        Future<LogHalfPack>[] futures = new Future[block];
        for (int i=0;i<block;i++){
            long startSize = i*blockSize;
            long endSize = (i+1)*blockSize;
            if(i == block-1){
                endSize = length;
            }
            futures[i] = executorService.submit(new NginxLogReaderWoker(startSize, endSize, filePath, map,resultPath));
        }
        List<LogHalfPack> logHalfPacks = new ArrayList<LogHalfPack>();
        for (int i = 0;i<block;i++){
            LogHalfPack pack = futures[i].get();
            logHalfPacks.add(pack);
        }
        executorService.shutdown();
        ByteWrap footerWrap = null;
        FileWriter fileWriter = new FileWriter(resultPath,true);
        for(LogHalfPack lp:logHalfPacks){
            byte[] bytes = null;
            if(Check.isNull(footerWrap) || footerWrap.isEmpty()){
                bytes = lp.getHeaderByte().getBytes();
            }else{
                bytes = mergeArray(footerWrap.getBytes(),lp.getHeaderByte().getBytes());
            }
            if(!Check.isNull(bytes)) {
                handlerLogHalfBytes(bytes, map, fileWriter);
            }
        }
        fileWriter.flush();
        fileWriter.close();
    }

    public void handlerLogHalfBytes(byte bytes[],Map<String,String> ipMap,FileWriter fileWriter) throws IOException {
        String str = new String(bytes);
        String[] strs = str.split(" ");
        if (ipMap.get(strs[0]) != null) {
            fileWriter.write(strs[6]);
        }
    }

    public byte[] mergeArray(byte[] from, byte[] to) {
        int fs = from.length;
        if (fs == 0) {
            return to;
        }
        byte[] temp = null;
        if (Check.isNull(to)) {
            temp = new byte[fs];
            System.arraycopy(from, 0, temp, 0, fs);
            return temp;
        }
        int ts = to.length;
        temp = new byte[fs + ts];
        if (ts != 0) {
            System.arraycopy(to, 0, temp, 0, ts);
        }
        System.arraycopy(from, 0, temp, ts, fs);
        return temp;
    }

}
