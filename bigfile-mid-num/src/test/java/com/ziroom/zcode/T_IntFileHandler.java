package com.ziroom.zcode;

import com.ziroom.zcode.bigfile.handler.IntFileHandler;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * Created by sence on 2015/6/28.
 */
public class T_IntFileHandler {

    @Test
    public void testInt() throws ExecutionException, InterruptedException {
        String[] files = new String[20];
        String fileDir = "E:\\intfile\\";
        for (int i = 0; i < files.length; i++) {
            files[i] = fileDir.concat("file").concat(String.valueOf(i)).concat(".txt");
        }
        IntFileHandler fileHandler = new IntFileHandler(files.length);
        long num = fileHandler.sumMidNum(files);
        System.out.println(num);
    }

}
