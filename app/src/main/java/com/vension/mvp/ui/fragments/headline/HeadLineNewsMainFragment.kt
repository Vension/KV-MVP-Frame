package com.vension.mvp.ui.fragments.headline

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.vension.frame.utils.navbar.BottomNavigationViewHelper
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_headlines_main.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/17 15:15
 * 描  述：今日头条·主入口
 * ========================================================
 */

class HeadLineNewsMainFragment : BaseFragment() {

    private var _TabFragment_1 : NewsTabFragment? = null //首页新闻
    private var _TabFragment_2 : VideoTabFragment? = null //视频
    private var _TabFragment_3 : HeadlineNumFragment? = null //微头条
    private var _TabFragment_4 : MineFragment? = null //我的

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_headlines_main
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(news_bottom_navigation)
        news_bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        switchFragment(0)
        news_bottom_navigation.menu.getItem(0).isChecked = true
    }

    override fun lazyLoadData() {
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_news_home -> {
                switchFragment(0)
            }
            R.id.navigation_news_video -> {
                switchFragment(1)
            }
            R.id.navigation_news_media -> {
                switchFragment(2)
            }
            R.id.navigation_news_me -> {
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
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        hideFragments(transaction!!)
        when (position) {
            0 -> _TabFragment_1?.let {
                transaction.show(it)
            } ?: NewsTabFragment().let {
                _TabFragment_1 = it
                transaction.add(R.id.news_main_content, it, "new_home")
            }
            1 -> _TabFragment_2?.let {
                transaction.show(it)
            } ?: VideoTabFragment().let {
                _TabFragment_2 = it
                transaction.add(R.id.news_main_content, it, "new_video") }
            2 -> _TabFragment_3?.let {
                transaction.show(it)
            } ?: HeadlineNumFragment().let {
                _TabFragment_3 = it
                transaction.add(R.id.news_main_content, it, "new_media") }
        //头条号
            3 -> _TabFragment_4?.let {
                transaction.show(it)
            } ?: MineFragment().let {
                _TabFragment_4 = it
                transaction.add(R.id.news_main_content, it, "new_mine") }
            else -> {
                transaction.add(R.id.news_main_content, Fragment(), "agent")
            }
        }
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

}