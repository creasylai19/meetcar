package com.creasylai.meetcar.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.EditText;

import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.consts.AppConst;
import com.creasylai.meetcar.utils.SystemUtil;

public class CommonInfoInputActivity extends BaseActivity {

	private EditText et_input_field;

	public static void startActivityForResult(Activity activity, int requestCode) {
		Intent intent = new Intent(activity, CommonInfoInputActivity.class);
		activity.startActivityForResult(intent, requestCode);
	}

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_important_info_input);
		et_input_field = (EditText) findViewById(R.id.et_input_field);
//		et_input_field.requestFocus();
		SystemUtil.showInputBroad(et_input_field);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

	}

	@Override
	public void onBackPressed() {
		if (!TextUtils.isEmpty(et_input_field.getText())) {
			AlertDialog mAlertDialog = new AlertDialog.Builder(this).setCancelable(true).setMessage(R.string.txt_is_save_data)
                   .setNegativeButton(R.string.txt_not_save, new DialogInterface.OnClickListener() {
	                   @Override
	                   public void onClick(DialogInterface dialog, int which) {
		                   CommonInfoInputActivity.super.onBackPressed();
	                   }
                   }).setPositiveButton(R.string.txt_save, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							saveData();
						}
					}).setTitle(R.string.txt_dialog_tip).create();
			mAlertDialog.show();
		} else {
			super.onBackPressed();
		}
	}

	private void saveData() {
		Intent intent = new Intent();
		intent.putExtra(AppConst.STATIC_STRING_KEY.COMMON_KEY, et_input_field.getText().toString());
		setResult(RESULT_OK, intent);
		this.finish();
	}

}
