package com.creasylai.meetcar.activities;

import android.view.View;
import android.widget.Button;

import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;

public class StartApplicationActivity extends BaseActivity implements View.OnClickListener{

	private UserInterface mUserInterface;

	@Override
	public void initView() {
		setContentView(R.layout.activity_start_application);
		mUserInterface = new UserInterface();
		findViews(mUserInterface);
		mUserInterface.btn_qq_login.setOnClickListener(this);
		mUserInterface.btn_weixin_login.setOnClickListener(this);
	}

	@Override
	public void initData() {

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
		startActivity(this, MainMapActivity.class);
		this.finish();
	}

	private void qq_login() {
		startActivity(this, MainMapActivity.class);
		this.finish();

	}

	private void findViews( UserInterface mUserInterface ) {
		mUserInterface.btn_weixin_login = (Button)findViewById(R.id.btn_weixin_login);
		mUserInterface.btn_qq_login = (Button)findViewById(R.id.btn_qq_login);
	}

	private class UserInterface {
		public Button btn_weixin_login;
		public Button btn_qq_login;
	}
}
