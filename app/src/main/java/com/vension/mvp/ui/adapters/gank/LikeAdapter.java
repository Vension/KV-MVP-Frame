package com.vension.mvp.ui.adapters.gank;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunfusheng.glideimageview.GlideImageLoader;
import com.vension.frame.VFrame;
import com.vension.frame.utils.TimeUtil;
import com.vension.mvp.R;
import com.vension.mvp.db.realm.bean.RealmLikeBean;
import com.vension.mvp.ui.activitys.ProxyAvtivity;
import com.vension.mvp.ui.fragments.gank.ImagePreviewFragment;
import com.vension.mvp.ui.fragments.gank.TechDetailFragment;
import com.vension.mvp.utils.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codeest on 16/8/23.
 */

public class LikeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<RealmLikeBean> mList;
    private LayoutInflater inflater;

    private static final int TYPE_ARTICLE = 0;
    private static final int TYPE_GIRL = 1;

    public LikeAdapter(Context mContext, List<RealmLikeBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        if(mList.get(position).getType() == Constants.TYPE_GIRL) {
            return TYPE_GIRL;
        } else {
            return TYPE_ARTICLE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ARTICLE) {
            return new ArticleViewHolder(inflater.inflate(R.layout.item_like_article, parent, false));
        } else {
            return new GirlViewHolder(inflater.inflate(R.layout.item_recy_gank_like_girl, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ArticleViewHolder) {
            ((ArticleViewHolder) holder).tvArticleDesc.setText(mList.get(position).getTitle());
            ((ArticleViewHolder) holder).tvArticleTime.setText(TimeUtil.millis2String(mList.get(position).getTime()));
            int width = (int) VFrame.getResources().getDimension(R.dimen.article_image_width);
            GlideImageLoader.create(((ArticleViewHolder) holder).ivLikeArticle).loadImage(mList.get(position).getImage() +"?imageView2/0/w/" + width,R.color.placeholder_color);
            switch (mList.get(position).getType()) {
//                case Constants.TYPE_ZHIHU:
//                    if (mList.get(position).getImage() != null) {
//                        GlideImageLoader.create(((ArticleViewHolder) holder).image).loadImage(mList.get(position).getImage(),R.color.placeholder_color);
//                    } else {
//                        ((ArticleViewHolder) holder).image.setImageResource(R.mipmap.ic_launcher);
//                    }
//                    ((ArticleViewHolder) holder).from.setText("来自 知乎");
//                    holder.itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            gotoDailyDetail(Integer.valueOf(mList.get(holder.getAdapterPosition()).getId()));
//                        }
//                    });
//                    break;
                case Constants.TYPE_ANDROID:
                    ((ArticleViewHolder) holder).tvArticleFrom.setText("来自-干货·Android");
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gotoTechDetail(mList.get(holder.getAdapterPosition()).getUrl(), null, mList.get(holder.getAdapterPosition()).getTitle()
                                    ,mList.get(holder.getAdapterPosition()).getId(), Constants.TYPE_ANDROID);
                        }
                    });
                    break;
                case Constants.TYPE_IOS:
                    ((ArticleViewHolder) holder).tvArticleFrom.setText("来自-干货·iOS");
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            gotoTechDetail(mList.get(holder.getAdapterPosition()).getUrl(), null, mList.get(holder.getAdapterPosition()).getTitle()
                                    ,mList.get(holder.getAdapterPosition()).getId(), Constants.TYPE_IOS);
                        }
                    });
                    break;
//                case Constants.TYPE_WEB:
//                    ((ArticleViewHolder) holder).image.setImageResource(R.mipmap.ic_web);
//                    ((ArticleViewHolder) holder).from.setText("来自 干货");
//                    holder.itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            gotoTechDetail(mList.get(holder.getAdapterPosition()).getUrl(), null ,mList.get(holder.getAdapterPosition()).getTitle()
//                                    ,mList.get(holder.getAdapterPosition()).getId(), Constants.TYPE_WEB);
//                        }
//                    });
//                    break;
//                case Constants.TYPE_WECHAT:
//                    ImageLoader.load(mContext, mList.get(position).getId(), ((ArticleViewHolder) holder).image);
//                    ((ArticleViewHolder) holder).from.setText("来自 微信");
//                    holder.itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            gotoTechDetail(mList.get(holder.getAdapterPosition()).getUrl(), mList.get(holder.getAdapterPosition()).getImage(), mList.get(holder.getAdapterPosition()).getTitle()
//                                    ,mList.get(holder.getAdapterPosition()).getId(), Constants.TYPE_WECHAT);
//                        }
//                    });
//                    break;
//                case Constants.TYPE_GOLD:
//                    if (mList.get(position).getImage() != null) {
//                        ImageLoader.load(mContext, mList.get(position).getImage(), ((ArticleViewHolder) holder).image);
//                    } else {
//                        ((ArticleViewHolder) holder).image.setImageResource(R.mipmap.ic_launcher);
//                    }
//                    ((ArticleViewHolder) holder).from.setText("来自 掘金");
//                    holder.itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            gotoTechDetail(mList.get(holder.getAdapterPosition()).getUrl(), mList.get(holder.getAdapterPosition()).getImage(), mList.get(holder.getAdapterPosition()).getTitle()
//                                    ,mList.get(holder.getAdapterPosition()).getId(), Constants.TYPE_GOLD);
//                        }
//                    });
//                    break;
//                case Constants.TYPE_VTEX:
//                    if (mList.get(position).getImage() != null) {
//                        ImageLoader.load(mContext, VtexPresenter.parseImg(mList.get(position).getImage()), ((ArticleViewHolder) holder).image);
//                    } else {
//                        ((ArticleViewHolder) holder).image.setImageResource(R.mipmap.ic_launcher);
//                    }
//                    ((ArticleViewHolder) holder).from.setText("来自 V2EX");
//                    holder.itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            gotoVtexDetail(mList.get(holder.getAdapterPosition()).getId());
//                        }
//                    });
//                    break;
            }
        } else if(holder instanceof GirlViewHolder) {
            ((GirlViewHolder) holder).tvLikeTime.setText(TimeUtil.millis2String(mList.get(position).getTime()));
            GlideImageLoader.create(((GirlViewHolder) holder).image).loadImage(mList.get(position).getImage(),R.color.placeholder_color);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoGirlDetail(mList.get(holder.getAdapterPosition()).getImage(),mList.get(holder.getAdapterPosition()).getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_article_desc)
		TextView tvArticleDesc;
        @BindView(R.id.iv_like_article)
		ImageView ivLikeArticle;
        @BindView(R.id.tv_article_from)
		TextView tvArticleFrom;
        @BindView(R.id.tv_article_time)
		TextView tvArticleTime;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    public static class GirlViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_girl_image)
		ImageView image;
        @BindView(R.id.tv_like_time)
        TextView tvLikeTime;

        public GirlViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

//    private void gotoDailyDetail(int id) {
//        Intent intent = new Intent();
//        intent.setClass(mContext, ZhihuDetailActivity.class);
//        intent.putExtra(Constants.IT_ZHIHU_DETAIL_ID, id);
//        intent.putExtra(Constants.IT_ZHIHU_DETAIL_TRANSITION, true);
//        mContext.startActivity(intent);
//    }
//

    /**
     *  mBundle?.putString("id",item._id)
     mBundle?.putString("title",item.desc)
     mBundle?.putString("url",item.url)
     mBundle?.putString("image_url",item.images[0])
     if (item.type == "Android"){
     mBundle?.putInt("type",Constants.TYPE_ANDROID)
     }else{
     mBundle?.putInt("type",Constants.TYPE_IOS)
     }
     */
    private void gotoTechDetail(String url, String imgUrl, String title, String id, int type) {
        Bundle mBundle = new Bundle();
        mBundle.putString("url",url);
        mBundle.putString("id",id);
        mBundle.putString("title",title);
        mBundle.putString("image_url",imgUrl);
        mBundle.putInt("type",type);
        mBundle.putString(Constants.AGENT_FRAGMENT_CLASS_KEY, TechDetailFragment.class.getName()); // com.project.app.activity.*
        Intent intent = new Intent(mContext, ProxyAvtivity.class);
        intent.putExtras(mBundle);
        mContext.startActivity(intent);
    }

    private void gotoGirlDetail(String url, String id) {
        Bundle mBundle = new Bundle();
        mBundle.putString("image_url",url);
        mBundle.putString("image_id",id);
        mBundle.putString(Constants.AGENT_FRAGMENT_CLASS_KEY, ImagePreviewFragment.class.getName()); // com.project.app.activity.*
        Intent intent = new Intent(mContext, ProxyAvtivity.class);
        intent.putExtras(mBundle);
        mContext.startActivity(intent);
    }

//    private void gotoVtexDetail(String topicId) {
//        Intent intent = new Intent();
//        intent.setClass(mContext, RepliesActivity.class);
//        intent.putExtra(Constants.IT_VTEX_TOPIC_ID,topicId);
//        mContext.startActivity(intent);
//    }
}
