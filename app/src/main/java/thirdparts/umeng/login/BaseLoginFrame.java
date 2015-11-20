package thirdparts.umeng.login;

import android.content.Context;

import com.creasylai.meetcar.consts.AppConst;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.weixin.controller.UMWXHandler;

public class BaseLoginFrame {

	public static UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.login");
	
	public static void initWeChatLogin(Context mContext) {
		UMWXHandler wxHandler = new UMWXHandler(mContext, AppConst.WECHAT_APP_ID, AppConst.WECHAT_SECRET_ID);
		wxHandler.setRefreshTokenAvailable(false);//每次进行微信登陆都获取最新user_token（需要重新授权）
		wxHandler.addToSocialSDK();
	}
}
