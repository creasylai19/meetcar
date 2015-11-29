package com.creasylai.meetcar.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.bean.UserBean;
import com.creasylai.meetcar.consts.AppConst;
import com.creasylai.meetcar.consts.AppPreferenceCache;
import com.mikepenz.materialdrawer.view.BezelImageView;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

	private UserWidget mUserWidget;
	private UserBean userBean;
	private ArrayList<String> userAges = new ArrayList<>();
	private OptionsPickerView userAgePvOptions;
	private ArrayList<String> userSexs = new ArrayList<>();
	private OptionsPickerView userSexPvOptions;
	private HashMap<Integer, String> sexMap = new HashMap<>();

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_user_info);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		mUserWidget = new UserWidget();
		findWidget(mUserWidget);
		userBean = AppPreferenceCache.getInstance(this).getUserBean();
		userBean = UserBean.getTestBean();//TODO delete
//		mUserWidget.biv_user_portrait.
		mUserWidget.tv_user_nickname.setText(userBean.getNickname());
		sexMap.put(AppConst.SEX.MAN, "男");
		sexMap.put(AppConst.SEX.WOMAN, "女");
		sexMap.put(AppConst.SEX.OTHER, "其它");
		mUserWidget.tv_user_sex.setText(sexMap.get(userBean.getSex()));
		mUserWidget.tv_user_age.setText(userBean.getBirthday());
		mUserWidget.tv_user_job.setText(userBean.getJob());
		mUserWidget.tv_user_signature.setText(userBean.getSignature());
		mUserWidget.rl_user_portrait.setOnClickListener(this);
		mUserWidget.rl_user_nickname.setOnClickListener(this);
		mUserWidget.rl_user_sex.setOnClickListener(this);
		mUserWidget.rl_user_age.setOnClickListener(this);
		mUserWidget.rl_user_job.setOnClickListener(this);
		mUserWidget.rl_user_signature.setOnClickListener(this);
		initUserAgeSelector();
		initUserSexSelector();
	}

	private void initUserSexSelector() {
		userSexPvOptions = new OptionsPickerView(this);

		//选项1

		userSexs.addAll(sexMap.values());

		userSexPvOptions.setPicker(userSexs);
		userSexPvOptions.setTitle("");
		userSexPvOptions.setCyclic(false);
		userSexPvOptions.setSelectOptions(0);
		//监听确定选择按钮
		userSexPvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

			@Override
			public void onOptionsSelect(int options1, int option2, int options3) {
				//返回的分别是三个级别的选中位置
				mUserWidget.tv_user_sex.setText(userSexs.get(options1));
				for (Map.Entry<Integer, String> entry: UserInfoActivity.this.sexMap.entrySet()){
					if(entry.getValue().equals(userSexs.get(options1))){
						userBean.setSex(entry.getKey());
						break;
					};
				}

			}
		});
	}

	private void initUserAgeSelector() {
		//选项选择器
		userAgePvOptions = new OptionsPickerView(this);
		//选项1
		userAges.add("60后");
		userAges.add("70后");
		userAges.add("80后");
		userAges.add("90后");
		userAges.add("00后");

		userAgePvOptions.setPicker(userAges);
		userAgePvOptions.setTitle("");
		userAgePvOptions.setCyclic(false);
		userAgePvOptions.setSelectOptions(2);
		//监听确定选择按钮
		userAgePvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

			@Override
			public void onOptionsSelect(int options1, int option2, int options3) {
				//返回的分别是三个级别的选中位置
				mUserWidget.tv_user_age.setText(userAges.get(options1));
				userBean.setBirthday(userAges.get(options1));
			}
		});
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
				//TODO 上传用户头像
				break;
			case R.id.rl_user_nickname:
				goToCommonInfoInputActivity(AppConst.STATIC_INT_KEY.COMMON_KEY_0);
				break;
			case R.id.rl_user_sex:
				userSexPvOptions.show();
				break;
			case R.id.rl_user_age:
				userAgePvOptions.show();
				break;
			case R.id.rl_user_job:
				goToCommonListActivity();
				break;
			case R.id.rl_user_signature:
				goToCommonInfoInputActivity(AppConst.STATIC_INT_KEY.COMMON_KEY_1);
				break;
			default:
				break;
		}
	}

	private void goToCommonListActivity() {
		CommonListSelectorActivity.startActivityForResult(this, AppConst.STATIC_INT_KEY.COMMON_KEY_2);
	}

	private void goToCommonInfoInputActivity(int requestCode) {
		CommonInfoInputActivity.startActivityForResult(this, requestCode);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case AppConst.STATIC_INT_KEY.COMMON_KEY_0://nickname
				if( resultCode == RESULT_OK && null != data) {
					mUserWidget.tv_user_nickname.setText(data.getStringExtra(AppConst.STATIC_STRING_KEY.COMMON_KEY));
					userBean.setNickname(data.getStringExtra(AppConst.STATIC_STRING_KEY.COMMON_KEY));
				}
				break;
			case AppConst.STATIC_INT_KEY.COMMON_KEY_1://signature
				if( resultCode == RESULT_OK && null != data) {
					mUserWidget.tv_user_signature.setText(data.getStringExtra(AppConst.STATIC_STRING_KEY.COMMON_KEY));
					userBean.setSignature(data.getStringExtra(AppConst.STATIC_STRING_KEY.COMMON_KEY));
				}
				break;
			case AppConst.STATIC_INT_KEY.COMMON_KEY_2://job
				if( resultCode == RESULT_OK && null != data) {
					mUserWidget.tv_user_job.setText(data.getStringExtra(AppConst.STATIC_STRING_KEY.COMMON_KEY));
					userBean.setJob(data.getStringExtra(AppConst.STATIC_STRING_KEY.COMMON_KEY));
				}
				break;
			default:
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBackPressed() {
		if(!UserBean.toJson(userBean).equals(UserBean.toJson(AppPreferenceCache.getInstance(this).getUserBean()))){
			AlertDialog mAlertDialog = new AlertDialog.Builder(this).setCancelable(true).setMessage(R.string.txt_is_save_data)
					                           .setNegativeButton(R.string.txt_not_save, new DialogInterface.OnClickListener() {
						                           @Override
						                           public void onClick(DialogInterface dialog, int which) {
							                           UserInfoActivity.super.onBackPressed();
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
		//TODO 提交数据并修改本地缓存，成功则finish本Activity
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
