package com.creasylai.meetcar.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.bean.UserBean;
import com.creasylai.meetcar.consts.AppConst;
import com.creasylai.meetcar.consts.AppPreferenceCache;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.umeng.analytics.MobclickAgent;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

	private UserWidget mUserWidget;
	private UserBean userBean;

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_user_info);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		mUserWidget = new UserWidget();
		findWidget(mUserWidget);
		userBean = AppPreferenceCache.getInstance(this).getUserBean();
//		mUserWidget.biv_user_portrait.
		mUserWidget.tv_user_nickname.setText(userBean.getNickname());
		mUserWidget.tv_user_sex.setText(userBean.getSex() == AppConst.SEX.MAN ? "男" : (userBean.getSex() == AppConst.SEX.WOMAN ? "女" : "未设置"));
		mUserWidget.tv_user_age.setText(userBean.getBirthday());
		mUserWidget.tv_user_job.setText(userBean.getJob());
		mUserWidget.tv_user_signature.setText(userBean.getSignature());
		mUserWidget.rl_user_portrait.setOnClickListener(this);
		mUserWidget.rl_user_nickname.setOnClickListener(this);
		mUserWidget.rl_user_sex.setOnClickListener(this);
		mUserWidget.rl_user_age.setOnClickListener(this);
		mUserWidget.rl_user_job.setOnClickListener(this);
		mUserWidget.rl_user_signature.setOnClickListener(this);
	}

	public void findWidget(UserWidget mUserWidget) {
		mUserWidget.rl_user_portrait = (RelativeLayout) findViewById(R.id.rl_user_portrait);
		mUserWidget.biv_user_portrait = (BezelImageView) findViewById(R.id.biv_user_portrait);
		mUserWidget.rl_user_nickname = (RelativeLayout) findViewById(R.id.rl_user_nickname);
		mUserWidget.tv_user_nickname = (TextView) findViewById(R.id.tv_user_nickname);
		mUserWidget.rl_user_sex = (RelativeLayout) findViewById(R.id.rl_user_sex);
		mUserWidget.tv_user_sex = (TextView) findViewById(R.id.tv_user_sex);
		mUserWidget.rl_user_age = (RelativeLayout) findViewById(R.id.rl_user_age);
		mUserWidget.tv_user_age = (TextView) findViewById(R.id.tv_user_age);
		mUserWidget.rl_user_job = (RelativeLayout) findViewById(R.id.rl_user_job);
		mUserWidget.tv_user_job = (TextView) findViewById(R.id.tv_user_job);
		mUserWidget.rl_user_signature = (RelativeLayout) findViewById(R.id.rl_user_signature);
		mUserWidget.tv_user_signature = (TextView) findViewById(R.id.tv_user_signature);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.rl_user_portrait:
				break;
			case R.id.rl_user_nickname:
				break;
			case R.id.rl_user_sex:
				break;
			case R.id.rl_user_age:
				break;
			case R.id.rl_user_job:
				break;
			case R.id.rl_user_signature:
				break;
			default:
				break;
		}
	}

	private class UserWidget {
		public RelativeLayout rl_user_portrait;
		public BezelImageView biv_user_portrait;
		public RelativeLayout rl_user_nickname;
		public TextView tv_user_nickname;
		public RelativeLayout rl_user_sex;
		public TextView tv_user_sex;
		public RelativeLayout rl_user_age;
		public TextView tv_user_age;
		public RelativeLayout rl_user_job;
		public TextView tv_user_job;
		public RelativeLayout rl_user_signature;
		public TextView tv_user_signature;
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
