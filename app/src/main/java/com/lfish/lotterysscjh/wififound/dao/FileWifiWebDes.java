package com.lfish.lotterysscjh.wififound.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenmegui on 2017/6/6.
 */
public class FileWifiWebDes implements Serializable {
    private String path;

    private List<FileWifiWeb> wifiWebList = new ArrayList<>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<FileWifiWeb> getWifiWebList() {
        return wifiWebList;
    }

    public void setWifiWebList(List<FileWifiWeb> wifiWebList) {
        this.wifiWebList = wifiWebList;
    }

    public class FileWifiWeb{
        private String name;
        private long length;
        private boolean isFile;
        private String path;
        private long lastModifyTime;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getLength() {
            return length;
        }

        public void setLength(long length) {
            this.length = length;
        }

        public boolean isFile() {
            return isFile;
        }

        public void setFile(boolean file) {
            isFile = file;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public long getLastModifyTime() {
            return lastModifyTime;
        }

        public void setLastModifyTime(long lastModifyTime) {
            this.lastModifyTime = lastModifyTime;
        }
    }

}
