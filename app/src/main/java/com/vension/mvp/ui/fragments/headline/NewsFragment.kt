package com.vension.mvp.ui.fragments.headline

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.View
import com.vension.frame.adapters.BaseFragmentStatePagerAdapter
import com.vension.frame.utils.TabLayoutHelper
import com.vension.frame.utils.VLogUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.vension.mvp.db.sqlite.dao.NewsChannelDao
import com.vension.mvp.showToast
import com.vension.mvp.utils.Constants
import com.vension.mvp.utils.RxBus
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_news.*
import java.util.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/3 16:52
 * 描  述：新闻
 * ========================================================
 */

class NewsFragment : BaseFragment(){

     val TAG = "NewsTabLayout"
    private val dao = NewsChannelDao()
    private var fragmentList: MutableList<Fragment>? = null
    private var titleList: MutableList<String>? = null
    private var observable: Observable<Boolean>? = null
    private val map = HashMap<String, Fragment>()
    private var adapter: BaseFragmentStatePagerAdapter? = null

    override fun getToolBarResId(layout: Int): Int {
        return R.layout.layout_toolbar_news
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setListener {v: View?, action: Int, extra: String? ->
            if (action == CommonTitleBar.ACTION_RIGHT_BUTTON || action == CommonTitleBar.ACTION_RIGHT_TEXT) {
                showToast("搜索")
            }
        }
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_news
    }


    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        initView()
        initData()
        VLogUtil.e("NewsFragment == initViewAndData")
    }

    private fun initView() {
        news_tablayout.setupWithViewPager(news_viewpager)
        news_tablayout.tabMode = TabLayout.MODE_SCROLLABLE
        TabLayoutHelper.setUpIndicatorWidth(news_tablayout)
        add_channel_iv.setOnClickListener {
            showToast("添加频道")
        }
    }


    private fun initData() {
        initTabs()
        adapter = BaseFragmentStatePagerAdapter(childFragmentManager, fragmentList, titleList)
        news_viewpager.adapter = adapter
        news_viewpager.offscreenPageLimit = 15

        observable = RxBus.getInstance().register(TAG)
        observable?.subscribe({ isRefresh ->
            if (isRefresh!!) {
                initTabs()
                adapter?.recreateItems(fragmentList, titleList)
            }
        })
    }


    private fun initTabs() {
        var channelList = dao.query(1)
        fragmentList = ArrayList<Fragment>() as ArrayList<Fragment>
        titleList = ArrayList<String>() as ArrayList<String>
        if (channelList.size == 0) {
            dao.addInitData()
            channelList = dao.query(Constants.NEWS_CHANNEL_ENABLE)
        }

        for (bean in channelList) {

            var fragment: Fragment? = null
            val channelId = bean.channelId

            when (channelId) {
                "essay_joke" -> if (map.containsKey(channelId)) {
                    (fragmentList as ArrayList<Fragment>)?.add(map[channelId]!!)
                } else {
//                    fragment = JokeContentView.newInstance()
//                    fragmentList.add(fragment)
                }
                "question_and_answer" -> if (map.containsKey(channelId)) {
//                    fragmentList.add(map.get(channelId))
                } else {
//                    fragment = WendaArticleView.newInstance()
//                    fragmentList.add(fragment)
                }
                else -> if (map.containsKey(channelId)) {
//                    fragmentList?.add(map.get(channelId))
                } else {
//                    fragment = NewsArticleView.newInstance(channelId)
//                    fragmentList.add(fragment)
                }
            }

            titleList?.add(bean.channelName)

            if (fragment != null) {
                map[channelId] = fragment
            }
        }
    }

//    fun onDoubleClick() {
//        if (titleList != null && titleList.size > 0 && fragmentList != null && fragmentList.size > 0) {
//            val item = viewPager.getCurrentItem()
//            (fragmentList.get(item) as BaseListFragment).onRefresh()
//        }
//    }

    override fun lazyLoadData() {

    }


    override fun onDestroy() {
        observable?.let { RxBus.getInstance().unregister(TAG, it) }
        super.onDestroy()
    }


}