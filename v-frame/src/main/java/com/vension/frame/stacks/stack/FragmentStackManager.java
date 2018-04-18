package com.vension.frame.stacks.stack;

import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 11:03
 * 描  述：使用 弱引用 WeakReference 保存每个Fragment对象, 有效的保证Fragment回收时不被调用
 * ========================================================
 */

public class FragmentStackManager extends ObjectStackManager<Fragment> {
	private static final String TAG = "FragmentStackManager";
	private static FragmentStackManager _StackManager;

	private FragmentStackManager() {
		super();
	}


	/***
	 * 获得AppManager的实例
	 *
	 * @return AppManager实例
	 */
	public static FragmentStackManager getInstance() {
		if (_StackManager == null) {
			_StackManager = new FragmentStackManager();
		}
		return _StackManager;
	}

	/**添加Activity到栈中*/
	synchronized public boolean addFragment(WeakReference<Fragment> fragment) {
		return addObjectInStack(fragment);
	}


	/**
	 * 销毁指定Activity
	 *
	 * @param fragment 弱引用的ac
	 */
	public boolean removeFragment(WeakReference<Fragment> fragment) {
		return removeObjectOutStack(fragment);
	}



	synchronized public boolean removeFragmentByClass(Class<? extends Fragment> _Class) {
		Fragment _Fragment = getTackByClass(_Class);
		if(!isOk(_Fragment)) {
			return false;
		}
		return removeFragment(new WeakReference(_Fragment));
	}


	public boolean isOk(Fragment fragment) {
		if(null == fragment) return false;
		if(null == fragment.getContext()) return false;
		if(null == getTackByClass(fragment.getClass())) return false;
		return true;
	}



}
