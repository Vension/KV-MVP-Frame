package com.vension.mvp.mvp.eye.model

import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.http.RetrofitFactory
import com.vension.mvp.http.rxscheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 16:31
 * 描  述：排行榜 Model
 * ========================================================
 */

class RankModel {

    /**
     * 获取排行榜
     */
    fun requestRankList(apiUrl:String): Observable<HomeBean.Issue> {
        return RetrofitFactory.eyesService.getIssueData(apiUrl)
                .compose(SchedulerUtils.ioToMain())
    }

}
