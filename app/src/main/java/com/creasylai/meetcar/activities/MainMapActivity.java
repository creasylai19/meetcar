package com.creasylai.meetcar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.bean.UserBean;
import com.creasylai.meetcar.common.ToastUtils;
import com.creasylai.meetcar.common.WebviewActivity;
import com.creasylai.meetcar.consts.AppConst;
import com.creasylai.meetcar.consts.InterfaceURLs;
import com.creasylai.meetcar.customviews.SearchViewBar;
import com.creasylai.meetcar.singleinstance.MyLocation;
import com.creasylai.meetcar.singleinstance.VolleySingleInstance;
import com.creasylai.meetcar.utils.ParameterConstructor;
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

import org.json.JSONArray;
import org.json.JSONObject;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import thirdparts.amap.LocationHelper;
import thirdparts.amap.MapUtils;
import thirdparts.amap.MyLocationOverlay;
import thirdparts.amap.OffLineMapUtils;
import thirdparts.amap.OnCameraPositionChanged;
import thirdparts.amap.OnMapLoadFinished;
import thirdparts.amap.UserBeanOverlay;
import thirdparts.umeng.share.BaseShareFrame;

public class MainMapActivity extends BaseActivity implements View.OnClickListener, LocationHelper.LocationListener {

	private Drawer result = null;
	private AccountHeader headerResult = null;
	private UserInterface mUserInterface;
	private AMap aMap;
	private MyLocationOverlay myLocationOverlay;
	private UserBeanOverlay userBeanOverlay;
	private LatLng lastMapCenter;
	private boolean moveMapByUser = true;

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
		mUserInterface.svb_view.setOnClickListener(this);
//		UmengUpdateAgent.setDeltaUpdate(false);//false代表全量更新，默认true代表增量更新
//		UmengUpdateAgent.silentUpdate(this);//当用户进入应用首页后如果处于wifi环境检测更新，如果有更新，后台下载新版本，如果下载成功，则进行通知栏展示，用户点击通知栏开始安装
		UmengUpdateAgent.update(this);//友盟更新
		initRongIM();
	}

	private void initRongIM() {
		String Token = "J0Xkg5vlVi1R8yZZ9hzSuSNcYy6GDww/zllT84U4sQ3E3gSCHv3//uZ0Vc2T8Z40MRdfEF86Dc63CoApZx4DRw==";
		/**
		 * IMKit SDK调用第二步
		 *
		 * 建立与服务器的连接
		 *
		 */
		RongIM.connect(Token, new RongIMClient.ConnectCallback() {
			@Override
			public void onTokenIncorrect() {

			}

			@Override
			public void onSuccess(String userId) {
				Log.e("MainActivity", "——onSuccess— -" + userId);
				ToastUtils.toastShort(MainMapActivity.this, "RongIM Connect Success");
			}

			@Override
			public void onError(RongIMClient.ErrorCode errorCode) {
				Log.e("MainActivity", "——onError— -" + errorCode);
			}
		});
	}

	@Override
	public void initData(Bundle savedInstanceState) {
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
								         goToChatPage();
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
		initMapView(savedInstanceState);
	}

	private void initMapView(Bundle savedInstanceState) {
		mUserInterface.mapView.onCreate(savedInstanceState);// 必须要写
		aMap = mUserInterface.mapView.getMap();
		aMap.getUiSettings().setZoomControlsEnabled(false);
//		aMap.getUiSettings().set
		MapsInitializer.sdcardDir = OffLineMapUtils.getSdCacheDir(this);
		aMap.setOnMapLoadedListener(new OnMapLoadFinished(aMap, null, new OnMapLoadFinished.CustomOnMapLoadedListener() {
			@Override
			public void onMapLoaded(LatLng mLatLng) {
				MainMapActivity.this.lastMapCenter = MapUtils.getMapCenter(MainMapActivity.this.aMap);
			}
		}));
		myLocationOverlay = new MyLocationOverlay(this, aMap);
		userBeanOverlay = new UserBeanOverlay(this, aMap, new UserBeanOverlay.CustomOnMarkerClickListener() {
			@Override
			public void onMarkerClick(UserBean userBean) {
				MapUtils.animateTo(MainMapActivity.this.aMap, new LatLng(userBean.getLatitude(), userBean.getLongitude()));
				moveMapByUser = false;
			}
		});
		aMap.setOnCameraChangeListener(new OnCameraPositionChanged(new OnCameraPositionChanged.CustomOnCameraPositionChangeListener() {
			@Override
			public void onCameraPositionChangeFinish(CameraPosition cameraPosition) {
				if( !moveMapByUser ) {
					moveMapByUser = true;
				} else {
					if (MapUtils.getDistance(MainMapActivity.this.lastMapCenter, cameraPosition.target) > AppConst.STATIC_INT_KEY.REQUEST_USE_DISTANCE) {
						requestAroundUser(cameraPosition.target, null);
					}
				}
			}
		}));
	}

	private void requestAroundUser(LatLng src, LatLng dest) {
		lastMapCenter = MapUtils.getMapCenter(aMap);
		JSONObject srcJson = new JSONObject();
		if( null != dest ) {
			ParameterConstructor.putParaInJson("latitude", src.latitude, srcJson);
			ParameterConstructor.putParaInJson("longitude", src.longitude, srcJson);
		}
		JSONObject destJson = new JSONObject();
		if( null != dest ) {
			ParameterConstructor.putParaInJson("latitude", dest.latitude, destJson);
			ParameterConstructor.putParaInJson("longitude", dest.longitude, destJson);
		}
		JSONObject requestData = new JSONObject();
		ParameterConstructor.putParaInJson("src", srcJson, requestData);
		ParameterConstructor.putParaInJson("dest", destJson, requestData);
		JsonObjectRequest getNearUsers = new JsonObjectRequest(InterfaceURLs.GET_NEARBY_USERS, ParameterConstructor.getRequestJson(requestData, this), new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				responseAroundUser(ParameterConstructor.getResponDataWithErrorToast(response, MainMapActivity.this));
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				ToastUtils.toastShort(MainMapActivity.this, R.string.err_unknown);
			}
		});
		VolleySingleInstance.getInstance(this).addToRequestQueue(getNearUsers);
	}

	private void responseAroundUser(JSONObject responDataWithErrorToast) {
		JSONArray userBeans = responDataWithErrorToast.optJSONArray("items");
		userBeanOverlay.addAllUserBeans(UserBean.getUserBeans(userBeans.toString()));
		for (int i = 0; i < 10; i++) {
			userBeanOverlay.addUserBean(UserBean.getTestBean());
		}
		userBeanOverlay.addMarkersToMap();
	}

	private void goToChatPage() {
		
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
			case R.id.svb_view:
				break;
			case R.id.btn_start_chat:
				break;
			case R.id.rl_my_location:
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
		LocationHelper.getInstance().registerListener(this, this);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		mUserInterface.mapView.onPause();
		LocationHelper.getInstance().unregisterListener(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mUserInterface.mapView.onSaveInstanceState(outState);//高德地图
		//add the values which need to be saved from the drawer to the bundle
		outState = result.saveInstanceState(outState);
		//add the values which need to be saved from the accountHeader to the bundle
		outState = headerResult.saveInstanceState(outState);
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
		mUserInterface.svb_view = (SearchViewBar) findViewById(R.id.svb_view);
		mUserInterface.ll_buttom_control = (LinearLayout) findViewById(R.id.ll_buttom_control);
		mUserInterface.btn_start_chat = (Button) findViewById(R.id.btn_start_chat);
		mUserInterface.rl_my_location = (RelativeLayout) findViewById(R.id.rl_my_location);
		mUserInterface.tv_my_location = (TextView) findViewById(R.id.tv_my_location);
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		myLocationOverlay.setMyLocationData(location);
		if( !mUserInterface.ll_buttom_control.isShown() ) {
			mUserInterface.ll_buttom_control.setAnimation(AnimationUtils.makeInChildBottomAnimation(this));
			mUserInterface.ll_buttom_control.invalidate();
			mUserInterface.ll_buttom_control.setVisibility(View.VISIBLE);
			mUserInterface.btn_start_chat.setOnClickListener(this);
			mUserInterface.rl_my_location.setOnClickListener(this);
			mUserInterface.tv_my_location.setText(MyLocation.getInstance(this).getAddress());
		}
	}

	private class UserInterface {
		public ImageView iv_menu_footview;
		public MapView mapView;
		public SearchViewBar svb_view;
		public LinearLayout ll_buttom_control;
		public Button btn_start_chat;
		public RelativeLayout rl_my_location;
		public TextView tv_my_location;
	}
}
