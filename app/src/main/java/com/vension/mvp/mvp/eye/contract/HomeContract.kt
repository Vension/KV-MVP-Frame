package com.vension.mvp.mvp.eye.contract

import com.kevin.vension.demo.base.V_IBaseView
import com.kevin.vension.demo.base.V_IPresenter
import com.vension.mvp.beans.eyes.HomeBean

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 17:56
 * 描  述：契约类
 * ========================================================
 */

interface HomeContract {

    interface View : V_IBaseView {

        /**
         * 设置第一次请求的数据
         */
        fun setHomeData(homeBean: HomeBean)

        /**
         * 设置加载更多的数据
         */
        fun setMoreData(itemList:ArrayList<HomeBean.Issue.Item>)

        /**
         * 显示错误信息
         */
        fun showError(msg: String,errorCode:Int)


    }

    interface Presenter : V_IPresenter<View> {

        /**
         * 获取首页精选数据
         */
        fun requestHomeData(num: Int)

        /**
         * 加载更多数据
         */
        fun loadMoreData()


    }


}