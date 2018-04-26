package com.vension.mvp.ui.fragments.headline;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;
import com.vension.frame.utils.VLogUtil;
import com.vension.mvp.R;
import com.vension.mvp.beans.headline.ChannelBean;
import com.vension.mvp.listener.ItemDragHelperCallBack;
import com.vension.mvp.listener.OnChannelDragListener;
import com.vension.mvp.listener.OnChannelListener;
import com.vension.mvp.ui.adapters.headline.ChannelAdapter;
import com.vension.mvp.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/19 14:09
 * 描  述：
 * ========================================================
 */

public class ChannelDialogFragment extends DialogFragment implements OnChannelDragListener {
    private List<ChannelBean> mDatas = new ArrayList<>();
    private ChannelAdapter mAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private ItemTouchHelper mHelper;

    private OnChannelListener mOnChannelListener;

    public void setOnChannelListener(OnChannelListener onChannelListener) {
        mOnChannelListener = onChannelListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Dialog dialog = getDialog();
        if (dialog != null) {
            //添加动画
            dialog.getWindow().setWindowAnimations(R.style.dialogSlideAnim);
        }
        return inflater.inflate(R.layout.fragment_news_channel_dialog, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        processLogic();
    }

    public static ChannelDialogFragment newInstance(List<ChannelBean> selectedDatas, List<ChannelBean> unselectedDatas) {
        ChannelDialogFragment dialogFragment = new ChannelDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.DATA_SELECTED, (Serializable) selectedDatas);
        bundle.putSerializable(Constants.DATA_UNSELECTED, (Serializable) unselectedDatas);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    private void setDataType(List<ChannelBean> datas, int type) {
        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setItemType(type);
        }
    }


    private void processLogic() {
        mDatas.add(new ChannelBean(ChannelBean.TYPE_MY, "我的频道", ""));
        Bundle bundle = getArguments();
        List<ChannelBean> selectedDatas = (List<ChannelBean>) bundle.getSerializable(Constants.DATA_SELECTED);
        List<ChannelBean> unselectedDatas = (List<ChannelBean>) bundle.getSerializable(Constants.DATA_UNSELECTED);
        setDataType(selectedDatas, ChannelBean.TYPE_MY_CHANNEL);
        setDataType(unselectedDatas, ChannelBean.TYPE_OTHER_CHANNEL);

        mDatas.addAll(selectedDatas);
        mDatas.add(new ChannelBean(ChannelBean.TYPE_OTHER, "频道推荐", ""));
        mDatas.addAll(unselectedDatas);


        mAdapter = new ChannelAdapter(mDatas);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = mAdapter.getItemViewType(position);
                return itemViewType == ChannelBean.TYPE_MY_CHANNEL || itemViewType == ChannelBean.TYPE_OTHER_CHANNEL ? 1 : 4;
            }
        });
        ItemDragHelperCallBack callBack = new ItemDragHelperCallBack(this);
        mHelper = new ItemTouchHelper(callBack);
        mAdapter.setOnChannelDragListener(this);
        //attachRecyclerView
        mHelper.attachToRecyclerView(mRecyclerView);
    }

    @OnClick(R.id.icon_collapse)
    public void onClick(View v) {
        dismiss();
    }

    private DialogInterface.OnDismissListener mOnDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null)
            mOnDismissListener.onDismiss(dialog);
    }

    @Override
    public void onStarDrag(BaseViewHolder baseViewHolder) {
        //开始拖动
        VLogUtil.i("开始拖动");
        mHelper.startDrag(baseViewHolder);
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
//        if (starPos < 0||endPos<0) return;
        //我的频道之间移动
        if (mOnChannelListener != null)
            mOnChannelListener.onItemMove(starPos - 1, endPos - 1);//去除标题所占的一个index
        onMove(starPos, endPos);
    }

    private void onMove(int starPos, int endPos) {
        ChannelBean startChannel = mDatas.get(starPos);
        //先删除之前的位置
        mDatas.remove(starPos);
        //添加到现在的位置
        mDatas.add(endPos, startChannel);
        mAdapter.notifyItemMoved(starPos, endPos);
    }

    @Override
    public void onMoveToMyChannel(int starPos, int endPos) {
        //移动到我的频道
        onMove(starPos, endPos);

        if (mOnChannelListener != null)
            mOnChannelListener.onMoveToMyChannel(starPos - 1 - mAdapter.getMyChannelSize(), endPos - 1);
    }

    @Override
    public void onMoveToOtherChannel(int starPos, int endPos) {
        //移动到推荐频道
        onMove(starPos, endPos);
        if (mOnChannelListener != null)
            mOnChannelListener.onMoveToOtherChannel(starPos - 1, endPos - 2 - mAdapter.getMyChannelSize());
    }
}
