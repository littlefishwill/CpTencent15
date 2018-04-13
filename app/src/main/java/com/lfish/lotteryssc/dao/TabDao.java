package com.lfish.lotteryssc.dao;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by shenmegui on 2018/3/9.
 */
public class TabDao implements CustomTabEntity {

    private String titles;
    private int icon;
    private int icos;

    public TabDao(String titles, int icon, int icos) {
        this.titles = titles;
        this.icon = icon;
        this.icos = icos;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIcos() {
        return icos;
    }

    public void setIcos(int icos) {
        this.icos = icos;
    }

    @Override
    public String getTabTitle() {
        return titles;
    }

    @Override
    public int getTabSelectedIcon() {
        return icos;
    }

    @Override
    public int getTabUnselectedIcon() {
        return icon;
    }
}
