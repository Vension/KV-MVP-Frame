package com.vension.mvp.ui.fragments.headline

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import cn.jzvd.*
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.vension.frame.VFrame
import com.vension.frame.utils.*
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.vension.mvp.beans.headline.DetailCloseEvent
import com.vension.mvp.beans.headline.NewsBean
import com.vension.mvp.beans.headline.NewsRecordBean
import com.vension.mvp.mvp.headline.contract.NewListContract
import com.vension.mvp.mvp.headline.presenter.NewListPresenter
import com.vension.mvp.ui.activitys.headline.NewVideoDetailActicity
import com.vension.mvp.ui.activitys.headline.NewsDetailActivity
import com.vension.mvp.ui.adapters.headline.NewsListAdapter
import com.vension.mvp.ui.adapters.headline.VideoListAdapter
import com.vension.mvp.ui.fragments.agent.WebFragment
import com.vension.mvp.utils.Constants
import com.vension.mvp.utils.NewsRecordHelper
import com.vension.mvp.utils.UIUtils
import kotlinx.android.synthetic.main.fragment_news_list.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 10:36
 * 描  述：展示每个频道新闻列表的fragment
 * ========================================================
 */

class NewsListFragment : BaseFragment() , NewListContract.View, SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {

    private var mChannelCode: String? = null
    private var isVideoList: Boolean = false


    /**
     * 是否是推荐频道
     */
    private var isRecommendChannel: Boolean = false
    private val mNewsList = ArrayList<NewsBean>()
    private var mNewsAdapter: BaseQuickAdapter<NewsBean, BaseViewHolder>? = null

    private val mGson = Gson()

    //新闻记录
    private var mNewsRecord: NewsRecordBean? = null

    private val mPresenter by lazy { NewListPresenter() }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_news_list
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        mPresenter.attachView(this)
        mChannelCode = arguments?.getString(Constants.CHANNEL_CODE)
        isVideoList = arguments?.getBoolean(Constants.IS_VIDEO_LIST, false)!!

        val channelCodes = VFrame.getResources().getStringArray(R.array.channel_code)
        isRecommendChannel = mChannelCode == channelCodes[0]//是否是推荐频道

        mLayoutStatusView = new_MultipleStatusView

        new_refreshLayout.setColorSchemeColors(VObsoleteUtil.getColor(R.color.color_new_main))
        new_refreshLayout.setOnRefreshListener(this)

        //初始化_RecyclerView
        refreshRecyclerView.setHasFixedSize(true)
        refreshRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        refreshRecyclerView.itemAnimator = DefaultItemAnimator()

        initListenter()
    }

    private fun initListenter() {
        if (isVideoList) {
            //如果是视频列表
            mNewsAdapter = VideoListAdapter(mNewsList)
        } else {
            //其他新闻列表
            mNewsAdapter = NewsListAdapter(mChannelCode, mNewsList)
        }
        refreshRecyclerView.adapter = mNewsAdapter


        mNewsAdapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val news = mNewsList[position]

            val itemId = news.item_id
            val urlSb = StringBuffer("http://m.toutiao.com/i")
            urlSb.append(itemId).append("/info/")
            val url = urlSb.toString()//http://m.toutiao.com/i6412427713050575361/info/
            var intent: Intent? = null

            if (news.has_video) {
                //视频
                intent = Intent(activity, NewVideoDetailActicity::class.java)
                VLogUtil.e("has_video==> " + news.url)
                intent.putExtra("video_url", news.url)
                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
                    val videoPlayer = JZVideoPlayerManager.getCurrentJzvd() as JZVideoPlayerStandard
                    //传递进度
                    val progress = JZMediaManager.instance().positionInList
                    VLogUtil.e("has_video==> $progress")
                    if (progress != 0) {
                        intent.putExtra("progress", progress)
                    }
                }
            } else {
                //非视频新闻
                if (news.article_type === 1) {
                    //如果article_type为1，则是使用WebViewActivity打开
                    mBundle?.putString("web_title",news.title)
                    mBundle?.putString("web_url",news.article_url)
                    startAgentActivity(WebFragment::class.java,mBundle!!)
                    return@OnItemClickListener
                }
                //其他新闻
                intent = Intent(activity, NewsDetailActivity::class.java)
            }

            intent?.putExtra("channelCode", mChannelCode)
            intent?.putExtra("position", position)

            intent?.putExtra("detailUrl", url)
            intent?.putExtra("groupId", news.group_id)
            intent?.putExtra("itemId", itemId)

            startActivity(intent)
//            mBundle?.putString("channelCode",mChannelCode)
//            mBundle?.putInt("position",position)
//            mBundle?.putString("detailUrl",url)
//            mBundle?.putString("groupId",news.group_id)
//            mBundle?.putString("itemId",itemId)
//            if (news.has_video) {
//                VToastUtil.showToast("视频")
//                //视频
////                intent = Intent(mActivity, VideoDetailActivity::class.java)
//                if (JZVideoPlayerManager.getCurrentJzvd() != null) {
//                    val videoPlayer = JZVideoPlayerManager.getCurrentJzvd() as JZVideoPlayerStandard
//                    //传递进度
//                    val progress = JZMediaManager.instance().positionInList
//                    if (progress != 0) {
//                        mBundle?.putInt("progress",progress)
//                    }
//                }
//                startAgentActivity(VideoDetailFragment::class.java,mBundle!!)
//            } else {
//                //非视频新闻
//                if (news.article_type === 1) {
//                    //如果article_type为1，则是使用WebViewActivity打开
//                    mBundle?.putString("web_title",news.title)
//                    mBundle?.putString("web_url",news.article_url)
//                    startAgentActivity(WebFragment::class.java,mBundle!!)
//                }else{
//                    //其他新闻
//                    startAgentActivity(WebFragment::class.java,mBundle!!)
//                }
//            }
        }

        mNewsAdapter?.setEnableLoadMore(true)
        mNewsAdapter?.setOnLoadMoreListener(this, refreshRecyclerView)
        if (isVideoList) {
            refreshRecyclerView.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
                override fun onChildViewAttachedToWindow(view: View) {

                }

                override fun onChildViewDetachedFromWindow(view: View) {
                    val jzvd = view.findViewById<JZVideoPlayer>(R.id.video_player)
                    if (jzvd != null && JZUtils.dataSourceObjectsContainsUri(jzvd.dataSourceObjects, JZMediaManager.getCurrentDataSource())) {
                        val currentJzvd = JZVideoPlayerManager.getCurrentJzvd()
                        if (currentJzvd != null && currentJzvd.currentScreen != JZVideoPlayer.SCREEN_WINDOW_FULLSCREEN) {
                            JZVideoPlayer.releaseAllVideos()
                        }
                    }
                }
            })
        }
    }


    override fun lazyLoadData() {
        //查找该频道的最后一组记录
        mNewsRecord = NewsRecordHelper.getLastNewsRecord(mChannelCode)
        if (mNewsRecord == null) {
            //找不到记录，拉取网络数据
            mNewsRecord = NewsRecordBean()//创建一个没有数据的对象
            mPresenter.getNewsList(mChannelCode!!)
            return
        }

        //找到最后一组记录，转换成新闻集合并展示
        val newsList = NewsRecordHelper.convertToNewsList(mNewsRecord?.json)
        mNewsList.addAll(newsList)//添加到集合中
        mNewsAdapter?.notifyDataSetChanged()//刷新adapter

        mLayoutStatusView?.showContent()//显示内容

        //判断时间是否超过10分钟，如果是则自动刷新
        if (mNewsRecord?.time!!.minus(System.currentTimeMillis()) === 10 * 60 * 100L) {
            onRefresh()
        }
    }

    override fun onRefresh() {
        if (!NetworkUtil.isNetworkAvailable(context)) {
            //网络不可用弹出提示
            tip_view.show()
            if (new_refreshLayout.isRefreshing){
                new_refreshLayout.isRefreshing = false
            }
            return
        }
        mPresenter.getNewsList(mChannelCode!!)
    }


    override fun onLoadMoreRequested() {
        // BaseRecyclerViewAdapterHelper的加载更多
        if (mNewsRecord?.page === 0 || mNewsRecord?.page === 1) {
            //如果记录的页数为0(即是创建的空记录)，或者页数为1(即已经是第一条记录了)
            mNewsAdapter?.loadMoreEnd()//结束加载更多
            return
        }

        val preNewsRecord = NewsRecordHelper.getPreNewsRecord(mChannelCode, mNewsRecord?.page!!)
        if (preNewsRecord == null) {
            mNewsAdapter?.loadMoreEnd()//结束加载更多
            return
        }

        mNewsRecord = preNewsRecord

        val startTime = System.currentTimeMillis()

        val newsList = NewsRecordHelper.convertToNewsList(mNewsRecord?.json)

        if (isRecommendChannel) {
            //如果是推荐频道
            newsList.removeAt(0)//移除第一个，因为第一个是置顶新闻，重复
        }

        val endTime = System.currentTimeMillis()
        //由于是读取数据库，如果耗时不足1秒，则1秒后才收起加载更多
        if (endTime - startTime <= 1000) {
            UIUtils.postTaskDelay(Runnable {
                mNewsAdapter?.loadMoreComplete()
                mNewsList.addAll(newsList)//添加到集合下面
                mNewsAdapter?.notifyDataSetChanged()//刷新adapter
            }, (1000 - (endTime - startTime)).toInt())
        }
    }

    override fun onGetNewsListSuccess(newList: ArrayList<NewsBean>, tipInfo: String) {
        VLogUtil.e("onGetNewsListSuccess==>" + newList.toString())
        //如果是第一次获取数据
        if (VEmptyUtil.isEmpty(mNewsList)) {
            if (VEmptyUtil.isEmpty(newList)) {
                //获取不到数据,显示空布局
                mLayoutStatusView?.showEmpty()
                return
            }
            mLayoutStatusView?.showContent()//显示内容
        }

        if (VEmptyUtil.isEmpty(newList)) {
            //已经获取不到新闻了，处理出现获取不到新闻的情况
            VToastUtil.showInfo(VObsoleteUtil.getString(R.string.no_news_now))
            return
        }

        if (TextUtils.isEmpty(newList[0].title)) {
            //由于汽车、体育等频道第一条属于导航的内容，所以如果第一条没有标题，则移除
            newList.removeAt(0)
        }

        dealRepeat(newList)//处理新闻重复问题

        mNewsList.addAll(0, newList)
        mNewsAdapter?.notifyDataSetChanged()

        tip_view.show(tipInfo)

        //保存到数据库
        NewsRecordHelper.save(mChannelCode, mGson.toJson(newList))
    }

    /**
     * 处理置顶新闻和广告重复
     */
    private fun dealRepeat(newList: MutableList<NewsBean>) {
        if (isRecommendChannel && !VEmptyUtil.isEmpty(mNewsList)) {
            //如果是推荐频道并且数据列表已经有数据,处理置顶新闻或广告重复的问题
            mNewsList.removeAt(0)//由于第一条新闻是重复的，移除原有的第一条
            //新闻列表通常第4个是广告,除了第一次有广告，再次获取的都移除广告
            if (newList.size >= 4) {
                val fourthNews = newList[3]
                //如果列表第4个和原有列表第4个新闻都是广告，并且id一致，移除
                if (fourthNews.tag == Constants.ARTICLE_GENRE_AD) {
                    newList.remove(fourthNews)
                }
            }
        }
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        tip_view.show()//弹出提示
        if (VEmptyUtil.isEmpty(mNewsList)) {
            //如果一开始进入没有数据
            mLayoutStatusView?.showEmpty()
        }

        //收起刷新
        if (new_refreshLayout.isRefreshing){
            new_refreshLayout.isRefreshing = false
        }
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        if (new_refreshLayout.isRefreshing){
            new_refreshLayout.isRefreshing = false
        }
        mLayoutStatusView?.showContent()
    }


    /**
     * 详情页关闭后传递过来的事件,更新评论数播放进度等
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onDetailCloseEvent(event: DetailCloseEvent) {
        if (!event.getChannelCode().equals(mChannelCode)) {
            //如果频道不一致，不用处理
            return
        }

        val position = event.getPosition()
        val commentCount = event.getCommentCount()

        val news = mNewsList[position]
        news.comment_count = commentCount

        if (news.video_detail_info != null) {
            //如果有视频
            val progress = event.getProgress()
            news.video_detail_info.progress = progress
        }

        //刷新adapter
        mNewsList[position] = news
        mNewsAdapter?.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        registerEventBus(this@NewsListFragment)
    }

    override fun onStop() {
        super.onStop()
        unregisterEventBus(this@NewsListFragment)
    }

    override fun onPause() {
        super.onPause()
         JZVideoPlayer.releaseAllVideos()
    }


    fun onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return
        }
        super.getActivity()?.onBackPressed()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


}