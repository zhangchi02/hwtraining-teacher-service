package com.huawei.hwtraining.student.service.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 
 * @author TankTian
 * @date 2018年1月29日
 */
public class ForumContent {
	private String classId;
	private String forumusername;
	private String name;
	private String tenant;
	private String time;
	private String content;
	private String path;

	public ForumContent() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNowStr = simpleDateFormat.format(date);
		time = dateNowStr;
	}

	public ForumContent(String classId, String forumusername, String name, String tenant, String time, String content, String path) {
		this.classId = classId;
		this.forumusername = forumusername;
		this.name = name;
		this.tenant = tenant;
		if (null != time) {
			this.time = time;
		}
		this.content = content;
		this.path = path;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
    
	public String getForumusername() {
		return forumusername;
	}

	public void setForumusername(String forumusername) {
		this.forumusername = forumusername;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
