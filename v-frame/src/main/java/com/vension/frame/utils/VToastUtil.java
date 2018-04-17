package com.vension.frame.utils;

import android.content.Context;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/3/30 16:25
 * 描  述：避免同样的信息多次触发重复弹出的问题
 * ========================================================
 */

public class VToastUtil {

	private static Context sContext;
	private static String oldMsg;
	protected static Toast toast = null;
	private static long oneTime = 0;
	private static long twoTime = 0;

	private VToastUtil() {
		throw new RuntimeException("V_ToastUtil cannot be initialized!");
	}

	public static void init(Context context) {
		sContext = context;
	}

	public static void showToast(String s) {
		if (toast == null) {
			toast = Toast.makeText(sContext, s, Toast.LENGTH_SHORT);
			toast.show();
			oneTime = System.currentTimeMillis();
		} else {
			twoTime = System.currentTimeMillis();
			if (s.equals(oldMsg)) {
				if (twoTime - oneTime > Toast.LENGTH_SHORT) {
					toast.show();
				}
			} else {
				oldMsg = s;
				toast.setText(s);
				toast.show();
			}
			oneTime = twoTime;
		}
	}

	public static void showToast(int resId) {
		showToast(sContext.getString(resId));
	}

	/** ========================  UniversalToast ================================= */
	public static void showDefault(String msg) {
		TastyToast.makeText(sContext, msg, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
	}

	public static void showInfo(String msg) {
		TastyToast.makeText(sContext, msg, TastyToast.LENGTH_LONG, TastyToast.INFO);
	}

	public static void showSuccess(String msg) {
		TastyToast.makeText(sContext, msg, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
	}

	public static void showWarning(String msg) {
		TastyToast.makeText(sContext, msg, TastyToast.LENGTH_LONG, TastyToast.WARNING);
	}

	public static void showError(String msg) {
		TastyToast.makeText(sContext, msg, TastyToast.LENGTH_LONG, TastyToast.ERROR);
	}
	public static void showConfusing(String msg) {
		TastyToast.makeText(sContext, msg, TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
	}


}
