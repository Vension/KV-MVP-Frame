package com.vension.mvp.ui.adapters.eye

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sunfusheng.glideimageview.GlideImageLoader
import com.vension.frame.utils.VObsoleteUtil
import com.vension.mvp.R
import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.durationFormat

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 12:11
 * 描  述：
 * ========================================================
 */

class RecyWatchHistoryAdapter : BaseQuickAdapter<HomeBean.Issue.Item,BaseViewHolder>(R.layout.item_recy_eye_video_small_card) {

    override fun convert(helper: BaseViewHolder?, item: HomeBean.Issue.Item) {
        helper?.setText(R.id.tv_smallcard_title, item.data?.title!!)
        helper?.setTextColor(R.id.tv_smallcard_title, VObsoleteUtil.getColor(R.color.black))
        helper?.setText(R.id.tv_smallcard_tag, "#${item.data?.category} / ${durationFormat(item.data?.duration)}")
        GlideImageLoader.create(helper?.getView(R.id.iv_video_small_card)).loadImage(item.data?.cover?.detail, R.color.placeholder_color)
    }

}

