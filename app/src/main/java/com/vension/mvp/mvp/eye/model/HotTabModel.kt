package com.vension.mvp.mvp.eye.model

import com.vension.mvp.beans.eyes.TabInfoBean
import com.vension.mvp.http.RetrofitFactory
import com.vension.mvp.http.rxscheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 16:17
 * 描  述：热门 Model
 * ========================================================
 */

class HotTabModel {

    /**
     * 获取 TabInfo
     */
    fun getTabInfo(): Observable<TabInfoBean> {
        return RetrofitFactory
                .eyesService.getRankList()
                .compose(SchedulerUtils.ioToMain())
    }

}
