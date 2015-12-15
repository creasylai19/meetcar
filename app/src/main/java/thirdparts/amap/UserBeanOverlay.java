package thirdparts.amap;

import android.content.Context;
import android.text.TextUtils;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.creasylai.meetcar.R;
import com.creasylai.meetcar.bean.UserBean;
import com.creasylai.meetcar.consts.AppConst;

import java.util.ArrayList;

/**
 * Created by laicreasy on 15/12/16.
 */
public class UserBeanOverlay implements AMap.OnMarkerClickListener {

	private AMap aMap;
	private Context context;
	private CustomOnMarkerClickListener customOnMarkerClickListener;
	private ArrayList<UserBean> userBeans = new ArrayList<>();
	private ArrayList<Marker> markers = new ArrayList<>();

	public UserBeanOverlay(Context context, AMap aMap, CustomOnMarkerClickListener customOnMarkerClickListener) {
		this.aMap = aMap;
		this.context = context;
		this.customOnMarkerClickListener = customOnMarkerClickListener;
		this.aMap.setOnMarkerClickListener(this);
	}

	public void addMarkersToMap() {
		removeAllMarkers();
		ArrayList<MarkerOptions> markerOptionses = new ArrayList<>();
		for (UserBean userBean : this.userBeans) {
			markerOptionses.add(getMarkerOption(userBean));
		}
		for (int i = 0; i < markerOptionses.size(); i++) {
			MarkerOptions tempMarkerOptions = markerOptionses.get(i);
			tempMarkerOptions.zIndex(i);
		}
		markers = aMap.addMarkers(markerOptionses, false);;
	}

	private MarkerOptions getMarkerOption(UserBean userBean) {
		MarkerOptions markerOptions = new MarkerOptions()
			.icon(BitmapDescriptorFactory.fromResource(userBean.getSex() == AppConst.SEX.MAN ? R.drawable.icon_boy : R.drawable.icon_girl))
				.position(new LatLng(userBean.getLatitude(), userBean.getLongitude()))
				.draggable(false)
				.title("" + userBean.getUid());
		return markerOptions;
	}

	public void addUserBean(UserBean userBean) {
		this.userBeans.add(userBean);
	}

	public void addAllUserBeans( ArrayList<UserBean> userBeans ) {
		this.userBeans.clear();
		for (UserBean userBean : userBeans) {
			this.userBeans.add(userBean);
		}
	}

	private void removeAllMarkers() {
		for (Marker marker: markers) {
			marker.remove();
		}
		markers.clear();
	}

	@Override
	public boolean onMarkerClick(Marker paramMarker) {
		if(!TextUtils.isEmpty(paramMarker.getTitle())) {
			try {
				int uid = Integer.parseInt(paramMarker.getTitle());
				for (UserBean userBean: userBeans) {
					if( userBean.getUid() == uid ) {
						if (customOnMarkerClickListener != null) {
							customOnMarkerClickListener.onMarkerClick(userBean);
						}
						return true;
					}
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	public interface CustomOnMarkerClickListener {
		void onMarkerClick(UserBean userBean);
	}
}
