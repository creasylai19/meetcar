package com.creasylai.meetcar.singleinstance;

import android.content.Context;

import com.amap.api.location.AMapLocation;

/**
 * Created by laicreasy on 15/12/14.
 */
public class MyLocation {

	private String country;
	private String province;
	private String city;
	private String cityCode;
	private String district;
	private String adCode;
	private String address;
	private String road;
	private int errorCode;
	private String errorInfo;
	private String locationDetail;
	private double altitude;
	private double bearing;
	private double speed;
	private String desc;
	private long time;
	private int locationType;
	private double accuracy;
	private double latitude;
	private double longitude;
	private String provider;
	private boolean hasSetData = false;

	private static MyLocation mInstance;
	private Context mCtx;

	private MyLocation(Context context) {
		mCtx = context.getApplicationContext();
	}

	public static synchronized MyLocation getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new MyLocation(context);
		}
		return mInstance;
	}

	public String getCountry() {
		return country;
	}

	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getDistrict() {
		return district;
	}

	public String getAdCode() {
		return adCode;
	}

	public String getAddress() {
		return address.replace(this.getProvince(), "").replace(this.getCity(), "");
	}

	public String getRoad() {
		return road;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public String getLocationDetail() {
		return locationDetail;
	}

	public double getAltitude() {
		return altitude;
	}

	public double getBearing() {
		return bearing;
	}

	public double getSpeed() {
		return speed;
	}

	public String getDesc() {
		return desc;
	}

	public long getTime() {
		return time;
	}

	public int getLocationType() {
		return locationType;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getProvider() {
		return provider;
	}

	public boolean isHasSetData() {
		return hasSetData;
	}

	public void setLocationData(AMapLocation locationData) {
		this.hasSetData = true;
		this.country = locationData.getCountry();
		this.province = locationData.getProvince();
		this.city = locationData.getCity();
		this.cityCode = locationData.getCityCode();
		this.district = locationData.getDistrict();
		this.adCode = locationData.getAdCode();
		this.address  = locationData.getAddress();
		this.road  = locationData.getRoad();
		this.errorCode  = locationData.getErrorCode();
		this.errorInfo  = locationData.getErrorInfo();
		this.locationDetail  = locationData.getLocationDetail();
		this.altitude  = locationData.getAltitude();
		this.bearing  = locationData.getBearing();
		this.speed = locationData.getSpeed();
		this.time = locationData.getTime();
		this.locationType = locationData.getLocationType();
		this.accuracy = locationData.getAccuracy();
		this.latitude = locationData.getLatitude();
		this.longitude = locationData.getLongitude();
		this.provider = locationData.getProvider();
	}

}
