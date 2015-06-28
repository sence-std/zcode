package com.ziroom.zcode.common.util;

/**
 * Created by sence on 2015/6/28.
 */
public class ByteWrap {

    private byte[] bytes;
    private int size;

    /**
     *
     * @param byteLength
     */
    public ByteWrap(int byteLength){
        bytes = new byte[byteLength];
        size = 0;
    }

    /**
     * getSize
     * @return
     */
    public int size(){
        return size;
    }

    /**
     *
     * @return
     */
    public byte[] getBytes(){
        return this.bytes;
    }

    /**
     *
     * @param byt
     */
    public void addByte(byte byt){
        this.bytes[size] = byt;
        this.size += 1;
    }

    /**
     * clear
     */
    public void clear(){
        this.size = 0;
    }

}
