package com.vension.mvp.mvp.eye.contract

import com.kevin.vension.demo.base.V_IBaseView
import com.kevin.vension.demo.base.V_IPresenter
import com.vension.mvp.beans.eyes.CategoryBean

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/12 17:25
 * 描  述：分类 契约类
 * ========================================================
 */

interface CategoryContract {

    interface View : V_IBaseView {
        /**
         * 显示分类的信息
         */
        fun showCategory(categoryList: ArrayList<CategoryBean>)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg:String,errorCode:Int)
    }

    interface Presenter:V_IPresenter<View>{
        /**
         * 获取分类的信息
         */
        fun getCategoryData()
    }

}