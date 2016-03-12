package com.creasylai.meetcar.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.consts.AppConst;
import com.creasylai.meetcar.consts.AppPreferenceCache;
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
				startActivity(this, CommonUsingAddressActivity.class);
				break;
			case R.id.rl_chat_setting:
				startActivity(this, ChatSettingActivity.class);
				break;
			case R.id.rl_user_feedback:
//				RongIM.getInstance().startConversation(this, Conversation.ConversationType.APP_PUBLIC_SERVICE, "KEFU144889862239428", getString(R.string.app_name));
				/**
				 * 启动单聊
				 * context - 应用上下文。
				 * targetUserId - 要与之聊天的用户 Id。
				 * title - 聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
				 */
//				if (RongIM.getInstance() != null) {
//					RongIM.getInstance().startPrivateChat(SettingActivity.this, "2462", "hello");
//				}
				startActivity(this, FeedbackActivity.class);
				break;
			case R.id.rl_about_meetcar:
				startActivity(this, AboutMeetcarActivity.class);
				break;
			case R.id.rl_login_out:
				showLogoutAlertDialog();
				break;
			default:
				break;
		}
	}

	private void showLogoutAlertDialog() {
		AlertDialog mAlertDialog = new AlertDialog.Builder(this).setCancelable(true).setMessage(R.string.txt_dialog_reminder_logout)
				.setNegativeButton(R.string.btn_cancel, null).setPositiveButton(R.string.btn_confirm,new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						login_out();
					}
				}).setTitle(R.string.txt_dialog_tip).create();
		mAlertDialog.show();
	}


	private void login_out() {
		Toast.makeText(SettingActivity.this, "登出成功", Toast.LENGTH_SHORT).show();
		AppPreferenceCache.getInstance(SettingActivity.this).clearAllCache();
		Intent intent = new Intent(SettingActivity.this, MainMapActivity.class);
		intent.putExtra(AppConst.STATIC_STRING_KEY.LOGIN_OUT, true);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();
//		BaseLoginFrame.mController.deleteOauth(this, SHARE_MEDIA.SINA,
//              new SocializeListeners.SocializeClientListener() {
//                  @Override
//                  public void onStart() {
//                  }
//
//                  @Override
//                  public void onComplete(int status, SocializeEntity entity) {
//                      if (status == 200) {
//
//                      } else {
//                          Toast.makeText(SettingActivity.this, "登出失败", Toast.LENGTH_SHORT).show();
//                      }
//                  }
//              });
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
