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
 * 处理单个文件计算线程
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
     * 回调执行
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
            ByteBuffer byteBuffer = ByteBuffer.allocate(DEFAULT_SIZE);
            int length = 0;
            byteBuffer.clear();
            while (length != -1) {
                length = fileChannel.read(byteBuffer);
                handlerByteBuffer(byteBuffer);
            }
            return true;
        } finally {
            if (Check.isNull(fileChannel)) {
                fileChannel.close();
            }
        }
    }

    /**
     * 处理byteBuffer
     * @param byteBuffer
     * @throws IOException
     */
    private void handlerByteBuffer(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.flip();
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
    }
}
