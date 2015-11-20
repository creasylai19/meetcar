package com.creasylai.meetcar.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.consts.AppConst;
import com.umeng.analytics.MobclickAgent;

public class WebviewActivity extends BaseActivity {

	private WebView mWebView;

	public static void startActivity(Context context, String url, String title) {
		Intent intent = new Intent(context, WebviewActivity.class);
		intent.putExtra("url", url);
		intent.putExtra("title", title);
		context.startActivity(intent);
	}

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.common_layout_webview);
		try {
			String title = getIntent().getStringExtra("title");
			if (TextUtils.isEmpty(title)) title = getString(R.string.app_name);
			//TODO set ActionBar with title
		} catch (Exception e) {
			ToastUtils.toastShort(this, getString(R.string.err_unknown));
			super.onBackPressed();
		}
		mWebView = (WebView) findViewById(R.id.webView);
		// 在自身的浏览器中打开网页而不是到其他浏览器中打开
		mWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		// 设置下载事件
		mWebView.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent,
			                            String contentDisposition, String mimetype,
			                            long contentLength) {
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(intent);
			}
		});
		mWebView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) { // 表示按返回键时的操作
						mWebView.goBack(); // 后退
						return true; // 已处理
					}
				}
				return false;
			}
		});
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		try {
			String mStrURL = getIntent().getStringExtra("url");
			if (TextUtils.isEmpty(mStrURL)) {
				mStrURL = AppConst.INTERFACE_URLS.WEBSITE;
			} else {
				if(mStrURL.startsWith("www")) {
					mStrURL = "http://" + mStrURL;
				}
				openULR(mStrURL);
			}
		} catch (Exception e) {
			ToastUtils.toastShort(this, getString(R.string.err_unknown));
			super.onBackPressed();
		}
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void openULR(String strURI) {
		mWebView.getSettings().setJavaScriptEnabled(true);
		if (URLUtil.isNetworkUrl(strURI)) {
			mWebView.loadUrl(strURI);
		} else {
			ToastUtils.toastShort(this, R.string.url_err);
			finish();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		// 统计页面
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 统计页面
		MobclickAgent.onPause(this);
	}

}
