package com.ziroom.zcode;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by sence on 2015/6/28.
 */
public class T_StringWrap {

    @Test
    public void test(){
        String str = "adsfdf\nasdsfdsf\nadasdasd\n";
        String[] sq = str.split("\n");
        Assert.assertEquals(sq.length,3);
        Assert.assertEquals(true,str.endsWith("\n"));

    }

}
