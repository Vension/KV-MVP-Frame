package com.vension.mvp.utils;

import android.support.annotation.NonNull;

import com.vension.mvp.BuildConfig;

import io.reactivex.functions.Consumer;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/24 15:23
 * 描  述：
 * ========================================================
 */

public class ErrorAction {

    @NonNull
    public static Consumer<Throwable> error() {
        return throwable -> {
            if (BuildConfig.DEBUG) {
                throwable.printStackTrace();
            }
        };
    }

    public static void print(@NonNull Throwable throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace();
        }
    }
}
