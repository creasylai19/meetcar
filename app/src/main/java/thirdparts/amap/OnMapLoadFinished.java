package thirdparts.amap;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.creasylai.meetcar.consts.AppConst;

public class OnMapLoadFinished implements AMap.OnMapLoadedListener {

	private LatLng mLatLng;
	private AMap aMap;
	private CustomOnMapLoadedListener customOnMapLoadedListener;

	public OnMapLoadFinished(AMap aMap, LatLng mLatLng, CustomOnMapLoadedListener customOnMapLoadedListener) {
		this.mLatLng = mLatLng;
		this.aMap = aMap;
		this.customOnMapLoadedListener = customOnMapLoadedListener;
	}

	@Override
	public void onMapLoaded() {
		MapUtils.animateTo(aMap, mLatLng);
		MapUtils.zoomTo(aMap, AppConst.STATIC_INT_KEY.DEFAULT_ZOOM);
		customOnMapLoadedListener.onMapLoaded(MapUtils.getMapCenter(aMap));
	}

	public interface CustomOnMapLoadedListener {
		void onMapLoaded(LatLng mLatLng);
	}

}
