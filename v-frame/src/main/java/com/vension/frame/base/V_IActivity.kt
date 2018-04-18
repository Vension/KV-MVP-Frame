package com.vension.frame.base

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 14:47
 * 描  述：BaseActivity 的接口
 * ========================================================
 */

interface V_IActivity {

     fun startAgentActivity(_Class: Class<out Fragment>)
     fun startAgentActivity(_Class: Class<out Fragment>, bundle: Bundle)
     fun startAgentActivity(_Class: Class<*>, _ActivityOptionsCompat: ActivityOptionsCompat)
     fun startAgentActivity(_Class: Class<*>, bundle: Bundle, _ActivityOptionsCompat: ActivityOptionsCompat)

     fun startAgentActivityForResult(_Class: Class<out Fragment>, requestCode: Int)
     fun startAgentActivityForResult(_Class: Class<out Fragment>, bundle: Bundle, requestCode: Int)
     fun startAgentActivityForResult(_Class: Class<out Fragment>, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat)
     fun startAgentActivityForResult(_Class: Class<out Fragment>, bundle: Bundle, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat)

}