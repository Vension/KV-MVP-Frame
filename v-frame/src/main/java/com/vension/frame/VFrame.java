package com.vension.frame;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;

import com.vension.frame.http.IHttpEngine;
import com.vension.frame.http.VHttp;
import com.vension.frame.imageloader.ImageLoader;
import com.vension.frame.imageloader.VImage;
import com.vension.frame.utils.VCrashUtil;
import com.vension.frame.utils.VObsoleteUtil;
import com.vension.frame.utils.VSizeUtil;
import com.vension.frame.utils.VToastUtil;
import com.vension.frame.utils.log.VLog;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ：Created by Administrator on 2018/3/30 15:39.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 */
public class VFrame {

	public final static String TAG = VFrame.class.getSimpleName();

	private static Application vApplication;
	private static Context vContext;
	public static WeakReference<Activity> activityWeakReference;
	public static List<Activity> vActivityList = new LinkedList<>();

	public static int mScreenHeight; //屏幕高度
	public static int mScreenWidth;  //屏幕宽度
	public static boolean isDebug = true;

	public VFrame() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}

	/**
	 * 初始化VFrame 工具类
	 *
	 * @param mApplication
	 */
	public static void init(@NonNull final Application mApplication){
		VFrame.vApplication = mApplication;
		VFrame.vContext = mApplication.getApplicationContext();
		mApplication.registerActivityLifecycleCallbacks(mCallbacks);
		mScreenHeight = VSizeUtil.getScreenHeight();
		mScreenWidth = VSizeUtil.getScreenWidth();
	}

    public static void initLog(){
		VLog.init();
	}

	public static void initToast(){
		VToastUtil.init(getContext());
	}

	public static void initCrash() {
		VCrashUtil.init();
	}

	public static void initImageLoader(ImageLoader loader) {
		VImage.init(loader);
	}

	public static void initXHttp(IHttpEngine httpEngine) {
		VHttp.init(httpEngine);
	}

	/**获取ApplicationContext*/
	public static Context getContext(){
		synchronized (VFrame.class){
			if (VFrame.vContext == null)
				throw new NullPointerException("Call VFrame.init(context) within your Application onCreate() method." +
						"Or extends V_Application");
			return VFrame.vContext;
		}
	}


	/**
	 * 获取Application
	 *
	 * @return Application
	 */
	public static Application getApplication() {
		if (vApplication != null) return vApplication;
		throw new NullPointerException("u should init first");
	}

	public static Resources getResources(){
		return VFrame.getContext().getResources();
	}

	public static String getString(@StringRes int id){
		return getResources().getString(id);
	}

	public static Resources.Theme getTheme(){
		return VFrame.getContext().getTheme();
	}

	public static AssetManager getAssets(){
		return VFrame.getContext().getAssets();
	}

	public static Drawable getDrawable(@DrawableRes int id){
		return VObsoleteUtil.getDrawable(id);
	}

	public static int getColor(@ColorRes int id){
		return VObsoleteUtil.getColor(id);
	}

	public static Object getSystemService(String name){
		return VFrame.getContext().getSystemService(name);
	}

	public static Configuration getConfigguration(){
		return VFrame.getResources().getConfiguration();
	}

	public static DisplayMetrics getDisplayMetrics(){
		return VFrame.getResources().getDisplayMetrics();
	}


	/**应用生命周期监听器*/
	private static Application.ActivityLifecycleCallbacks mCallbacks = new Application.ActivityLifecycleCallbacks() {
		@Override
		public void onActivityCreated(Activity activity, Bundle bundle) {
			vActivityList.add(activity);
			setTopActivityWeakRef(activity);
		}

		@Override
		public void onActivityStarted(Activity activity) {
		}

		@Override
		public void onActivityResumed(Activity activity) {
		}

		@Override
		public void onActivityPaused(Activity activity) {

		}

		@Override
		public void onActivityStopped(Activity activity) {

		}

		@Override
		public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

		}

		@Override
		public void onActivityDestroyed(Activity activity) {
             vActivityList.remove(activity);
		}
	};

	private static void setTopActivityWeakRef(Activity activity) {
		if (activityWeakReference == null || !activity.equals(activityWeakReference.get())) {
           activityWeakReference = new WeakReference<>(activity);
		}
	}

}
