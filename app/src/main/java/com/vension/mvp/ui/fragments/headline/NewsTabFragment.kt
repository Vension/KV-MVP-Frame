package com.vension.mvp.ui.fragments.headline

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.vension.frame.utils.VLogUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.vension.mvp.beans.headline.ChannelBean
import com.vension.mvp.listener.OnChannelListener
import com.vension.mvp.showToast
import com.vension.mvp.ui.activitys.headline.SearchActivity2
import com.vension.mvp.ui.adapters.headline.ChannelPagerAdapter
import com.vension.mvp.utils.Constants
import com.vension.mvp.utils.PreUtils
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_news_home_tab.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/18 15:14
 * 描  述：头条·新闻
 * ========================================================
 */

class NewsTabFragment : BaseFragment(), OnChannelListener {

    private val mSelectedChannels = ArrayList<ChannelBean>()
    private val mUnSelectedChannels = ArrayList<ChannelBean>()
    private val mChannelFragments = ArrayList<Fragment>()
    private val mGson = Gson()

    private var mChannelPagerAdapter: ChannelPagerAdapter? = null
    private var mChannelCodes: Array<String>? = null

    override fun getToolBarResId(layout: Int): Int {
        return R.layout.layout_toolbar_new_home
    }

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setListener { _: View?, action: Int, extra: String? ->
            when(action){
                CommonTitleBar.ACTION_LEFT_BUTTON,CommonTitleBar.ACTION_LEFT_TEXT -> {
                    showToast("登录")
                }
                CommonTitleBar.ACTION_RIGHT_BUTTON,CommonTitleBar.ACTION_RIGHT_TEXT -> {
                    showToast("发布")
                }
            }
        }
        mCommonTitleBar.centerCustomView.setOnClickListener {
            showToast("搜索")
            startActivity(Intent(activity,SearchActivity2::class.java))
        }
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_news_home_tab
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        initChannelData()
        initChannelFragments()
        initViewPagerTab()
    }

    override fun lazyLoadData() {

    }

    /**
     * 初始化已选频道和未选频道的数据
     */
    private fun initChannelData() {
        var selectedChannelJson = PreUtils.getString(Constants.SELECTED_CHANNEL_JSON, "")
        val unselectChannel = PreUtils.getString(Constants.UNSELECTED_CHANNEL_JSON, "")

        if (TextUtils.isEmpty(selectedChannelJson) || TextUtils.isEmpty(unselectChannel)) {
            //本地没有title
            val channels = resources.getStringArray(R.array.channel)
            val channelCodes = resources.getStringArray(R.array.channel_code)
            //默认添加了全部频道
            for (i in channelCodes.indices) {
                val title = channels[i]
                val code = channelCodes[i]
                mSelectedChannels.add(ChannelBean(title, code))
            }
            selectedChannelJson = mGson.toJson(mSelectedChannels)//将集合转换成json字符串
            VLogUtil.i("selectedChannelJson:$selectedChannelJson")
            PreUtils.putString(Constants.SELECTED_CHANNEL_JSON, selectedChannelJson)//保存到sp
        } else {
            //之前添加过
            val selectedChannel = mGson.fromJson<List<ChannelBean>>(selectedChannelJson, object : TypeToken<List<ChannelBean>>() {}.type)
            val unselectedChannel = mGson.fromJson<List<ChannelBean>>(unselectChannel, object : TypeToken<List<ChannelBean>>() {}.type)
            mSelectedChannels.addAll(selectedChannel)
            mUnSelectedChannels.addAll(unselectedChannel)
        }
    }

    /**
     * 初始化已选频道的fragment的集合
     */
    private fun initChannelFragments() {
        VLogUtil.e("initChannelFragments")
        mChannelCodes = resources.getStringArray(R.array.channel_code)
        for (channel in mSelectedChannels) {
//            val newsFragment = TestFragment.getInstance(channel.title)
            val newsFragment = NewsListFragment()
            val bundle = Bundle()
            bundle.putString(Constants.CHANNEL_CODE, channel.channelCode)
            //是否是视频列表页面,根据判断频道号是否是视频
            bundle.putBoolean(Constants.IS_VIDEO_LIST, channel.channelCode == (mChannelCodes as Array<out String>?)!![1])
            newsFragment.arguments = bundle
            mChannelFragments.add(newsFragment)//添加到集合中
        }
    }

    private fun initViewPagerTab() {
        mChannelPagerAdapter  = ChannelPagerAdapter(childFragmentManager, mChannelFragments, mSelectedChannels)
        //给viewPager设置适配器
        vp_new_home_tab.adapter = mChannelPagerAdapter
        //TabLayout绑定ViewPager
        new_home_tablayout.setupWithViewPager(vp_new_home_tab)
        vp_new_home_tab.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                //当页签切换的时候，如果有播放视频，则释放资源
                GSYVideoManager.releaseAllVideos()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        iv_change_channel.setOnClickListener(this)
    }

    fun getCurrentChannelCode(): String {
        val currentItem = vp_new_home_tab.currentItem
        return mSelectedChannels[currentItem].channelCode
    }


    override fun onClick(v: View?) {
        super.onClick(v)
        when(v?.id){
            R.id.iv_change_channel-> {
                val dialogFragment = ChannelDialogFragment.newInstance(mSelectedChannels, mUnSelectedChannels)
                dialogFragment.setOnChannelListener(this)
                dialogFragment.show(childFragmentManager, "CHANNEL")
                dialogFragment.setOnDismissListener {
                    mChannelPagerAdapter?.notifyDataSetChanged()
//                    vp_new_home_tab.setOffscreenPageLimit(mSelectedChannels.size)
                    vp_new_home_tab.currentItem = new_home_tablayout.selectedTabPosition

                    //保存选中和未选中的channel
                    PreUtils.putString(Constants.SELECTED_CHANNEL_JSON, mGson.toJson(mSelectedChannels))
                    PreUtils.putString(Constants.UNSELECTED_CHANNEL_JSON, mGson.toJson(mUnSelectedChannels))
                }
            }
        }
    }

    override fun onItemMove(starPos: Int, endPos: Int) {
        listMoveChannel(mSelectedChannels, starPos, endPos)
        listMoveFragment(mChannelFragments, starPos, endPos)
    }

    override fun onMoveToMyChannel(starPos: Int, endPos: Int) {
        //移动到我的频道
        val channel = mUnSelectedChannels.removeAt(starPos)
        mSelectedChannels.add(endPos, channel)

//        val newsFragment = TestFragment.getInstance(channel.title)
        val newsFragment = NewsListFragment()
        val bundle = Bundle()
        bundle.putString(Constants.CHANNEL_CODE, channel.channelCode)
        bundle.putBoolean(Constants.IS_VIDEO_LIST, channel.channelCode == mChannelCodes!![1])
        newsFragment.arguments = bundle
        mChannelFragments.add(newsFragment)
    }

    override fun onMoveToOtherChannel(starPos: Int, endPos: Int) {
        //移动到推荐频道
        mUnSelectedChannels.add(endPos, mSelectedChannels.removeAt(starPos))
        mChannelFragments.removeAt(starPos)
    }

    private fun listMoveChannel(datas: ArrayList<ChannelBean>, starPos: Int, endPos: Int) {
        val o = datas[starPos]
        //先删除之前的位置
        datas.removeAt(starPos)
        //添加到现在的位置
        datas.add(endPos, o)
    }

    private fun listMoveFragment(datas: ArrayList<Fragment>, starPos: Int, endPos: Int) {
        val o = datas[starPos]
        //先删除之前的位置
        datas.removeAt(starPos)
        //添加到现在的位置
        datas.add(endPos, o)
    }

}