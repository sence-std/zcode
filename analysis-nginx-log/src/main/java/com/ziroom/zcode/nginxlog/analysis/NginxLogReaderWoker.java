package com.ziroom.zcode.nginxlog.analysis;

import com.ziroom.zcode.common.util.ByteWrap;
import com.ziroom.zcode.common.util.Check;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
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

    private Map<String,String> ipMap;

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final int DEFAULT_READ_SIZE = 1024*1024*30; //10MB

    FileWriter fileWriter;

    public NginxLogReaderWoker(long startSize,long endSize,String fileName,Map<String,String> ipMap){
        this.startSize = startSize;
        this.endSize = endSize;
        this.fileName = fileName;
        this.ipMap = ipMap;
        logHalfPack = new LogHalfPack();
    }


    public LogHalfPack call() throws Exception {
        FileChannel fileChannel = null;
        try {
            if (!Check.isFileExist(fileName)) {
                throw new FileNotFoundException("File not found:" + fileName);
            }
            fileWriter = new FileWriter("E:\\nginxlog\\result.txt");
            RandomAccessFile fileAccess = new RandomAccessFile(fileName, "r");
            fileChannel = fileAccess.getChannel();
            int _readSize = DEFAULT_READ_SIZE;
            long fileSize = endSize - startSize + 1;
            long totalReadTimes = (long) Math.ceil(fileSize / DEFAULT_READ_SIZE);
            long readTimes = 0;
            long cursorPoint;
            ByteBuffer byteBuffer = ByteBuffer.allocate(DEFAULT_READ_SIZE);
            MappedByteBuffer mappedByteBuffer = null;
            while (totalReadTimes > -1) {
                cursorPoint = startSize + readTimes * _readSize;
                if (totalReadTimes == 0) {
                    _readSize = (int) (endSize - cursorPoint);
                }
                mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, cursorPoint, _readSize);
                byteBuffer.put(mappedByteBuffer);
                handlerByteBuffer(byteBuffer);
                totalReadTimes--;
                readTimes++;
                System.out.println(startSize+"read size:"+cursorPoint);
            }
            logHalfPack.setTailWrap(byteWrap);
        } finally {
            if (!Check.isNull(fileChannel)) {
                fileChannel.close();
            }
            if (!Check.isNull(fileWriter)) {
                fileWriter.close();
            }
        }
        return logHalfPack;
    }

    private void handlerByteBuffer(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.remaining()];
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
