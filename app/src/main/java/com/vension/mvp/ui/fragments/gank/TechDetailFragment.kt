package com.vension.mvp.ui.fragments.gank

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import com.vension.frame.utils.VToastUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.vension.mvp.db.realm.RealmHelper
import com.vension.mvp.db.realm.bean.RealmLikeBean
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_gank_tech_detail.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/11 9:34
 * 描  述：gank 技术类文章详情
 * ========================================================
 */

class TechDetailFragment : BaseFragment(){

    var title: String? = null
    var url:String? = null
    var imgUrl:String? = null
    var id:String? = null
    var type: Int = 0
    private var isLiked: Boolean = false
    private var mRealmHelper: RealmHelper? = null

    override fun getToolBarResId(layout: Int): Int {
        return R.layout.layout_toolbar_gank_tech_detail
    }


    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        id = arguments?.getString("id")
        title = arguments?.getString("title")
        imgUrl = arguments?.getString("image_url")
        url = arguments?.getString("url")
        type = arguments?.getInt("type")!!
        mCommonTitleBar.centerTextView.text = title
        mRealmHelper = RealmHelper()
        setLikeState(mCommonTitleBar.rightImageButton,mRealmHelper?.queryLikeId(id)!!)
        mCommonTitleBar.setListener {v: View?, action: Int, extra: String? ->
            when(action){
                CommonTitleBar.ACTION_LEFT_BUTTON, CommonTitleBar.ACTION_LEFT_TEXT -> {
                    activity?.onBackPressed()
                }
                CommonTitleBar.ACTION_RIGHT_TEXT, CommonTitleBar.ACTION_RIGHT_BUTTON -> {
                    if (isLiked) {
                        mRealmHelper?.deleteLikeBean(id)
                        VToastUtil.showSuccess("取消收藏成功")
                    } else {
                        val bean = RealmLikeBean()
                        bean.id = this.id
                        bean.image = imgUrl
                        bean.url = url
                        bean.title = title
                        bean.type = type
                        bean.time = System.currentTimeMillis()
                        mRealmHelper?.insertLikeBean(bean)
                        VToastUtil.showSuccess("添加收藏成功")
                    }
                    isLiked = !isLiked
                    setLikeState(v as ImageButton,isLiked)
                }
            }
        }
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_gank_tech_detail
    }


    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        //禁止跳转到第三方浏览器
        wv_tech_detail.webViewClient = WebViewClient()
        //允许JS互调。可以使用网页上的功能代码了
        wv_tech_detail.settings.javaScriptEnabled = true
        //可以设置进度条和页面进度
        wv_tech_detail.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    tech_detail_progress.visibility = View.GONE
                } else {
                    tech_detail_progress.visibility = View.VISIBLE
                    tech_detail_progress.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }
        }
        wv_tech_detail.loadUrl(url)
    }

    override fun lazyLoadData() {
    }


    /**
     * 设置收藏按钮的状态
     */
    private fun setLikeState(view : ImageButton, state: Boolean) {
        isLiked = if (state) {
            view.setImageResource(R.drawable.ic_favorite_main_24dp)
            true
        } else {
            view.setImageResource(R.drawable.ic_favorite_black_24dp)
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(wv_tech_detail != null){
            wv_tech_detail.destroy()
        }
    }
    
}