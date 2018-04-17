package com.vension.mvp.ui.activitys

import android.os.Bundle
import android.view.KeyEvent
import com.vension.frame.utils.AppUtil
import com.vension.frame.utils.VObsoleteUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_about.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/4 12:23
 * 描  述：关于
 * ========================================================
 */

class AboutActivity : BaseActivity(){

    private val url = "file:///android_asset/about.html"

    override fun attachLayoutRes(): Int {
        return R.layout.activity_about
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        initToolBar()
    }

    override fun requestLoadData() {
        webViewLayout.loadUrl(url)
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener({ v -> onBackPressed() })
        collapsingToolbarLayout.title = ""
        toolbar.title = ""
        collapsingToolbarLayout.setExpandedTitleColor(VObsoleteUtil.getColor(R.color.transparent))//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(VObsoleteUtil.getColor(R.color.transparent))//设置收缩后Toolbar上字体的颜色
        tv_title.text = getString(R.string.app_name) + " (" + AppUtil.getVersionName(this) + ")"
        appBarLayout.addOnOffsetChangedListener({ appBar, offset -> tv_title.alpha = Math.abs(offset * 1f / appBar.totalScrollRange) })
    }

    override fun onResume() {
        super.onResume()
        webViewLayout.onResume()
    }

    override fun onPause() {
        super.onPause()
        webViewLayout.onPause()
    }

    override fun onDestroy() {
        webViewLayout.onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && webViewLayout.goBack()) {
            true
        } else super.onKeyDown(keyCode, event)
    }
}