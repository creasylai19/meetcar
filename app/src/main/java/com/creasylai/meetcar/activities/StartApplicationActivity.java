package com.creasylai.meetcar.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.common.ToastUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

public class StartApplicationActivity extends BaseActivity implements View.OnClickListener{

	private UserInterface mUserInterface;

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_start_application);
		mUserInterface = new UserInterface();
		findViews(mUserInterface);
		mUserInterface.btn_qq_login.setOnClickListener(this);
		mUserInterface.btn_weixin_login.setOnClickListener(this);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		/** 友盟统计中设置是否对日志信息进行加密, 默认false(不加密). */
		AnalyticsConfig.enableEncrypt(true);
		MobclickAgent.setDebugMode(true);//TODO delete
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_qq_login:
				qq_login();
				break;
			case R.id.btn_weixin_login:
				weixin_login();
				break;
			default:
				break;
		}
	}

	private void weixin_login() {
//		startActivity(this, MainMapActivity.class);
//		this.finish();
		ToastUtils.toastShort(this, R.string.login_using_qq);
	}

	private void qq_login() {
		startActivity(this, MainMapActivity.class);
		this.finish();

	}

	private void findViews( UserInterface mUserInterface ) {
		mUserInterface.btn_weixin_login = (ImageView)findViewById(R.id.btn_weixin_login);
		mUserInterface.btn_qq_login = (ImageView)findViewById(R.id.btn_qq_login);
	}

	private class UserInterface {
		public ImageView btn_weixin_login;
		public ImageView btn_qq_login;
	}
}
