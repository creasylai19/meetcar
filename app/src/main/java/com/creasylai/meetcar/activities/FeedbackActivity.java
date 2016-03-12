package com.creasylai.meetcar.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.common.ToastUtils;
import com.creasylai.meetcar.consts.AppPreferenceCache;
import com.creasylai.meetcar.consts.InterfaceURLs;
import com.creasylai.meetcar.singleinstance.VolleySingleInstance;
import com.creasylai.meetcar.utils.ParameterConstructor;
import com.creasylai.meetcar.utils.SystemUtil;

import org.json.JSONObject;

public class FeedbackActivity extends BaseActivity {

	private EditText et_fb_content;
	private EditText et_fb_email;

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_feedback);
		et_fb_content = (EditText) findViewById(R.id.et_fb_content);
		et_fb_email = (EditText) findViewById(R.id.et_fb_email);
		SystemUtil.showInputBroad(et_fb_content);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		et_fb_content.setText(AppPreferenceCache.getInstance(this).getFeedbackContent());
		et_fb_email.setText(AppPreferenceCache.getInstance(this).getUserEmail());
	}

	@Override
	public void onBackPressed() {
		if (!TextUtils.isEmpty(et_fb_content.getText())) {
			AppPreferenceCache.getInstance(this).setFeedbackContent(et_fb_content.getText().toString());
		}
		if (!TextUtils.isEmpty(et_fb_email.getText())) {
			AppPreferenceCache.getInstance(this).setFeedbackContent(et_fb_email.getText().toString());
		}
		super.onBackPressed();
	}

	private void commit_feedback_data() {
		if (TextUtils.isEmpty(et_fb_content.getText())) {
			ToastUtils.toastShort(this, "请输入您的意见");
			return;
		}
		if (TextUtils.isEmpty(et_fb_email.getText())) {
			ToastUtils.toastShort(this, "请输入您的Email");
			return;
		}
		JSONObject requestData = new JSONObject();
		ParameterConstructor.putParaInJson("content", et_fb_content.getText().toString(), requestData);
		ParameterConstructor.putParaInJson("email", et_fb_email.getText().toString(), requestData);
		JsonObjectRequest getString = new JsonObjectRequest(InterfaceURLs.FEEDBACK, ParameterConstructor.getRequestJson(requestData, this), new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				dismissProgress();
				ToastUtils.toastShort(FeedbackActivity.this, R.string.feedback_success);
				onBackPressed();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				dismissProgress();
				ToastUtils.toastShort(FeedbackActivity.this, R.string.err_unknown);
			}
		});
		showProgress();
		VolleySingleInstance.getInstance(FeedbackActivity.this).addToRequestQueue(getString);
	}

	public void submit_feedback(View view) {
		commit_feedback_data();
	}
}
