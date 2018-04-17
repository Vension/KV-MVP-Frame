package com.vension.mvp.http.api

import com.vension.mvp.beans.SearchRecommentBean
import com.vension.mvp.beans.headline.joke.JokeCommentBean
import com.vension.mvp.beans.headline.joke.JokeContentBean
import com.vension.mvp.utils.Constants
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by xuhao on 2017/11/16.
 *
 */

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 18:00
 * 描  述：头条Api 接口
 * ========================================================
 */

interface ApiTouTiaoService{

    /**
     * 获取段子正文内容
     * http://www.toutiao.com/api/article/feed/?category=essay_joke&as=A115C8457F69B85&cp=585F294B8845EE1
     */
    @GET("api/article/feed/?category=essay_joke")
    abstract fun getJokeContent(
            @Query("max_behot_time") maxBehotTime: String,
            @Query("as") `as`: String,
            @Query("cp") cp: String): Observable<JokeContentBean>

    /**
     * 获取段子评论
     * http://m.neihanshequ.com/api/get_essay_comments/?group_id=编号&count=数量&offset=偏移量
     */
    @GET("http://m.neihanshequ.com/api/get_essay_comments/?count=20")
    @Headers("User-Agent:" + Constants.USER_AGENT_MOBILE)
    abstract fun getJokeComment(
            @Query("group_id") groupId: String,
            @Query("offset") offset: Int): Observable<JokeCommentBean>


    /**
     * 获取搜索建议
     * http://is.snssdk.com/2/article/search_sug/?keyword=3&from=search_tab&iid=10344168417&device_id=36394312781
     *
     * @param keyword 搜索内容
     */
//    @GET("http://is.snssdk.com/2/article/search_sug/?from=search_tab&iid=10344168417&device_id=36394312781")
//    abstract fun getSearchSuggestion(@Query("keyword") keyword: String): Observable<SearchSuggestionBean>

    /**
     * 获取搜索结果
     * http://is.snssdk.com/api/2/wap/search_content/?from=search_tab&keyword=123&iid=10344168417&device_id=36394312781&count=10&cur_tab=1&format=json&offset=20
     *
     * @param keyword 搜索内容
     * @param curTab  搜索栏目 1综合 2视频 3图集 4用户 5问答
     * @param offset  偏移量
     */
//    @GET("http://is.snssdk.com/api/2/wap/search_content/?from=search_tab&iid=12507202490&device_id=37487219424&count=10&format=json")
//    abstract fun getSearchResult(
//            @Query("keyword") keyword: String,
//            @Query("cur_tab") curTab: String,
//            @Query("offset") offset: Int): Observable<SearchResultBean>

//    @GET("http://is.snssdk.com/api/2/wap/search_content/?from=search_tab&iid=12507202490&device_id=37487219424&count=10&format=json")
//    abstract fun getSearchResult2(
//            @Query("keyword") keyword: String,
//            @Query("cur_tab") curTab: String,
//            @Query("offset") offset: Int): Observable<ResponseBody>

    /**
     * 获取搜索推荐
     * http://is.snssdk.com/search/suggest/wap/initial_page/?from=feed&sug_category=__all__&iid=10344168417&device_id=36394312781&format=json
     */
    @GET("http://is.snssdk.com/search/suggest/wap/initial_page/?from=feed&sug_category=__all__&iid=10344168417&device_id=36394312781&format=json")
    abstract fun getSearchRecomment(): Observable<SearchRecommentBean>

    /**
     * 获取搜索视频内容
     * https://m.365yg.com/i6436151402837312001/info/
     */
//    @GET
//    abstract fun getSearchVideoInfo(@Url url: String): Observable<SearchVideoInfoBean>







}