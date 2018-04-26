package com.vension.mvp.ui.adapters.headline.provider;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.sunfusheng.glideimageview.GlideImageLoader;
import com.vension.frame.utils.VObsoleteUtil;
import com.vension.mvp.R;
import com.vension.mvp.beans.headline.NewsBean;
import com.vension.mvp.ui.adapters.headline.NewsListAdapter;
import com.vension.mvp.utils.TimeUtils;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 18:07
 * 描  述：居中大图布局(1.单图文章；2.单图广告；3.视频，中间显示播放图标，右侧显示时长)
 * ========================================================
 */

public class CenterPicNewsItemProvider extends BaseNewsItemProvider {

    public CenterPicNewsItemProvider(String channelCode) {
        super(channelCode);
    }

    @Override
    public int viewType() {
        return NewsListAdapter.CENTER_SINGLE_PIC_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_recy_new_home_center_pic;
    }

    @Override
    protected void setData(BaseViewHolder helper, NewsBean news) {
        //中间大图布局，判断是否有视频
        TextView tvBottomRight = helper.getView(R.id.tv_bottom_right);
        if (news.has_video) {
            helper.setVisible(R.id.iv_play, true);//显示播放按钮
            tvBottomRight.setCompoundDrawables(null, null, null, null);//去除TextView左侧图标
            helper.setText(R.id.tv_bottom_right, TimeUtils.secToTime(news.video_duration));//设置时长
            //中间图片使用视频大图
            GlideImageLoader.create(helper.getView(R.id.iv_img))
                    .loadImage(news.video_detail_info.detail_video_large_image.url,R.color.placeholder_color);
        } else {
            helper.setVisible(R.id.iv_play, false);//隐藏播放按钮
            if (news.gallary_image_count == 1){
                //去除TextView左侧图标
                tvBottomRight.setCompoundDrawables(null, null, null, null);
            }else{
                //TextView增加左侧图标
                tvBottomRight.setCompoundDrawables(mContext.getResources().getDrawable(R.mipmap.icon_picture_group), null, null, null);
                //设置图片数
                helper.setText(R.id.tv_bottom_right, news.gallary_image_count + VObsoleteUtil.getString(R.string.img_unit));
            }
            //中间图片使用image_list第一张
            GlideImageLoader.create(helper.getView(R.id.iv_img))
                    .loadImage(news.image_list.get(0).url.replace("list/300x196", "large"),R.color.placeholder_color);
        }
    }
}
