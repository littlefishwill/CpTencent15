package com.lfish.lotterysscjh.wififound.core.cmd;


import com.lfish.lotterysscjh.wififound.abroadcast.lanbroadcast.UdpBroadCast;
import com.lfish.lotterysscjh.wififound.dao.FileWifiWebDes;

/**
 * Created by shenmegui on 2017/6/29.
 */
public class BroadCastFilePlay extends UdpBroadCast {
    private FileWifiWebDes.FileWifiWeb fileWifiWeb;
    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public FileWifiWebDes.FileWifiWeb getFileWifiWeb() {
        return fileWifiWeb;
    }

    public void setFileWifiWeb(FileWifiWebDes.FileWifiWeb fileWifiWeb) {
        this.fileWifiWeb = fileWifiWeb;
    }

}
