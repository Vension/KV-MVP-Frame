package com.vension.mvp.beans.headline;

import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/24 15:55
 * 描  述：
 * ========================================================
 */

public class SearchSuggestionBean {
    /**
     * message : success
     * data : [{"keyword":"6 18小米6"},{"keyword":"618小米手机"},{"keyword":"618京东"},{"keyword":"6 18"},{"keyword":"618手机"},{"keyword":"618京东节"},{"keyword":"618电商大战"},{"keyword":"618电商节"},{"keyword":"618大促"},{"keyword":"618手机排行榜"}]
     */

    private String message;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * keyword : 6 18小米6
         */

        private String keyword;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
