package com.creasylai.meetcar.activities;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.umeng.analytics.MobclickAgent;

public class CommonUsingAddressActivity extends BaseActivity {

	private UserWidget mUserWidget;

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_common_using_address);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		mUserWidget = new UserWidget();
		findWidget(mUserWidget);
	}

	public void findWidget(UserWidget mUserWidget) {
		mUserWidget.rl_home_addr = (RelativeLayout) findViewById(R.id.rl_home_addr);
		mUserWidget.tv_home_addr_name = (TextView) findViewById(R.id.tv_home_addr_name);
		mUserWidget.tv_home_addr_detail = (TextView) findViewById(R.id.tv_home_addr_detail);
		mUserWidget.rl_company_addr = (RelativeLayout) findViewById(R.id.rl_company_addr);
		mUserWidget.tv_company_addr_name = (TextView) findViewById(R.id.tv_company_addr_name);
		mUserWidget.tv_company_addr_detail = (TextView) findViewById(R.id.tv_company_addr_detail);
		mUserWidget.rl_common_using_addr = (RelativeLayout) findViewById(R.id.rl_common_using_addr);
		mUserWidget.tv_common_using_addr_name = (TextView) findViewById(R.id.tv_common_using_addr_name);
		mUserWidget.tv_common_using_addr_detail = (TextView) findViewById(R.id.tv_common_using_addr_detail);
	}

	private class UserWidget {
		public RelativeLayout rl_home_addr;
		public TextView tv_home_addr_name;
		public TextView tv_home_addr_detail;
		public RelativeLayout rl_company_addr;
		public TextView tv_company_addr_name;
		public TextView tv_company_addr_detail;
		public RelativeLayout rl_common_using_addr;
		public TextView tv_common_using_addr_name;
		public TextView tv_common_using_addr_detail;
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
}
