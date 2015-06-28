/**
 * @FileName: StringWrap.java
 * @Package: com.ziroom.zcode.common.util
 * @author sence
 * @created 6/28/2015 5:54 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.zcode.common.util;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @since 1.0
 * @version 1.0
 */
public class StringWrap {

    private StringBuffer stringBuffer;

    /**
     * 添加str
     * @param str
     */
    public void addString(String str){
        stringBuffer.append(str);
    }

    /**
     * 清空
     */
    public void clear(){
        stringBuffer = new StringBuffer();
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty(){
        return stringBuffer.length() == 0;
    }

}
