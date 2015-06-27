/**
 * @FileName: BitArray.java
 * @Package: com.ziroom.zcode.bigfile
 * @author sence
 * @created 6/27/2015 6:35 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.zcode.bigfile;

/**
 * <p></p>
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
public class BitArray {

    private int[] bits = null;
    private int length;
    //用于设置或者提取int类型的数据的某一位(bit)的值时使用
    private final static int[] bitValue = {
            0x80000000,//10000000 00000000 00000000 00000000
            0x40000000,//01000000 00000000 00000000 00000000
            0x20000000,//00100000 00000000 00000000 00000000
            0x10000000,//00010000 00000000 00000000 00000000
            0x08000000,//00001000 00000000 00000000 00000000
            0x04000000,//00000100 00000000 00000000 00000000
            0x02000000,//00000010 00000000 00000000 00000000
            0x01000000,//00000001 00000000 00000000 00000000
            0x00800000,//00000000 10000000 00000000 00000000
            0x00400000,//00000000 01000000 00000000 00000000
            0x00200000,//00000000 00100000 00000000 00000000
            0x00100000,//00000000 00010000 00000000 00000000
            0x00080000,//00000000 00001000 00000000 00000000
            0x00040000,//00000000 00000100 00000000 00000000
            0x00020000,//00000000 00000010 00000000 00000000
            0x00010000,//00000000 00000001 00000000 00000000
            0x00008000,//00000000 00000000 10000000 00000000
            0x00004000,//00000000 00000000 01000000 00000000
            0x00002000,//00000000 00000000 00100000 00000000
            0x00001000,//00000000 00000000 00010000 00000000
            0x00000800,//00000000 00000000 00001000 00000000
            0x00000400,//00000000 00000000 00000100 00000000
            0x00000200,//00000000 00000000 00000010 00000000
            0x00000100,//00000000 00000000 00000001 00000000
            0x00000080,//00000000 00000000 00000000 10000000
            0x00000040,//00000000 00000000 00000000 01000000
            0x00000020,//00000000 00000000 00000000 00100000
            0x00000010,//00000000 00000000 00000000 00010000
            0x00000008,//00000000 00000000 00000000 00001000
            0x00000004,//00000000 00000000 00000000 00000100
            0x00000002,//00000000 00000000 00000000 00000010
            0x00000001 //00000000 00000000 00000000 00000001
    };

    public BitArray(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length必须大于零！");
        }
        bits = new int[length / 32 + (length % 32 > 0 ? 1 : 0)];
        this.length = length;
    }

    //取index位的值
    public int getBit(int index) {
        if (index < 0 || index > length) {
            throw new IllegalArgumentException("length必须大于零小于" + length);
        }
        int intData = bits[index / 32];
        return (intData & bitValue[index % 32]) >>> (32 - index % 32 - 1);
    }

    //设置index位的值，只能为0或者1
    public void setBit(int index, int value) {
        if (index < 0 || index > length) {
            throw new IllegalArgumentException("length必须大于零小于" + length);
        }
        if (value != 1 && value != 0) {
            throw new IllegalArgumentException("value必须为0或者1");
        }
        int intData = bits[index / 32];
        if (value == 1) {
            bits[index / 32] = intData | bitValue[index % 32];
        } else {
            bits[index / 32] = intData & ~bitValue[index % 32];
        }
    }

    public int getLength() {
        return length;
    }

}
