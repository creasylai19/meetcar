package com.creasylai.meetcar.utils;

import android.content.Context;

import com.creasylai.meetcar.common.ToastUtils;
import com.creasylai.meetcar.consts.AppPreferenceCache;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by laicreasy on 15/12/11.
 */
public class ParameterConstructor {

	public static JSONObject getRequestJson(JSONObject dataField, Context context) {
		JSONObject requestJson = new JSONObject();
		try {
			requestJson.put("token", AppPreferenceCache.getInstance(context).getUserToken());
			requestJson.put("data", dataField);
		} catch (JSONException e) {
			LogUtils.error("ParameterConstructor", e);
		}
		return  requestJson;
	}

	public static JSONObject putParaInJson( String key, Object value, JSONObject parameter ) {
		if( null == parameter ) {
			parameter = new JSONObject();
		}
		try {
			parameter.put(key, value);
		} catch (JSONException e) {
			LogUtils.error("ParameterConstructor", e);
		}
		return parameter;
	}

	public static JSONObject getResponDataWithErrorToast( JSONObject response, Context context ) {
		int status = response.optInt("status");
		if( status != 200 ) {
			ToastUtils.toastShort(context, response.optString("info"));
		}
		return response.optJSONObject("data");
	}

	public static int getResponStatus( JSONObject response, Context context ) {
		return response.optInt("status");
	}

	public static JSONObject getResponData(JSONObject response, Context context) {
		return response.optJSONObject("data");
	}
}
