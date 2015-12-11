package thirdparts.amap;

import android.content.Context;
import android.location.Location;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.creasylai.meetcar.common.AndroidHelper;

/**
 * Created by laicreasy on 15/12/12.
 */
public class MyLocationOverlay implements LocationSource {
	private Context context;
	private AMap aMap;
	private OnLocationChangedListener mListener;
	private double latitude = 0;
	private double longitude = 0;
	private float bearing = 0;


	public MyLocationOverlay(Context context, AMap aMap) {
		this.aMap = aMap;
		this.context = context;
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
	}

	public float getLocationDataDirection() {
		if (aMap.getMyLocation() != null) {
			return aMap.getMyLocation().getBearing();
		}
		return 0;
	}

	public void setMyLocationData(double latitude, double longitude, float direction) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.bearing = direction;
		setMyLocationData();
	}

	public void setMyLocationData(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		setMyLocationData();
	}

	public void setMyLocationData(float bearing) {
		this.bearing = bearing;
		setMyLocationData();
	}

	private void setMyLocationData() {
		Location location = aMap.getMyLocation();
		if (location == null) {
			location = AndroidHelper.getLocation(context);
		}
		if (location != null) {
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			location.setBearing(bearing);
		}
		if (mListener != null) {
			mListener.onLocationChanged(location);
		}
	}


	public void onDestroy() {
		mListener = null;
	}

	@Override
	public void activate(OnLocationChangedListener onLocationChangedListener) {
		mListener = onLocationChangedListener;
	}

	@Override
	public void deactivate() {

	}

	public void setMyLocationData(AMapLocation location) {
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		bearing = location.getBearing();
		setMyLocationData();
	}
}