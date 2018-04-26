package com.vension.mvp.ui.adapters.headline;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.vension.frame.utils.VEmptyUtil;
import com.vension.mvp.beans.headline.NewsBean;
import com.vension.mvp.ui.adapters.headline.provider.CenterPicNewsItemProvider;
import com.vension.mvp.ui.adapters.headline.provider.RightPicNewsItemProvider;
import com.vension.mvp.ui.adapters.headline.provider.TextNewsItemProvider;
import com.vension.mvp.ui.adapters.headline.provider.ThreePicNewsItemProvider;

import java.util.List;


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 17:44
 * 描  述：新闻列表的适配器
 * ========================================================
 */

public class NewsListAdapter extends MultipleItemRvAdapter<NewsBean,BaseViewHolder> {

    /**
     * 纯文字布局(文章、广告)
     */
    public static final int TEXT_NEWS = 100;
    /**
     * 居中大图布局(1.单图文章；2.单图广告；3.视频，中间显示播放图标，右侧显示时长)
     */
    public static final int CENTER_SINGLE_PIC_NEWS = 200;
    /**
     * 右侧小图布局(1.小图新闻；2.视频类型，右下角显示视频时长)
     */
    public static final int RIGHT_PIC_VIDEO_NEWS = 300;
    /**
     * 三张图片布局(文章、广告)
     */
    public static final int THREE_PICS_NEWS = 400;


    private String mChannelCode;


    public NewsListAdapter(String channelCode, @Nullable List<NewsBean> data) {
        super(data);
        mChannelCode = channelCode;
        finishInitialize();
    }

    @Override
    protected int getViewType(NewsBean news) {
        if (news.has_video) {
            //如果有视频
            if (news.video_style == 0) {
                //右侧视频
                if (news.middle_image == null || TextUtils.isEmpty(news.middle_image.url)) {
                    return TEXT_NEWS;
                }
                return RIGHT_PIC_VIDEO_NEWS;
            } else if (news.video_style == 2) {
                //居中视频
                return CENTER_SINGLE_PIC_NEWS;
            }
        } else {
            //非视频新闻
            if (!news.has_image) {
                //纯文字新闻
                return TEXT_NEWS;
            } else {
                if (VEmptyUtil.isEmpty(news.image_list)) {
                    //图片列表为空，则是右侧图片
                    return RIGHT_PIC_VIDEO_NEWS;
                }

                if (news.gallary_image_count == 3) {
                    //图片数为3，则为三图
                    return THREE_PICS_NEWS;
                }

                //中间大图，右下角显示图数
                return CENTER_SINGLE_PIC_NEWS;
            }
        }

        return TEXT_NEWS;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        mProviderDelegate.registerProvider(new TextNewsItemProvider(mChannelCode));
        mProviderDelegate.registerProvider(new CenterPicNewsItemProvider(mChannelCode));
        mProviderDelegate.registerProvider(new RightPicNewsItemProvider(mChannelCode));
        mProviderDelegate.registerProvider(new ThreePicNewsItemProvider(mChannelCode));
    }
}