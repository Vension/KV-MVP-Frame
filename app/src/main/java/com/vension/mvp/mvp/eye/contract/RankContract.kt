package com.vension.mvp.mvp.eye.contract

import com.vension.frame.base.V_IBaseView
import com.vension.frame.base.V_IPresenter
import com.vension.mvp.beans.eyes.HomeBean

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 16:29
 * 描  述：契约类
 * ========================================================
 */

interface RankContract {

    interface View: V_IBaseView {
        /**
         * 设置排行榜的数据
         */
        fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg:String,errorCode:Int)
    }


    interface Presenter: V_IPresenter<View> {
        /**
         * 获取 TabInfo
         */
        fun requestRankList(apiUrl:String)
    }
}