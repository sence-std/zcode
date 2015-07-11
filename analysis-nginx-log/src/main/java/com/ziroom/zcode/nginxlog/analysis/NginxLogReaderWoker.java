package com.ziroom.zcode.nginxlog.analysis;

import com.ziroom.zcode.common.util.ByteWrap;
import com.ziroom.zcode.common.util.Check;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by sence on 2015/6/28.
 */
public class NginxLogReaderWoker implements Callable<LogHalfPack> {

    private LogHalfPack logHalfPack;

    private ByteWrap byteWrap;

    private long startSize;

    private long endSize;

    private String fileName;

    private String resultPath;

    private Map<String,String> ipMap;

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final int DEFAULT_READ_SIZE = 1024*1024*30; //10MB

    FileWriter fileWriter;

    public NginxLogReaderWoker(long startSize,long endSize,String fileName,Map<String,String> ipMap,String resultPath,FileWriter fileWriter){
        this.startSize = startSize;
        this.endSize = endSize;
        this.fileName = fileName;
        this.ipMap = ipMap;
        logHalfPack = new LogHalfPack();
        this.resultPath = resultPath;
        this.fileWriter = fileWriter;
    }


    public LogHalfPack call() throws Exception {
        FileChannel fileChannel = null;
        try {
            if (!Check.isFileExist(fileName)) {
                throw new FileNotFoundException("File not found:" + fileName);
            }
           // fileWriter = new FileWriter(resultPath,true);
            RandomAccessFile fileAccess = new RandomAccessFile(fileName, "r");
            fileChannel = fileAccess.getChannel();
            int _readSize = DEFAULT_READ_SIZE;
            long fileSize = endSize - startSize + 1;
            long totalReadTimes = (long) Math.ceil(fileSize / DEFAULT_READ_SIZE);
            long readTimes = 0;
            long cursorPoint;
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(DEFAULT_READ_SIZE);
            while (totalReadTimes > -1) {
                cursorPoint = startSize + readTimes * _readSize;
                if (totalReadTimes == 0) {
                    _readSize = (int) (endSize - cursorPoint);
                }
                if(_readSize<=0){
                    break;
                }
                fileChannel.read(byteBuffer,cursorPoint);
                handlerByteBuffer(byteBuffer, _readSize);
                totalReadTimes--;
                readTimes++;
            }
            logHalfPack.setTailWrap(byteWrap);
        } finally {
            if (!Check.isNull(fileChannel)) {
                fileChannel.close();
            }
        }
        return logHalfPack;
    }

    private void handlerByteBuffer(ByteBuffer byteBuffer,int size) throws IOException {
        byteBuffer.flip();
        byte[] bytes = new byte[size];
        byteBuffer.get(bytes);
        byteBuffer.clear();
        for ( int i = 0;i < bytes.length; i++) {
            if (bytes[i] == '\n') {
                if(logHalfPack.isHeaderNull()){
                    logHalfPack.setHeaderByte(byteWrap);
                }else{
                   String str = new String(byteWrap.getBytes(),0, byteWrap.size()).trim();
                   String[] strs = str.split(" ");
                   if (ipMap.get(strs[0]) != null) {
                      fileWriter.write(strs[6]+"\n");
                   }
                }
                byteWrap.clear();
            }else{
                if(Check.isNull(byteWrap)){
                    byteWrap = new ByteWrap(1024*1024*2);
                }
                byteWrap.addByte(bytes[i]);
            }
        }
    }
}
