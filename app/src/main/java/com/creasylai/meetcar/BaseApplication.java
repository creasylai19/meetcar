package com.creasylai.meetcar;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;

import io.rong.imkit.RongIM;

/**
 * Created by laicreasy on 15/11/11.
 */
public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		DrawerImageLoader.init(new AbstractDrawerImageLoader() {
			@Override
			public void set(ImageView imageView, Uri uri, Drawable placeholder) {
//				Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
			}

			@Override
			public void cancel(ImageView imageView) {
//				Glide.clear(imageView);
			}

			@Override
			public Drawable placeholder(Context ctx, String tag) {
				//define different placeholders for different imageView targets
				//default tags are accessible via the DrawerImageLoader.Tags
				//custom ones can be checked via string. see the CustomUrlBasePrimaryDrawerItem LINE 111
				if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
					return DrawerUIUtils.getPlaceHolder(ctx);
				} else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
					return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary).sizeDp(56);
				} else if ("customUrlItem".equals(tag)) {
					return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_red_500).sizeDp(56);
				}

				//we use the default one for
				//DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name()

				return super.placeholder(ctx, tag);
			}
		});

		/**
		 * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
		 * io.rong.push 为融云 push 进程名称，不可修改。
		 */
		if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
				    "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
			/**
			 * IMKit SDK调用第一步 初始化
			 */
			RongIM.init(this);
		}
	}

	/**
	 * 获得当前进程的名字
	 *
	 * @param context
	 * @return 进程号
	 */
	public static String getCurProcessName(Context context) {
		int pid = android.os.Process.myPid();
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return null;
	}
}
