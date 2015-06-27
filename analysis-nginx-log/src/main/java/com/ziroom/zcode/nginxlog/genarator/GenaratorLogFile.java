/**
 * @FileName: GenaratorLogFile.java
 * @Package: com.ziroom.zcode.nginxlog.genarator
 * @author sence
 * @created 6/25/2015 8:33 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.zcode.nginxlog.genarator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

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
public class GenaratorLogFile {

    public static void main(String[] args) {
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter("F:\\nginxlog\\access_log2.log", true);
            Random r = new Random();
            for (int i = 0; i < 100000000; i++) {
                int i1 = r.nextInt(254) + 1;
                int i2 = r.nextInt(254) + 1;
                int i3 = r.nextInt(254) + 1;
                int i4 = r.nextInt(254) + 1;
                String content = "" + i1 + "." + i2 + "." + i3 + "." + i4 + " - - [14/Jun/2015:05:00:05 +0800] 'GET /ops/?action=getServerSystemTraffic&create=1&server=172.16.4.197 HTTP/1.1' 200";
                writer.write(content + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
