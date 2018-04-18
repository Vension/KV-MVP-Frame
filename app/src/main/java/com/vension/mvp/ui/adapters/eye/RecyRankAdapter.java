package com.vension.mvp.ui.adapters.eye;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageLoader;
import com.vension.mvp.R;
import com.vension.mvp.beans.eyes.HomeBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 16:36
 * 描  述：热门排行榜adapter
 * ========================================================
 */

public class RecyRankAdapter extends BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {

	@BindView(R.id.iv_image)
	ImageView ivImage;
	@BindView(R.id.tv_title)
	TextView tvTitle;
	@BindView(R.id.tv_tag)
	TextView tvTag;

	public RecyRankAdapter() {
		super(R.layout.item_recy_eye_rank);
	}

	@Override
	protected void convert(BaseViewHolder helper, HomeBean.Issue.Item item) {
		ButterKnife.bind(this, helper.itemView);
		if (item != null){
			helper.addOnClickListener(R.id.iv_cover_feed);
			HomeBean.Issue.Item.Data data = item.getData();
			if (data != null){
				tvTitle.setText(data.getTitle());
				// 格式化时间
				String timeFormat = durationFormat(data.getDuration());
				//标签
				if (!data.getTags().isEmpty()) {
					tvTag.setText("#" + data.getTags().get(0).getName() +  "/" +  timeFormat);
				}
				HomeBean.Issue.Item.Data.Cover cover = item.getData().getCover();
				if (cover != null){
					GlideImageLoader.create(ivImage).loadImage(cover.getFeed(), R.color.placeholder_color);
				}
			}
		}
	}


	private String durationFormat(long duration){
		String format = "";
		long minute = duration / 60;
		long second = duration % 60;
		if (minute <= 9){
			if (second <= 9) {
				format = "0" + minute + "'0" + second + "''";
			} else {
				format = "0" + minute + "'" + second + "''";
			}
		}else{
			if (second <= 9) {
				format =  minute + "'0" + second + "''";
			} else {
				format =  minute + "'" + second + "''";
			}
		}
		return format;
	}

}
