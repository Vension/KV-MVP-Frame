package com.vension.mvp.beans.headline;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/23 15:54
 * 描  述：访问返回的response
 * ========================================================
 */

public class ResultResponse<T> {

    public String has_more;
    public String message;
    public String success;
    public T data;

    public ResultResponse(String more, String _message, T result) {
        has_more = more;
        message = _message;
        data = result;
    }
}
