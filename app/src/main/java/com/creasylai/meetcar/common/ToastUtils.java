package com.creasylai.meetcar.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by laicreasy on 15/11/19.
 */
public class ToastUtils {
	public static void toastShort(Context context, String string) {
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}

	public static void toastShort(Context context, int stringRes) {
		Toast.makeText(context, stringRes, Toast.LENGTH_SHORT).show();
	}
}
