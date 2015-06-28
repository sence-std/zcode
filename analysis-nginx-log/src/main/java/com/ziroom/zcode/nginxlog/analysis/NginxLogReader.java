/**
 * @FileName: NginxLogReader.java
 * @Package: com.ziroom.zcode.nginxlog.analysis
 * @author sence
 * @created 6/27/2015 1:46 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.zcode.nginxlog.analysis;

import com.ziroom.zcode.common.util.Check;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;

/**
 * <p>读取nginx日志</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class NginxLogReader {

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final int DEFAULT_READ_SIZE = 1024*1024*20; //10MB

    private String charset;
    private int readSize;

    private Map<String, String> ipMap;

    private byte[] leftBytes;

    public NginxLogReader() throws IOException {
        this(DEFAULT_CHARSET, DEFAULT_READ_SIZE);
    }

    public NginxLogReader(String charsetName, int readSize) throws IOException {
        if (Check.isNull(charsetName)) {
            this.charset = DEFAULT_CHARSET;
        } else {
            this.charset = charsetName;
        }
        if (readSize < 1024) {
            this.readSize = DEFAULT_READ_SIZE;
        } else {
            this.readSize = readSize;
        }

    }

    /**
     * 从logFile中读取数据并放入到队列
     *
     * @param logFilePath
     */
    public void readFromLogFile(String logFilePath,String resultPath,String findIpFilePath) throws IOException {
        long t1 = System.currentTimeMillis();
        initFindIp(findIpFilePath);
        FileChannel fileChannel = null;
        FileWriter fileWriter = new FileWriter(resultPath,true);
        try {
            if (!Check.isFileExist(logFilePath)) {
                throw new FileNotFoundException("File not found:" + logFilePath);
            }
            RandomAccessFile fileAccess = new RandomAccessFile(logFilePath, "r");
            fileChannel = fileAccess.getChannel();
            //每次读取20MB
            int _readSize = this.readSize;
            long fileSize = fileChannel.size();
            long totalReadTimes = (long) Math.ceil(fileSize / this.readSize);
            long readTimes = 0;
            long cursorPoint;
            ByteBuffer byteBuffer = ByteBuffer.allocate(this.readSize);
            while (totalReadTimes > -1) {
                cursorPoint = readTimes * _readSize;
                if (totalReadTimes == 0) {
                    _readSize = (int) (fileSize - cursorPoint);
                }
                fileChannel.read(byteBuffer);
                handlerByteBuffer(byteBuffer,fileWriter);
                totalReadTimes--;
                readTimes++;
            }
            System.out.println(System.currentTimeMillis() - t1);
        } finally {
            if (!Check.isNull(fileChannel)) {
                fileChannel.close();
            }
            fileWriter.flush();
            fileWriter.close();
        }

    }

    private void initFindIp(String findIpFilePath) throws IOException {
        if (!Check.isFileExist(findIpFilePath)) {
            throw new FileNotFoundException("File not found:" + findIpFilePath);
        }
        AnalysisIPLoader ipLoader = new AnalysisIPLoader();
        this.ipMap = ipLoader.loadAnalysisIP(findIpFilePath);
    }

    /**
     * 处理ByteBuffer
     *
     * @param byteBuffer
     */
    private void handlerByteBuffer(ByteBuffer byteBuffer,FileWriter fileWriter) throws IOException {
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        byte[] mergeBytes = mergeArray(bytes, leftBytes);
        int remark = -1;
        int i = 0;
        for (; i < mergeBytes.length; i++) {
            if (mergeBytes[i] == '\n') {
                String str = new String(mergeBytes, remark + 1, i - remark, this.charset);
                handlerIP(str,fileWriter);
                remark = i;
            }
        }
        if (remark != i) {
            leftBytes = new byte[i - remark - 1];
            System.arraycopy(mergeBytes, remark + 1, leftBytes, 0, i - remark - 1);
        }
        byteBuffer.clear();
    }

    /**
     * 处理IP
     *
     * @param str
     */
    private void handlerIP(String str,FileWriter fileWriter) throws IOException {
        String[] strs = str.split(" ");
        if (ipMap.get(strs[0]) != null) {
           fileWriter.write(strs[6]+"\n");
        }
    }

    /**
     * 处理遗留byte合并问题
     * @param from
     * @param to
     * @return
     */
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
