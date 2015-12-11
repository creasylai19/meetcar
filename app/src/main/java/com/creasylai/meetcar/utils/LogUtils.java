package com.creasylai.meetcar.utils;

import android.util.Log;

/**
 * Created by laicreasy on 15/12/11.
 */
public class LogUtils {

	private static boolean IS_LOG = true;

	public static void debug(String tag, String msg) {
		if (IS_LOG) {
			Log.d(tag, msg);
		}
	}

	public static void debug(String tag, Throwable tr) {
		if (IS_LOG) {
			Log.d(tag, Log.getStackTraceString(tr));
		}
	}

	public static void error(String tag, String msg) {
		if (IS_LOG) {
			Log.e(tag, msg);
		}
	}

	public static void error(String tag, Throwable tr) {
		if (IS_LOG) {
			Log.e(tag, Log.getStackTraceString(tr));
		}
	}

	public static void info(String tag, String msg) {
		if (IS_LOG) {
			Log.i(tag, msg);
		}
	}

	public static void verbose(String tag, String msg) {
		if (IS_LOG) {
			Log.v(tag, msg);
		}
	}

	public static void warn(String tag, String msg) {
		if (IS_LOG) {
			Log.w(tag, msg);
		}
	}

}
