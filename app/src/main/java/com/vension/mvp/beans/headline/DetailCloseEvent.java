package com.vension.mvp.beans.headline;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/24 10:00
 * 描  述：
 * ========================================================
 */

public class DetailCloseEvent {

    private String channelCode;
    private int position;
    private int progress;
    private int commentCount;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
