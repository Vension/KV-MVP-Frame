package com.vension.mvp.db.realm.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/10 10:05
 * 描  述：用于数据库的bean 种类包括 新闻、技术、福利
 * ========================================================
 */

public class RealmLikeBean extends RealmObject implements Serializable,MultiItemEntity {

    public RealmLikeBean() { }

    @PrimaryKey
    private String id;

    private String image;

    private String title;

    private String url;

    private int type;

    private long time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int getItemType() {
        return type;
    }

}
