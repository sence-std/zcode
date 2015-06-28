package com.ziroom.zcode.bigfile.handler;

import com.ziroom.zcode.common.util.ByteWrap;
import com.ziroom.zcode.common.util.Check;
import com.ziroom.zcode.common.util.StringWrap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.Callable;

/**
 * ��ȡ�ļ�д����λ����
 * Created by sence on 2015/6/27.
 */
public class ReaderIntFileWorker implements Callable<Boolean> {

    private static final int DEFAULT_SIZE = 1024*1024*10;
    private BitArray bitArray;
    private String file;
    private ByteWrap byteWrap;

    ReaderIntFileWorker(BitArray bitArray, String file) {
        this.bitArray = bitArray;
        this.file = file;
    }

    /**
     * �����ļ�
     * @return
     * @throws Exception
     */
    public Boolean call() throws Exception {
        FileChannel fileChannel = null;
        try {
            if (!Check.isFileExist(file)) {
                throw new FileNotFoundException("file not found:" + file);
            }
            RandomAccessFile fileAccess = new RandomAccessFile(file, "r");
            fileChannel = fileAccess.getChannel();
            int _readSize = DEFAULT_SIZE;
            long fileSize = fileChannel.size();
            long totalReadTimes = (long) Math.ceil(fileSize /DEFAULT_SIZE);
            long readTimes = 0;
            long cursorPoint;
            MappedByteBuffer mappedByteBuffer = null;
            ByteBuffer byteBuffer = ByteBuffer.allocate(DEFAULT_SIZE);
            while (totalReadTimes > -1) {
                long t1 = System.currentTimeMillis();
                cursorPoint = readTimes * _readSize;
                if (totalReadTimes == 0) {
                    _readSize = (int) (fileSize - cursorPoint);
                }
                mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, cursorPoint, _readSize);
                byteBuffer.put(mappedByteBuffer);
                handlerByteBuffer(byteBuffer);
                totalReadTimes--;
                readTimes++;
                System.out.println(file+"-"+(System.currentTimeMillis()-t1));
            }
            return true;
        } finally {
            if (Check.isNull(fileChannel)) {
                fileChannel.close();
            }
        }
    }

    /**
     * ����byteBuffer
     * @param byteBuffer
     * @throws IOException
     */
    private void handlerByteBuffer(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.flip();
        long t1 = System.currentTimeMillis();
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        byteBuffer.clear();
        for ( int i = 0;i < bytes.length; i++) {
            if (bytes[i] == '\n') {
                int digit = Integer.valueOf(new String(byteWrap.getBytes(),0, byteWrap.size()).trim());
                bitArray.setBit(digit, 1);
                byteWrap.clear();
            }else{
                if(Check.isNull(byteWrap)){
                    byteWrap = new ByteWrap(32);
                }
                byteWrap.addByte(bytes[i]);
            }
        }
        System.out.println("--"+(System.currentTimeMillis()-t1));
    }
}
