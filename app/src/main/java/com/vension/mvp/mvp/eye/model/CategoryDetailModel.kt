package com.vension.mvp.mvp.eye.model

import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.http.RetrofitFactory
import com.vension.mvp.http.rxscheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/13 9:41
 * 描  述：分类详情的 Model
 * ========================================================
 */

class CategoryDetailModel {

    /**
     * 获取分类下的 List 数据
     */
    fun getCategoryDetailList(id: Long): Observable<HomeBean.Issue> {
        return RetrofitFactory
                .eyesService.getCategoryDetailList(id)
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData(url: String): Observable<HomeBean.Issue> {
        return RetrofitFactory
                .eyesService.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }

}