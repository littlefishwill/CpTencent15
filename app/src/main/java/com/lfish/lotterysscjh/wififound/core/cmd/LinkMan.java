package com.lfish.lotterysscjh.wififound.core.cmd;

import com.lfish.lotterysscjh.wififound.abroadcast.lanbroadcast.UdpBroadCast;

/**
 * Created by shenmegui on 2017/6/5.
 */
public class LinkMan extends UdpBroadCast {
    private boolean isNewOnLine;

    public boolean isNewOnLine() {
        return isNewOnLine;
    }

    public void setNewOnLine(boolean newOnLine) {
        isNewOnLine = newOnLine;
    }
}
