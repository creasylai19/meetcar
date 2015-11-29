package com.creasylai.meetcar.bean;

import com.google.gson.Gson;

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

	public static UserBean getUserBean(String json) {
		UserBean obj = new UserBean();
		Gson gson = new Gson();
		obj = gson.fromJson(json, UserBean.class);
		return obj;
	}

	public static String toJson(UserBean userBean) {
		Gson gson = new Gson();
		return gson.toJson(userBean);
	}

	public static UserBean getTestBean() {
		UserBean userBean = new UserBean();
		userBean.setBirthday("90后");
		userBean.setJob("计算机|互联网");
		userBean.setNickname("creasylai");
		userBean.setSex(1);
		userBean.setSignature("我思固我在");
		userBean.setHeadurl("http://img1.2345.com/duoteimg/qqTxImg/2013/12/ka_3/04-054424_937.jpg");
		return userBean;
	}

}
