/**
 * @FileName: T_NginxLogReader.java
 * @Package: com.ziroom.zcode
 * @author sence
 * @created 6/27/2015 2:10 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.zcode;

import com.ziroom.zcode.nginxlog.analysis.NginxLogHandler;
import com.ziroom.zcode.nginxlog.analysis.NginxLogReader;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

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
public class T_NginxLogReader {

    @Test
    public void testLogReader() throws IOException {
        NginxLogReader nginxLogReader = new NginxLogReader();
        nginxLogReader.readFromLogFile("F:\\nginxlog\\access_log2.log","F:\\nginxlog\\result.txt","F:\\nginxlog\\ip.txt");
    }

    @Test
    public void testNginxLog() throws InterruptedException, ExecutionException, IOException {
        long t1 = System.currentTimeMillis();
        NginxLogHandler handler = new NginxLogHandler();
        handler.handlerNginxLog("E:\\nginxlog\\access_log.log");
        System.out.println(System.currentTimeMillis()-t1);
    }

}
