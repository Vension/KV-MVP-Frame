package com.vension.frame.base

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 14:50
 * 描  述：
 * ========================================================
 */

interface V_IFragment {

     fun startAgentActivity(_Class: Class<*>)
     fun startAgentActivity(_Class: Class<*>, bundle: Bundle)

     fun startAgentActivity(_Class: Class<*>, _ActivityOptionsCompat: ActivityOptionsCompat)
     fun startAgentActivity(_Class: Class<*>, bundle: Bundle, _ActivityOptionsCompat: ActivityOptionsCompat)

     fun startAgentActivityForResult(_Class: Class<*>, requestCode: Int)
     fun startAgentActivityForResult(_Class: Class<*>, bundle: Bundle, requestCode: Int)
     fun startAgentActivityForResult(_Class: Class<*>, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat)
     fun startAgentActivityForResult(_Class: Class<*>, bundle: Bundle, requestCode: Int, _ActivityOptionsCompat: ActivityOptionsCompat)

}