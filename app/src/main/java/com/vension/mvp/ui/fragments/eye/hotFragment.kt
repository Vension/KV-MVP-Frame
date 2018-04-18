package com.vension.mvp.ui.fragments.eye

import android.support.v4.app.Fragment
import android.view.View
import com.vension.frame.adapters.BaseFragmentAdapter
import com.vension.frame.utils.VObsoleteUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.vension.mvp.beans.eyes.TabInfoBean
import com.vension.mvp.mvp.eye.contract.HotTabContract
import com.vension.mvp.mvp.eye.presenter.HotTabPresenter
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_eye_hot.*
import java.util.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 16:07
 * 描  述：开眼·热门
 * ========================================================
 */

class hotFragment : BaseFragment(), HotTabContract.View{

    private val mPresenter by lazy { HotTabPresenter() }

    /**
     * 存放 tab 标题
     */
    private val mTabTitleList = ArrayList<String>()

    private val mFragmentList = ArrayList<Fragment>()

    init {
        mPresenter.attachView(this)
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_eye_hot
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setBackgroundColor(VObsoleteUtil.getColor(R.color.app_main_thme_color))
        mCommonTitleBar.leftImageButton.visibility = View.GONE
        mCommonTitleBar.centerTextView.text = "人气榜"
        mCommonTitleBar.centerTextView.setTextColor(VObsoleteUtil.getColor(R.color.white))
    }


    override fun lazyLoadData() {
        mPresenter.getTabInfo()
    }


    /**
     * 设置 TabInfo
     */
    override fun setTabInfo(tabInfoBean: TabInfoBean) {

        tabInfoBean.tabInfo.tabList.mapTo(mTabTitleList) { it.name }
        tabInfoBean.tabInfo.tabList.mapTo(mFragmentList) { RankFragment.getInstance(it.name,it.apiUrl) }

        vp_eye_hot.adapter = BaseFragmentAdapter(childFragmentManager, mFragmentList,mTabTitleList)
        /**
         * 把 TabIndicator 跟viewpager关联起来
         */
        line_indicator
        line_indicator.setViewPagerSwitchSpeed(vp_eye_hot, 600)
        line_indicator.setTabData(vp_eye_hot, mTabTitleList) { position ->
            //顶部点击的方法公布出来
            vp_eye_hot.currentItem = position
        }
    }

    override fun showError(errorMsg: String) {

    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


}