package com.vension.mvp.ui.activitys.eye

import android.annotation.TargetApi
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.transition.Transition
import android.widget.ImageView
import com.kevin.vension.demo.utils.statubar.StatusBarUtil
import com.orhanobut.logger.Logger
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.sunfusheng.glideimageview.progress.GlideApp
import com.vension.frame.VFrame
import com.vension.frame.utils.VObsoleteUtil
import com.vension.frame.utils.VToastUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseActivity
import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.mvp.eye.contract.VideoDetailContract
import com.vension.mvp.mvp.eye.presenter.VideoDetailPresenter
import com.vension.mvp.showToast
import com.vension.mvp.ui.adapters.eye.RecyVideoDetailAdapter
import com.vension.mvp.utils.Constants
import com.vension.mvp.utils.WatchHistoryUtils
import kotlinx.android.synthetic.main.activity_eye_video_detail.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/13 16:42
 * 描  述：视频详情
 * ========================================================
 */

class VideoDetailActivity : BaseActivity(), VideoDetailContract.View,SwipeRefreshLayout.OnRefreshListener {

    companion object {
        val IMG_TRANSITION = "IMG_TRANSITION"
        val TRANSITION = "TRANSITION"
    }

    /**
     * 第一次调用的时候初始化
     */
    private val mPresenter by lazy { VideoDetailPresenter() }

    private val mAdapter by lazy { RecyVideoDetailAdapter(this, itemList) }

    private val mFormat by lazy { SimpleDateFormat("yyyyMMddHHmmss"); }

    /**
     * Item 详细数据
     */
    private lateinit var itemData: HomeBean.Issue.Item
    private var orientationUtils: OrientationUtils? = null

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private var isPlay: Boolean = false
    private var isPause: Boolean = false


    private var isTransition: Boolean = false

    private var transition: Transition? = null

    init {
        mPresenter.attachView(this)
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_eye_video_detail
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        itemData = intent.getSerializableExtra(Constants.BUNDLE_VIDEO_DATA) as HomeBean.Issue.Item
        isTransition = intent.getBooleanExtra(TRANSITION, false)
        saveWatchVideoHistoryInfo(itemData)

        //过渡动画
        initTransition()
        //初始化视频空间
        initVideoViewConfig()
        //初始化刷新控件
        mRefreshLayout.setColorSchemeColors(VObsoleteUtil.getColor(R.color.app_main_thme_color))
        //设置刷新控件监听
        mRefreshLayout.setOnRefreshListener(this)
        //初始化recyclerview
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter

        //设置相关视频 Item 的点击事件
        mAdapter?.setOnItemDetailClick { mPresenter.loadVideoInfo(it) }

        //状态栏透明和间距处理
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, mGSYVideoView)
    }

    override fun requestLoadData() {

    }

    override fun onRefresh() {
        loadVideoInfo()
    }

    /**初始化视频播放器*/
    private fun initVideoViewConfig() {
        //外部辅助的旋转，帮助全屏
        orientationUtils = OrientationUtils(this, mGSYVideoView)
        //是否旋转
        mGSYVideoView?.isRotateViewAuto = false

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        GlideApp.with(this)
                .load(itemData?.data?.cover?.feed)
                .centerCrop()
                .into(imageView)

        val gsyVideoOption = GSYVideoOptionBuilder()
        gsyVideoOption.setThumbImageView(imageView)
                .setVideoTitle(itemData?.data?.title)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setSeekRatio(1f)
                .setCacheWithPlay(false)
                .setVideoAllCallBack(object : GSYSampleCallBack() {
                    override fun onPrepared(url: String?, vararg objects: Any) {
                        Debuger.printfError("***** onPrepared **** " + objects[0])
                        Debuger.printfError("***** onPrepared **** " + objects[1])
                        super.onPrepared(url, *objects)
                        //开始播放了才能旋转和全屏
                        orientationUtils?.isEnable = true
                        isPlay = true
                    }

                    override fun onEnterFullscreen(url: String?, vararg objects: Any) {
                        super.onEnterFullscreen(url, *objects)
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[0])//title
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[1])//当前全屏player
                    }

                    override fun onAutoComplete(url: String?, vararg objects: Any) {
                        super.onAutoComplete(url, *objects)
                        Logger.d("***** onAutoPlayComplete **** ")
                    }

                    override fun onClickStartError(url: String?, vararg objects: Any) {
                        super.onClickStartError(url, *objects)
                        showToast("播放失败")
                    }

                    override fun onQuitFullscreen(url: String?, vararg objects: Any) {
                        super.onQuitFullscreen(url, *objects)
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[0])//title
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[1])//当前非全屏player
                        //列表返回的样式判断
                        if (orientationUtils != null) {
                            orientationUtils?.backToProtVideo()
                        }
                    }
                })
                //锁屏事件
                .setLockClickListener { view, lock ->
                    if (orientationUtils != null) {
                        //配合下方的onConfigurationChanged
                        orientationUtils?.isEnable = !lock
                    }
                }
                .setGSYVideoProgressListener { progress, secProgress, currentPosition, duration -> Debuger.printfLog(" progress $progress secProgress $secProgress currentPosition $currentPosition duration $duration") }
                .build(mGSYVideoView)

        //设置返回按键功能
        mGSYVideoView?.backButton?.setOnClickListener({ onBackPressed() })
        //设置全屏按键功能
        mGSYVideoView?.fullscreenButton?.setOnClickListener {
            //直接横屏
            orientationUtils?.resolveByClick()
            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            mGSYVideoView?.startWindowFullscreen(this, true, true)
        }
    }


    /**
     * 保存观看记录
     */
    private fun saveWatchVideoHistoryInfo(watchItem: HomeBean.Issue.Item) {
        //保存之前要先查询sp中是否有该value的记录，有则删除.这样保证搜索历史记录不会有重复条目
        val historyMap = WatchHistoryUtils.getAll(Constants.FILE_WATCH_HISTORY_NAME, VFrame.getContext()) as Map<*, *>
        for ((key,value) in historyMap) {
            if (watchItem == WatchHistoryUtils.getObject(Constants.FILE_WATCH_HISTORY_NAME, VFrame.getContext(), key as String)) {
                WatchHistoryUtils.remove(Constants.FILE_WATCH_HISTORY_NAME, VFrame.getContext(), key)
            }
        }
        WatchHistoryUtils.putObject(Constants.FILE_WATCH_HISTORY_NAME, VFrame.getContext(), watchItem,"" + mFormat.format(Date()))
    }


    /**
     * 1.加载视频信息
     */
    private fun loadVideoInfo() {
        mPresenter.loadVideoInfo(itemData!!)
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {
        if (mRefreshLayout.isRefreshing){
            mRefreshLayout.isRefreshing = false
        }
    }

    /**
     * 设置播放视频 URL
     */
    override fun setVideo(url: String) {
        Logger.d("playUrl:$url")
        mGSYVideoView.setUp(url, false, "")
        //开始自动播放
        mGSYVideoView.startPlayLogic()
    }

    /**
     * 设置视频信息
     */
    override fun setVideoInfo(itemInfo: HomeBean.Issue.Item) {
        itemData = itemInfo
        mAdapter.addData(itemInfo)
        // 请求相关的最新等视频
        mPresenter.requestRelatedVideo(itemInfo.data!!.id)
    }

    /**
     * 设置背景颜色
     */
    override fun setBackground(url: String) {
        GlideApp.with(this)
                .load(url)
                .centerCrop()
                .into(mVideoBackground)
    }

    /**
     * 设置相关的数据视频
     */
    override fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>) {
        mAdapter.addData(itemList)
        this.itemList = itemList
    }

    override fun setErrorMsg(errorMsg: String) {
        VToastUtil.showError(errorMsg)
    }



    /** ============================= 过渡动画相关 ================================== */
    private fun initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(mGSYVideoView, IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        }else{
            onRefresh()
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun addTransitionListener() {
        transition = window.sharedElementEnterTransition
        transition?.addListener(object : Transition.TransitionListener {
            override fun onTransitionResume(p0: Transition?) {
            }

            override fun onTransitionPause(p0: Transition?) {
            }

            override fun onTransitionCancel(p0: Transition?) {
            }

            override fun onTransitionStart(p0: Transition?) {
            }

            override fun onTransitionEnd(p0: Transition?) {
                Logger.d("onTransitionEnd()------")
                onRefresh()
                transition?.removeListener(this)
            }

        })
    }

    /** ======================= 生命周期处理 ================= */
    override fun onPause() {
        getCurPlay()?.onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        getCurPlay()?.onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            getCurPlay()?.release()
        }
        if (orientationUtils != null)
            orientationUtils?.releaseListener()
        mPresenter.detachView()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            mGSYVideoView?.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }

    private fun getCurPlay(): GSYVideoPlayer? {
        return if (mGSYVideoView?.fullWindowPlayer != null) {
            mGSYVideoView?.fullWindowPlayer
        } else mGSYVideoView
    }

    override fun onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils?.backToProtVideo()
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        //释放所有
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) run {
            super.onBackPressed()
        } else {
            finish()
            overridePendingTransition(R.anim.anim_out, R.anim.anim_in)
        }
    }

}