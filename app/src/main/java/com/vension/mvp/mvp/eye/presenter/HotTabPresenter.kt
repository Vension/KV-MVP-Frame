package com.vension.mvp.mvp.eye.presenter

import com.vension.frame.base.V_BasePresenter
import com.vension.mvp.mvp.eye.contract.HotTabContract
import com.vension.mvp.mvp.eye.model.HotTabModel

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 16:19
 * 描  述：
 * ========================================================
 */

class HotTabPresenter: V_BasePresenter<HotTabContract.View>(),HotTabContract.Presenter {

    private val hotTabModel by lazy { HotTabModel() }


    override fun getTabInfo() {
        checkViewAttached()
        val disposable = hotTabModel.getTabInfo()
                .subscribe({
                    tabInfo->
                    mRootView?.setTabInfo(tabInfo)
                },{
                    throwable->
                    mRootView?.showError(throwable.toString())
                })
        addSubscription(disposable)
    }
}