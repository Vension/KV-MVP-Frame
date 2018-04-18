package com.vension.mvp.http.exception

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/3 9:38
 * 描  述：
 * ========================================================
 */

class ApiException : RuntimeException {

    private var code: Int? = null


    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))
}