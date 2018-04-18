package com.vension.mvp.mvp.eye.model

import com.vension.mvp.beans.eyes.CategoryBean
import com.vension.mvp.http.RetrofitFactory
import com.vension.mvp.http.rxscheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/12 17:28
 * 描  述：分类数据模型
 * ========================================================
 */

class CategoryModel {


    /**
     * 获取分类信息
     */
    fun getCategoryData(): Observable<ArrayList<CategoryBean>> {
        return RetrofitFactory.eyesService.getCategory()
                .compose(SchedulerUtils.ioToMain())
    }

}