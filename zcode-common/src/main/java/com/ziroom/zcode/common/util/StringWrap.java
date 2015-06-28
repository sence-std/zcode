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
 * <BR>	�޸ļ�¼
 * <BR>-----------------------------------------------
 * <BR>	�޸�����			�޸���			�޸�����
 * </PRE>
 *
 * @author sence
 * @since 1.0
 * @version 1.0
 */
public class StringWrap {

    private StringBuffer stringBuffer;

    public StringWrap(){
        stringBuffer = new StringBuffer();
    }
    /**
     * 添加str
     * @param str
     */
    public String addString(String str){
       return stringBuffer.append(str).toString();
    }

    /**
     * 取得值
     * @return
     */
    public String getString(){
        return stringBuffer.toString();
    }
    /**
     * 清空
     */
    public void clear(){
        stringBuffer = new StringBuffer();
    }

    /**
     * 判空
     * @return
     */
    public boolean isEmpty(){
        return stringBuffer==null || stringBuffer.length() == 0;
    }

}
