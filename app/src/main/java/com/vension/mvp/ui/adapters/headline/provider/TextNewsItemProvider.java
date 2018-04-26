package com.vension.mvp.ui.adapters.headline.provider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.vension.mvp.R;
import com.vension.mvp.beans.headline.NewsBean;
import com.vension.mvp.ui.adapters.headline.NewsListAdapter;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 17:58
 * 描  述：纯文本新闻
 * ========================================================
 */

public class TextNewsItemProvider extends BaseNewsItemProvider {

    public TextNewsItemProvider(String channelCode) {
        super(channelCode);
    }

    @Override
    public int viewType() {
        return NewsListAdapter.TEXT_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_recy_new_home_text;
    }

    @Override
    protected void setData(BaseViewHolder helper, NewsBean news) {
         //由于文本消息的逻辑目前已经在基类中封装，所以此处无须写
        //定义此类是提供文本消息的ItemProvider
    }
}
