/**
 * @FileName: AnalysisIPLoader.java
 * @Package: com.ziroom.zcode.nginxlog.analysis
 * @author sence
 * @created 6/25/2015 8:51 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.zcode.nginxlog.analysis;

import com.ziroom.zcode.common.util.Check;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
public class AnalysisIPLoader {

    /**
     * 加载analysisIP
     *
     * @param filePath
     * @return
     */
    public Map<String, String> loadAnalysisIP(String filePath) throws IOException {
        if (Check.isBlankStr(filePath)) {
            throw new FileNotFoundException("file path is null");
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("file was not found by path:" + filePath);
        }
        BufferedReader reader = null;
        Map<String, String> ipmap = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            ipmap = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                ipmap.put(line, line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return ipmap;
    }

}
