package com.vension.frame.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/9 11:06
 * 描  述：系统分享工具
 * ========================================================
 */

public class ShareUtil {

    private static final String EMAIL_ADDRESS = "Kevin-vension@foxmail.com";

    /**分享文本*/
    public static void shareText(@NonNull Context context, @NonNull String shareText,String title) {
        Intent shareIntent = new Intent()
                .setAction(Intent.ACTION_SEND)
                .setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, shareText);
        context.startActivity(Intent.createChooser(shareIntent, title));
    }

    /**分享图片*/
    public static void shareImage(@NonNull Context context, @NonNull Uri uri,String title) {
        Intent shareIntent = new Intent()
                .setAction(Intent.ACTION_SEND)
                .setType("image/*")
                .putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(shareIntent,title));
    }

    public static void sendEmail(Context context, String title) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(
                "mailto:" + EMAIL_ADDRESS));
        context.startActivity(Intent.createChooser(intent, title));
    }

}
