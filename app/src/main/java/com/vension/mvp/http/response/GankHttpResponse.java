package com.vension.mvp.http.response;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/4 15:32
 * 描  述：Gank 接口返回的数据格式
 * ========================================================
 */

public class GankHttpResponse<T> {

    private boolean error;
    private T results;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
