package com.creasylai.meetcar.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.umeng.analytics.MobclickAgent;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

	private UserInterface mUserInterface;

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_setting);
		mUserInterface = new UserInterface();
		findViews(mUserInterface);
		mUserInterface.rl_frequently_addr.setOnClickListener(this);
		mUserInterface.rl_chat_setting.setOnClickListener(this);
		mUserInterface.rl_user_feedback.setOnClickListener(this);
		mUserInterface.rl_about_meetcar.setOnClickListener(this);
		mUserInterface.rl_login_out.setOnClickListener(this);
	}

	@Override
	public void initData(Bundle savedInstanceState) {

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
			case R.id.rl_frequently_addr:
				break;
			case R.id.rl_chat_setting:
				break;
			case R.id.rl_user_feedback:
				break;
			case R.id.rl_about_meetcar:
				startActivity(this, AboutMeetcarActivity.class);
				break;
			case R.id.rl_login_out:
				break;
			default:
				break;
		}
	}

	private void findViews(UserInterface mUserInterface) {
		mUserInterface.rl_frequently_addr = (RelativeLayout) findViewById(R.id.rl_frequently_addr);
		mUserInterface.rl_chat_setting = (RelativeLayout) findViewById(R.id.rl_chat_setting);
		mUserInterface.rl_user_feedback = (RelativeLayout) findViewById(R.id.rl_user_feedback);
		mUserInterface.rl_about_meetcar = (RelativeLayout) findViewById(R.id.rl_about_meetcar);
		mUserInterface.rl_login_out = (RelativeLayout) findViewById(R.id.rl_login_out);
	}

	private class UserInterface {
		public RelativeLayout rl_frequently_addr;
		public RelativeLayout rl_chat_setting;
		public RelativeLayout rl_user_feedback;
		public RelativeLayout rl_about_meetcar;
		public RelativeLayout rl_login_out;
	}
}
