package com.vension.mvp.ui.activitys.headline

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import butterknife.OnClick
import cn.jzvd.JZVideoPlayer
import cn.jzvd.JZVideoPlayerStandard
import com.vension.frame.utils.VLogUtil
import com.vension.mvp.R
import com.vension.mvp.beans.headline.NewsDetail
import com.vension.mvp.beans.headline.ResultResponse
import kotlinx.android.synthetic.main.activity_news_video_detail.*
import kotlinx.android.synthetic.main.layout_news_detail_bottombar.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/24 9:37
 * 描  述：
 * ========================================================
 */

class NewVideoDetailActicity : NewsDetailBaseActivity() {

    private var mSensorManager: SensorManager? = null
    private var mSensorEventListener: JZVideoPlayer.JZAutoFullscreenListener? = null
    private var mProgress: Int = 0
    private var url: String = ""

    override fun getViewContentViewId(): Int {
        return R.layout.activity_news_video_detail
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        mLayoutStatusView = layout_new_detail_multiple
        mRvComment = rv_comment
        mTvCommentCount = tv_comment_count
        super.initViewAndData(savedInstanceState)

        mProgress = intent.getIntExtra(PROGRESS, 0)
        url = intent.getStringExtra("video_url")

        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mSensorEventListener = JZVideoPlayer.JZAutoFullscreenListener()

        video_player.setAllControlsVisiblity(GONE, GONE, VISIBLE, GONE, VISIBLE, VISIBLE, GONE)
        video_player.titleTextView.visibility = GONE
    }


    override fun onGetNewsDetailSuccess(newsDetailResult: ResultResponse<NewsDetail>) {
        VLogUtil.e("onGetNewsDetailSuccess=>$url")
        if (url.isNotEmpty()){
            video_player.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "")
            video_player.seekToInAdvance = mProgress.toLong()//设置进度
            video_player.startVideo()
        }
        val newsDetail = newsDetailResult.data
        mHeaderView?.setDetail(newsDetail) {
            //加载完成后，显示内容布局
            mLayoutStatusView?.showContent()
        }
    }


    override fun onPause() {
        super.onPause()
        mSensorManager?.unregisterListener(mSensorEventListener)
        JZVideoPlayer.releaseAllVideos()
    }

    override fun onResume() {
        super.onResume()
        val accelerometerSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSensorManager?.registerListener(mSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (JZVideoPlayer.backPress()) {
            return
        }
        postVideoEvent(true)
    }

    @OnClick(R.id.iv_back)
    fun onViewClicked() {
        postVideoEvent(true)
    }



}