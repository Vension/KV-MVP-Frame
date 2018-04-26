package com.vension.mvp.ui.fragments.gank

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.mvp.base.BaseRefreshMVPFragment
import com.vension.mvp.beans.gank.GankItemBean
import com.vension.mvp.beans.gank.GankResult
import com.vension.mvp.mvp.gank.GankContract
import com.vension.mvp.mvp.gank.GankPresenter
import com.vension.mvp.ui.adapters.gank.RecyGankTechAdapter
import com.vension.mvp.utils.Constants

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/8 15:56
 * 描  述：Gank 技术类文章
 * ========================================================
 */

class TechFragment : BaseRefreshMVPFragment<GankItemBean, GankPresenter>(), GankContract.View{

    private var type : String = "Android"

    companion object {
        fun newInstance(title: String): TechFragment {
            val fragment = TechFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.type = title
            return fragment
        }
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun initRefreshFragment() {
    }

    override fun createPresenter(): GankPresenter {
        return GankPresenter()
    }

    override fun createCommonRecyAdapter(): BaseQuickAdapter<GankItemBean, BaseViewHolder> {
        return RecyGankTechAdapter()
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<GankItemBean, BaseViewHolder>?) {
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            val item : GankItemBean = mAdapter?.getItem(position) as GankItemBean
            mBundle?.putString("id",item._id)
            mBundle?.putString("title",item.desc)
            mBundle?.putString("url",item.url)
            mBundle?.putString("image_url", item.images[0])
            if (item.type == "Android"){
                mBundle?.putInt("type",Constants.TYPE_ANDROID)
            }else{
                mBundle?.putInt("type",Constants.TYPE_IOS)
            }
            val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, view, Constants.TRANSITION_NAME)
            startAgentActivity(TechDetailFragment::class.java, mBundle!!, activityOptionsCompat)
        }
    }

    override fun onTargerRequestApi(isRefreshing: Boolean, page: Int, limit: Int) {
        mPresenter?.loadTechList(isRefreshing,type,limit,page)
    }

    override fun setTechData(isRefresh: Boolean, data: GankResult) {
        addItemData(isRefresh,data.results)
    }

    override fun setGrilData(isRefresh: Boolean, data: GankResult) {
    }
    override fun showError(msg: String, errorCode: Int) {
    }
}