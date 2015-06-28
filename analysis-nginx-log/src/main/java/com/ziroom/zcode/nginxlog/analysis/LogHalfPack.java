package com.ziroom.zcode.nginxlog.analysis;

import com.ziroom.zcode.common.util.ByteWrap;
import com.ziroom.zcode.common.util.Check;

/**
 * Created by sence on 2015/6/28.
 */
public class LogHalfPack {

    private ByteWrap headerByte;
    private ByteWrap tailWrap;

    public ByteWrap getHeaderByte() {
        return headerByte;
    }

    public void setHeaderByte(ByteWrap headerByte) {
        this.headerByte = headerByte;
    }

    public ByteWrap getTailWrap() {
        return tailWrap;
    }

    public void setTailWrap(ByteWrap tailWrap) {
        this.tailWrap = tailWrap;
    }

    public boolean isHeaderNull(){
        return Check.isNull(headerByte);
    }
    public boolean isTailNull(){
        return Check.isNull(tailWrap);
    }
}
