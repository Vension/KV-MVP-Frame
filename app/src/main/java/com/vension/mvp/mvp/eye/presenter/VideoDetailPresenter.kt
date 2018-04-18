package com.vension.mvp.mvp.eye.presenter

import android.app.Activity
import com.vension.mvp.http.exception.ExceptionHandle
import com.vension.frame.base.V_BasePresenter
import com.vension.frame.VFrame
import com.vension.frame.utils.NetworkUtil
import com.vension.frame.utils.VSizeUtil
import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.dataFormat
import com.vension.mvp.mvp.eye.contract.VideoDetailContract
import com.vension.mvp.mvp.eye.model.VideoDetailModel
import com.vension.mvp.showToast

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/13 12:18
 * 描  述：
 * ========================================================
 */

class VideoDetailPresenter : V_BasePresenter<VideoDetailContract.View>(), VideoDetailContract.Presenter {

    private val videoDetailModel: VideoDetailModel by lazy {
        VideoDetailModel()
    }

    /**
     * 加载视频相关的数据
     */
    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {

        val playInfo = itemInfo.data?.playInfo

        val netType = NetworkUtil.isWifiConnected(VFrame.getContext())
        // 检测是否绑定 View
        checkViewAttached()
        if (playInfo!!.size > 1) {
            // 当前网络是 Wifi环境下选择高清的视频
            if (netType) {
                for (i in playInfo) {
                    if (i.type == "high") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        break
                    }
                }
            } else {
                //否则就选标清的视频
                for (i in playInfo) {
                    if (i.type == "normal") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        //Todo 待完善
                        (mRootView as Activity).showToast("本次消耗${(mRootView as Activity)
                                .dataFormat(i.urlList[0].size)}流量")
                        break
                    }
                }
            }
        } else {
            mRootView?.setVideo(itemInfo.data.playUrl)
        }

        //设置背景
        val backgroundUrl = itemInfo.data.cover.blurred + "/thumbnail/${VFrame.mScreenHeight - VSizeUtil.dip2px(250f)!!}x${VFrame.mScreenWidth}"
        backgroundUrl.let { mRootView?.setBackground(it) }

        mRootView?.setVideoInfo(itemInfo)
    }


    /**
     * 请求相关的视频数据
     */
    override fun requestRelatedVideo(id: Long) {
        mRootView?.showLoading()
        val disposable = videoDetailModel.requestRelatedData(id)
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        setRecentRelatedVideo(issue.itemList)
                    }
                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        setErrorMsg(ExceptionHandle.handleException(t))
                    }
                })

        addSubscription(disposable)

    }


}