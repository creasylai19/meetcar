package thirdparts.amap;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;

public class OnMapClicked implements AMap.OnMapClickListener {

	private CustomOnClickedListener customOnClickedListener;

	public OnMapClicked(CustomOnClickedListener customOnClickedListener) {
		this.customOnClickedListener = customOnClickedListener;
	}

	@Override
	public void onMapClick(LatLng latLng) {
		if (customOnClickedListener != null) {
			customOnClickedListener.onMapMapClicked(latLng);
		}
	}

	public interface CustomOnClickedListener {
		void onMapMapClicked(LatLng mLatLng);
	}

}
