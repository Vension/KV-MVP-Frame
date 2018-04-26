package com.vension.mvp.ui.adapters.headline;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageLoader;
import com.sunfusheng.glideimageview.GlideImageView;
import com.vension.frame.utils.VLogUtil;
import com.vension.mvp.R;
import com.vension.mvp.beans.headline.NewsBean;
import com.vension.mvp.utils.TimeUtils;
import com.vension.mvp.widget.MyJZVideoPlayerStandard;

import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZUserAction;
import cn.jzvd.JZVideoPlayerStandard;

import static android.view.View.GONE;
import static cn.jzvd.JZVideoPlayer.CURRENT_STATE_AUTO_COMPLETE;
import static cn.jzvd.JZVideoPlayer.CURRENT_STATE_NORMAL;
import static cn.jzvd.JZVideoPlayer.CURRENT_STATE_PAUSE;
import static cn.jzvd.JZVideoPlayer.CURRENT_STATE_PLAYING;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 15:32
 * 描  述：视频列表的Adapter
 * ========================================================
 */

public class VideoListAdapter extends BaseQuickAdapter<NewsBean,BaseViewHolder> {


    public VideoListAdapter(@Nullable List<NewsBean> data) {
        super(R.layout.item_recy_news_video_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean news) {
        if (TextUtils.isEmpty(news.title)){
            //如果没有标题，则直接跳过
            return;
        }

        helper.setText(R.id.tv_title, news.title);//设置标题

        String format = mContext.getString(R.string.video_play_count);
        int watchCount = news.video_detail_info.video_watch_count;
        String countUnit = "";
        if (watchCount> 10000){
            watchCount = watchCount / 10000;
            countUnit = "万";
        }

        helper.setText(R.id.tv_watch_count, String.format(format, watchCount + countUnit));//播放次数
        ((GlideImageView)helper.getView(R.id.giv_avatar)).loadImage(news.user_info.avatar_url,R.color.placeholder_color);

        helper .setText(R.id.tv_duration, TimeUtils.secToTime(news.video_duration));//设置时长

        helper.setText(R.id.tv_author, news.user_info.name)//昵称
                .setText(R.id.tv_comment_count, String.valueOf(news.comment_count));//评论数

		MyJZVideoPlayerStandard videoPlayer = helper.getView(R.id.video_player);
		GlideImageLoader.create(videoPlayer.thumbImageView).loadImage(news.video_detail_info.detail_video_large_image.url,R.color.placeholder_color);
		videoPlayer.tinyBackImageView.setVisibility(GONE);
		VLogUtil.e("video_url==> " + news.url);
		videoPlayer.setUp(news.url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
		videoPlayer.positionInList = helper.getAdapterPosition();
		videoPlayer.startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.i(TAG, "onClick start [" + this.hashCode() + "] ");
				if (videoPlayer.currentState == CURRENT_STATE_NORMAL) {
					helper.setVisible(R.id.tv_title,false);
					helper.setVisible(R.id.tv_watch_count,false);
					helper.setVisible(R.id.tv_duration,false);
					videoPlayer.startVideo();
					videoPlayer.onEvent(JZUserAction.ON_CLICK_START_ICON);
				} else if (videoPlayer.currentState == CURRENT_STATE_PLAYING) {
					videoPlayer.onEvent(JZUserAction.ON_CLICK_PAUSE);
					Log.d(TAG, "pauseVideo [" + this.hashCode() + "] ");
					JZMediaManager.pause();
					videoPlayer.onStatePause();
					helper.setVisible(R.id.tv_title,true);
					helper.setVisible(R.id.tv_watch_count,true);
					helper.setVisible(R.id.tv_duration,true);
				} else if (videoPlayer.currentState == CURRENT_STATE_PAUSE) {
					videoPlayer.onEvent(JZUserAction.ON_CLICK_RESUME);
					JZMediaManager.start();
					videoPlayer.onStatePlaying();
					helper.setVisible(R.id.tv_title,false);
					helper.setVisible(R.id.tv_watch_count,false);
					helper.setVisible(R.id.tv_duration,false);
				} else if (videoPlayer.currentState == CURRENT_STATE_AUTO_COMPLETE) {
					videoPlayer.onEvent(JZUserAction.ON_CLICK_START_AUTO_COMPLETE);
					videoPlayer.startVideo();
					helper.setVisible(R.id.tv_title,false);
					helper.setVisible(R.id.tv_watch_count,false);
					helper.setVisible(R.id.tv_duration,false);
				}
			}
		});
    }

}
