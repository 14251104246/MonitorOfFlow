package com.example.model;

import android.graphics.drawable.Drawable;

public class Program {
	Drawable icon;
	boolean idRunning;
	float mobileTraffic=0;
	String name="";
	float speed=0;
	float traffic=0;
	int uid=0;
	public Drawable getIcon() {
		return icon;
	}
	public void setIcon(Drawable icon) {
		this.icon = icon;
	}
	public boolean isIdRunning() {
		return idRunning;
	}
	public void setIdRunning(boolean idRunning) {
		this.idRunning = idRunning;
	}
	public float getMobileTraffic() {
		return mobileTraffic;
	}
	public void setMobileTraffic(float mobileTraffic) {
		this.mobileTraffic = mobileTraffic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getTraffic() {
		return traffic;
	}
	public void setTraffic(float traffic) {
		this.traffic = traffic;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	
}
