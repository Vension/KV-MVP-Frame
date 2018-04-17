package com.vension.mvp.ui.activitys

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.vension.frame.utils.VObsoleteUtil
import com.vension.frame.utils.navbar.BottomNavigationViewHelper
import com.vension.mvp.R
import com.vension.mvp.base.BaseActivity
import com.vension.mvp.showToast
import com.vension.mvp.ui.fragments.headline.HeadlineNumFragment
import com.vension.mvp.ui.fragments.TestFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_main.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/3 12:08
 * 描  述：主界面
 * ========================================================
 */

class MainActivity : BaseActivity()  {

//    private var _TabFragment_1 : NewsFragment? = null //新闻
    private var _TabFragment_1 : TestFragment? = null //新闻
    private var _TabFragment_2 : TestFragment? = null //图片
    private var _TabFragment_3 : TestFragment? = null //视频
    private var _TabFragment_4 : HeadlineNumFragment? = null //头条号

    //默认为0
    private var mIndex = 0

    override fun isEnableSlideClose(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex",0)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //记录fragment的位置,防止崩溃 activity被系统回收时，fragment错乱
        outState.putInt("currTabIndex", mIndex)
    }


    override fun attachLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        mCommonTitleBar.setBackgroundColor(VObsoleteUtil.getColor(R.color.app_main_thme_color))
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(bottom_navigation)
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        switchFragment(mIndex)
        bottom_navigation.menu.getItem(mIndex).isChecked = true
    }


    override fun requestLoadData() {
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_news -> {
                switchFragment(0)
            }
            R.id.action_photo -> {
                switchFragment(1)
            }
            R.id.action_video -> {
                switchFragment(2)
            }
            R.id.action_media -> {
                switchFragment(3)
            }
        }
        true
    }



    /**
     * 切换Fragment
     * @param position 下标
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
        // 新闻
            0 -> _TabFragment_1?.let {
                transaction.show(it)
            } ?: TestFragment.getInstance("news").let {
                _TabFragment_1 = it
                transaction.add(R.id.main_content, it, "news")
            }
        //图片
            1 -> _TabFragment_2?.let {
                transaction.show(it)
            } ?: TestFragment.getInstance("discovery").let {
                _TabFragment_2 = it
                transaction.add(R.id.main_content, it, "discovery") }
        //视频
            2 -> _TabFragment_3?.let {
                transaction.show(it)
            } ?: TestFragment.getInstance("hot").let {
                _TabFragment_3 = it
                transaction.add(R.id.main_content, it, "hot") }
        //头条号
            3 -> _TabFragment_4?.let {
                transaction.show(it)
            } ?: HeadlineNumFragment().let {
                _TabFragment_4 = it
                transaction.add(R.id.main_content, it, "mine") }
            else -> {
                transaction.add(R.id.main_content, Fragment(), "agent")
            }
        }
        mIndex = position
        transaction.commitAllowingStateLoss()
    }


    /**
     * 隐藏所有的Fragment
     * @param transaction transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        _TabFragment_1?.let { transaction.hide(it) }
        _TabFragment_2?.let { transaction.hide(it) }
        _TabFragment_3?.let { transaction.hide(it) }
        _TabFragment_4?.let { transaction.hide(it) }
    }

    private var exitTime: Long = 0
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - exitTime < 2000) {
            super.onBackPressed()
        } else {
            showToast("再按一次退出程序")
            exitTime = currentTime
        }
    }

}
