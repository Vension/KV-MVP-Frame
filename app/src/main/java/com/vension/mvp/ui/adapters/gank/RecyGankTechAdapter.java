package com.vension.mvp.ui.adapters.gank;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageLoader;
import com.vension.frame.VFrame;
import com.vension.mvp.R;
import com.vension.mvp.beans.gank.GankItemBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/8 16:01
 * 描  述：Gank 技术类文章 adapter
 * ========================================================
 */

public class RecyGankTechAdapter extends BaseQuickAdapter<GankItemBean, BaseViewHolder> {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	@BindView(R.id.tv_author)
	TextView tvAuthor;
	@BindView(R.id.iv_show)
	ImageView ivShow;
	@BindView(R.id.tv_desc)
	TextView tvDesc;
	@BindView(R.id.tv_source)
	TextView tvSource;
	@BindView(R.id.tv_time)
	TextView tvTime;

	public RecyGankTechAdapter() {
		super(R.layout.item_recy_gank_tech);
	}


	@Override
	protected void convert(BaseViewHolder helper, GankItemBean item) {
		ButterKnife.bind(this, helper.itemView);
		tvAuthor.setText(item.getWho());
		tvDesc.setText(item.getDesc());
		tvSource.setText("来源：" + item.getType());
		try {
			tvTime.setText(DateUtils.getRelativeTimeSpanString(sdf.parse(item.getPublishedAt()).getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (item.getImages() == null || item.getImages().size() == 0) {
			ivShow.setVisibility(View.GONE);
		}else{
			ivShow.setVisibility(View.VISIBLE);
			int width = (int) VFrame.getResources().getDimension(R.dimen.article_image_width);
			GlideImageLoader.create(ivShow).loadImage(item.getImages().get(0) +"?imageView2/0/w/" + width,R.color.placeholder_color);
		}
	}

}
