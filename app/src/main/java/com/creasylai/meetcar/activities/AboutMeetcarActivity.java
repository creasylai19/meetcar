package com.creasylai.meetcar.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.common.WebviewActivity;
import com.creasylai.meetcar.consts.AppConst;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

public class AboutMeetcarActivity extends BaseActivity implements View.OnClickListener {

	private UserInterface mUserInterface;

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_about_meetcar);
		mUserInterface = new UserInterface();
		findViews(mUserInterface);
		mUserInterface.rl_check_update.setOnClickListener(this);
		mUserInterface.rl_user_protocol.setOnClickListener(this);
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
			case R.id.rl_check_update:
				checkUpdate();
				break;
			case R.id.rl_user_protocol:
				goToUserProtocolPage();
				break;
			default:
				break;
		}
	}

	private void checkUpdate() {
		UmengUpdateAgent.setDefault();
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
				switch (updateStatus) {
					case UpdateStatus.Yes: // has update
						UmengUpdateAgent.showUpdateDialog(AboutMeetcarActivity.this, updateInfo);
						break;
					case UpdateStatus.No: // has no update
						Toast.makeText(AboutMeetcarActivity.this, "没有更新", Toast.LENGTH_SHORT).show();
						break;
					case UpdateStatus.NoneWifi: // none wifi
						Toast.makeText(AboutMeetcarActivity.this, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
						break;
					case UpdateStatus.Timeout: // time out
						Toast.makeText(AboutMeetcarActivity.this, "超时", Toast.LENGTH_SHORT).show();
						break;
				}
			}
		});
		UmengUpdateAgent.update(this);
	}

	private void goToUserProtocolPage() {
		WebviewActivity.startActivity(this, AppConst.INTERFACE_URLS.WEBSITE, getString(R.string.user_protocol));
	}

	private void findViews(UserInterface mUserInterface) {
		mUserInterface.rl_check_update = (RelativeLayout) findViewById(R.id.rl_check_update);
		mUserInterface.rl_user_protocol = (RelativeLayout) findViewById(R.id.rl_user_protocol);
	}

	private class UserInterface {
		public RelativeLayout rl_check_update;
		public RelativeLayout rl_user_protocol;
	}
}
