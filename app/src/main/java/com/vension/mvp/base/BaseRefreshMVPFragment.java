package com.vension.mvp.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.vension.frame.base.V_BasePresenter;
import com.vension.frame.base.V_IBaseView;
import com.vension.frame.utils.NetworkUtil;
import com.vension.frame.utils.VLogUtil;
import com.vension.frame.utils.VObsoleteUtil;
import com.vension.frame.utils.VToastUtil;
import com.vension.frame.views.MultipleStatusView;
import com.vension.mvp.R;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.BindView;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/3 14:05
 * 描  述：MVP 上下刷新的BaseFragment
 * ========================================================
 */

public abstract class BaseRefreshMVPFragment<T,P extends V_BasePresenter> extends BaseFragment
		implements SwipeRefreshLayout.OnRefreshListener,
		BaseQuickAdapter.RequestLoadMoreListener,V_IBaseView{

	@BindView(R.id.mSwipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;
	@BindView(R.id.refresh_MultipleStatusView) MultipleStatusView refresh_MultipleStatusView;
	@BindView(R.id.refreshRecyclerView) RecyclerView mRecyclerView;

	private BaseQuickAdapter<T,BaseViewHolder> mAdapter = null;
	protected P mPresenter = null;
	private int page = 1; //页数
	private int limit = 15; //条目数

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_base_refresh;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		VLogUtil.e("BaseRefreshMVPFragment == initViewAndData");
		setMLayoutStatusView(refresh_MultipleStatusView);
		//初始化刷新控件
		mSwipeRefreshLayout.setColorSchemeColors(VObsoleteUtil.getColor(R.color.app_main_thme_color));
		//设置刷新控件监听
		mSwipeRefreshLayout.setOnRefreshListener(this);
		//初始化_RecyclerView
		mRecyclerView.setHasFixedSize(true);
		mRecyclerView.setLayoutManager(createLayoutManager());
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());

		mPresenter = createPresenter();
		if (mPresenter != null){
			mPresenter.attachView(this);
		}
		//一些特殊的初始化
		initRefreshFragment();


		if (mAdapter == null){
			mAdapter = createCommonRecyAdapter();
		}
		if (mAdapter != null){
			mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
			if (hasLoadMore()) {
				mAdapter.setOnLoadMoreListener(this, mRecyclerView);
			}
			addItemClickListener(mAdapter);
			mRecyclerView.setAdapter(mAdapter);
		}
	}


	/**加载数据*/
	@Override
	public void lazyLoadData() {
		//自动刷新，演示效果
		VLogUtil.e("BaseRefreshMVPFragment ==> lazyLoadData");
		mSwipeRefreshLayout.setRefreshing(true);
		mSwipeRefreshLayout.postDelayed(() -> onRefresh(),300);
	}


	/**下拉刷新数据*/
	@Override
	public void onRefresh() {
		VLogUtil.e("BaseRefreshMVPFragment ==> onRefresh");
		if (isCheckNet()){
			if (!NetworkUtil.isConnected(getActivity())){
				if (getMLayoutStatusView() != null){
					getMLayoutStatusView().showNoNetwork();
				}
				if (mSwipeRefreshLayout.isRefreshing()) {
					mSwipeRefreshLayout.setRefreshing(false);
				}
				return;
			}
		}
		if (mAdapter != null){
			mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
		}

		onTargerRequestApi(true,1,limit);
	}

	/**加载更多*/
	@Override
	public void onLoadMoreRequested() {
		//上拉加载更多/下一页
		VLogUtil.e("BaseRefreshMVPFragment ==> onLoadmore");
		mSwipeRefreshLayout.setEnabled(false); //加载更多时不能同时下拉刷新
		onTargerRequestApi(false, ++page, limit);
	}


	/** ================================= 设置数据 Start ============================ */

	/**
	 * 设置数据
	 *
	 * @param isRefresh 是否刷新
	 * @param listData  列表数据
	 */
	public void addItemData(boolean isRefresh,List<T> listData) {
		addItemData(isRefresh,listData, null, null);
	}

	/**
	 * 设置数据
	 *
	 * @param isRefresh 是否刷新
	 * @param listData  列表数据
	 * @param header    头部
	 * @param footer    尾部
	 * */
	public void addItemData(boolean isRefresh, List<T> listData,List<View> header,List<View> footer) {
		VLogUtil.e("BaseRefreshMVPFragment ==> addItemData");
		if (mAdapter != null){
			if (isRefresh){
				VLogUtil.e("BaseRefreshMVPFragment ==> addItemData 加载第一页数据");
				//加载第一页数据
				getMLayoutStatusView().showContent();
				mAdapter.setEnableLoadMore(true);
				mAdapter.removeAllHeaderView();
				mAdapter.removeAllFooterView();
				//添加头部
				if (header != null && !header.isEmpty()){
					for (View view : header) {
						mAdapter.addHeaderView(view);
					}
				}
				//添加尾部
				if (footer != null && !footer.isEmpty()){
					for (View view : footer) {
						mAdapter.addFooterView(view);
					}
				}
				//添加内容
				if (!listData.isEmpty()) {
					mAdapter.setNewData(listData);
				} else {
					getMLayoutStatusView().showEmpty();
				}
			}
			else //加载下一页数据
			{
				VLogUtil.e("BaseRefreshMVPFragment ==> addItemData 加载下一页数据");
				mSwipeRefreshLayout.setEnabled(true); //加载更多时不能同时下拉刷新
				if (!listData.isEmpty()) {
					VLogUtil.e("BaseRefreshMVPFragment ==> addItemData 加载下一页数据Size=" + listData.size());
					mAdapter.addData(listData);
					mAdapter.loadMoreComplete();//加载更多完成
				}else{
					VToastUtil.showDefault("没有更多了");
					mAdapter.loadMoreEnd();//没有更多了
				}
			}
		}
		if (mSwipeRefreshLayout.isRefreshing()) {
			mSwipeRefreshLayout.setRefreshing(false);
		}
	}


	/** ================================= 设置数据 End ============================ */


	@Override
	public void showLoading() {
	}


	@Override
	public void dismissLoading() {
	}

	/**一些特殊处理*/
	protected abstract void initRefreshFragment();
	/**创建Presenter*/
	protected abstract P createPresenter();
	/**创建adapter*/
	protected abstract BaseQuickAdapter<T, BaseViewHolder> createCommonRecyAdapter();
	/**adapter 的点击事件处理*/
	protected abstract void addItemClickListener(BaseQuickAdapter<T, BaseViewHolder> mAdapter);
	/**请求Api数据*/
	protected abstract void onTargerRequestApi(boolean isRefresh,int page,int limit);

	/**
	 * 是否显示通用toolBar 默认不显示
	 *
	 * @return false
	 */
	public boolean hasLoadMore() {
		VLogUtil.e("BaseRefreshMVPFragment ==> hasLoadMore");
		return true;
	}
	/**
	 * 创建RecyclerView 的 LayoutManager， 默认LinearLayoutManager
	 * @return  LinearLayoutManager
	 */
	public RecyclerView.LayoutManager createLayoutManager() {
		VLogUtil.e("BaseRefreshMVPFragment ==> createLayoutManager");
		return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mPresenter != null){
			mPresenter.detachView();
		}
		VLogUtil.e("BaseRefreshMVPFragment ==> onDestroy");
	}

}

