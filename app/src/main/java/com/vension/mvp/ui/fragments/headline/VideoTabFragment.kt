package com.vension.mvp.ui.fragments.headline

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.View
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.vension.mvp.beans.headline.ChannelBean
import com.vension.mvp.showToast
import com.vension.mvp.ui.activitys.headline.SearchActivity2
import com.vension.mvp.ui.adapters.headline.ChannelPagerAdapter
import com.vension.mvp.utils.Constants
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_news_video_tab.*
import java.util.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/18 15:14
 * 描  述：头条·视频
 * ========================================================
 */

class VideoTabFragment : BaseFragment() {

    private val mChannelList = ArrayList<ChannelBean>()
    private val mFrgamentList = ArrayList<Fragment>()

    override fun getToolBarResId(layout: Int): Int {
        return R.layout.layout_toolbar_new_home
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setListener { _: View?, action: Int, extra: String? ->
            when(action){
                CommonTitleBar.ACTION_LEFT_BUTTON, CommonTitleBar.ACTION_LEFT_TEXT -> {
                    showToast("登录")
                }
                CommonTitleBar.ACTION_RIGHT_BUTTON, CommonTitleBar.ACTION_RIGHT_TEXT -> {
                    showToast("发布")
                }
            }
        }
        mCommonTitleBar.centerCustomView.setOnClickListener {
            showToast("搜索")
            startActivity(Intent(activity, SearchActivity2::class.java))
        }
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_news_video_tab
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        initChannelData()
        initChannelFragments()
        initViewPagerTab()
    }


    override fun lazyLoadData() {

    }

    private fun initChannelData() {
        val channels = resources.getStringArray(R.array.channel_video)
        val channelCodes = resources.getStringArray(R.array.channel_code_video)
        for (i in channelCodes.indices) {
            val title = channels[i]
            val code = channelCodes[i]
            mChannelList.add(ChannelBean(title, code))
        }
    }

    private fun initChannelFragments() {
        for (channel in mChannelList) {
            val newsFragment = NewsListFragment()
            val bundle = Bundle()
            bundle.putString(Constants.CHANNEL_CODE, channel.channelCode)
            bundle.putBoolean(Constants.IS_VIDEO_LIST, true)//是否是视频列表页面,]true
            newsFragment.arguments = bundle
            mFrgamentList.add(newsFragment)//添加到集合中
        }
    }

    private fun initViewPagerTab() {
        //给viewPager设置适配器
        vp_new_video_tab.adapter = ChannelPagerAdapter(childFragmentManager,mFrgamentList,mChannelList)
        //TabLayout绑定ViewPager
        new_video_tablayout.setupWithViewPager(vp_new_video_tab)

        vp_new_video_tab.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                //当页签切换的时候，如果有播放视频，则释放资源
                GSYVideoManager.releaseAllVideos()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }


    fun getCurrentChannelCode(): String {
        val currentItem = vp_new_video_tab.currentItem
        return mChannelList[currentItem].channelCode
    }



}