package com.vension.mvp.mvp.headline.model

import com.vension.mvp.beans.headline.NewsResponse
import com.vension.mvp.http.RetrofitFactory
import com.vension.mvp.http.rxscheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 10:56
 * 描  述：新闻数据模型
 * ========================================================
 */

class NewListModel {


    /**
     * 获取分类信息
     */
    fun getNewsList(category : String,lastTime : Long,currentTime:Long): Observable<NewsResponse> {
        return RetrofitFactory.toutiaoService.getNewsList(category,lastTime,currentTime)
                .compose(SchedulerUtils.ioToMain())
    }

}