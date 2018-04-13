package com.lfish.lotteryssc.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenmegui on 2018/4/12.
 */
public class News {
    private String  retcode;
    private String  appCode;
    private String  pageToken;
    private String  dataType;
    private List<New> data = new ArrayList<>();

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getPageToken() {
        return pageToken;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<New> getData() {
        return data;
    }

    public void setData(List<New> data) {
        this.data = data;
    }

    public class New{
        private String title;
        private String content;
        private String publishDateStr;
        private List<String> imageUrls = new ArrayList<>();
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPublishDateStr() {
            return publishDateStr;
        }

        public void setPublishDateStr(String publishDateStr) {
            this.publishDateStr = publishDateStr;
        }

        public List<String> getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(List<String> imageUrls) {
            this.imageUrls = imageUrls;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
