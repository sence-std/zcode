/**
 * @FileName: T_AnalysisIPLoader.java
 * @Package: com.ziroom.zcode
 * @author sence
 * @created 6/25/2015 9:11 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.zcode;

import com.ziroom.zcode.nginxlog.analysis.AnalysisIPLoader;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
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
public class T_AnalysisIPLoader {


    @Test
    public void testLoadIP() throws URISyntaxException, IOException {
        AnalysisIPLoader loader = new AnalysisIPLoader();
        Map<String, String> map = loader.loadAnalysisIP("F:\\nginxlog\\ip.txt");
        System.out.print(map);
    }

}
