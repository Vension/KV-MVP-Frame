package com.vension.mvp.ui.adapters.headline.provider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageLoader;
import com.vension.mvp.R;
import com.vension.mvp.beans.headline.NewsBean;
import com.vension.mvp.ui.adapters.headline.NewsListAdapter;
import com.vension.mvp.utils.TimeUtils;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/20 9:54
 * 描  述：右侧小图布局(1.小图新闻；2.视频类型，右下角显示视频时长)
 * ========================================================
 */

public class RightPicNewsItemProvider extends BaseNewsItemProvider {


    public RightPicNewsItemProvider(String channelCode) {
        super(channelCode);
    }

    @Override
    public int viewType() {
        return NewsListAdapter.RIGHT_PIC_VIDEO_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_recy_news_pic_video;
    }


    @Override
    protected void setData(BaseViewHolder helper, NewsBean news) {
        //右侧小图布局，判断是否有视频
        if (news.has_video) {
            helper.setVisible(R.id.ll_duration, true);//显示时长
            helper.setText(R.id.tv_duration, TimeUtils.secToTime(news.video_duration));//设置时长
        } else {
            helper.setVisible(R.id.ll_duration, false);//隐藏时长
        }
        //右侧图片或视频的图片使用middle_image
        GlideImageLoader.create(helper.getView(R.id.iv_img))
                .loadImage(news.middle_image.url,R.color.placeholder_color);
    }

}
