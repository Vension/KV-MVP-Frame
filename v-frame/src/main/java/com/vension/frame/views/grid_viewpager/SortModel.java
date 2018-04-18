package com.vension.frame.views.grid_viewpager;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/18 18:00
 * 描  述：类型实体
 * ========================================================
 */

public class SortModel {

    public String name;
    public int iconRes;

    public SortModel(String name, int iconRes) {
        this.name = name;
        this.iconRes = iconRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }
}