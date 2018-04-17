package com.vension.mvp.mvp.eye.model

import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.http.RetrofitFactory
import com.vension.mvp.http.rxscheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 14:15
 * 描  述：关注Model
 * ========================================================
 */

class FollowModel {

    /**
     * 获取关注信息
     */
    fun requestFollowList(): Observable<HomeBean.Issue> {
        return RetrofitFactory.eyesService.getFollowInfo()
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多
     */
    fun loadMoreData(url:String):Observable<HomeBean.Issue>{
        return RetrofitFactory.eyesService.getIssueData(url)
                .compose(SchedulerUtils.ioToMain())
    }


}
