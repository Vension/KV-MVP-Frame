package com.vension.mvp.ui.adapters.headline;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageView;
import com.vension.mvp.R;
import com.vension.mvp.beans.headline.CommentData;
import com.vension.mvp.utils.TimeUtils;
import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/23 16:11
 * 描  述：新闻详情页评论的适配器
 * ========================================================
 */

public class CommentAdapter extends BaseQuickAdapter<CommentData, BaseViewHolder> {

    private Context mContext;

    public CommentAdapter(Context context, @LayoutRes int layoutResId, @Nullable List<CommentData> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentData commentData) {

        GlideImageView mGlideImageView = helper.getView(R.id.iv_avatar);
        mGlideImageView.loadImage( commentData.comment.user_profile_image_url,R.color.placeholder_color);
        helper.setText(R.id.tv_name, commentData.comment.user_name)
                .setText(R.id.tv_like_count, String.valueOf(commentData.comment.digg_count))
                .setText(R.id.tv_content, commentData.comment.text)
                .setText(R.id.tv_time, TimeUtils.getShortTime(commentData.comment.create_time * 1000));
    }
}
