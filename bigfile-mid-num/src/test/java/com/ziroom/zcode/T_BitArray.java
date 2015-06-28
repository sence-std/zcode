/**
 * @FileName: T_BitArray.java
 * @Package: com.ziroom.zcode
 * @author sence
 * @created 6/27/2015 6:37 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.zcode;

import com.ziroom.zcode.bigfile.handler.BitArray;
import org.junit.Test;

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
public class T_BitArray {

    @Test
    public void test() {
        int i = Integer.MAX_VALUE;
        BitArray bitArray = new BitArray(i);
        bitArray.setBit(123433, 1);
        bitArray.setBit(123434, 1);

        System.out.println(bitArray.getBit(123433));
        System.out.println(bitArray.getBit(123435));

    }


    @Test
    public void test01() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (i == Integer.MAX_VALUE >> 1) {
                System.out.println(i);
            }
        }
    }

    @Test
    public void test02() {
        System.out.println(Math.floor(2.3));
    }
}
