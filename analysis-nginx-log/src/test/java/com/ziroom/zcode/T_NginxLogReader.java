/**
 * @FileName: T_NginxLogReader.java
 * @Package: com.ziroom.zcode
 * @author sence
 * @created 6/27/2015 2:10 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.zcode;

import com.ziroom.zcode.nginxlog.analysis.NginxLogReader;
import org.junit.Test;

import java.io.IOException;

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
public class T_NginxLogReader {

    @Test
    public void testLogReader() throws IOException {
        NginxLogReader nginxLogReader = new NginxLogReader();
        nginxLogReader.readFromLogFile("F:\\nginxlog\\access_log2.log","F:\\nginxlog\\result.txt","F:\\nginxlog\\ip.txt");
    }

}
