package com.vension.frame.utils.log;


import android.text.TextUtils;
import com.vension.frame.VFrame;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/30 16:16
 * 描  述：
 * ========================================================
 */

public class VLogConfig {

    private boolean showThreadInfo = true;
    private boolean debug = VFrame.isDebug;
    private String tag = VFrame.TAG;


    public VLogConfig setTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            this.tag = tag;
        }
        return this;
    }

    public VLogConfig setShowThreadInfo(boolean showThreadInfo) {
        this.showThreadInfo = showThreadInfo;
        return this;
    }


    public VLogConfig setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }
}
