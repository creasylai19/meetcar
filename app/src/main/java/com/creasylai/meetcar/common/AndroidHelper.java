package com.creasylai.meetcar.common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by laicreasy on 15/12/12.
 */
public class AndroidHelper {

	public static Location getLocation(Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
		//获取GPS支持
		Location location = null;
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
				    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
			if (location == null) {
				//获取NETWORK支持
				location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
			}
		}
		return location;
	}
}
