package com.creasylai.meetcar.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.creasylai.meetcar.BaseActivity;
import com.creasylai.meetcar.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.util.RecyclerViewCacheUtil;

public class MainMapActivity extends BaseActivity {

	private Drawer result = null;
	private AccountHeader headerResult = null;


	@Override
	public void initView(Bundle savedInstanceState) {
		setContentView(R.layout.activity_main_map);
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		final IProfile profile4 = new ProfileDrawerItem().withName("Felix House").withIcon(R.mipmap.profile3).withIdentifier(103);

		headerResult = new AccountHeaderBuilder()
				               .withActivity(this)
				               .withSelectionListEnabled(false)
				               .withAccountHeader(R.layout.custom_material_drawer_compact_header)
				               .withTranslucentStatusBar(false)
				               .withHeightDp(144)
				               .addProfiles(profile4)
				               .withSavedInstance(savedInstanceState)
				               .build();

		// Create a few sample profile
		result = new DrawerBuilder()
				         .withActivity(this)
				         .withTranslucentStatusBar(false)
				         .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
				         .addDrawerItems(
						                        new PrimaryDrawerItem().withName("我的聊天").withIcon(android.R.drawable.ic_notification_clear_all).withIconColorRes(R.color.md_black_transparent_54).withSelectable(false).withTextColorRes(R.color.md_black_transparent_87),
						                        new PrimaryDrawerItem().withName("推荐给好友").withIcon(android.R.drawable.ic_secure).withIconColorRes(R.color.md_black_transparent_54).withSelectable(false).withTextColorRes(R.color.md_black_transparent_87),
						                        new SecondaryDrawerItem().withName("设置").withIcon(android.R.drawable.ic_menu_set_as).withIconColorRes(R.color.md_black_transparent_54).withSelectable(false).withTextColorRes(R.color.md_black_transparent_87)
				         )
				         .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
					         @Override
					         public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
						         if (drawerItem instanceof Nameable) {
							         Toast.makeText(MainMapActivity.this, ((Nameable) drawerItem).getName().getText(MainMapActivity.this), Toast.LENGTH_SHORT).show();
						         }
						         return false;
					         }
				         })

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
	protected void onSaveInstanceState(Bundle outState) {
		//add the values which need to be saved from the drawer to the bundle
		outState = result.saveInstanceState(outState);
		//add the values which need to be saved from the accountHeader to the bundle
		outState = headerResult.saveInstanceState(outState);
		super.onSaveInstanceState(outState);
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
}
