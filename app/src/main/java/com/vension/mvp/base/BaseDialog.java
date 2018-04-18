package com.vension.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import com.vension.frame.base.V_BaseDialog;
import com.vension.mvp.R;

import butterknife.ButterKnife;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 17:04
 * 描  述：
 * ========================================================
 */

public abstract class BaseDialog extends V_BaseDialog {
	public BaseDialog(Context context) {
		super(context);
	}

	public BaseDialog(Context context, int themeId) {
		super(context, themeId);
	}

	protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAnimationStyle(R.style.DialogStyleOfDefault);//设置弹窗动画
		setContentView(bindLayoutId());//
		ButterKnife.bind(this);
		final WindowManager.LayoutParams fLayoutParams = getWindow().getAttributes();
		fLayoutParams.dimAmount = 0.3f;
		fLayoutParams.gravity = Gravity.CENTER;
		getWindow().setAttributes(fLayoutParams);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		this.init();
	}

}
