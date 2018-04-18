package com.vension.mvp.mvp.eye.presenter

import com.vension.mvp.http.exception.ExceptionHandle
import com.vension.frame.base.V_BasePresenter
import com.vension.mvp.mvp.eye.contract.FollowContract
import com.vension.mvp.mvp.eye.model.FollowModel

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 14:16
 * 描  述：关注 Presenter
 * ========================================================
 */

class FollowPresenter : V_BasePresenter<FollowContract.View>(), FollowContract.Presenter {

    private val followModel by lazy { FollowModel() }

    private var nextPageUrl:String?=null

    /**
     *  请求排行榜数据
     */
    override fun requestFollowList() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = followModel.requestFollowList()
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        nextPageUrl = issue.nextPageUrl
                        setFollowInfo(true,issue)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        dismissLoading()
                        //处理异常
                        showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                    }
                })
        addSubscription(disposable)
    }

    /**
     * 加载更多
     */
    override fun loadMoreData(){
        val disposable = nextPageUrl?.let {
            followModel.loadMoreData(it)
                    .subscribe({ issue->
                        mRootView?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setFollowInfo(false,issue)
                        }

                    },{ t ->
                        mRootView?.apply {
                            showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                        }
                    })


        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }
}