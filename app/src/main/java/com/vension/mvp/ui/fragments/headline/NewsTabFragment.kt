package com.vension.mvp.ui.fragments.headline

import android.os.Bundle
import android.support.v4.app.Fragment
import com.vension.frame.adapters.BaseFragmentStatePagerAdapter
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.vension.mvp.ui.fragments.TestFragment
import kotlinx.android.synthetic.main.fragment_news_home_tab.*
import java.util.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/18 15:14
 * 描  述：头条·新闻
 * ========================================================
 */

class NewsTabFragment : BaseFragment() {

    private val mChannelList = ArrayList<String>()
    private val fragments = ArrayList<Fragment>()

    override fun getToolBarResId(layout: Int): Int {
        return R.layout.layout_toolbar_new_home
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_news_home_tab
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        val channels = resources.getStringArray(R.array.channel_video)
        val channelCodes = resources.getStringArray(R.array.channel_code_video)
        for (i in channels) {
            fragments.add(TestFragment.getInstance(i))
        }
        //给viewPager设置适配器
        vp_new_video_tab.adapter = BaseFragmentStatePagerAdapter(childFragmentManager,fragments,channels)
        //TabLayout绑定ViewPager
        new_home_tablayout.setupWithViewPager(vp_new_video_tab)
    }


    override fun lazyLoadData() {

    }

    private fun initChannelData() {

//        for (i in channelCodes.indices) {
//            val title = channels[i]
//            val code = channelCodes[i]
//            mChannelList.add(Channel(title, code))
//        }
    }

//    private fun initChannelFragments() {
//        for (channel in mChannelList) {
//            val newsFragment = NewsListFragment()
//            val bundle = Bundle()
//            bundle.putString(Constant.CHANNEL_CODE, channel.channelCode)
//            bundle.putBoolean(Constant.IS_VIDEO_LIST, true)//是否是视频列表页面,]true
//            newsFragment.setArguments(bundle)
//            mFrgamentList.add(newsFragment)//添加到集合中
//        }
//    }


}