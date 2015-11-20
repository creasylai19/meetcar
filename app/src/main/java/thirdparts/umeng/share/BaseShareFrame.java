package thirdparts.umeng.share;

import android.app.Activity;

import com.creasylai.meetcar.consts.AppConst;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMShareBoardListener;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class BaseShareFrame {

	public static final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	private static boolean isShareBoardOpen = false;

	public static void initShareWCSQ(Activity mActivity) {
		mController.getConfig().removePlatform(SHARE_MEDIA.SINA, SHARE_MEDIA.DOUBAN, SHARE_MEDIA.QZONE, SHARE_MEDIA.EMAIL, SHARE_MEDIA.RENREN, SHARE_MEDIA.SMS, SHARE_MEDIA.TENCENT);
		mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ);
		// 设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		mController.getConfig().addFollow(SHARE_MEDIA.SINA, AppConst.SINA);
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(mActivity, AppConst.WECHAT_APP_ID);
		wxHandler.addToSocialSDK();
		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(mActivity, AppConst.WECHAT_APP_ID);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		// 参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mActivity, AppConst.QQ_APP_ID, AppConst.QQ_APP_KEY);
		qqSsoHandler.addToSocialSDK();
		mController.getConfig().setPlatformOrder(SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ);
		mController.setShareBoardListener(new UMShareBoardListener() {
			@Override
			public void onShow() {
				isShareBoardOpen = true;
			}
			@Override
			public void onDismiss() {
				isShareBoardOpen = false;
			}
		});
	}
	
	public static boolean getShareBoardOpenOrClose() {
		return isShareBoardOpen;
	}
}
