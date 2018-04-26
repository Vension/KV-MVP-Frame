package com.vension.mvp.mvp.headline.model

import com.vension.mvp.beans.headline.CommentResponse
import com.vension.mvp.beans.headline.NewsDetail
import com.vension.mvp.beans.headline.ResultResponse
import com.vension.mvp.http.RetrofitFactory
import com.vension.mvp.http.rxscheduler.SchedulerUtils
import io.reactivex.Observable


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/23 15:53
 * 描  述：新闻详情数据模型
 * ========================================================
 */

class NewsDetailModel {


    fun getNewsDetail(url : String): Observable<ResultResponse<NewsDetail>> {
        return RetrofitFactory.toutiaoService.getNewsDetail(url)
                .compose(SchedulerUtils.ioToMain())
    }

    fun getNewsComment(groupId: String,itemId: String,offset: String,count: String): Observable<CommentResponse> {
        return RetrofitFactory.toutiaoService.getComment(groupId,itemId,offset,count)
                .compose(SchedulerUtils.ioToMain())
    }


}