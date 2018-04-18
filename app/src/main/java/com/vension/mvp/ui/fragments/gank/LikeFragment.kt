package com.vension.mvp.ui.fragments.gank

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.frame.utils.VObsoleteUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseRefreshFragment
import com.vension.mvp.db.realm.DataManager
import com.vension.mvp.db.realm.RealmHelper
import com.vension.mvp.db.realm.bean.RealmLikeBean
import com.vension.mvp.ui.adapters.gank.RecyLikeAdapter
import com.vension.mvp.widget.DefaultItemTouchHelpCallback
import com.wuhenzhizao.titlebar.widget.CommonTitleBar

/**
 * @author ：Created by Administrator on 2018/4/10 11:16.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */

class LikeFragment : BaseRefreshFragment<RealmLikeBean>() {

    var mDataManager : DataManager? = null

    lateinit var mCallback: DefaultItemTouchHelpCallback

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.showStatusBar(false)
        mCommonTitleBar.setBackgroundColor(VObsoleteUtil.getColor(R.color.app_main_thme_color))
        mCommonTitleBar.leftImageButton.visibility = View.GONE
        mCommonTitleBar.centerTextView.text = "收藏"
        mCommonTitleBar.centerTextView.setTextColor(VObsoleteUtil.getColor(R.color.white))
    }


    override fun initRefreshFragment() {
        mDataManager = DataManager(RealmHelper())
    }

    override fun createCommonRecyAdapter(): BaseQuickAdapter<RealmLikeBean, BaseViewHolder> {
        return RecyLikeAdapter(null)
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<RealmLikeBean, BaseViewHolder>) {
    }

    override fun onTargerRequestApi(isRefreshing: Boolean, page: Int, limit: Int) {
        addItemData(true, mDataManager?.likeList!!)
    }



}