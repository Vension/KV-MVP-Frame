package com.vension.mvp.utils

import android.graphics.Color

//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃   神兽保佑
//    ┃　　　┃   代码无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/10 10:32
 * 描  述：常量
 * ========================================================
 */

object Constants {

    // 打开对象Fragment类
    const val AGENT_FRAGMENT_CLASS_KEY = "Agent Fragment class key"

    const val TRANSITION_NAME = "shareView"
    const val BUNDLE_VIDEO_DATA = "video_data"
    const val BUNDLE_CATEGORY_DATA = "category_data"

    /** ============== 头条相关Start ========================= */
    //已选中频道的json
    const val SELECTED_CHANNEL_JSON = "selectedChannelJson"
    //未选频道的json
    const val UNSELECTED_CHANNEL_JSON = "unselectChannelJson"

    //频道对应的请求参数
    const val CHANNEL_CODE = "channelCode"
    const val IS_VIDEO_LIST = "isVideoList"

    const val ARTICLE_GENRE_VIDEO = "video"
    const val ARTICLE_GENRE_AD = "ad"
    const val TAG_MOVIE = "video_movie"

    const val DATA_SELECTED = "dataSelected"
    const val DATA_UNSELECTED = "dataUnselected"

    /**获取评论列表每页的数目 */
    const val COMMENT_PAGE_SIZE = 20

    /** ============== 头条相关End ========================= */


    //sp 存储的文件名
    const val FILE_WATCH_HISTORY_NAME = "watch_history_file"   //观看记录
    const val FILE_COLLECTION_NAME = "collection_file"    //收藏视屏缓存的文件名

    //================= TYPE ====================

    const val TYPE_ZHIHU = 101

    const val TYPE_ANDROID = 102

    const val TYPE_IOS = 103

    const val TYPE_WEB = 104

    const val TYPE_GIRL = 105

    const val TYPE_WECHAT = 106

    val TYPE_GANK = 107

    val TYPE_GOLD = 108

    val TYPE_VTEX = 109

    val TYPE_SETTING = 110

    val TYPE_LIKE = 111

    val TYPE_ABOUT = 112

    const val USER_AGENT_MOBILE = "Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Mobile Safari/537.36"

    const val USER_AGENT_PC = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36"

    val TAG_COLORS = intArrayOf(Color.parseColor("#90C5F0"), Color.parseColor("#91CED5"), Color.parseColor("#F88F55"), Color.parseColor("#C0AFD0"), Color.parseColor("#E78F8F"), Color.parseColor("#67CCB7"), Color.parseColor("#F6BC7E"))

    val ICONS_TYPE = arrayOf("circle", "rect", "square")

    val SLIDABLE_DISABLE = 0
    val SLIDABLE_EDGE = 1
    val SLIDABLE_FULL = 2

    val AS = "as"
    val CP = "cp"

    const val NEWS_CHANNEL_ENABLE = 1
    const val NEWS_CHANNEL_DISABLE = 0

    /**
     * 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
     * [com.meiji.toutiao.module.news.content.NewsContentPresenter.openImage]
     */
    val JS_INJECT_IMG = ("javascript:(function(){" +
            "var objs = document.getElementsByTagName(\"img\"); " +
            "for(var i=0;i<objs.length;i++)  " +
            "{"
            + "    objs[i].onclick=function()  " +
            "    {  "
            + "        window.imageListener.openImage(this.src);  " +
            "    }  " +
            "}" +
            "})()")

}