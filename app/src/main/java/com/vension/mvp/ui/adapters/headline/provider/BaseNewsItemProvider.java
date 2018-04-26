package com.vension.mvp.ui.adapters.headline.provider;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.BaseItemProvider;
import com.vension.frame.utils.VObsoleteUtil;
import com.vension.mvp.R;
import com.vension.mvp.beans.headline.NewsBean;
import com.vension.mvp.utils.Constants;
import com.vension.mvp.utils.TimeUtils;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 17:46
 * 描  述：将新闻中设置数据公共部分抽取
 * ========================================================
 */

public abstract class BaseNewsItemProvider extends BaseItemProvider<NewsBean,BaseViewHolder> {

    private String mChannelCode;

    public BaseNewsItemProvider(String channelCode) {
        mChannelCode = channelCode;
    }

    @Override
    public void convert(BaseViewHolder helper, NewsBean news, int i) {
        if (TextUtils.isEmpty(news.title)) {
            //如果没有标题，则直接跳过
            return;
        }

        //设置标题、底部作者、评论数、发表时间
        helper.setText(R.id.tv_title, news.title)
                .setText(R.id.tv_author, news.source)
                .setText(R.id.tv_comment_num, news.comment_count + VObsoleteUtil.getString(R.string.comment))
                .setText(R.id.tv_time, TimeUtils.getShortTime(news.behot_time * 1000));

        //根据情况显示置顶、广告和热点的标签
        int position = helper.getAdapterPosition();
        String[] channelCodes = VObsoleteUtil.getStringArr(R.array.channel_code);
        boolean isTop = position == 0 && mChannelCode.equals(channelCodes[0]); //属于置顶
        boolean isHot = news.hot == 1;//属于热点新闻
        boolean isAD = !TextUtils.isEmpty(news.tag) ? news.tag.equals(Constants.ARTICLE_GENRE_AD) : false;//属于广告新闻
        boolean isMovie = !TextUtils.isEmpty(news.tag) ? news.tag.equals(Constants.TAG_MOVIE) : false;//如果是影视
//        helper.setVisible(R.id.tv_tag, isTop || isHot || isAD);//如果是上面任意一个，显示标签
        helper.getView(R.id.tv_tag).setVisibility(isTop || isHot || isAD ? View.VISIBLE : View.GONE);
        helper.setVisible(R.id.tv_comment_num, !isAD);//如果是广告，则隐藏评论数

        String tag = "";
        if (isTop) {
            tag = VObsoleteUtil.getString(R.string.to_top);
            helper.setTextColor(R.id.tv_tag, VObsoleteUtil.getColor(R.color.color_F96B6B));
        } else if (isHot) {
            tag = VObsoleteUtil.getString(R.string.hot);
            helper.setTextColor(R.id.tv_tag, VObsoleteUtil.getColor(R.color.color_F96B6B));
        } else if (isAD) {
            tag = VObsoleteUtil.getString(R.string.ad);
            helper.setTextColor(R.id.tv_tag, VObsoleteUtil.getColor(R.color.color_3091D8));
        } else if (isMovie) {
            //如果是影视
            tag = VObsoleteUtil.getString(R.string.tag_movie);
            helper.setTextColor(R.id.tv_tag, VObsoleteUtil.getColor(R.color.color_F96B6B));
        }
        helper.setText(R.id.tv_tag, tag);


        setData(helper, news);
    }

    protected abstract void setData(BaseViewHolder helper, NewsBean news);
}
