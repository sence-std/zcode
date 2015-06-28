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

    /**
     * ���str
     * @param str
     */
    public void addString(String str){
        stringBuffer.append(str);
    }

    /**
     * ���
     */
    public void clear(){
        stringBuffer = new StringBuffer();
    }

    /**
     * �Ƿ�Ϊ��
     * @return
     */
    public boolean isEmpty(){
        return stringBuffer.length() == 0;
    }

}
