package com.vension.mvp.http.rxscheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/3 9:34
 * 描  述：
 * ========================================================
 */

class IoMainScheduler<T> : BaseScheduler<T>(Schedulers.io(), AndroidSchedulers.mainThread())
