package com.vension.mvp.http.api

import com.vension.mvp.beans.gank.GankItemBean
import com.vension.mvp.beans.gank.GankResult
import com.vension.mvp.http.response.GankHttpResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/4 15:24
 * 描  述：Gank Api接口
 * ========================================================
 */

interface GankApiService {

    /**
     * 技术文章列表
     */
    @GET("data/{tech}/{num}/{page}")
     fun getTechList(@Path("tech") tech: String, @Path("num") num: Int, @Path("page") page: Int): Flowable<GankResult>
//     fun getTechList(@Path("tech") tech: String, @Path("num") num: Int, @Path("page") page: Int): Flowable<GankHttpResponse<List<GankItemBean>>>

    /**
     * 妹纸列表
     */
    @GET("data/福利/{num}/{page}")
     fun getGirlList(@Path("num") num: Int, @Path("page") page: Int): Flowable<GankResult>
//     fun getGirlList(@Path("num") num: Int, @Path("page") page: Int): Flowable<GankHttpResponse<List<GankItemBean>>>

    /**
     * 随机妹纸图
     */
    @GET("random/data/福利/{num}")
    abstract fun getRandomGirl(@Path("num") num: Int): Flowable<GankHttpResponse<List<GankItemBean>>>

    /**
     * 搜索
     */
//    @GET("search/query/{query}/category/{type}/count/{count}/page/{page}")
//    abstract fun getSearchList(@Path("query") query: String, @Path("type") type: String, @Path("count") num: Int, @Path("page") page: Int): Flowable<GankHttpResponse<List<GankSearchItemBean>>>

    //===================================================================



    //    @Headers("Cache-Control: public, max-age=3600")
//    @GET("day/{year}/{month}/{day}")
//     fun getGankDay(@Path("year") year: Int, @Path("month") month: Int, @Path("day") day: Int): Observable<GankDay>


    @GET("api/day/{year}/{month}/{day}")
    fun getDataByDate(@Path("year") year: String,
                      @Path("month") month: String,
                      @Path("day") day: String)

    @GET("api/day/{date}")
    fun getDataByDate(@Path("date") date: String):Observable<ResponseBody>

    @GET("history")
    fun getHistory():Observable<ResponseBody>

//    @GET("api/day/history")
//    fun getPublishedDate():Observable<PublishedDate>

}