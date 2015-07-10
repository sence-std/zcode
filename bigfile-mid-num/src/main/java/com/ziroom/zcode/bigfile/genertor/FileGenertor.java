package com.ziroom.zcode.bigfile.genertor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by sence on 2015/6/27.
 */
public class FileGenertor {


    /**
     * 生产文件
     *
     * @param files
     * @return
     */
    public boolean genertorIntFile(String[] files, String lineSeparator) throws IOException {
        Random random = new Random();
        for (String fileName : files) {
            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(file);
            for (int i = 0; i < 80000000; i++) {
                int randomValue = random.nextInt(Integer.MAX_VALUE);
                fileWriter.write(randomValue + lineSeparator);
            }
            fileWriter.flush();
            fileWriter.close();
        }
        return true;
    }

}
