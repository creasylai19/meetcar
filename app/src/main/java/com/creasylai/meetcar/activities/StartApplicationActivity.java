package com.creasylai.meetcar.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.common.ToastUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;

import java.util.Map;
import java.util.Set;

import thirdparts.umeng.login.BaseLoginFrame;

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
		// 添加微信平台
//		BaseLoginFrame.initWeChatLogin(this);
//		BaseLoginFrame.mController.doOauthVerify(StartApplicationActivity.this, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMAuthListener() {
//			@Override
//			public void onStart(SHARE_MEDIA platform) {
//				Toast.makeText(StartApplicationActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onError(SocializeException e, SHARE_MEDIA platform) {
//				Toast.makeText(StartApplicationActivity.this, "授权错误", Toast.LENGTH_SHORT).show();
//			}
//
//			@Override
//			public void onComplete(Bundle value, SHARE_MEDIA platform) {
//				Toast.makeText(StartApplicationActivity.this, "授权完成", Toast.LENGTH_SHORT).show();
//				//获取相关授权信息
//				BaseLoginFrame.mController.getPlatformInfo(StartApplicationActivity.this, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMDataListener() {
//					@Override
//					public void onStart() {
//						Toast.makeText(StartApplicationActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
//					}
//
//					@Override
//					public void onComplete(int status, Map<String, Object> info) {
//						if (status == 200 && info != null) {
//							StringBuilder sb = new StringBuilder();
//							Set<String> keys = info.keySet();
//							for (String key : keys) {
//								sb.append(key + "=" + info.get(key).toString() + "\r\n");
//							}
//							Log.d("TestData", sb.toString());
//						} else {
//							Log.d("TestData", "发生错误：" + status);
//						}
//					}
//				});
//			}
//
//			@Override
//			public void onCancel(SHARE_MEDIA platform) {
//				Toast.makeText(StartApplicationActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
//			}
//		} );
		ToastUtils.toastShort(this, R.string.login_using_qq);
	}

	private void qq_login() {
		BaseLoginFrame.initWeChatLogin(this);
		BaseLoginFrame.mController.doOauthVerify(StartApplicationActivity.this, SHARE_MEDIA.QQ, new SocializeListeners.UMAuthListener() {
			@Override
			public void onStart(SHARE_MEDIA platform) {
				Toast.makeText(StartApplicationActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(SocializeException e, SHARE_MEDIA platform) {
				Toast.makeText(StartApplicationActivity.this, "授权错误", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onComplete(Bundle value, SHARE_MEDIA platform) {
				Toast.makeText(StartApplicationActivity.this, "授权完成", Toast.LENGTH_SHORT).show();
				//获取相关授权信息
				BaseLoginFrame.mController.getPlatformInfo(StartApplicationActivity.this, SHARE_MEDIA.QQ, new SocializeListeners.UMDataListener() {
					@Override
					public void onStart() {
						Toast.makeText(StartApplicationActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(int status, Map<String, Object> info) {
						if (status == 200 && info != null) {
							StringBuilder sb = new StringBuilder();
							Set<String> keys = info.keySet();
							for (String key : keys) {
								sb.append(key + "=" + info.get(key).toString() + "\r\n");
							}
							Log.d("TestData", sb.toString());
							startActivity(StartApplicationActivity.this, MainMapActivity.class);
							finish();
						} else {
							Log.d("TestData", "发生错误：" + status);
						}
					}
				});
			}

			@Override
			public void onCancel(SHARE_MEDIA platform) {
				Toast.makeText(StartApplicationActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
			}
		});
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
