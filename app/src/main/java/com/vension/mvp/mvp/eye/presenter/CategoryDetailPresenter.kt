package com.vension.mvp.mvp.eye.presenter

import com.kevin.vension.demo.base.V_BasePresenter
import com.vension.mvp.mvp.eye.contract.CategoryDetailContract
import com.vension.mvp.mvp.eye.model.CategoryDetailModel

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/13 9:39
 * 描  述：
 * ========================================================
 */

class CategoryDetailPresenter : V_BasePresenter<CategoryDetailContract.View>(),CategoryDetailContract.Presenter{

   private val categoryDetailModel by lazy {
       CategoryDetailModel()
   }

    private var nextPageUrl:String?=null

    /**
     * 获取分类详情的列表信息
     */
    override fun getCategoryDetailList(id: Long) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable= categoryDetailModel.getCategoryDetailList(id)
                .subscribe({
                    issue ->
                    mRootView?.apply {
                        dismissLoading()
                        nextPageUrl = issue.nextPageUrl
                        setCateDetailList(issue.itemList)
                    }
                },{
                    throwable ->
                    mRootView?.apply {
                        dismissLoading()
                        showError(throwable.toString())
                    }
                })

        addSubscription(disposable)
    }

    /**
     * 加载更多数据
     */
    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            categoryDetailModel.loadMoreData(it)
                    .subscribe({ issue ->
                        mRootView?.apply {
                            nextPageUrl = issue.nextPageUrl
                            setMoreCateDetailList(issue.itemList)
                        }
                    }, { throwable ->
                        mRootView?.apply {
                            showError(throwable.toString())
                        }
                    })
        }

        disposable?.let { addSubscription(it) }
    }
}