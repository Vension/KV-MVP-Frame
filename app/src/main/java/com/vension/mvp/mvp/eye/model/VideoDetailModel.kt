package com.vension.mvp.mvp.eye.model

import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.http.RetrofitFactory
import com.vension.mvp.http.rxscheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/13 12:19
 * 描  述：
 * ========================================================
 */

class VideoDetailModel {

    fun requestRelatedData(id:Long):Observable<HomeBean.Issue>{
        return RetrofitFactory.eyesService.getRelatedData(id)
                .compose(SchedulerUtils.ioToMain())
    }

}