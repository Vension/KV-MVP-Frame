package com.vension.mvp.ui.adapters.eye

import android.graphics.Typeface
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.sunfusheng.glideimageview.GlideImageLoader
import com.vension.frame.VFrame
import com.vension.mvp.R
import com.vension.mvp.beans.eyes.CategoryBean

/**
 * @author ：Created by Administrator on 2018/4/12 17:35.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
class RecyCategoryAdapter : BaseQuickAdapter<CategoryBean,BaseViewHolder>(R.layout.item_recy_eye_category) {

    private var textTypeface:Typeface?=null

    init {
        textTypeface = Typeface.createFromAsset(VFrame.getAssets(), "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

        override fun convert(helper: BaseViewHolder?, item: CategoryBean?) {
            helper?.setText(R.id.tv_category_name, "#${item?.name}")
            //设置方正兰亭细黑简体
            helper?.getView<TextView>(R.id.tv_category_name)?.typeface = textTypeface
            GlideImageLoader.create(helper?.getView(R.id.iv_category)).loadImage(item?.bgPicture, R.color.placeholder_color)
        }
}

