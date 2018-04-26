package com.vension.mvp.ui.fragments.gank

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.vension.frame.adapters.BaseFragmentAdapter
import com.vension.frame.utils.VObsoleteUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_gank_main.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/4 13:51
 * 描  述：干货集中营主入口
 * ========================================================
 */

class GankMainFragment : BaseFragment() {

    private val tabList = ArrayList<String>()
    private val fragments = ArrayList<Fragment>()

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setBackgroundColor(VObsoleteUtil.getColor(R.color.app_main_thme_color))
        mCommonTitleBar.leftImageButton.visibility = View.GONE
        mCommonTitleBar.centerTextView.text = "干货集中营"
        mCommonTitleBar.centerTextView.setTextColor(VObsoleteUtil.getColor(R.color.white))
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_gank_main
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        tabList.add("Android")
        tabList.add("iOS")
        tabList.add("福利")
        fragments.add(TechFragment.newInstance("Android"))
        fragments.add(TechFragment.newInstance("iOS"))
        fragments.add(WelfareFragment.newInstance("福利"))

        //getSupportFragmentManager() 替换为getChildFragmentManager()
        gank_viewpager.adapter = BaseFragmentAdapter(childFragmentManager,fragments,tabList)
        gank_tablayout.setupWithViewPager(gank_viewpager)
//        TabLayoutHelper.setUpIndicatorWidth(gank_tablayout)
    }


    override fun lazyLoadData() {

    }

}