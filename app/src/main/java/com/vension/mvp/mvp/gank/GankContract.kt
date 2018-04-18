package com.vension.mvp.mvp.gank

import com.vension.frame.base.V_IBaseView
import com.vension.frame.base.V_IPresenter
import com.vension.mvp.beans.gank.GankResult

/**
 * @author ：Created by Administrator on 2018/4/3 18:00.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
interface GankContract {

    interface View : V_IBaseView {

        /**
         * 设置Gank福利数据
         */
        fun setGrilData(isRefresh : Boolean,data: GankResult)

        /**
         * 设置Gank技术数据
         */
        fun setTechData(isRefresh : Boolean,data: GankResult)

        /**
         * 显示错误信息
         */
        fun showError(msg: String,errorCode:Int)

    }

    interface Presenter : V_IPresenter<View> {


        /**
         * 获取Gank福利数据
         */
        fun loadGrilList(isRefresh : Boolean,limit: Int, page: Int)

        /**
         * 加载技术类文章
         * @param isRefresh 是否是刷新
         */
        fun loadTechList(isRefresh : Boolean,type : String,page : Int,limit: Int)
    }

}