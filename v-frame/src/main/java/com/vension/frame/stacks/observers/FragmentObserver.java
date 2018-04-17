package com.vension.frame.stacks.observers;


import android.support.v4.app.Fragment;

import java.util.List;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 11:03
 * 描  述：Fragment管理
 * ========================================================
 */

public class FragmentObserver extends ObjectObserver<Fragment> {

	private static FragmentObserver _Observer;

	private FragmentObserver() {
		super();
	}

	/**实例化*/
	static synchronized public FragmentObserver getInstance() {
		if(_Observer == null) {
			_Observer = new FragmentObserver();
		}
		return _Observer;
	}

	/**保存Fragment*/
	synchronized public boolean regist(Fragment fragment) {
		return registObjectObserver(fragment);
	}

	/**销毁Fragment*/
	synchronized public boolean unregist(Fragment fragment) {
		return unregistObjectObserver(fragment);
	}

	synchronized public boolean unregistByClass(Class<? extends Fragment> _Class) {
		Fragment _Fragment = getObserver(_Class);
		if(!isOk(_Fragment)) {
			return false;
		}
		return unregist(_Fragment);
	}

	@Override
	public void notifyDataSetChanged() {
		List<Fragment> _Observers = getObserverList();

		for (Fragment _IProjectFragment : _Observers) {
			if(isOk(_IProjectFragment)) {
//				_IProjectFragment.onResetRefresh();
			}
		}
	}

	@Override
	public void notifyDataSetChanged(Fragment fragment) {
		if(isOk(fragment)) {
			//TODO fragment 刷新
//			fragment.onResetRefresh();
		}
	}

	@Override
	public void notifyDataSetChanged(Class<? extends Fragment> _Class) {
		Fragment _IProjectFragment = getObserver(_Class);
		if(isOk(_IProjectFragment)) {
//			_IProjectFragment.onResetRefresh();
		}
	}


	public boolean isOk(Fragment fragment) {
		if(null == fragment) return false;
		if(null == fragment.getContext()) return false;
		if(null == getObserver(fragment.getClass())) return false;
		return true;
	}


}
