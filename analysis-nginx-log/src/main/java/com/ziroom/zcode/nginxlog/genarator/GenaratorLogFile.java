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
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class GenaratorLogFile {

    public static void main(String[] args) {
        try {
            FileWriter writer = new FileWriter("F:\\nginxlog\\ip.txt", true);
            Random r = new Random();
            for (int i = 0; i < 10000; i++) {
                int i1 = r.nextInt(254) + 1;
                int i2 = r.nextInt(254) + 1;
                int i3 = r.nextInt(254) + 1;
                int i4 = r.nextInt(254) + 1;
                String content = i1 + "." + i2 + "." + i3 + "." + i4;
                writer.write(content + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
