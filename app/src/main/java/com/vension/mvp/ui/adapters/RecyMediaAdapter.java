package com.vension.mvp.ui.adapters;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageView;
import com.vension.mvp.R;
import com.vension.mvp.beans.headline.MediaChannelBean;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/3 15:03
 * 描  述：头条号adapter
 * ========================================================
 */

public class RecyMediaAdapter extends BaseQuickAdapter<MediaChannelBean, BaseViewHolder> {

	@BindView(R.id.giv_media) GlideImageView givMedia;
	@BindView(R.id.tv_mediaName) TextView tvMediaName;
	@BindView(R.id.tv_followCount) TextView tvFollowCount;
	@BindView(R.id.tv_descText) TextView tvDescText;

	public RecyMediaAdapter() {
		super(R.layout.item_recy_media_channel);
	}

	@Override
	protected void convert(BaseViewHolder helper, MediaChannelBean item) {
		ButterKnife.bind(this, helper.itemView);
		givMedia.loadImage(item.getAvatar(),R.color.placeholder_color);
		tvMediaName.setText(item.getName());
		tvFollowCount.setText(item.getFollowCount() + "人关注");
		tvDescText.setText(item.getDescText());
	}

}
