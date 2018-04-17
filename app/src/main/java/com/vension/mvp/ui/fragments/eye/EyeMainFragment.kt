package com.vension.mvp.ui.fragments.eye

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.vension.frame.utils.navbar.BottomNavigationViewHelper
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_eye_main.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/9 14:58
 * 描  述：开眼主入口
 * ========================================================
 */

class EyeMainFragment : BaseFragment(){

    private var _TabFragment_1 : HomeFragment? = null //精选
    private var _TabFragment_2 : DiscoverFragment? = null //发现
    private var _TabFragment_3 : hotFragment? = null //热门
    private var _TabFragment_4 : MineFragment? = null //我的

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_eye_main
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        //默认 >3 的选中效果会影响ViewPager的滑动切换时的效果，故利用反射去掉
        BottomNavigationViewHelper.disableShiftMode(eye_bottom_navigation)
        eye_bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        switchFragment(0)
        eye_bottom_navigation.menu.getItem(0).isChecked = true
    }

    override fun lazyLoadData() {
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(0)
            }
            R.id.navigation_discover -> {
                switchFragment(1)
            }
            R.id.navigation_hot -> {
                switchFragment(2)
            }
            R.id.navigation_me -> {
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
            } ?: HomeFragment.getInstance("精选").let {
                _TabFragment_1 = it
                transaction.add(R.id.eye_main_content, it, "home")
            }
            1 -> _TabFragment_2?.let {
                transaction.show(it)
            } ?: DiscoverFragment().let {
                _TabFragment_2 = it
                transaction.add(R.id.eye_main_content, it, "discovery") }
            2 -> _TabFragment_3?.let {
                transaction.show(it)
            } ?: hotFragment().let {
                _TabFragment_3 = it
                transaction.add(R.id.eye_main_content, it, "hot") }
        //头条号
            3 -> _TabFragment_4?.let {
                transaction.show(it)
            } ?: MineFragment().let {
                _TabFragment_4 = it
                transaction.add(R.id.eye_main_content, it, "mine") }
            else -> {
                transaction.add(R.id.eye_main_content, Fragment(), "agent")
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