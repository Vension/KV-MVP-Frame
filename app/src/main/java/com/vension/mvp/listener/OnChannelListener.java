package com.vension.mvp.listener;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 12:08
 * 描  述：
 * ========================================================
 */

public interface OnChannelListener {
    void onItemMove(int starPos, int endPos);
    void onMoveToMyChannel(int starPos, int endPos);
    void onMoveToOtherChannel(int starPos, int endPos);
}
