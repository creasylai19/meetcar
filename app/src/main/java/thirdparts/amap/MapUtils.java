package thirdparts.amap;


import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.services.core.LatLonPoint;

import java.util.List;

public class MapUtils {

	public static void zoomToSpan(AMap aMap, List<LatLng> mLatLngs) {
		if ( null == aMap || null == mLatLngs) {
			return;
		}
		if (mLatLngs.size() > 0) {
			LatLngBounds.Builder localBuilder = new LatLngBounds.Builder();
			for (LatLng latLng : mLatLngs) {
				localBuilder.include(latLng);
			}
			CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(localBuilder.build(), 0);
			aMap.moveCamera(mCameraUpdate);
		}
	}

	public static void zoomToSpanSmooth(AMap aMap, List<LatLng> mLatLngs) {
		if ( null == aMap || null == mLatLngs) {
			return;
		}
		if (mLatLngs.size() > 0) {
			LatLngBounds.Builder localBuilder = new LatLngBounds.Builder();
			for (LatLng latLng : mLatLngs) {
				localBuilder.include(latLng);
			}
			CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(localBuilder.build(), 0);
			aMap.animateCamera(mCameraUpdate);
		}
	}

	public static double getDistance(LatLng paramLatLng1, LatLng paramLatLng2) {
		if ( null == paramLatLng1 || null == paramLatLng2) {
			return 0;
		}
		return AMapUtils.calculateLineDistance(paramLatLng1, paramLatLng2);//返回单位为米
	}

	public static void zoomIn(AMap aMap) {
		if ( null == aMap) {
			return;
		}
		aMap.animateCamera(CameraUpdateFactory.zoomIn());
	}

	public static void zoomOut(AMap aMap) {
		if ( null == aMap) {
			return;
		}
		aMap.animateCamera(CameraUpdateFactory.zoomOut());
	}

	public static void setTraffic(AMap aMap, boolean bEnableTraffic) {
		if ( null == aMap) {
			return;
		}
		aMap.setTrafficEnabled(bEnableTraffic);
	}

	public static LatLng getMapCenter(AMap aMap) {
		if ( null == aMap) {
			return null;
		}
		return aMap.getCameraPosition().target;
	}

	public static void setRotation(AMap aMap, float paramFloat) {
		if ( null == aMap) {
			return;
		}
		aMap.animateCamera(CameraUpdateFactory.changeBearing(paramFloat));
	}

	public static void animateTo(AMap aMap, LatLng mLatLng) {
		if ( null == aMap || null == mLatLng) {
			return;
		}
		aMap.animateCamera(CameraUpdateFactory.changeLatLng(mLatLng), 500, null);
	}

	public static void animateTo(AMap aMap, LatLng mLatLng, AMap.CancelableCallback cancelableCallback) {
		if ( null == aMap || null == mLatLng) {
			return;
		}
		aMap.animateCamera(CameraUpdateFactory.changeLatLng(mLatLng), 500, cancelableCallback);
	}
	
	public static void zoomTo( AMap aMap, float zoom ) {
		if ( null == aMap ) {
			return;
		}
		aMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
	}

	public static LatLonPoint convertToLatLonPoint(LatLng latlon) {
		if ( null == latlon) {
			return null;
		}
		return new LatLonPoint(latlon.latitude, latlon.longitude);
	}

	public static LatLng convertToLatLng(LatLonPoint latLonPoint) {
		if ( null == latLonPoint) {
			return null;
		}
		return new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
	}

}
