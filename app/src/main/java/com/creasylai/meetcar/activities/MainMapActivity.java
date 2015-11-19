package com.creasylai.meetcar.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
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

import thirdpart.amap.OffLineMapUtils;

public class MainMapActivity extends BaseActivity implements View.OnClickListener {

	private Drawer result = null;
	private AccountHeader headerResult = null;
	private UserInterface mUserInterface;
	private AMap aMap;

	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main_map);
		mUserInterface = new UserInterface();
		findViews(mUserInterface);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		mUserInterface.mapView.onCreate(savedInstanceState);// 必须要写
		aMap = mUserInterface.mapView.getMap();
		MapsInitializer.sdcardDir = OffLineMapUtils.getSdCacheDir(this);
		ViewGroup menu_footview = (ViewGroup) getLayoutInflater().inflate(R.layout.activity_main_map_menu_footview, null, false);
		mUserInterface.iv_menu_footview = (ImageView) menu_footview.findViewById(R.id.iv_menu_footview);
		mUserInterface.iv_menu_footview.setOnClickListener(this);
		final IProfile profile4 = new ProfileDrawerItem().withName("Felix House").withIcon(R.mipmap.profile3).withIdentifier(103);

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
								                        .withTextColorRes(R.color.md_black_transparent_87).withIdentifier(AppConst.MenuItem.MY_CHAT),
						                        new PrimaryDrawerItem().withName("推荐给好友").withIcon(android.R.drawable.ic_menu_call)
								                        .withIconColorRes(R.color.md_black_transparent_54).withSelectable(false)
								                        .withTextColorRes(R.color.md_black_transparent_87).withIdentifier(AppConst.MenuItem.SHARE_TO_FRIEND),
						                        new PrimaryDrawerItem().withName("设置").withIcon(android.R.drawable.ic_menu_set_as)
								                        .withIconColorRes(R.color.md_black_transparent_54).withSelectable(false)
								                        .withTextColorRes(R.color.md_black_transparent_87).withIdentifier(AppConst.MenuItem.SETTING)
				         )
				         .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
					         @Override
					         public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
						         switch (drawerItem.getIdentifier()) {
							         case AppConst.MenuItem.MY_CHAT:
								         Toast.makeText(MainMapActivity.this, "My_Chat_Click", Toast.LENGTH_SHORT).show();
								         break;
							         case AppConst.MenuItem.SHARE_TO_FRIEND:
								         Toast.makeText(MainMapActivity.this, "Share_To_Friend_Click", Toast.LENGTH_SHORT).show();
								         break;
							         case AppConst.MenuItem.SETTING:
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
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_menu_footview:
				Toast.makeText(MainMapActivity.this, "FootView_Click", Toast.LENGTH_SHORT).show();
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
		mUserInterface.mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
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
	}

	private void findViews(UserInterface mUserInterface) {
		mUserInterface.mapView = (MapView) findViewById(R.id.map);
	}

	private class UserInterface {
		public ImageView iv_menu_footview;
		public MapView mapView;
	}
}
