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
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class Check {


    /**
     * 判断对象是否为空
     *
     * @param obj
     * @return boolean
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 判断是否是空字符串
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
     * 判断是否是空集合
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

    public static boolean isFileExist(String path){
        if(isNull(path)) return false;
        File file = new File(path);
        if(!file.exists()) return false;
        file = null;
        return true;
    }

    /**
     * 判断是否是空数组
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
