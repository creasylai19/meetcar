package com.creasylai.meetcar.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.singleinstance.VolleySingleInstance;
import com.creasylai.meetcar.utils.SystemUtil;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

public class VolleyExampleActivity extends BaseActivity {

	private UserWidget mUserWidget;
	private String string_url = "http://api.map.baidu.com/place/v2/suggestion?query=%E5%A4%A9%E5%AE%89%E9%97%A8&region=131&output=json&ak=02b7266521b84609fde0c9384d1314a6";
	String image_url = "http://img3.imgtn.bdimg.com/it/u=3841157212,2135341815&fm=116&gp=0.jpg";

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_user_info);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		mUserWidget = new UserWidget();
		findWidget(mUserWidget);
		mUserWidget.btn_getstring.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mUserWidget.tv_string_show.setText(SystemUtil.getDeviceInfo(VolleyExampleActivity.this));
				JsonObjectRequest getString = new JsonObjectRequest(string_url, null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						dismissProgress();
						mUserWidget.tv_string_show.setText(response.toString());
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						mUserWidget.tv_string_show.setText(error.toString());
						dismissProgress();
					}
				});
				showProgress();
				VolleySingleInstance.getInstance(VolleyExampleActivity.this).addToRequestQueue(getString);
			}
		});
		// Retrieves an image specified by the URL, displays it in the UI.
		ImageRequest request = new ImageRequest(image_url,
				                                       new Response.Listener<Bitmap>() {
					                                       @Override
					                                       public void onResponse(Bitmap bitmap) {
						                                       mUserWidget.iv_image_show.setImageBitmap(bitmap);
					                                       }
				                                       }, 0, 0, ImageView.ScaleType.CENTER_INSIDE, null,
				                                       new Response.ErrorListener() {
					                                       public void onErrorResponse(VolleyError error) {
						                                       mUserWidget.iv_image_show.setImageResource(R.drawable.ic_launcher);
					                                       }
				                                       });
		VolleySingleInstance.getInstance(this).addToRequestQueue(request);
	}

	public void findWidget(UserWidget mUserWidget) {
		mUserWidget.btn_getstring = (Button) findViewById(R.id.btn_getstring);
		mUserWidget.tv_string_show = (TextView) findViewById(R.id.tv_string_show);
		mUserWidget.iv_image_show = (ImageView) findViewById(R.id.iv_image_show);
	}

	private class UserWidget {
		public Button btn_getstring;
		public TextView tv_string_show;
		public ImageView iv_image_show;
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
