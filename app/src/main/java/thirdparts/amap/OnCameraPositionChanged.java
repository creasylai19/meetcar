package thirdparts.amap;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.CameraPosition;

public class OnCameraPositionChanged implements AMap.OnCameraChangeListener {

	private CustomOnCameraPositionChangeListener mCustomOnCameraPositionChangeListener;

	public OnCameraPositionChanged(CustomOnCameraPositionChangeListener mCustomOnCameraPositionChangeListener) {
		this.mCustomOnCameraPositionChangeListener = mCustomOnCameraPositionChangeListener;
	}

	@Override
	public void onCameraChange(CameraPosition cameraPosition) {

	}

	@Override
	public void onCameraChangeFinish(CameraPosition cameraPosition) {
		if (mCustomOnCameraPositionChangeListener != null) {
			mCustomOnCameraPositionChangeListener.onCameraPositionChangeFinish(cameraPosition);
		}
	}

	public interface CustomOnCameraPositionChangeListener {
		void onCameraPositionChangeFinish(CameraPosition cameraPosition);
	}
}
