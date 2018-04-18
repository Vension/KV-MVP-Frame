package com.vension.mvp.ui.fragments.eye

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.mvp.http.exception.ErrorStatus
import com.vension.frame.adapters.recy.decoration.RecGridDividerItemDecoration
import com.vension.mvp.base.BaseRefreshMVPFragment
import com.vension.mvp.beans.eyes.CategoryBean
import com.vension.mvp.mvp.eye.contract.CategoryContract
import com.vension.mvp.mvp.eye.presenter.CategoryPresenter
import com.vension.mvp.showToast
import com.vension.mvp.ui.activitys.eye.CategoryDetailActivity
import com.vension.mvp.ui.adapters.eye.RecyCategoryAdapter
import com.vension.mvp.utils.Constants
import kotlinx.android.synthetic.main.fragment_base_refresh.*

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/12 17:20
 * 描  述：开眼·发现·分类
 * ========================================================
 */

class CategoryFragment : BaseRefreshMVPFragment<CategoryBean, CategoryPresenter>(), CategoryContract.View{

    override fun showToolBar(): Boolean {
        return false
    }

    override fun hasLoadMore(): Boolean {
        return false
    }

    override fun initRefreshFragment() {
        val mItemDecoration = RecGridDividerItemDecoration(createLayoutManager() as GridLayoutManager, 20, true)
        refreshRecyclerView.addItemDecoration(mItemDecoration)
    }

    override fun createPresenter(): CategoryPresenter {
        return CategoryPresenter()
    }

    override fun createCommonRecyAdapter(): BaseQuickAdapter<CategoryBean, BaseViewHolder> {
        return RecyCategoryAdapter()
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<CategoryBean, BaseViewHolder>?) {
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            val item : CategoryBean = mAdapter?.getItem(position) as CategoryBean
            val intent = Intent(context as Activity, CategoryDetailActivity::class.java)
            intent.putExtra(Constants.BUNDLE_CATEGORY_DATA,item)
            startActivity(intent)
        }
    }

    override fun onTargerRequestApi(isRefreshing: Boolean, page: Int, limit: Int) {
        mPresenter.getCategoryData()
    }

    override fun showCategory(categoryList: ArrayList<CategoryBean>) {
        addItemData(true,categoryList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
        if (mSwipeRefreshLayout.isRefreshing) {
            mSwipeRefreshLayout.isRefreshing = false
        }
    }


    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context,2)
    }

}