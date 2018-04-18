package com.vension.mvp.ui.fragments.agent

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vension.mvp.base.BaseRefreshFragment
import com.vension.mvp.beans.test.MultipleItem
import com.vension.mvp.ui.adapters.MultipleItemQuickAdapter
import java.util.*

/**
 * @author ：Created by Administrator on 2018/4/10 17:57.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/10 17:57
 * 描  述：多item 测试
 * ========================================================
 */

class MultipleItemTestFragment : BaseRefreshFragment<MultipleItem>(){

    override fun hasLoadMore(): Boolean {
        return false
    }

    override fun initRefreshFragment() {
    }

    override fun createCommonRecyAdapter(): BaseQuickAdapter<MultipleItem, BaseViewHolder> {
        return MultipleItemQuickAdapter(null)
    }

    override fun addItemClickListener(mAdapter: BaseQuickAdapter<MultipleItem, BaseViewHolder>) {
    }

    override fun onTargerRequestApi(isRefreshing: Boolean, page: Int, limit: Int) {
        addItemData(true,getMultipleItemData())
    }


    private fun getMultipleItemData(): List<MultipleItem> {
        val list = ArrayList<MultipleItem>()
        for (i in 0..4) {
            list.add(MultipleItem(MultipleItem.IMG, MultipleItem.IMG_SPAN_SIZE))
            list.add(MultipleItem(MultipleItem.TEXT, MultipleItem.TEXT_SPAN_SIZE, "Vension"))
            list.add(MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE))
            list.add(MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN))
            list.add(MultipleItem(MultipleItem.IMG_TEXT, MultipleItem.IMG_TEXT_SPAN_SIZE_MIN))
        }
        return list
    }

}