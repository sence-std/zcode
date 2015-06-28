package com.ziroom.zcode;

import com.ziroom.zcode.bigfile.genertor.FileGenertor;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by sence on 2015/6/27.
 */
public class T_FileGenertor {

    @Test
    public void testGenertor() throws IOException {
        FileGenertor fileGenertor = new FileGenertor();
        String[] files = new String[2];
        String fileDir = "F:\\intfile\\";
        for (int i = 0; i < files.length; i++) {
            files[i] = fileDir.concat("file").concat(String.valueOf(i)).concat(".txt");
        }
        fileGenertor.genertorIntFile(files, "\n");
    }


}
