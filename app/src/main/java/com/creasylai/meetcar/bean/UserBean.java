package com.creasylai.meetcar.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by laicreasy on 15/11/28.
 */
public class UserBean {

	private String nickname;
	private int sex;
	private String job;
	private String signature;
	private String headurl;
	private String birthday;
	private String bg_url;
	private String latitude;
	private String longitude;
	private int uid;
	private String token;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBg_url() {
		return bg_url;
	}

	public void setBg_url(String bg_url) {
		this.bg_url = bg_url;
	}

	public double getLongitude() {
		return Double.parseDouble(longitude);
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return Double.parseDouble(latitude);
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static UserBean getUserBean(String jsonStr) {
		UserBean obj = new UserBean();
		Gson gson = new Gson();
		obj = gson.fromJson(jsonStr, UserBean.class);
		return obj;
	}

	public static String toJson(UserBean userBean) {
		Gson gson = new Gson();
		return gson.toJson(userBean);
	}

	public static ArrayList<UserBean> getUserBeans(String jsonArrayStr) {
		Gson gson = new Gson();
		Type collectionType = new TypeToken<ArrayList<UserBean>>(){}.getType();
		ArrayList<UserBean> userBeans = gson.fromJson(jsonArrayStr, collectionType);
		return userBeans;
	}

	static Random random = new Random();
	public static UserBean getTestBean() {
		UserBean userBean = new UserBean();
		userBean.setBirthday("90后");
		userBean.setJob("计算机|互联网");
		userBean.setNickname("creasylai");
		userBean.setSex(random.nextInt(100) % 3);
		userBean.setSignature("我思固我在");
		userBean.setHeadurl("http://img1.2345.com/duoteimg/qqTxImg/2013/12/ka_3/04-054424_937.jpg");
		userBean.setLatitude("22.5" + random.nextInt(9) + random.nextInt(9) +"523");
		userBean.setLongitude("113.9" + random.nextInt(9) + random.nextInt(9) +"575");
		userBean.setUid(random.nextInt(100000));
		userBean.setToken("2134149709dsaklhlk");
		return userBean;
	}

}
