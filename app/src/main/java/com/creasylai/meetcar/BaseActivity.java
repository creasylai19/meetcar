package com.creasylai.meetcar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by laicreasy on 15/11/11.
 */
public abstract class BaseActivity extends Activity {

	public static void startActivity(Context context, Class<?> cls) {
		context.startActivity(new Intent(context, cls));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(savedInstanceState);
		initData(savedInstanceState);
	}

	public abstract void initView(Bundle savedInstanceState);
	public abstract void initData(Bundle savedInstanceState);
}
