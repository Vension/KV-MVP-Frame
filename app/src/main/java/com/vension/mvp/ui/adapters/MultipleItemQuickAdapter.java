package com.vension.mvp.ui.adapters;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vension.mvp.R;
import com.vension.mvp.beans.test.MultipleItem;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 * modify by AllenCoder
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    public MultipleItemQuickAdapter( List data) {
        super(data);
        addItemType(MultipleItem.TEXT, R.layout.item_like_article);
        addItemType(MultipleItem.IMG, R.layout.item_recy_gank_gril);
        addItemType(MultipleItem.IMG_TEXT, R.layout.item_recy_gank_like_girl);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.TEXT:
                break;
            case MultipleItem.IMG_TEXT:
                switch (helper.getLayoutPosition() % 2) {
                    case 0:
                        helper.setImageResource(R.id.iv_girl_image, R.drawable.ic_default_1);
                        break;
                    case 1:
                        helper.setImageResource(R.id.iv_girl_image, R.drawable.ic_default_3);
                        break;

                }
                break;
        }
    }

}
