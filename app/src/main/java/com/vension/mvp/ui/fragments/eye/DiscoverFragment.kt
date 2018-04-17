package com.vension.mvp.ui.fragments.eye

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.vension.frame.adapters.BaseFragmentAdapter
import com.vension.frame.utils.VObsoleteUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_eye_discover.*
import java.util.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/12 16:28
 * 描  述：开眼·发现
 * ========================================================
 */

class DiscoverFragment : BaseFragment(){

    private val mFragments = Arrays.asList(FollowFragment(),CategoryFragment())
    private val mTitle = Arrays.asList("关注", "分类")

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_eye_discover
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setBackgroundColor(VObsoleteUtil.getColor(R.color.app_main_thme_color))
        mCommonTitleBar.leftImageButton.visibility = View.GONE
        mCommonTitleBar.centerTextView.text = "发现"
        mCommonTitleBar.centerTextView.setTextColor(VObsoleteUtil.getColor(R.color.white))
    }


    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        vp_eye_discover.adapter = BaseFragmentAdapter(childFragmentManager, mFragments as List<Fragment>)
        /**
         * 把 TabIndicator 跟viewpager关联起来
         */
        line_indicator.setViewPagerSwitchSpeed(vp_eye_discover, 600)
        line_indicator.setTabData(vp_eye_discover, mTitle) { position ->
            //顶部点击的方法公布出来
            vp_eye_discover.currentItem = position
        }
    }

    override fun lazyLoadData() {
    }

}