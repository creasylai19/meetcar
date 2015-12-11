package com.creasylai.meetcar.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.common.ToastUtils;
import com.creasylai.meetcar.consts.AppConst;
import com.creasylai.meetcar.consts.AppPreferenceCache;
import com.creasylai.meetcar.consts.InterfaceURLs;
import com.creasylai.meetcar.singleinstance.VolleySingleInstance;
import com.creasylai.meetcar.utils.ParameterConstructor;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;

import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

import thirdparts.umeng.login.BaseLoginFrame;

public class StartApplicationActivity extends BaseActivity implements View.OnClickListener{

	private UserInterface mUserInterface;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case AppConst.STATIC_INT_KEY.COMMON_KEY_0:
					goToMainPage();
					break;
				case AppConst.STATIC_INT_KEY.COMMON_KEY_1:
					goGuidePage();
					break;
			}
			super.handleMessage(msg);
		}
	};

	private void goToMainPage() {
		startActivity(this, MainMapActivity.class);
		finish();
	}

	private void goGuidePage() {

	}


	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_start_application);
		mUserInterface = new UserInterface();
		findViews(mUserInterface);
		if(AppPreferenceCache.getInstance(this).isLogin()) {
			mUserInterface.ll_login.setVisibility(View.GONE);
			handler.sendEmptyMessageDelayed(AppConst.STATIC_INT_KEY.COMMON_KEY_0, AppConst.STATIC_INT_KEY.GO_HOME_DELAY);
		} else {
			mUserInterface.btn_qq_login.setOnClickListener(this);
			mUserInterface.btn_weixin_login.setOnClickListener(this);
		}
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
//		BaseLoginFrame.initWeChatAndQQLogin(this);
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
//		oauthVerifySuccess(null);
	}

	private void qq_login() {
		BaseLoginFrame.initWeChatAndQQLogin(this);
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
				oauthVerifyRegistered(value.getString("openid"), AppConst.LOGIN_TYPE.QQ);
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
//							oauthVerifySuccess(null);
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

	private void oauthVerifyRegistered(String openid, int type) {
		JSONObject requestData = new JSONObject();
		ParameterConstructor.putParaInJson("openid", openid, requestData);
		ParameterConstructor.putParaInJson("type", type, requestData);
		JsonObjectRequest getString = new JsonObjectRequest(InterfaceURLs.IS_REGISTERED, ParameterConstructor.getRequestJson(requestData, this), new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				dismissProgress();
				if( ParameterConstructor.getResponStatus(response, StartApplicationActivity.this) != 200 ) {
					doRegister();
				} else {
					oauthVerifySuccess(ParameterConstructor.getResponData(response, StartApplicationActivity.this));
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				dismissProgress();
				ToastUtils.toastShort(StartApplicationActivity.this, R.string.err_unknown);
			}
		});
		showProgress();
		VolleySingleInstance.getInstance(StartApplicationActivity.this).addToRequestQueue(getString);
	}

	private void doRegister() {

	}

	private void oauthVerifySuccess(JSONObject jsonObject) {
		AppPreferenceCache.getInstance(this).setUserToken(jsonObject.optString("token"));
		AppPreferenceCache.getInstance(this).setUserId(jsonObject.optInt("uid"));
		goToMainPage();
	}

	private void findViews( UserInterface mUserInterface ) {
		mUserInterface.ll_login = (LinearLayout)findViewById(R.id.ll_login);
		mUserInterface.btn_weixin_login = (ImageView)findViewById(R.id.btn_weixin_login);
		mUserInterface.btn_qq_login = (ImageView)findViewById(R.id.btn_qq_login);
	}

	private class UserInterface {
		public LinearLayout ll_login;
		public ImageView btn_weixin_login;
		public ImageView btn_qq_login;
	}
}
