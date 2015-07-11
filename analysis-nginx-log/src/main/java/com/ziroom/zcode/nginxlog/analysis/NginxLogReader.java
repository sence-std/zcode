/**
 * @FileName: NginxLogReader.java
 * @Package: com.ziroom.zcode.nginxlog.analysis
 * @author sence
 * @created 6/27/2015 1:46 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.zcode.nginxlog.analysis;

import com.ziroom.zcode.common.util.ByteWrap;
import com.ziroom.zcode.common.util.Check;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;

/**
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class NginxLogReader {

    public static final String DEFAULT_CHARSET = "UTF-8";
    public static final int DEFAULT_READ_SIZE = 1024 * 1024 * 20; //10MB

    private String charset;
    private int readSize;

    private Map<String, String> ipMap;

    private ByteWrap byteWrap;

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
     * 从文件读取数据 以及IP数据，结果写到文件
     *
     * @param logFilePath
     */
    public void readFromLogFile(String logFilePath, String resultPath, String findIpFilePath) throws IOException {
        initFindIp(findIpFilePath);
        FileChannel fileChannel = null;
        FileWriter fileWriter = new FileWriter(resultPath, true);
        try {
            if (!Check.isFileExist(logFilePath)) {
                throw new FileNotFoundException("File not found:" + logFilePath);
            }
            RandomAccessFile fileAccess = new RandomAccessFile(logFilePath, "r");
            fileChannel = fileAccess.getChannel();
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
                handlerByteBuffer(byteBuffer, fileWriter);
                totalReadTimes--;
                readTimes++;
            }
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
    private void handlerByteBuffer(ByteBuffer byteBuffer, FileWriter fileWriter) throws IOException {
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        byteBuffer.clear();
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == '\n') {
                String str = new String(byteWrap.getBytes(), 0, byteWrap.size()).trim();
                handlerIP(str, fileWriter);
                byteWrap.clear();
            } else {
                if (Check.isNull(byteWrap)) {
                    byteWrap = new ByteWrap(1024 * 1024 * 2);
                }
                byteWrap.addByte(bytes[i]);
            }
        }
    }

    /**
     * 处理IP
     *
     * @param str
     */
    private void handlerIP(String str, FileWriter fileWriter) throws IOException {
        String[] strs = str.split(" ");
        if (ipMap.get(strs[0]) != null) {
            fileWriter.write(strs[6] + "\n");
        }
    }


}
