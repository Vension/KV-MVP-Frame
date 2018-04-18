package com.vension.mvp.mvp.eye.contract

import com.vension.frame.base.V_IBaseView
import com.vension.frame.base.V_IPresenter
import com.vension.mvp.beans.eyes.HomeBean

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/17 11:53
 * 描  述：搜索契约类
 * ========================================================
 */

interface SearchContract {

    interface View : V_IBaseView {
        /**
         * 设置热门关键词数据
         */
        fun setHotWordData(string: ArrayList<String>)

        /**
         * 设置搜索关键词返回的结果
         */
        fun setSearchResult(issue: HomeBean.Issue)
        /**
         * 关闭软件盘
         */
        fun closeSoftKeyboard()

        /**
         * 设置空 View
         */
        fun setEmptyView()


        fun showError(errorMsg: String,errorCode:Int)
    }


    interface Presenter : V_IPresenter<View> {
        /**
         * 获取热门关键字的数据
         */
        fun requestHotWordData()

        /**
         * 查询搜索
         */
        fun querySearchData(words:String)

        /**
         * 加载更多
         */
        fun loadMoreData()
    }
}