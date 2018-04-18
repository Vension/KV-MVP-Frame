package com.vension.mvp.mvp.eye.presenter

import com.vension.mvp.http.exception.ExceptionHandle
import com.vension.frame.base.V_BasePresenter
import com.vension.mvp.mvp.eye.contract.CategoryContract
import com.vension.mvp.mvp.eye.model.CategoryModel

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/12 17:27
 * 描  述：分类的 Presenter
 * ========================================================
 */

class CategoryPresenter : V_BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {

    private val categoryModel: CategoryModel by lazy {
        CategoryModel()
    }

    /**
     * 获取分类
     */
    override fun getCategoryData() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = categoryModel.getCategoryData()
                .subscribe({ categoryList ->
                    mRootView?.apply {
                        dismissLoading()
                        showCategory(categoryList)
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