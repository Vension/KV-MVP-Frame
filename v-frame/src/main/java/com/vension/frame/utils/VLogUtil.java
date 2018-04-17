package com.vension.frame.utils;

import com.orhanobut.logger.Logger;
import com.vension.frame.BuildConfig;

/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/2 12:12
 * 描  述：基于Logger的日志打印工具
 * ========================================================
 */

public class VLogUtil {

	public static boolean isDebug = BuildConfig.DEBUG;

	/**
	 * log.i
	 *
	 * @param msg
	 */
	public static void i(String msg) {
		if (isDebug) {
			Logger.i(msg);
		}
	}

	/**
	 * log.d
	 *
	 * @param msg
	 */
	public static void d(String msg) {
		if (isDebug) {
			Logger.d(msg);
		}
	}

	/**
	 * log.e
	 *
	 * @param msg
	 */
	public static void e(String msg) {
		if (isDebug) {
			Logger.e(msg);
		}
	}

	/**
	 * log.w
	 *
	 * @param msg
	 */
	public static void w(String msg) {
		if (isDebug) {
			Logger.w(msg);
		}
	}

	/**
	 * log.v
	 *
	 * @param msg
	 */
	public static void v(String msg) {
		if (isDebug) {
			Logger.v(msg);
		}
	}

	/**
	 * log.wtf
	 *
	 * @param msg
	 */
	public static void wtf(String msg) {
		if (isDebug) {
			Logger.wtf(msg);
		}
	}

	/**
	 * log.json
	 *
	 * @param msg
	 */
	public static void json(String msg) {
		if (isDebug) {
			Logger.json(msg);
		}
	}

	/**
	 * log.xml
	 *
	 * @param msg
	 */
	public static void xml(String msg) {
		if (isDebug) {
			Logger.xml(msg);
		}
	}

}
