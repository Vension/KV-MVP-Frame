package com.vension.mvp.ui.fragments.agent;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.vension.mvp.R;
import com.vension.mvp.base.BaseFragment;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/8 17:20
 * 描  述：
 * ========================================================
 */

public class WebFragment extends BaseFragment {

	@BindView(R.id.progress_bar)
	ProgressBar progressBar;
	@BindView(R.id.webview)
	WebView webView;


	@Override
	public void initToolBar(@NotNull CommonTitleBar mCommonTitleBar) {
		super.initToolBar(mCommonTitleBar);
		String title = getArguments().getString("web_title");
		mCommonTitleBar.getCenterTextView().setText(title);
	}

	@Override
	public int attachLayoutRes() {
		return R.layout.fragment_web;
	}

	@Override
	public void initViewAndData(@Nullable Bundle savedInstanceState) {
		super.initViewAndData(savedInstanceState);
		String url = getArguments().getString("web_url");
		//禁止跳转到第三方浏览器
//		webView.setWebViewClient(new WebViewClient());
		//允许JS互调。可以使用网页上的功能代码了
		webView.getSettings().setJavaScriptEnabled(true);
		//可以设置进度条和页面进度
//		webView.setWebChromeClient(new WebChromeClient(){
//			@Override
//			public void onProgressChanged(WebView view, int newProgress) {
//				if(newProgress==100){
//					progressBar.setVisibility(View.GONE);
//				}else {
//					progressBar.setVisibility(View.VISIBLE);
//					progressBar.setProgress(newProgress);
//				}
//				super.onProgressChanged(view, newProgress);
//			}
//		});
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				progressBar.setVisibility(View.GONE);
			}
		});

		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				progressBar.setProgress(newProgress);
			}
		});

		webView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {  //表示按返回键
						webView.goBack();   //后退
						return true;    //已处理
					}
				}
				return false;
			}
		});
		webView.loadUrl(url);

	}

	@Override
	public void lazyLoadData() {
		
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		webView.destroy();
	}

}
