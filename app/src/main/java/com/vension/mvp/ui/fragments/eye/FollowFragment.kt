package com.vension.mvp.ui.fragments.eye

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.mvp.http.exception.ErrorStatus
import com.vension.frame.utils.VToastUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseRefreshFragment
import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.mvp.eye.contract.FollowContract
import com.vension.mvp.mvp.eye.presenter.FollowPresenter
import com.vension.mvp.showToast
import com.vension.mvp.ui.adapters.eye.RecyFollowAdapter

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 14:12
 * 描  述：关注
 * ========================================================
 */

class FollowFragment : BaseRefreshFragment<HomeBean.Issue.Item>(), FollowContract.View {


    private val mPresenter by lazy { FollowPresenter() }


    init {
        mPresenter.attachView(this)
    }

    override fun showToolBar(): Boolean {
        return false
    }

    override fun initRefreshFragment() {
    }

    override fun createCommonRecyAdapter(): BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {
        return RecyFollowAdapter()
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>) {
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.tv_follow ->{
                    VToastUtil.showConfusing("谢谢关注")
                }
            }
        }
    }

    override fun onTargerRequestApi(isRefreshing: Boolean, page: Int, limit: Int) {
        if (isRefreshing){
            mPresenter.requestFollowList()
        }else{
            mPresenter.loadMoreData()
        }
    }


    override fun setFollowInfo(isRefresh: Boolean, issue: HomeBean.Issue) {
        addItemData(isRefresh,issue.itemList as List<HomeBean.Issue.Item>)
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

}