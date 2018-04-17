package com.vension.mvp.ui.adapters.eye

import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sunfusheng.glideimageview.progress.GlideApp
import com.vension.mvp.R
import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.durationFormat

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/13 9:45
 * 描  述：分类详情Adapter
 * ========================================================
 */

class RecyCategoryDetailAdapter : BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder>(R.layout.item_recy_eye_category_detail){

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.Item?) {

        val itemData = item?.data
        val cover = itemData?.cover?.feed
        // 加载封页图
        GlideApp.with(mContext)
                .load(cover)
                .apply(RequestOptions().placeholder(R.mipmap.img_load_empty))
                .into(helper?.getView(R.id.iv_image))
        helper?.setText(R.id.tv_title, itemData?.title!!)

        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)

        helper?.setText(R.id.tv_tag, "#${itemData?.category}/$timeFormat")
    }


}