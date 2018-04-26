package com.vension.mvp.ui.fragments.gank

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.mvp.base.BaseRefreshMVPFragment
import com.vension.mvp.beans.gank.GankItemBean
import com.vension.mvp.beans.gank.GankResult
import com.vension.mvp.mvp.gank.GankContract
import com.vension.mvp.mvp.gank.GankPresenter
import com.vension.mvp.ui.adapters.gank.RecyGankGrilAdapter

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/4 15:50
 * 描  述：Gank-福利
 * ========================================================
 */

class WelfareFragment : BaseRefreshMVPFragment<GankItemBean, GankPresenter>(),GankContract.View{

    private var type : String = "福利"

    companion object {
        fun newInstance(title: String): WelfareFragment {
            val fragment = WelfareFragment()
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

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    override fun createCommonRecyAdapter(): BaseQuickAdapter<GankItemBean, BaseViewHolder> {
        return RecyGankGrilAdapter()
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<GankItemBean, BaseViewHolder>?) {
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            val item : GankItemBean = mAdapter?.getItem(position) as GankItemBean
            mBundle?.putString("image_url",item.url)
            mBundle?.putString("image_id",item._id)
            startAgentActivity(ImagePreviewFragment::class.java, mBundle!!)
        }
    }


    override fun onTargerRequestApi(isRefreshing: Boolean, page: Int, limit: Int) {
            mPresenter?.loadGrilList(isRefreshing,limit,page)
    }

    override fun setGrilData(isRefresh: Boolean, data: GankResult) {
        addItemData(isRefresh,data.results)
    }

    override fun setTechData(isRefresh: Boolean, data: GankResult) {
    }


    override fun showError(msg: String, errorCode: Int) {
    }

}