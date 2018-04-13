package com.lfish.lotteryssc.dao;

/**
 * Created by shenmegui on 2018/2/27.
 */
public class YYEnter {
    private String name;
    private String code;
    private int drawable;

    public YYEnter(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public YYEnter(String name, String code,int drawable) {
        this.name = name;
        this.code = code;
        this.drawable = drawable;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
