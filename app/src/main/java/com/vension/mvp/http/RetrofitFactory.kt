package com.vension.mvp.http

import com.vension.frame.VFrame
import com.vension.frame.utils.NetworkUtil
import com.vension.frame.utils.VPreference
import com.vension.mvp.http.api.ApiEyesService
import com.vension.mvp.http.api.ApiTouTiaoService
import com.vension.mvp.http.api.GankApiService
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 17:35
 * 描  述：网络工厂
 * ========================================================
 */

object RetrofitFactory{

    private var mClient: OkHttpClient? = null
    private var mRetrofitNews: Retrofit? = null
    private var mRetrofitEyes: Retrofit? = null
    private var mRetrofitGanks: Retrofit? = null
    @Volatile
    private var mRetrofit: Retrofit? = null
    val toutiaoService: ApiTouTiaoService by lazy { getRetrofitNews()!!.create(ApiTouTiaoService::class.java)}
    val eyesService: ApiEyesService by lazy { getRetrofitEyes()!!.create(ApiEyesService::class.java)}
    val gankService: GankApiService by lazy { getRetrofitGanks()!!.create(GankApiService::class.java)}

    private var token:String by  VPreference("token","")

    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    // Provide your custom parameter here
                    .addQueryParameter("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/8.0.0.13547")
                    .addQueryParameter("Cache-Control", "max-age=0")
                    .addQueryParameter("Upgrade-Insecure-Requests", "1")
                    .addQueryParameter("X-Requested-With", "XMLHttpRequest")
                    .addQueryParameter("Cookie", "uuid=\"w:f2e0e469165542f8a3960f67cb354026\"; __tasessionId=4p6q77g6q1479458262778; csrftoken=7de2dd812d513441f85cf8272f015ce5; tt_webid=36385357187")
                    .addQueryParameter("phoneSystem", "")
                    .addQueryParameter("phoneModel", "")
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    // Provide your custom header here
                    .header("token", token)
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }


    /**
     * 缓存机制
     * 在响应请求之后在 data/data/<包名>/cache 下建立一个response 文件夹，保持缓存数据。
     * 这样我们就可以在请求的时候，如果判断到没有网络，自动读取缓存的数据。
     * 同样这也可以实现，在我们没有网络的情况下，重新打开App可以浏览的之前显示过的内容。
     * 也就是：判断网络，有网络，则从网络获取，并保存到缓存中，无网络，则从缓存中获取。
     * https://werb.github.io/2016/07/29/%E4%BD%BF%E7%94%A8Retrofit2+OkHttp3%E5%AE%9E%E7%8E%B0%E7%BC%93%E5%AD%98%E5%A4%84%E7%90%86/
     */
    private fun addCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            //网络不可用
            if (!NetworkUtil.isNetworkAvailable(VFrame.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }
            val response = chain.proceed(request)
            //网络已连接
            if (NetworkUtil.isNetworkAvailable(VFrame.getContext())) {
                val maxAge = 0
                // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build()
            } else {
                // 无网络时，设置超时为1周  只对get有用,post没有缓冲
                val maxStale = 60 * 60 * 24 * 7
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .removeHeader("Pragma")
                        .build()
            }
            response
        }
    }

    private fun getRetrofitNews(): Retrofit? {
        if (mRetrofitNews == null) {
            synchronized(RetrofitFactory::class.java) {
                if (mRetrofitNews == null) {
                    // 获取retrofit的实例
                    mRetrofitNews = Retrofit.Builder()
                            .baseUrl(UriConstant.BASE_URL_TOUTIAO)  //自己配置
                            .client( createClient())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
            }
        }
        return mRetrofitNews
    }

    private fun getRetrofitEyes(): Retrofit? {
        if (mRetrofitEyes == null) {
            synchronized(RetrofitFactory::class.java) {
                if (mRetrofitEyes == null) {
                    // 获取retrofit的实例
                    mRetrofitEyes = Retrofit.Builder()
                            .baseUrl(UriConstant.BASE_URL_EYES)  //自己配置
                            .client( createClient())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
            }
        }
        return mRetrofitEyes
    }

    private fun getRetrofitGanks(): Retrofit? {
        if (mRetrofitGanks == null) {
            synchronized(RetrofitFactory::class.java) {
                if (mRetrofitGanks == null) {
                    // 获取retrofit的实例
                    mRetrofitGanks = Retrofit.Builder()
                            .baseUrl(UriConstant.BASE_URL_GANKS)  //自己配置
                            .client( createClient())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
            }
        }
        return mRetrofitGanks
    }


    fun getRetrofit(): Retrofit? {
        if (mRetrofit == null) {
            synchronized(RetrofitFactory::class.java) {
                if (mRetrofit == null) {
                    // 获取retrofit的实例
                    mRetrofit = Retrofit.Builder()
                            .baseUrl(UriConstant.BASE_URL_GANKS)  //自己配置
                            .client( createClient())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
            }
        }
        return mRetrofit
    }


    private fun createClient(): OkHttpClient? {
        if (mClient == null) {
            synchronized(RetrofitFactory::class.java) {
                if (mClient == null) {

                    //添加一个log拦截器,打印所有的log
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    //可以设置请求过滤的水平,body,basic,headers
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                    //设置 请求的缓存的大小跟位置
                    val cacheFile = File(VFrame.getContext().cacheDir, "HttpCache")
                    val cache = Cache(cacheFile, 1024 * 1024 * 50) //50Mb 缓存的大小

                    // Cookie 持久化
//                    val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(InitApp.AppContext))

                    mClient = OkHttpClient.Builder()
//                            .cookieJar(cookieJar)
                            .addInterceptor(addQueryParameterInterceptor())  //参数添加
                            .addInterceptor(addHeaderInterceptor()) // token过滤
                            .addInterceptor(addCacheInterceptor()) //添加网络缓存拦截
                            .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                            .cache(cache)  //添加缓存
                            .connectTimeout(60L, TimeUnit.SECONDS)
                            .readTimeout(60L, TimeUnit.SECONDS)
                            .writeTimeout(60L, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true)//重连
                            .build()
                }
            }
        }

        return mClient
    }


}

