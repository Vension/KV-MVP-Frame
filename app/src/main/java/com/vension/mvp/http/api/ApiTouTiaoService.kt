package com.vension.mvp.http.api

import com.vension.mvp.beans.headline.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

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

    val GET_COMMENT_LIST: String get() = "article/v2/tab_comments/"
    //http://is.snssdk.com
    //http://is.snssdk.com/api/news/feed/v54/?refer=1&count=20&min_behot_time=1498722625&last_refresh_sub_entrance_interval=1498724693&loc_mode=4&tt_from=pull（tab_tip） 新闻列表
    //http://is.snssdk.com/article/v2/tab_comments/?group_id=6436886053704958466&item_id=6436886053704958466&offset=30&count=20 评论
    //http://is.snssdk.com/2/article/information/v21/ 详情

    /**
     * 获取新闻列表
     *
     * @param category 频道
     * @return
     */
    @GET("api/news/feed/v62/?refer=1&count=20&loc_mode=4&device_id=34960436458&iid=13136511752")
    fun getNewsList(@Query("category") category: String,
                    @Query("min_behot_time") lastTime: Long,
                    @Query("last_refresh_sub_entrance_interval") currentTime: Long)
            : Observable<NewsResponse>


    @POST("http://service.iiilab.com/video/toutiao")
    fun getVideoPath(@Query("link") link: String, @Query("r") r: String, @Query("s") s: String): Observable<VideoPathResponse>


    /**
     * 获取视频信息
     * Api 生成较复杂 详情查看
     * http://ib.365yg.com/video/urls/v/1/toutiao/mp4/视频ID?r=17位随机数&s=加密结果
     */
    @GET
    fun getVideoContent(@Url url: String): Observable<VideoContentBean>

    /**
     * 获取新闻详情
     */
    @GET
    fun getNewsDetail(@Url url: String): Observable<ResultResponse<NewsDetail>>

    /**
     * 获取评论列表数据
     *
     * @param groupId
     * @param itemId
     * @param offset
     * @param count
     * @return
     */
    @GET("article/v2/tab_comments/")
    fun getComment(@Query("group_id") groupId: String, @Query("item_id") itemId: String,
                   @Query("offset") offset: String, @Query("count") count: String)
                  : Observable<CommentResponse>


    /**
     * 获取搜索推荐
     * http://is.snssdk.com/search/suggest/wap/initial_page/?from=feed&sug_category=__all__&iid=10344168417&device_id=36394312781&format=json
     */
    @GET("http://is.snssdk.com/search/suggest/wap/initial_page/?from=feed&sug_category=__all__&iid=10344168417&device_id=36394312781&format=json")
     fun getSearchRecomment(): Observable<SearchRecommentBean>

    /**
     * 获取搜索建议
     * http://is.snssdk.com/2/article/search_sug/?keyword=3&from=search_tab&iid=10344168417&device_id=36394312781
     *
     * @param keyword 搜索内容
     */
    @GET("http://is.snssdk.com/2/article/search_sug/?from=search_tab&iid=10344168417&device_id=36394312781")
     fun getSearchSuggestion(@Query("keyword") keyword: String): Observable<SearchSuggestionBean>

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
     * 获取搜索视频内容
     * https://m.365yg.com/i6436151402837312001/info/
     */
//    @GET
//    abstract fun getSearchVideoInfo(@Url url: String): Observable<SearchVideoInfoBean>







}