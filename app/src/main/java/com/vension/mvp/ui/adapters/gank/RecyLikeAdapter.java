package com.vension.mvp.ui.adapters.gank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageLoader;
import com.vension.frame.utils.TimeUtil;
import com.vension.mvp.R;
import com.vension.mvp.db.realm.bean.RealmLikeBean;
import com.vension.mvp.ui.activitys.ProxyAvtivity;
import com.vension.mvp.ui.fragments.gank.ImagePreviewFragment;
import com.vension.mvp.utils.Constants;

import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/10 12:03
 * 描  述：我的收藏 adapter
 * ========================================================
 */

public class RecyLikeAdapter extends BaseMultiItemQuickAdapter<RealmLikeBean, BaseViewHolder> {

	private static final int TYPE_ARTICLE = 0;
	private static final int TYPE_GIRL = 1;

	/**
	 * Same as QuickAdapter#QuickAdapter(Context,int) but with
	 * some initialization data.
	 *
	 * @param data A new list is created out of this one to avoid mutable list
	 */
	public RecyLikeAdapter(List<RealmLikeBean> data) {
		super(data);
		addItemType(TYPE_GIRL, R.layout.item_recy_gank_like_girl);
//		addItemType(TYPE_ARTICLE , R.layout.item_text_view);
	}


	@Override
	protected void convert(BaseViewHolder helper, RealmLikeBean item) {
		switch (helper.getItemViewType()) {
			case TYPE_GIRL://
				helper.setText(R.id.tv_like_time, TimeUtil.millis2String(item.getTime()));
				GlideImageLoader.create(helper.getView(R.id.iv_girl_image)).loadImage(item.getUrl(),R.color.placeholder_color);
				helper.itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						gotoGirlDetail(item.getImage(),item.getId());
					}
				});
				break;
			case TYPE_ARTICLE:
//				switch (helper.getLayoutPosition() % 2) {
//					case 0:
//						helper.setImageResource(R.id.iv, R.mipmap.animation_img1);
//						break;
//					case 1:
//						helper.setImageResource(R.id.iv, R.mipmap.animation_img2);
//						break;
//
//				}
				break;
		}
	}



	private void gotoGirlDetail(String url,String id) {
		Bundle mBundle = new Bundle();
		mBundle.putString("image_url",url);
		mBundle.putString("image_id",id);
		mBundle.putString(Constants.AGENT_FRAGMENT_CLASS_KEY, ImagePreviewFragment.class.getName()); // com.project.app.activity.*
		Intent intent = new Intent(mContext, ProxyAvtivity.class);
		intent.putExtras(mBundle);
		mContext.startActivity(intent);
	}

}
