package com.vension.mvp.mvp.eye.model

import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.http.RetrofitFactory
import com.vension.mvp.http.rxscheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/17 11:54
 * 描  述：搜索 Model
 * ========================================================
 */

class SearchModel {

    /**
     * 请求热门关键词的数据
     */
    fun requestHotWordData(): Observable<ArrayList<String>> {
        return RetrofitFactory.eyesService.getHotWord()
                .compose(SchedulerUtils.ioToMain())
    }


    /**
     * 搜索关键词返回的结果
     */
    fun getSearchResult(words: String):Observable<HomeBean.Issue>{
        return RetrofitFactory.eyesService.getSearchData(words)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitFactory.eyesService.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }

}
