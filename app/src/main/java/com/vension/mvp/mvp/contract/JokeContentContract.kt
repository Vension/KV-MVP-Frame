package com.vension.mvp.mvp.contract

import com.kevin.vension.demo.base.V_IBaseView
import com.kevin.vension.demo.base.V_IPresenter

/**
 * @author ：Created by Administrator on 2018/4/3 18:00.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
interface JokeContentContract {

    interface View : V_IBaseView{
        /**
         * 请求数据
         */
         fun onLoadData()
    }

    interface Presenter : V_IPresenter<View>{
        /**
         * 请求数据
         */
         fun doLoadData()

        /**
         * 再起请求数据
         */
         fun doLoadMoreData()

        /**
         * 设置适配器
         */
         fun doSetAdapter()

         fun doShowNoMore()
    }
}