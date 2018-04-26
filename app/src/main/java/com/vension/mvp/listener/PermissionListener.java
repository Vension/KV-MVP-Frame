package com.vension.mvp.listener;

import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 12:08
 * 描  述：
 * ========================================================
 */

public interface PermissionListener {

    void onGranted();

    void onDenied(List<String> deniedPermissions);
}
