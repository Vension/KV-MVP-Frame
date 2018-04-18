package com.vension.mvp.mvp.gank

import com.vension.frame.base.V_BasePresenter
import com.vension.mvp.http.exception.ExceptionHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/4 16:06
 * 描  述：
 * ========================================================
 */

class GankPresenter : V_BasePresenter<GankContract.View>(), GankContract.Presenter {

    private val mGankModel by lazy { GankModel() }

    override fun loadGrilList(isRefresh: Boolean, limit: Int, page: Int) {
        checkViewAttached()
        mRootView?.showLoading()

        val disposable = mGankModel.getGrilList(limit,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    data ->
                    mRootView?.apply {
                        dismissLoading()
                        setGrilData(isRefresh,data)
                    }
                },{
                    throwable ->
                    mRootView?.apply {
                        dismissLoading()
                        //处理异常
                        showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                    }
                })

        addSubscription(disposable)
    }


    override fun loadTechList(isRefresh : Boolean,type : String,limit: Int, page: Int) {
        checkViewAttached()
        mRootView?.showLoading()

        val disposable = mGankModel.getTechList(type,limit,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    data ->
                    mRootView?.apply {
                        dismissLoading()
                        setTechData(isRefresh,data)
                    }
                },{
                    throwable ->
                    mRootView?.apply {
                        dismissLoading()
                        //处理异常
                        showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                    }
                })

        addSubscription(disposable)
    }



}