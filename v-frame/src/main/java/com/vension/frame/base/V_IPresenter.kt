package com.vension.frame.base

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 15:03
 * 描  述：使用 in 符号修饰参数类型，表示只能被用作方法的参数，被操作，而不能作为返回值使用： <in T>
 * ========================================================
 */

interface V_IPresenter<in V : V_IBaseView> {

    fun attachView(mRootView: V)

    fun detachView()

}