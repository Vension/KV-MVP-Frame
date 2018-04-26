package com.vension.mvp.ui.adapters.gank;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageLoader;
import com.vension.mvp.R;
import com.vension.mvp.beans.gank.GankItemBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/4 16:17
 * 描  述：Gank 妹子图 adapter
 * ========================================================
 */

public class RecyGankGrilAdapter extends BaseQuickAdapter<GankItemBean, BaseViewHolder> {

	@BindView(R.id.iv_gank_gril) ImageView ivGankGril;

	public RecyGankGrilAdapter() {
		super(R.layout.item_recy_gank_gril);
	}


	@Override
	protected void convert(BaseViewHolder helper, GankItemBean item) {
		ButterKnife.bind(this, helper.itemView);
		GlideImageLoader.create(ivGankGril).loadImage(item.getUrl(),R.color.placeholder_color);
	}

}
