/**
 * @FileName: Check.java
 * @Package: com.ziroom.zcode.common.util
 * @author sence
 * @created 6/25/2015 8:55 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.zcode.common.util;

import java.io.File;
import java.util.Collection;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	�޸ļ�¼
 * <BR>-----------------------------------------------
 * <BR>	�޸�����			�޸���			�޸�����
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class Check {


    /**
     * �ж϶����Ƿ�Ϊ��
     *
     * @param obj
     * @return boolean
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * �ж��Ƿ��ǿ��ַ���
     *
     * @param str
     * @return
     */
    public static boolean isBlankStr(String str) {
        if (isNull(str)) {
            return true;
        }
        if ("".equals(str)) {
            return true;
        }
        return false;
    }

    /**
     * �ж��Ƿ��ǿռ���
     *
     * @param collect
     * @return
     */
    public static boolean isEmptyCollection(Collection collect) {
        if (isNull(collect)) {
            return true;
        }
        if (collect.size() == 0) {
            return true;
        }
        if (collect.contains(null)) {
            return true;
        }
        return false;
    }

    public static boolean isFileExist(String path) {
        if (isNull(path)) return false;
        File file = new File(path);
        if (!file.exists()) return false;
        file = null;
        return true;
    }

    /**
     * �ж��Ƿ��ǿ�����
     *
     * @param objs
     * @return
     */
    public static boolean isEmptyArray(Object[] objs) {
        if (isNull(objs)) {
            return true;
        }
        if (objs.length == 0) {
            return true;
        }
        return false;
    }
}
