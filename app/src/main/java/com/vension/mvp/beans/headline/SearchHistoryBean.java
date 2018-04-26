package com.vension.mvp.beans.headline;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/24 15:48
 * 描  述：
 * ========================================================
 */

public class SearchHistoryBean {

    private String keyWord;
    private String time;
    private int type;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
