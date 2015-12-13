package thirdparts.amap;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.creasylai.meetcar.singleinstance.MyLocation;

import java.util.ArrayList;

public class LocationService implements AMapLocationListener {

	public interface LocationListener {
		void onLocationChanged(AMapLocation location);
	}
	public AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;
	
	private static LocationService instance;

	public ArrayList<LocationListener> listenerList = new ArrayList<>();
	private Context context;

	private LocationService() {
	}
	
	public static LocationService getInstance() {
		if( instance == null ) {
			instance = new LocationService();
		}
		return instance;
	}

	private void initLocation(Context context) {
		locationClient = new AMapLocationClient(context);
		locationOption = new AMapLocationClientOption();
		// 设置定位模式为高精度模式
		locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		locationOption.setNeedAddress(true);
		locationOption.setInterval(2000);
		locationClient.setLocationOption(locationOption);
		// 设置定位监听
		locationClient.setLocationListener(this);
		
	}

	public void registerListener(LocationListener listener, Context context) {
		listenerList.add(listener);
		if (1 == listenerList.size()) {
			// 刚刚有新的listener加入的时候开启服务并更新线程
			this.context = context.getApplicationContext();
			startService(this.context);
		}
	}

	public void unregisterListener(LocationListener listener) {
		if ( listenerList.remove(listener) && 0 == listenerList.size()) {
			// 如果成功移除listener后导致没有任何listener监听位置变化，暂停定位服务
			stopService();
		}
	}

	public void onDestory() {
		listenerList.clear();
		stopService();
	}

	public void startService(Context context) {
		initLocation(context);//这行必须放在这边，否则用户退出应用后，再次进入时定位会启动不成功(也许资源已经释放，但还未来得及再实例化)
		// 设置是否需要显示地址信息
		locationClient.startLocation();
	}

	protected void stopService() {
		if (null != locationClient) {
			/**
			 * 如果AMapLocationClient是在当前Activity实例化的，
			 * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
			 */
			locationClient.stopLocation();
			locationClient.onDestroy();
			locationClient = null;
			locationOption = null;
		}
	}

	@Override
	public void onLocationChanged(AMapLocation arg0) {
		MyLocation.getInstance(this.context).setLocationData(arg0);
		if (listenerList.size() > 0) {
			for (int i = listenerList.size() - 1; i >= 0; i--) {
				(listenerList.get(i)).onLocationChanged(arg0);
			}
		}
	}

}
