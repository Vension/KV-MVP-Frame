package com.vension.mvp.beans.headline;

import org.litepal.crud.DataSupport;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 11:01
 * 描  述：用于记录获取到的新闻,用于上拉加载更多
 * ========================================================
 */

public class NewsRecordBean extends DataSupport{

    private String channelCode;
    private int page;
    private String json;
    private long time;

    public NewsRecordBean(){

    }

    public NewsRecordBean(String channelCode, int page, String json, long time) {
        this.channelCode = channelCode;
        this.page = page;
        this.json = json;
        this.time = time;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}
