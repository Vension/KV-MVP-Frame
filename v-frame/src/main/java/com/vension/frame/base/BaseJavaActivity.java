package com.vension.frame.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.gw.swipeback.WxSwipeBackLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vension.frame.R;
import com.vension.frame.stacks.observers.ActivityObserver;
import com.vension.frame.utils.NetworkUtil;
import com.vension.frame.views.MultipleStatusView;
import com.wuhenzhizao.titlebar.utils.AppUtils;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

/**
 * @author ：Created by Administrator on 2018/4/3 10:11.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/3 11:39
 * 描  述：为解决传入 Presenter 类
 * ========================================================
 */

public abstract class BaseJavaActivity<P extends V_BasePresenter> extends AppCompatActivity implements V_IActivity, V_IBaseView {

	protected Context mContext;
	protected View mRootView = null;//在使用自定义toolbar时候的根布局 = toolBarView+childView
	protected P mPresenter = null;
	protected MultipleStatusView mLayoutStatusView = null; //多种状态的 View 的切换
	protected RxPermissions mRxPermissions = null; //6.0动态获取权限

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		//        KeyboardConflictCompat.assistWindow(window)//
		AppUtils.transparencyBar(getWindow());//透明状态栏
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(attachLayoutRes());//加载布局
			mContext = this.getApplicationContext();
			mRxPermissions = new RxPermissions(this);
			mPresenter = createPresenter();

			//设置侧滑返回
			if (isEnableSlideClose()) {
				WxSwipeBackLayout mSwipeBackLayout = new WxSwipeBackLayout(this);
				mSwipeBackLayout.setMaskAlpha(180);
				mSwipeBackLayout.attachToActivity(this);
			}

			//设置ToolBar
			if (showToolBar()) {
				CommonTitleBar mCommonTitleBar = mRootView.findViewById(R.id.mCommonTitleBar);//子布局容器
				initToolBar(mCommonTitleBar);//初始化标题栏
			}

			if (mPresenter != null){
				mPresenter.attachView(this);
			}
			initViewAndData(savedInstanceState);//初始化view和数据
			initRequestState();//初始化状态
			setRetryClickListener();//设置重新加载按钮点击监听
			ActivityObserver.getInstance().regist(this);//activity进栈
//            ActivityStackManager.getInstance().addActivity(WeakReference(this))//activity进栈

		}catch (Exception e){
			e.printStackTrace();
		}

	}


	@Override
	public void setContentView(int layoutResID) {
		if (layoutResID == 0){
			throw new RuntimeException("layoutResID == -1 have u create your layout?");
		}
		if (showToolBar() && getToolBarResId() != -1) {
			//如果需要显示自定义toolbar,并且资源id存在的情况下，实例化baseView;
			mRootView = LayoutInflater.from(this).inflate(isToolbarCover()
					? R.layout.activity_base_toolbar_cover : R.layout.activity_base,
					null, false);//根布局

			ViewStub vs_toolbar = mRootView.findViewById(R.id.vs_toolbar); //toolbar容器
			FrameLayout fl_container = mRootView.findViewById(R.id.fl_container); //子布局容器
			vs_toolbar.setLayoutResource(getToolBarResId()); //toolbar资源id
			vs_toolbar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			vs_toolbar.inflate();//填充toolbar
			LayoutInflater.from(this).inflate(layoutResID, fl_container, true);//子布局
			setContentView(mRootView);
		} else {
			//不显示通用toolbar
			super.setContentView(layoutResID);
		}
	}


	/**初始化状态*/
	private void initRequestState() {
		if (isShowLoading()) {
			if (mLayoutStatusView != null){
				mLayoutStatusView.showLoading();
			}
		}
		if (isCheckNet()){
			if (!NetworkUtil.isConnected(mContext)) {
				if (mLayoutStatusView != null){
					mLayoutStatusView.showNoNetwork();
				}
				return;
			}
		}
		requestLoadData();//请求网络数据
	}


	/**设置重新加载按钮点击监听*/
	private void setRetryClickListener() {
		if (mLayoutStatusView != null){
			mLayoutStatusView.setOnClickListener(mRetryClickListener);
		}
	}

	public View.OnClickListener mRetryClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			initRequestState();
		}
	};


	/**
	 *  加载布局
	 */
	@LayoutRes
	protected abstract int attachLayoutRes();

	/**
	 * 初始化标题栏
	 */
	protected abstract void initToolBar(CommonTitleBar mCommonTitleBar);

	/**
	 * 创建Presenter
	 */
	protected abstract P createPresenter();

	/**
	 *  请求数据前的一些初始化设置
	 */
	protected abstract void initViewAndData(@NonNull Bundle savedInstanceState);

	/**
	 * 请求加载网络数据
	 */
	protected abstract void requestLoadData();


	// ====================================================================
	/**
	 * 是否显示通用toolBar 默认不显示
	 *
	 * @return false
	 */
	public boolean showToolBar() {
		return false;
	}

	/**
	 * toolbar是否覆盖在内容区上方
	 *
	 * @return false 不覆盖  true 覆盖
	 */
	public boolean isToolbarCover() {
		return false;
	}

	/**
	 * 获取自定义toolbarview 资源id 默认为-1，
	 * showToolBar()方法必须返回true才有效
	 */
	private int getToolBarResId() {
		return R.layout.v_layout_default_toolbar;
	}

	/**
	 * 请求数据前是否显示loading页面
	 *
	 * @return true 默认显示
	 */
	public boolean isShowLoading() {
		return false;
	}

	/**
	 * 请求数据前是否检查网络连接
	 *
	 * @return false 默认不检查
	 */
	public boolean isCheckNet() {
		return false;
	}

	/**
	 * 是否开启侧滑返回
	 *
	 * @return true 默认开启
	 */
	public boolean isEnableSlideClose(){
		return true;
	}


	// ====================================================================

	@Override
	public void showLoading() {

	}

	@Override
	public void dismissLoading() {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mRootView = null;
		if (mPresenter != null){
			mPresenter.detachView();
		}
		ActivityObserver.getInstance().unregist(this);//activity出栈
//        ActivityStackManager.getInstance().removeActivity(WeakReference(this))
		V_Application.Companion.getRefWatcher(this).watch(this);
	}

	@Override
	public void onBackPressed() {
		// Fragment 逐个出栈
		final int count = getSupportFragmentManager().getBackStackEntryCount();
		if (count == 0) {
			super.onBackPressed();
		} else {
			getSupportFragmentManager().popBackStack();
		}
	}

	//重写此方法，会在内存紧张的时候回调（支持多个级别），便于我们主动的进行资源释放，避免OOM。
	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);
	}


	/**
	 * 打卡软键盘
	 */
	void openKeyBord(Context mContext,EditText mEditText){
		InputMethodManager methodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		methodManager.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		methodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 */
	void closeKeyBord(Context mContext,EditText mEditText){
		InputMethodManager methodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		methodManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}


	// ====================================添加fragment=======================================
	/**
	 * 添加 Fragment
	 * @param containerViewId
	 * @param fragment
	 */
	protected void addFragment(int containerViewId,Fragment fragment) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.add(containerViewId, fragment);
		fragmentTransaction.commit();
	}


	/**
	 * 添加 Fragment
	 * @param containerViewId
	 * @param fragment
	 */
	protected void addFragment(int containerViewId,Fragment fragment,String tag) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		// 设置tag，不然下面 findFragmentByTag(tag)找不到
		fragmentTransaction.add(containerViewId, fragment, tag);
		fragmentTransaction.addToBackStack(tag);
		fragmentTransaction.commit();
	}

	/**
	 * 替换 Fragment
	 * @param containerViewId
	 * @param fragment
	 */
	protected void replaceFragment(int containerViewId,Fragment fragment) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(containerViewId, fragment);
		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	/**
	 * 替换 Fragment
	 * @param containerViewId
	 * @param fragment
	 */
	protected void replaceFragment(int containerViewId,Fragment fragment,String tag) {
		if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
			// 设置tag
			fragmentTransaction.replace(containerViewId, fragment, tag);
			fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			// 这里要设置tag，上面也要设置tag
			fragmentTransaction.addToBackStack(tag);
			fragmentTransaction.commit();
		} else {
			// 存在则弹出在它上面的所有fragment，并显示对应fragment
			getSupportFragmentManager().popBackStack(tag, 0);
		}
	}



}
