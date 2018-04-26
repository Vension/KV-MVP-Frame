package com.vension.mvp.ui.activitys.headline

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.OnClick
import com.vension.frame.utils.VLogUtil
import com.vension.mvp.R
import com.vension.mvp.beans.headline.NewsDetail
import com.vension.mvp.beans.headline.ResultResponse
import kotlinx.android.synthetic.main.activity_news_detail.*
import kotlinx.android.synthetic.main.include_top.*
import kotlinx.android.synthetic.main.layout_news_detail_bottombar.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/24 11:21
 * 描  述：非视频新闻详情
 * ========================================================
 */

class NewsDetailActivity : NewsDetailBaseActivity() {

    override fun getViewContentViewId(): Int {
        return R.layout.activity_news_detail
    }

    override fun initViewAndData(savedInstanceState: Bundle?) {
        mLayoutStatusView = layout_new_detail_multiple
        mRvComment = rv_comment
        mTvCommentCount = tv_comment_count
        super.initViewAndData(savedInstanceState)

        val llInfoBottom = mHeaderView?.mLlInfo?.bottom
        val layoutManager = mRvComment?.layoutManager as LinearLayoutManager
        mRvComment?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                val position = layoutManager.findFirstVisibleItemPosition()
                val firstVisiableChildView = layoutManager.findViewByPosition(position)
                val itemHeight = firstVisiableChildView.height
                val scrollHeight = position * itemHeight - firstVisiableChildView.top

                VLogUtil.i("scrollHeight: $scrollHeight")
                VLogUtil.i("llInfoBottom: $llInfoBottom")

                ll_user.visibility = if (scrollHeight > llInfoBottom!!) View.VISIBLE else View.GONE//如果滚动超过用户信息一栏，显示标题栏中的用户头像和昵称
            }
        })
    }

    override fun onGetNewsDetailSuccess(newsDetailResult: ResultResponse<NewsDetail>) {
        val newsDetail = newsDetailResult.data
        mHeaderView?.setDetail(newsDetail) {
            //加载完成后，显示内容布局
            mLayoutStatusView?.showContent()
        }

        ll_user.visibility = View.GONE
        if (newsDetail.media_user != null) {
            iv_avatar.loadImage(newsDetail.media_user.avatar_url,R.color.placeholder_color)
            tv_author.text = newsDetail.media_user.screen_name
        }
    }

    override fun onBackPressed() {
        postVideoEvent(false)
    }

    @OnClick(R.id.iv_back)
    fun onViewClicked() {
        postVideoEvent(false)
    }

}