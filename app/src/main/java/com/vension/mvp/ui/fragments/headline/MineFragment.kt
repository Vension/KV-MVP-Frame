package com.vension.mvp.ui.fragments.headline

import android.graphics.Typeface
import android.os.Bundle
import com.vension.frame.VFrame
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_new_mine.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/17 16:51
 * 描  述：头天·我的
 * ========================================================
 */

class MineFragment :BaseFragment() {

    private var mTextTypeface: Typeface? = null

    init {
        //细黑简体字体
        mTextTypeface = Typeface.createFromAsset(VFrame.getAssets(), "fonts/Lobster-1.4.otf")
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_new_mine
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        tv_new_user_name.typeface = mTextTypeface
    }

    override fun lazyLoadData() {
    }

}