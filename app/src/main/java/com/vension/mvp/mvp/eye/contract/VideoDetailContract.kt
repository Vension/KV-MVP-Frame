package com.vension.mvp.mvp.eye.contract

import com.kevin.vension.demo.base.V_IBaseView
import com.kevin.vension.demo.base.V_IPresenter
import com.vension.mvp.beans.eyes.HomeBean

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/13 12:16
 * 描  述：视频详情契约类
 * ========================================================
 */

interface VideoDetailContract {

    interface View : V_IBaseView {

        /**
         * 设置视频播放源
         */
        fun setVideo(url: String)

        /**
         * 设置视频信息
         */
        fun setVideoInfo(itemInfo: HomeBean.Issue.Item)

        /**
         * 设置背景
         */
        fun setBackground(url: String)

        /**
         * 设置最新相关视频
         */
        fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>)

        /**
         * 设置错误信息
         */
        fun setErrorMsg(errorMsg: String)

    }

    interface Presenter : V_IPresenter<View> {

        /**
         * 加载视频信息
         */
        fun loadVideoInfo(itemInfo: HomeBean.Issue.Item)

        /**
         * 请求相关的视频数据
         */
        fun requestRelatedVideo(id: Long)

    }


}