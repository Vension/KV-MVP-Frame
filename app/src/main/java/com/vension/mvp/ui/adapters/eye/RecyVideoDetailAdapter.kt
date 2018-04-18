package com.vension.mvp.ui.adapters.eye

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.vension.frame.adapters.recy.CommonRecyAdapter
import com.kevin.vension.demo.adapters.recy.MultipleType
import com.kevin.vension.demo.adapters.recy.ViewHolder
import com.sunfusheng.glideimageview.GlideImageLoader
import com.sunfusheng.glideimageview.GlideImageView
import com.vension.frame.VFrame
import com.vension.mvp.R
import com.vension.mvp.beans.eyes.HomeBean
import com.vension.mvp.durationFormat

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/13 16:53
 * 描  述：视频详情Adapter
 * ========================================================
 */

class RecyVideoDetailAdapter(mContext: Context, data: ArrayList<HomeBean.Issue.Item>) :
        CommonRecyAdapter<HomeBean.Issue.Item>(mContext, data, object : MultipleType<HomeBean.Issue.Item> {
            override fun getLayoutId(item: HomeBean.Issue.Item, position: Int): Int {
                return when {
                    position == 0 ->
                        R.layout.item_recy_eye_video_detail_info

                    data[position].type == "textCard" ->
                        R.layout.item_recy_eye_video_text_card

                    data[position].type == "videoSmallCard" ->
                        R.layout.item_recy_eye_video_small_card
                    else ->
                        throw IllegalAccessException("Api 解析出错了，出现其他类型") as Throwable
                }
            }
        }) {

    private var textTypeface:Typeface?=null

    init {
        textTypeface = Typeface.createFromAsset(VFrame.getAssets(), "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    /**
     * 添加视频的详细信息
     */
    fun addData(item: HomeBean.Issue.Item) {
        mData.clear()
        notifyDataSetChanged()
        mData.add(item)
        notifyItemInserted(0)
    }

    /**
     * 添加相关推荐等数据 Item
     */
    fun addData(item: ArrayList<HomeBean.Issue.Item>) {
        mData.addAll(item)
        notifyItemRangeInserted(1, item.size)
    }

    /**
     * Kotlin的函数可以作为参数，写callback的时候，可以不用interface了
     */

    private var mOnItemClickRelatedVideo: ((item: HomeBean.Issue.Item) -> Unit)? = null


    fun setOnItemDetailClick(mItemRelatedVideo: (item: HomeBean.Issue.Item) -> Unit) {
        this.mOnItemClickRelatedVideo = mItemRelatedVideo
    }


    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        when {
            position == 0 -> setVideoDetailInfo(data, holder)

            data.type == "textCard" -> {
                holder.setText(R.id.tv_text_card, data.data?.text!!)
                //设置方正兰亭细黑简体
                holder.getView<TextView>(R.id.tv_text_card).typeface =textTypeface
            }
            data.type == "videoSmallCard" -> {
                with(holder) {
                    setText(R.id.tv_smallcard_title, data.data?.title!!)
                    setText(R.id.tv_smallcard_tag, "#${data.data.category} / ${durationFormat(data.data.duration)}")
                    GlideImageLoader.create(holder.getView(R.id.iv_video_small_card)).loadImage(data.data.cover.detail,R.color.placeholder_color)
                }
                // 判断onItemClickRelatedVideo 并使用
                holder.itemView.setOnClickListener { mOnItemClickRelatedVideo?.invoke(data) }

            }
            else -> throw IllegalAccessException("Api 解析出错了，出现其他类型")
        }
    }

    /**
     * 设置视频详情数据
     */
    private fun setVideoDetailInfo(data: HomeBean.Issue.Item, holder: ViewHolder) {
        data.data?.title?.let { holder.setText(R.id.tv_videoinfo_title, it) }
        //视频简介
        data.data?.description?.let { holder.setText(R.id.expandable_text, it) }
        //标签
        holder.setText(R.id.tv_videoinfo_tag, "#${data.data?.category} / ${durationFormat(data.data?.duration)}")
        //喜欢
        holder.setText(R.id.tv_action_favorites, data.data?.consumption?.collectionCount.toString())
        //分享
        holder.setText(R.id.tv_action_share, data.data?.consumption?.shareCount.toString())
        //评论
        holder.setText(R.id.tv_action_reply, data.data?.consumption?.replyCount.toString())

        if (data.data?.author != null) {
            with(holder) {
                setText(R.id.tv_author_name, data.data.author.name)
                setText(R.id.tv_author_desc, data.data.author.description)
                holder.getView<GlideImageView>(R.id.iv_avatar).loadImage(data.data.author.icon,R.color.placeholder_color)
            }
        } else {
            holder.setViewVisibility(R.id.layout_author_view, View.GONE)
        }

        with(holder) {
            getView<TextView>(R.id.tv_action_favorites).setOnClickListener {
                Toast.makeText(mContext, "喜欢", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_share).setOnClickListener {
                Toast.makeText(mContext, "分享", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_reply).setOnClickListener {
                Toast.makeText(mContext, "评论", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
