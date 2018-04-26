package com.vension.mvp.ui.adapters.headline.provider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageLoader;
import com.vension.mvp.R;
import com.vension.mvp.beans.headline.NewsBean;
import com.vension.mvp.ui.adapters.headline.NewsListAdapter;

/**
 * @author ChayChan
 * @description: 三张图片布局(文章、广告)
 * @date 2018/3/22  14:36
 */
public class ThreePicNewsItemProvider extends BaseNewsItemProvider {

    public ThreePicNewsItemProvider(String channelCode) {
        super(channelCode);
    }

    @Override
    public int viewType() {
        return NewsListAdapter.THREE_PICS_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_recy_news_three_pics;
    }

    @Override
    protected void setData(BaseViewHolder helper, NewsBean news) {
        //三张图片的新闻
        GlideImageLoader.create(helper.getView(R.id.iv_img1))
                .loadImage(news.image_list.get(0).url,R.color.placeholder_color);
        GlideImageLoader.create(helper.getView(R.id.iv_img2))
                .loadImage(news.image_list.get(1).url,R.color.placeholder_color);
        GlideImageLoader.create(helper.getView(R.id.iv_img3))
                .loadImage(news.image_list.get(2).url,R.color.placeholder_color);
    }

}
