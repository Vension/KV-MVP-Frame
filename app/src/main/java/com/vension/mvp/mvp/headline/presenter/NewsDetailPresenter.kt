package com.vension.mvp.mvp.headline.presenter

import com.vension.frame.base.V_BasePresenter
import com.vension.mvp.http.exception.ExceptionHandle
import com.vension.mvp.mvp.headline.contract.NewsDetailContract
import com.vension.mvp.mvp.headline.model.NewsDetailModel
import com.vension.mvp.utils.Constants

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/23 16:01
 * 描  述：新闻详情的 Presenter
 * ========================================================
 */

class NewsDetailPresenter : V_BasePresenter<NewsDetailContract.View>(), NewsDetailContract.Presenter {

    private val mNewsDetailModel: NewsDetailModel by lazy {
        NewsDetailModel()
    }

    override fun getComment(groupId: String,itemId: String,pageNow: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val offset = (pageNow - 1) * Constants.COMMENT_PAGE_SIZE
        val disposable =  mNewsDetailModel.getNewsComment(groupId,itemId,
                offset.toString(), Constants.COMMENT_PAGE_SIZE.toString())
                .subscribe({ response ->
                    mRootView?.apply {
                        dismissLoading()
                        onGetCommentSuccess(response)
                    }
                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        //处理异常
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }

                })
    }

    override fun getNewsDetail(url: String) {
        checkViewAttached()
        mRootView?.showLoading()


        val disposable =  mNewsDetailModel.getNewsDetail(url)
                .subscribe({ response ->
                    mRootView?.apply {
                        dismissLoading()
                        onGetNewsDetailSuccess(response)
                    }
                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        //处理异常
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }

                })

        addSubscription(disposable)
    }


}