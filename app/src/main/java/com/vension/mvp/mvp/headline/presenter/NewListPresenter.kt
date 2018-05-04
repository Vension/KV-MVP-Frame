package com.vension.mvp.mvp.headline.presenter

import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import com.vension.frame.VFrame.TAG
import com.vension.frame.base.V_BasePresenter
import com.vension.frame.utils.VLogUtil
import com.vension.mvp.beans.headline.NewsBean
import com.vension.mvp.http.RetrofitFactory
import com.vension.mvp.http.api.ApiTouTiaoService
import com.vension.mvp.http.exception.ExceptionHandle
import com.vension.mvp.mvp.headline.contract.NewListContract
import com.vension.mvp.mvp.headline.model.NewListModel
import com.vension.mvp.utils.PreUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.zip.CRC32

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 11:13
 * 描  述：新闻的 Presenter
 * ========================================================
 */

class NewListPresenter : V_BasePresenter<NewListContract.View>(), NewListContract.Presenter {

    private var lastTime: Long = 0

    private val mNewListModel: NewListModel by lazy {
        NewListModel()
    }

    override fun getNewsList(channelCode: String) {
        checkViewAttached()
        mRootView?.showLoading()
        lastTime = PreUtils.getLong(channelCode, 0)//读取对应频道下最后一次刷新的时间戳
        if (lastTime == 0L) {
            //如果为空，则是从来没有刷新过，使用当前时间戳
            lastTime = System.currentTimeMillis() / 1000
        }
        val disposable = mNewListModel.getNewsList(channelCode,lastTime,System.currentTimeMillis() / 1000)
                .subscribe({ response ->
                    mRootView?.apply {
                        dismissLoading()

                        lastTime = System.currentTimeMillis() / 1000
                        PreUtils.putLong(channelCode, lastTime)//保存刷新的时间戳

                        val data = response.data
                        val newsList = ArrayList<NewsBean>()

                        if (data.isNotEmpty()) {
                            for (newsData in data) {
                                var news = Gson().fromJson(newsData.content, NewsBean::class.java)
                                newsList.add(news)
                            }
                        }

                        //TODO 解密头条视频（暂时先这么处理，后续优化）
                        newsList.filter { item ->
                            item.has_video && item.video_detail_info != null
                        }.forEach{ item ->
                            synchronized (this) {
                            val url = getVideoContentApi(item.video_detail_info.video_id)
                            RetrofitFactory.getRetrofit()
                                    ?.create(ApiTouTiaoService::class.java)
                                    ?.getVideoContent(url)
                                    ?.subscribeOn(Schedulers.io())
                                    ?.map({ videoContentBean ->
                                        val videoList = videoContentBean.data.video_list
                                        if (videoList.video_3 != null) {
                                            val base64 = videoList.video_3.main_url
                                            val url1 = String(Base64.decode(base64.toByteArray(), Base64.DEFAULT))
                                            Log.d(TAG, "getVideoUrls: $url1")
                                            return@map url1
                                        }

                                        if (videoList.video_2 != null) {
                                            val base64 = videoList.video_2.main_url
                                            val url1 = String(Base64.decode(base64.toByteArray(), Base64.DEFAULT))
                                            Log.d(TAG, "getVideoUrls: $url1")
                                            return@map url1
                                        }

                                        if (videoList.video_1 != null) {
                                            val base64 = videoList.video_1.main_url
                                            val url1 = String(Base64.decode(base64.toByteArray(), Base64.DEFAULT))
                                            Log.d(TAG, "getVideoUrls: $url1")
                                            return@map url1
                                        }
                                        return@map null
                                    })
                                    ?.observeOn(AndroidSchedulers.mainThread())
                                    ?.subscribe({ s ->
                                        item.url = s.toString()
//                                        onGetNewsListSuccess(newsList, response.tips.display_info)
                                        Log.d(TAG, "subscribe: ${item.url}")
                                    }, { throwable ->

                                    })
//                            item.url = doLoadVideoData(item.video_detail_info.video_id)
                            VLogUtil.e("video_url==>${item.url}")
                        }
                        }
                        VLogUtil.e(newsList.toString())
                        onGetNewsListSuccess(newsList, response.tips.display_info)
                    }
                }, { t ->
                    mRootView?.apply {
                        dismissLoading()
                        //处理异常
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }

                })

        addSubscription(disposable)

    }


    private fun getVideoContentApi(videoid: String): String {
        val VIDEO_HOST = "http://ib.365yg.com"
        val VIDEO_URL = "/video/urls/v/1/toutiao/mp4/%s?r=%s"
        val r = getRandom()
        val s = String.format(VIDEO_URL, videoid, r)
        // 将/video/urls/v/1/toutiao/mp4/{videoid}?r={Math.random()} 进行crc32加密
        val crc32 = CRC32()
        crc32.update(s.toByteArray())
        val crcString = crc32.value.toString() + ""
        return "$VIDEO_HOST$s&s=$crcString"
    }


    private fun getRandom(): String {
        val random = Random()
        val result = StringBuilder()
        for (i in 0..15) {
            result.append(random.nextInt(10))
        }
        return result.toString()
    }

}