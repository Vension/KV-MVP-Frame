package com.vension.mvp.ui.activitys

import android.Manifest
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.KeyEvent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.vension.frame.VFrame
import com.vension.frame.utils.TimeUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseActivity
import com.vension.mvp.showToast
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 15:57
 * 描  述：启动页
 * ========================================================
 */

class SplashActivity : BaseActivity() {

    private var textTypeface : Typeface ?= null
    private var descTypeFace : Typeface ?= null
    private var alphaAnimation : AlphaAnimation ?= null

    init {
        textTypeface = Typeface.createFromAsset(VFrame.getContext().assets, "fonts/Lobster-1.4.otf")
        descTypeFace = Typeface.createFromAsset(VFrame.getContext().assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun isEnableSlideClose(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_splash
    }


    override fun initViewAndData(savedInstanceState: Bundle?) {
        tv_app_name.typeface = textTypeface
        tv_version.typeface = descTypeFace
        val welcome_hint = getString(R.string.welcome_hint_noVersion,"2014", TimeUtil.getNowString("yyyy"), "kevin~vension.com")
        tv_version.text = welcome_hint

        //渐变展示启动屏
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 3000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                redirectToMain()
            }

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationStart(animation: Animation) {}

        })
        layout_splash.startAnimation(alphaAnimation)
//        checkPermission()
    }

    override fun requestLoadData() {
    }



    /**
     * 6.0以下版本(系统自动申请) 不会弹框
     * 有些厂商修改了6.0系统申请机制，他们修改成系统自动申请权限了
     */
    private fun checkPermission(){
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                .subscribe { granted ->
                    if (granted) {
                        // All requested permissions are granted
                    } else {
                        // At least one permission is denied
                        showToast("用户关闭了权限")
                    }
                }
    }

    fun redirectToMain() {
        val intent = Intent(this, MainMeunActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        alphaAnimation?.cancel()
    }

    //设置点击返回键不响应
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else super.onKeyDown(keyCode, event)
    }

}
