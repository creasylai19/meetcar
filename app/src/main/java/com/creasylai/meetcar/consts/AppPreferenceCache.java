package com.creasylai.meetcar.consts;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.creasylai.meetcar.bean.UserBean;

/**
 * Created by laicreasy on 15/11/25.
 */
public class AppPreferenceCache {

	private static AppPreferenceCache mInstance;
	private Context mCtx;
	private SharedPreferences sharedPreferences;
	private SharedPreferences.Editor editor;

	private AppPreferenceCache(Context context) {
		// getApplicationContext() is key, it keeps you from leaking the
		// Activity or BroadcastReceiver if someone passes one in.
		mCtx = context.getApplicationContext();
		sharedPreferences = mCtx.getSharedPreferences(AppConst.STATIC_STRING_KEY.APP_SHARE_PREFERENCE, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
	}

	public static synchronized AppPreferenceCache getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new AppPreferenceCache(context);
		}
		return mInstance;
	}

	public boolean isLogin() {
		return !TextUtils.isEmpty(sharedPreferences.getString(AppConst.STATIC_STRING_KEY.USER_TOKEN, ""));
	}

	public String getUserToken() {
		return sharedPreferences.getString(AppConst.STATIC_STRING_KEY.USER_TOKEN, "");
	}

	public void setUserToken( String userToken ) {

		editor.putString(AppConst.STATIC_STRING_KEY.USER_TOKEN, userToken);
		editor.apply();
	}

	public int getUserId() {
		return sharedPreferences.getInt(AppConst.STATIC_STRING_KEY.USER_ID, -1);
	}

	public void setUserId( int userId ) {
		editor.putInt(AppConst.STATIC_STRING_KEY.USER_ID, userId);
		editor.apply();
	}

	public void clearAllCache() {
		editor.clear();
		editor.commit();
	}

	public UserBean getUserBean() {
		return UserBean.getUserBean(sharedPreferences.getString(AppConst.STATIC_STRING_KEY.USER_INFO, ""));
	}

}
