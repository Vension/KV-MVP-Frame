package com.vension.mvp.ui.fragments.gank

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.vension.frame.utils.VObsoleteUtil
import com.vension.mvp.R
import com.vension.mvp.base.BaseFragment
import com.vension.mvp.db.realm.DataManager
import com.vension.mvp.db.realm.RealmHelper
import com.vension.mvp.db.realm.bean.RealmLikeBean
import com.vension.mvp.ui.adapters.gank.LikeAdapter
import com.vension.mvp.widget.DefaultItemTouchHelpCallback
import com.wuhenzhizao.titlebar.widget.CommonTitleBar
import kotlinx.android.synthetic.main.fragment_like.*
import java.util.*

/**
 * @author ：Created by Administrator on 2018/4/10 15:22.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class LikeFragment2 : BaseFragment(){

    var mDataManager : DataManager? = null
    var mAdapter: LikeAdapter? = null
    var mList: MutableList<RealmLikeBean>? = null
    lateinit var mCallback: DefaultItemTouchHelpCallback

    override fun initToolBar(mCommonTitleBar: CommonTitleBar) {
        mCommonTitleBar.setBackgroundColor(VObsoleteUtil.getColor(R.color.app_main_thme_color))
        mCommonTitleBar.leftImageButton.visibility = View.GONE
        mCommonTitleBar.centerTextView.text = "收藏"
        mCommonTitleBar.centerTextView.setTextColor(VObsoleteUtil.getColor(R.color.white))
    }

    override fun attachLayoutRes(): Int {
        return R.layout.fragment_like
    }


    override fun initViewAndData(savedInstanceState: Bundle?) {
        super.initViewAndData(savedInstanceState)
        mDataManager = DataManager(RealmHelper())
        mList = ArrayList<RealmLikeBean>()
        mAdapter = LikeAdapter(context, mList)
        rv_like_list.layoutManager = LinearLayoutManager(context)
        rv_like_list.adapter = mAdapter
        mCallback = DefaultItemTouchHelpCallback(object : DefaultItemTouchHelpCallback.OnItemTouchCallbackListener {
            override fun onSwiped(adapterPosition: Int) {
                // 滑动删除的时候，从数据库、数据源移除，并刷新UI
                this@LikeFragment2.doDelete(adapterPosition)
            }

            override fun onMove(srcPosition: Int, targetPosition: Int): Boolean {
                this@LikeFragment2.doMove(srcPosition,targetPosition)
                return false
            }
        })
        mCallback.setDragEnable(true)
        mCallback.setSwipeEnable(true)
        val itemTouchHelper = ItemTouchHelper(mCallback)
        itemTouchHelper.attachToRecyclerView(rv_like_list)
    }


    override fun lazyLoadData() {
        getData()
    }

    private fun getData() {
        mList?.clear()
        mList?.addAll(mDataManager?.likeList!!)
        mAdapter?.notifyDataSetChanged()
    }

    private fun doDelete(adapterPosition: Int) {
        // 滑动删除的时候，从数据库、数据源移除，并刷新UI
        if (mList != null) {
            mDataManager?.deleteLikeBean(mList!![adapterPosition].id)
            mList!!.removeAt(adapterPosition)
            mAdapter?.notifyItemRemoved(adapterPosition)
        }
    }

    private fun doMove(srcPosition: Int, targetPosition: Int) : Boolean {
        if (mList != null) {
            // 更换数据库中的数据Item的位置
            val isPlus = srcPosition < targetPosition
            mDataManager?.changeLikeTime(mList!![srcPosition].id,mList!![targetPosition].time,isPlus)
            // 更换数据源中的数据Item的位置
            Collections.swap(mList, srcPosition, targetPosition)
            // 更新UI中的Item的位置，主要是给用户看到交互效果
            mAdapter?.notifyItemMoved(srcPosition, targetPosition)
            return true
        }
        return false
    }


}