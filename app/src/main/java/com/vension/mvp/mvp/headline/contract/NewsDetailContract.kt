package com.vension.mvp.mvp.headline.contract

import com.vension.frame.base.V_IBaseView
import com.vension.frame.base.V_IPresenter
import com.vension.mvp.beans.headline.CommentResponse
import com.vension.mvp.beans.headline.NewsDetail
import com.vension.mvp.beans.headline.ResultResponse


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/23 15:44
 * 描  述：新闻详情契约类
 * ========================================================
 */

interface NewsDetailContract {

    interface View : V_IBaseView {

         fun onGetNewsDetailSuccess(newsDetail: ResultResponse<NewsDetail>)

         fun onGetCommentSuccess(response: CommentResponse)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg:String,errorCode:Int)

    }

    interface Presenter : V_IPresenter<View> {


        fun getComment(groupId: String,itemId: String,pageNow: Int)

        fun getNewsDetail(url: String)

    }


}