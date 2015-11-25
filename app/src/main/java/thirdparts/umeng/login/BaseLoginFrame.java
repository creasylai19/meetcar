package thirdparts.umeng.login;

import android.app.Activity;

import com.creasylai.meetcar.consts.AppConst;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class BaseLoginFrame {

	public static UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
	
	public static void initWeChatLogin(Activity activity) {
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, AppConst.QQ_APP_ID, AppConst.QQ_APP_KEY);
		qqSsoHandler.isShareAfterAuthorize();
		qqSsoHandler.addToSocialSDK();
		UMWXHandler wxHandler = new UMWXHandler(activity, AppConst.WECHAT_APP_ID, AppConst.WECHAT_SECRET_ID);
		wxHandler.setRefreshTokenAvailable(false);//每次进行微信登陆都获取最新user_token（需要重新授权）
		wxHandler.addToSocialSDK();
	}
}
