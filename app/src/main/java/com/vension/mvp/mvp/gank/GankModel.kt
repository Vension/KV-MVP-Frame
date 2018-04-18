package com.vension.mvp.mvp.gank

import com.vension.mvp.beans.gank.GankResult
import com.vension.mvp.http.RetrofitFactory
import com.vension.mvp.http.rxscheduler.SchedulerUtils
import io.reactivex.Flowable

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/3 18:00
 * 描  述：Gank Model
 * ========================================================
 */

class GankModel {

    /**
     * 根据Gank类型获取数据
     */
    fun getGrilList(limit: Int, page: Int): Flowable<GankResult> {
        return RetrofitFactory
                .gankService
                .getGirlList(limit,page)
                .compose(SchedulerUtils.ioToMain())
    }
    /**
     * 根据Gank类型获取数据
     */
    fun getTechList(type : String,limit: Int, page: Int): Flowable<GankResult> {
        return RetrofitFactory
                .gankService
                .getTechList(type,limit,page)
                .compose(SchedulerUtils.ioToMain())
    }


//    /**
//     * 搜索关键词返回的结果
//     */
//    fun getSearchResult(words: String):Observable<HomeBean.Issue>{
//        return RetrofitFactory.eyesService.getSearchData(words)
//                .compose(SchedulerUtils.ioToMain())
//    }
//
//    /**
//     * 加载更多数据
//     */
//    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
//        return RetrofitManager.service.getIssueData(url)
//                .compose(SchedulerUtils.ioToMain())
//    }

}
