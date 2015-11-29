package com.creasylai.meetcar.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.creasylai.meetcar.R;

/**
 * Created by laicreasy on 15/11/20.
 */
public class SystemUtil {

	/**
	 * 弹出键盘
	 * @param v 获取焦点的view
	 */
	public static void showInputBroad(View v){
		try {
			if(v==null)return;
			InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 弹出键盘
	 * @param v 获取焦点的view
	 */
	public static void hideInputBroad(View v){
		try {
			if(v==null)return;
			InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			if(imm.isActive())imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );
		} catch (Exception e) {	e.printStackTrace();}
	}

	public static void hideInputBroad(Activity activity) {
		InputMethodManager imm =  (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if(imm != null) {
			imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
		}
	}

	/**
	 * 获取设备的序列号
	 * @param context
	 * @return 设备的序列号 String
	 */
	public static String getDeviceId(Context context)
	{
		String deviceId = null;
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if(null != telephonyManager)
		{
			deviceId = telephonyManager.getDeviceId();
		}
		return deviceId;
	}

	public static void phoneDial(String phoneNumber, Activity activity) {
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(intent);
	}

	public static void setClipboard(String text, Context context) {
		ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
		clipboard.setPrimaryClip(ClipData.newPlainText(null, text));
	}

	/**
	 *
	 * @return 手机型号
	 */
	public static String getDeviceInfo() {
		return android.os.Build.DEVICE;
	}

	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = info.versionName;
			return context.getString(R.string.app_name) + version;
		} catch (Exception e) {
			e.printStackTrace();
			return context.getString(R.string.can_not_find_version_name);
		}
	}

}
