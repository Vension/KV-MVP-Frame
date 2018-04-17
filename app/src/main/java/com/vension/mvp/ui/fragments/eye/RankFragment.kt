package com.vension.mvp.ui.fragments.eye

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.mvp.http.exception.ErrorStatus
import com.vension.mvp.R
import com.vension.mvp.base.BaseRefreshFragment
import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.mvp.eye.contract.RankContract
import com.vension.mvp.mvp.eye.presenter.RankPresenter
import com.vension.mvp.showToast
import com.vension.mvp.ui.activitys.eye.VideoDetailActivity
import com.vension.mvp.ui.adapters.eye.RecyRankAdapter
import com.vension.mvp.utils.Constants

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 16:27
 * 描  述：热门排行
 * ========================================================
 */

class RankFragment : BaseRefreshFragment<HomeBean.Issue.Item>(), RankContract.View {

    private val mPresenter by lazy { RankPresenter() }

    private var title: String? = null
    private var apiUrl: String? = null

    companion object {
        fun getInstance(title : String,apiUrl: String): RankFragment {
            val fragment = RankFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.title = title
            fragment.apiUrl = apiUrl
            return fragment
        }
    }

    init {
        mPresenter.attachView(this)
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun hasLoadMore(): Boolean {
        return false
    }

    override fun initRefreshFragment() {
    }

    override fun createCommonRecyAdapter(): BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {
        return RecyRankAdapter()
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>) {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as HomeBean.Issue.Item
            goToVideoPlayer(this!!.activity!!,view,item)
        }
    }

    override fun onTargerRequestApi(isRefresh: Boolean, page: Int, limit: Int) {
        if (!apiUrl.isNullOrEmpty()) {
            mPresenter.requestRankList(apiUrl!!)
        }
    }

    override fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>) {
        addItemData(true,itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.Companion.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair<View, String>(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, pair)
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }

}