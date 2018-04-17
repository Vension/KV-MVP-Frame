package com.vension.mvp.ui.adapters.eye;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageLoader;
import com.vension.mvp.R;
import com.vension.mvp.beans.eyes.HomeBean;
import com.vension.mvp.ui.activitys.eye.VideoDetailActivity;
import com.vension.mvp.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/16 14:34
 * 描  述：关注adapter
 * ========================================================
 */

public class RecyFollowAdapter extends BaseQuickAdapter<HomeBean.Issue.Item, BaseViewHolder> {

	@BindView(R.id.iv_avatar)
	ImageView ivAvatar;
	@BindView(R.id.tv_title)
	TextView tvTitle;
	@BindView(R.id.tv_desc)
	TextView tvDesc;
	@BindView(R.id.tv_follow)
	TextView tvFollow;
	@BindView(R.id.fl_recyclerView)
	RecyclerView flRecyclerView;

	private RecyFollowHorizontalAdapter mHorizontalAdapter;

	public RecyFollowAdapter() {
		super(R.layout.item_recy_eye_follow);
	}

	@Override
	protected void convert(BaseViewHolder helper, HomeBean.Issue.Item item) {
		ButterKnife.bind(this, helper.itemView);
		if (item.getType().equals("videoCollectionWithBrief")) {
			helper.addOnClickListener(R.id.tv_follow);
			HomeBean.Issue.Item.Data.Header header = item.getData().getHeader();
			if (header != null) {
				//加载作者头像
				GlideImageLoader.create(ivAvatar).loadImage(header.getIcon(), R.color.placeholder_color);
				tvTitle.setText(header.getTitle());
				tvDesc.setText(header.getDescription());
				//设置嵌套水平的 RecyclerView
				LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
				flRecyclerView.setLayoutManager(manager);

				flRecyclerView.setAdapter(mHorizontalAdapter = new RecyFollowHorizontalAdapter(item.getData().getItemList()));
				mHorizontalAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
					@Override
					public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
						if (view.getId() == R.id.iv_cover_feed){
							HomeBean.Issue.Item item1 = (HomeBean.Issue.Item) adapter.getItem(position);
							if (item1 != null){
								goToVideoPlayer(mContext,view,item1);
							}
						}
					}
				});
			}
		}
	}


	/**
	 * 跳转到视频详情页面播放
	 *
	 * @param mContext
	 * @param view
	 */
	private void goToVideoPlayer(Context mContext, View view, HomeBean.Issue.Item item) {
		Intent mIntent = new Intent(mContext,VideoDetailActivity.class);
		mIntent.putExtra(Constants.BUNDLE_VIDEO_DATA, item);
		mIntent.putExtra(VideoDetailActivity.Companion.getTRANSITION(), true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
			Pair<View, String> pair = new Pair<>(view, VideoDetailActivity.Companion.getIMG_TRANSITION());
			ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, pair);
			ActivityCompat.startActivity(mContext, mIntent, optionsCompat.toBundle());
		}else{
			mContext.startActivity(mIntent);
			((Activity) mContext).overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
		}
	}

}
