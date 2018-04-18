package com.vension.mvp.mvp.eye.contract

import com.vension.frame.base.V_IBaseView
import com.vension.frame.base.V_IPresenter
import com.vension.mvp.beans.eyes.HomeBean

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/13 9:40
 * 描  述：分类详情契约类
 * ========================================================
 */

interface CategoryDetailContract {

    interface View: V_IBaseView {
        /**
         *  设置列表数据
         */
        fun setCateDetailList(itemList:ArrayList<HomeBean.Issue.Item>)
        fun setMoreCateDetailList(itemList:ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg:String)
    }

    interface Presenter: V_IPresenter<View> {

        fun getCategoryDetailList(id:Long)

        fun loadMoreData()
    }

}