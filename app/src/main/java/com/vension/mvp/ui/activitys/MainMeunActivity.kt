package com.vension.mvp.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.vension.frame.utils.ShareUtil
import com.vension.frame.utils.VObsoleteUtil
import com.vension.frame.utils.VToastUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseActivity
import com.vension.mvp.showToast
import com.vension.mvp.ui.fragments.agent.MultipleItemTestFragment
import com.vension.mvp.ui.fragments.eye.EyeMainFragment
import com.vension.mvp.ui.fragments.eye.WatchHistoryFragment
import com.vension.mvp.ui.fragments.gank.GankMainFragment
import com.vension.mvp.ui.fragments.gank.LikeFragment2
import com.vension.mvp.ui.fragments.headline.HeadLineNewsMainFragment
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.activity_main_meun.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/4 10:33
 * 描  述：主菜单
 * ========================================================
 */

class MainMeunActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener{

    private var mTabFragment1 : HeadLineNewsMainFragment? = null //新闻
    private var mTabFragment2 : EyeMainFragment? = null //开眼
    private var mTabFragment3 : GankMainFragment? = null //Gank

    //默认为0
    private var mIndex = 0

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

    override fun isEnableSlideClose(): Boolean {
        return false
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        super.initToolBar(mCommonTitleBar)
        mCommonTitleBar.setBackgroundColor(VObsoleteUtil.getColor(R.color.app_main_thme_color))
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_main_meun
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        //设置MenuItem默认选中项
        nav_view.menu.getItem(0).isChecked = true
        nav_view.setNavigationItemSelectedListener(this)
        switchFragment(mIndex)
    }

    override fun requestLoadData() {

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_news ->{
                switchFragment(0)
            }
            R.id.nav_eyes -> {
                switchFragment(1)
            }
            R.id.nav_gank -> {
                switchFragment(2)
            }
            R.id.nav_favorite -> {
                startAgentActivity(LikeFragment2::class.java)
            }
            R.id.nav_watch_history -> {
                startAgentActivity(WatchHistoryFragment::class.java)
            }
            R.id.nav_share -> {
                ShareUtil.shareText(this, getString(R.string.share_app_text) + getString(R.string.source_code_url),getString(R.string.app_name))
            }
            R.id.nav_send -> {
                startAgentActivity(MultipleItemTestFragment::class.java)
                VToastUtil.showSuccess("测试多item")
            }
            R.id.nav_about -> {
                startActivity(Intent(this@MainMeunActivity, AboutActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    /**
     * 切换Fragment
     * @param position 下标
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 -> mTabFragment1?.let {
                transaction.show(it)
            } ?: HeadLineNewsMainFragment().let {
                mTabFragment1 = it
                transaction.add(R.id.main_contentlayout, it, "nav_news")
            }
            1 -> mTabFragment2?.let {
                transaction.show(it)
            } ?: EyeMainFragment().let {
                mTabFragment2 = it
                transaction.add(R.id.main_contentlayout, it, "nav_eyes")
            }
            2 -> mTabFragment3?.let {
                transaction.show(it)
            } ?: GankMainFragment().let {
                mTabFragment3 = it
                transaction.add(R.id.main_contentlayout, it, "nav_gank")
            }
            else -> {
                transaction.add(R.id.main_contentlayout, Fragment(), "agent")
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
        mTabFragment1?.let { transaction.hide(it) }
        mTabFragment2?.let { transaction.hide(it) }
        mTabFragment3?.let { transaction.hide(it) }
    }


    private var exitTime: Long = 0
    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if (currentTime - exitTime < 2000) {
                super.finish()
            } else {
                showToast("再按一次退出程序")
                exitTime = currentTime
            }
        }
    }


}