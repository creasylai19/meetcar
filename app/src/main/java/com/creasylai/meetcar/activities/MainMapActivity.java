package com.creasylai.meetcar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.common.ToastUtils;
import com.creasylai.meetcar.common.WebviewActivity;
import com.creasylai.meetcar.consts.AppConst;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.RecyclerViewCacheUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.umeng.update.UmengUpdateAgent;

import thirdparts.amap.OffLineMapUtils;
import thirdparts.umeng.share.BaseShareFrame;

public class MainMapActivity extends BaseActivity implements View.OnClickListener {

	private Drawer result = null;
	private AccountHeader headerResult = null;
	private UserInterface mUserInterface;
	private AMap aMap;

	@Override
	public void initView(Bundle savedInstanceState) {
		Intent intent = getIntent();//如果是登出，则结束本页面，启动登陆页
		if( null != intent && intent.getBooleanExtra(AppConst.STATIC_STRING_KEY.LOGIN_OUT, false) ) {
			startActivity(this, StartApplicationActivity.class);
			finish();
		}
		setContentView(R.layout.activity_main_map);
		mUserInterface = new UserInterface();
		findViews(mUserInterface);
//		UmengUpdateAgent.setDeltaUpdate(false);//false代表全量更新，默认true代表增量更新
//		UmengUpdateAgent.silentUpdate(this);//当用户进入应用首页后如果处于wifi环境检测更新，如果有更新，后台下载新版本，如果下载成功，则进行通知栏展示，用户点击通知栏开始安装
		UmengUpdateAgent.update(this);//友盟更新

	}

	@Override
	public void initData(Bundle savedInstanceState) {
		mUserInterface.mapView.onCreate(savedInstanceState);// 必须要写
		aMap = mUserInterface.mapView.getMap();
		MapsInitializer.sdcardDir = OffLineMapUtils.getSdCacheDir(this);
		ViewGroup menu_footview = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_main_map_menu_footview, null, false);
		mUserInterface.iv_menu_footview = (ImageView) menu_footview.findViewById(R.id.iv_menu_footview);
		mUserInterface.iv_menu_footview.setOnClickListener(this);
		final IProfile profile4 = new ProfileDrawerItem().withName("Felix House").withIcon(R.drawable.profile3).withIdentifier(103);

		headerResult = new AccountHeaderBuilder()
				               .withActivity(this)
				               .withSelectionListEnabled(false)
				               .withAccountHeader(R.layout.custom_material_drawer_compact_header)
				               .withTranslucentStatusBar(false)
				               .withHeightDp(144)
				               .addProfiles(profile4)
				               .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
					               @Override
					               public boolean onClick(View view, IProfile profile) {
						               startActivity(MainMapActivity.this, UserInfoActivity.class);
						               if( null != MainMapActivity.this.result ) {
							               MainMapActivity.this.result.closeDrawer();
						               }
						               return true;
					               }
				               })
				               .withSavedInstance(savedInstanceState)
				               .build();

		// Create a few sample profile
		result = new DrawerBuilder()
				         .withActivity(this)
						 .withCloseOnClick(true)
				         .withTranslucentStatusBar(false)
				         .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
				         .addDrawerItems(
						                        new PrimaryDrawerItem().withName("我的聊天").withIcon(android.R.drawable.ic_notification_clear_all)
								                        .withIconColorRes(R.color.md_black_transparent_54).withSelectable(false)
								                        .withTextColorRes(R.color.md_black_transparent_87).withIdentifier(AppConst.MENUITEM.MY_CHAT),
						                        new PrimaryDrawerItem().withName("推荐给好友").withIcon(android.R.drawable.ic_menu_call)
								                        .withIconColorRes(R.color.md_black_transparent_54).withSelectable(false)
								                        .withTextColorRes(R.color.md_black_transparent_87).withIdentifier(AppConst.MENUITEM.SHARE_TO_FRIEND),
						                        new PrimaryDrawerItem().withName("设置").withIcon(android.R.drawable.ic_menu_set_as)
								                        .withIconColorRes(R.color.md_black_transparent_54).withSelectable(false)
								                        .withTextColorRes(R.color.md_black_transparent_87).withIdentifier(AppConst.MENUITEM.SETTING)
				         )
				         .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
					         @Override
					         public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
						         switch (drawerItem.getIdentifier()) {
							         case AppConst.MENUITEM.MY_CHAT:
								         Toast.makeText(MainMapActivity.this, "My_Chat_Click", Toast.LENGTH_SHORT).show();
								         break;
							         case AppConst.MENUITEM.SHARE_TO_FRIEND:
								         openSharePannel();
								         break;
							         case AppConst.MENUITEM.SETTING:
								         startActivity(MainMapActivity.this, SettingActivity.class);
								         break;
						         }
						         return false;
					         }
				         })
						 .withInnerShadow(false)
				         .withStickyFooter(menu_footview)
				         .withStickyFooterShadow(false)
				         .withStickyFooterDivider(false)
				         .withSliderBackgroundColorRes(android.R.color.white)
				         .withSelectedItemByPosition(-1)
				         .withSavedInstance(savedInstanceState)
				         .build();
		//if you have many different types of DrawerItems you can magically pre-cache those items to get a better scroll performance
		//make sure to init the cache after the DrawerBuilder was created as this will first clear the cache to make sure no old elements are in
		RecyclerViewCacheUtil.getInstance().withCacheSize(2).init(result);

		//only set the active selection or active profile if we do not recreate the activity
		if (savedInstanceState == null) {
			//set the active profile
			headerResult.setActiveProfile(profile4);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ( BaseShareFrame.getShareBoardOpenOrClose() && keyCode == KeyEvent.KEYCODE_BACK ) {
			BaseShareFrame.mController.dismissShareBoard();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void openSharePannel() {
		BaseShareFrame.initShareWCSQ(this);
		try {
			// 设置分享内容
			BaseShareFrame.mController.setShareContent(getShareText());
			// 设置分享图片, 参数2为图片的url地址
			UMImage mUMImage = new UMImage(this, AppConst.SHARE_ICON);
			BaseShareFrame.mController.setShareMedia(mUMImage);
			// 设置分享到微信的内容, 图片类型
			UMImage mUMImgBitmap = new UMImage(this, AppConst.SHARE_ICON);
			//设置新浪微博分享内容
			SinaShareContent mSinaShareContent = new SinaShareContent();
			mSinaShareContent.setShareContent(getShareText());
			mSinaShareContent.setShareImage(mUMImage);
			BaseShareFrame.mController.setShareMedia(mSinaShareContent);
			//设置微信分享内容
			WeiXinShareContent weixinContent = new WeiXinShareContent(mUMImgBitmap);
			weixinContent.setShareContent(getShareText());
			weixinContent.setTargetUrl(getShareUrl());
			weixinContent.setTitle(getShareTitle());
			BaseShareFrame.mController.setShareMedia(weixinContent);
			// 设置朋友圈分享的内容
			CircleShareContent circleMedia = new CircleShareContent(mUMImgBitmap);
			circleMedia.setShareContent(getShareText());
			circleMedia.setTargetUrl(getShareUrl());
			circleMedia.setTitle(getShareTitle());
			BaseShareFrame.mController.setShareMedia(circleMedia);
			//设置QQ分享
			QQShareContent qqShareContent = new QQShareContent();
			qqShareContent.setShareContent(getShareText());
			qqShareContent.setTitle(getShareTitle());
			qqShareContent.setTargetUrl(getShareUrl());
			BaseShareFrame.mController.setShareMedia(qqShareContent);

			BaseShareFrame.mController.openShare(this, false);
		} catch (Exception e) {
			ToastUtils.toastShort(this, e.getMessage());
		}
	}

	private String getShareTitle() {
		//TODO
		return "标题";
	}

	private String getShareUrl() {
		//TODO
		return "http://www.ubercoder.com";
	}

	private String getShareText() {
		//TODO
		return "内容";
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/**使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = BaseShareFrame.mController.getConfig().getSsoHandler(requestCode) ;
		if(ssoHandler != null){
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_menu_footview:
				this.result.closeDrawer();
				WebviewActivity.startActivity(this, AppConst.INTERFACE_URLS.WEBSITE, getString(R.string.user_protocol));
				break;
		}
	}



	@Override
	public void onBackPressed() {
		//handle the back press :D close the drawer first and if the drawer is closed close the activity
		if (result != null && result.isDrawerOpen()) {
			result.closeDrawer();
		} else {
			super.onBackPressed();
		}
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		mUserInterface.mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		mUserInterface.mapView.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//add the values which need to be saved from the drawer to the bundle
		outState = result.saveInstanceState(outState);
		//add the values which need to be saved from the accountHeader to the bundle
		outState = headerResult.saveInstanceState(outState);
		mUserInterface.mapView.onSaveInstanceState(outState);//高德地图
		super.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mUserInterface.mapView.onDestroy();
		//MobclickAgent.onKillProcess(this) 如果我们使用Process.kill或者System.exit杀死进程时，需要调用此语句保存统计数据
	}

	private void findViews(UserInterface mUserInterface) {
		mUserInterface.mapView = (MapView) findViewById(R.id.map);
	}

	private class UserInterface {
		public ImageView iv_menu_footview;
		public MapView mapView;
	}
}
