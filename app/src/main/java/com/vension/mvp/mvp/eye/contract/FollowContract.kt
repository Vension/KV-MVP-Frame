package com.vension.mvp.mvp.eye.contract

import com.kevin.vension.demo.base.V_IBaseView
import com.kevin.vension.demo.base.V_IPresenter
import com.vension.mvp.beans.eyes.HomeBean

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 14:14
 * 描  述：
 * ========================================================
 */

interface FollowContract {

    interface View : V_IBaseView {
        /**
         * 设置关注信息数据
         */
        fun setFollowInfo(isRefresh : Boolean,issue: HomeBean.Issue)

        fun showError(errorMsg: String, errorCode: Int)
    }


    interface Presenter : V_IPresenter<View> {
        /**
         * 获取List
         */
        fun requestFollowList()

        /**
         * 加载更多
         */
        fun loadMoreData()
    }
}