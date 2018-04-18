package com.vension.mvp.mvp.eye.presenter

import com.vension.mvp.http.exception.ExceptionHandle
import com.vension.frame.base.V_BasePresenter
import com.vension.mvp.mvp.eye.contract.RankContract
import com.vension.mvp.mvp.eye.model.RankModel

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 16:31
 * 描  述：TabInfo Presenter
 * ========================================================
 */

class RankPresenter : V_BasePresenter<RankContract.View>(), RankContract.Presenter {

    private val rankModel by lazy { RankModel() }


    /**
     *  请求排行榜数据
     */
    override fun requestRankList(apiUrl: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = rankModel.requestRankList(apiUrl)
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        setRankList(issue.itemList)
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
}