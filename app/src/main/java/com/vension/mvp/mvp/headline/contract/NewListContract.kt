package com.vension.mvp.mvp.headline.contract

import com.vension.frame.base.V_IBaseView
import com.vension.frame.base.V_IPresenter
import com.vension.mvp.beans.headline.NewsBean

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 10:50
 * 描  述：新闻条目契约类
 * ========================================================
 */

interface NewListContract {

    interface View : V_IBaseView {

         fun onGetNewsListSuccess(newList: ArrayList<NewsBean>, tipInfo: String)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg:String,errorCode:Int)

    }

    interface Presenter : V_IPresenter<View> {

        /**
         * 获取新闻数据
         */
        fun getNewsList(channelCode: String)

    }


}