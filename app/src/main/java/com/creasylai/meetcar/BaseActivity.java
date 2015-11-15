package com.creasylai.meetcar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

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

	private ProgressDialog pDialog = null;

	public void showProgress() {
		pDialog = new ProgressDialog(this);
		pDialog.setMessage(getString(R.string.loading));
		pDialog.show();
	}

	public void showProgress(String progressTip) {
		pDialog = new ProgressDialog(this);
		if( TextUtils.isEmpty(progressTip) ) {
			showProgress();
		} else {
			pDialog.setMessage(getString(R.string.loading));
			pDialog.show();
		}
	}

	public void dismissProgress() {
		pDialog.dismiss();
	}
}
