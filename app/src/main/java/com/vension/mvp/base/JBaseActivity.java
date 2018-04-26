package com.vension.mvp.base;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.View;
import com.vension.frame.base.V_BasePresenter;
import com.vension.frame.base.BaseJavaActivity;
import com.vension.mvp.utils.AppCompat;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ：Created by Administrator on 2018/4/3 10:45.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
public abstract class JBaseActivity<P extends V_BasePresenter> extends BaseJavaActivity<P> implements View.OnClickListener{

	private Unbinder _Unbinder;

	@Override
	public void initToolBar(@NotNull CommonTitleBar mCommonTitleBar) {
		mCommonTitleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
			@Override
			public void onClicked(View v, int action, String extra) {
				if (action == CommonTitleBar.ACTION_LEFT_BUTTON || action == CommonTitleBar.ACTION_LEFT_TEXT) {
					onBackPressed();
				}
			}
		});
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		_Unbinder = ButterKnife.bind(this);
	}

	@Override
	public void startAgentActivity(@NotNull Class<? extends Fragment> _Class) {
		startActivity(AppCompat.createAgentIntent(this, _Class));
	}

	@Override
	public void startAgentActivity(@NotNull Class<? extends Fragment> _Class, @NotNull Bundle bundle) {
		startActivity(AppCompat.createAgentIntent(this, _Class, bundle));
	}

	@Override
	public void startAgentActivity(@NotNull Class<?> _Class, @NotNull ActivityOptionsCompat _ActivityOptionsCompat) {
		ActivityCompat.startActivity(this, AppCompat.createAgentIntent(this, _Class), _ActivityOptionsCompat.toBundle());
	}

	/**
	 *  val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, iv_search, iv_search.transitionName)
	 *  startActivity(Intent(activity, SearchActivity::class.java), options.toBundle())
	 **/
	@Override
	public void startAgentActivity(@NotNull Class<?> _Class, @NotNull Bundle bundle, @NotNull ActivityOptionsCompat _ActivityOptionsCompat) {
		ActivityCompat.startActivity(this, AppCompat.createAgentIntent(this, _Class, bundle), _ActivityOptionsCompat.toBundle());
	}

	@Override
	public void startAgentActivityForResult(@NotNull Class<? extends Fragment> _Class, int requestCode) {
		startActivityForResult(AppCompat.createAgentIntent(this, _Class), requestCode);
	}

	@Override
	public void startAgentActivityForResult(@NotNull Class<? extends Fragment> _Class, @NotNull Bundle bundle, int requestCode) {
		startActivityForResult(AppCompat.createAgentIntent(this, _Class, bundle), requestCode);
	}

	@Override
	public void startAgentActivityForResult(@NotNull Class<? extends Fragment> _Class, int requestCode, @NotNull ActivityOptionsCompat _ActivityOptionsCompat) {
		ActivityCompat.startActivityForResult(this, AppCompat.createAgentIntent(this, _Class), requestCode, _ActivityOptionsCompat.toBundle());
	}

	@Override
	public void startAgentActivityForResult(@NotNull Class<? extends Fragment> _Class, @NotNull Bundle bundle, int requestCode, @NotNull ActivityOptionsCompat _ActivityOptionsCompat) {
		ActivityCompat.startActivityForResult(this, AppCompat.createAgentIntent(this, _Class, bundle), requestCode, _ActivityOptionsCompat.toBundle());
	}

	@Override
	public void onClick(View view) {
		//TODO 点击事件监听
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (_Unbinder != null){
			_Unbinder.unbind();
		}
	}



}
