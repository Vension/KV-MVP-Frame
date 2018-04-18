package com.vension.mvp.mvp.eye.model

import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.http.RetrofitFactory
import com.vension.mvp.http.rxscheduler.SchedulerUtils
import io.reactivex.Observable


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 17:58
 * 描  述：首页精选 model
 * ========================================================
 */

class HomeModel{

    /**
     * 获取首页 Banner 数据
     */
    fun requestHomeData(num:Int):Observable<HomeBean>{
        return RetrofitFactory.eyesService.getFirstHomeData(num)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多
     */
    fun loadMoreData(url:String):Observable<HomeBean>{
        return RetrofitFactory.eyesService.getMoreHomeData(url)
                .compose(SchedulerUtils.ioToMain())
    }



}
