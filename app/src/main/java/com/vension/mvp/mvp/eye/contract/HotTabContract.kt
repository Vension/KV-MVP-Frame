package com.vension.mvp.mvp.eye.contract

import com.kevin.vension.demo.base.V_IBaseView
import com.kevin.vension.demo.base.V_IPresenter
import com.vension.mvp.beans.eyes.TabInfoBean

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 16:16
 * 描  述：契约类
 * ========================================================
 */

interface HotTabContract {

    interface View:V_IBaseView{
        /**
         * 设置 TabInfo
         */
        fun setTabInfo(tabInfoBean: TabInfoBean)

        fun showError(errorMsg:String)
    }


    interface Presenter:V_IPresenter<View>{
        /**
         * 获取 TabInfo
         */
        fun getTabInfo()
    }
}