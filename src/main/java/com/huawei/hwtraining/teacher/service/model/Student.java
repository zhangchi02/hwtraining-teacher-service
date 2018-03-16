package com.huawei.hwtraining.teacher.service.model;

/**
 * 
 * @author TankTian
 *
 *         2018年1月27日
 */
public class Student {
	private String inviter;
	private String classId;
	private String companyName;
	private String industry;
	private String name;
	private String title;
	private String phoneNumber;
	private String email;
	private String hwcloudId;
	private String comment;
	private String studentId;

	public Student() {

	}

	public Student(String inviter,String classId, String companyName, String industry, String name, String title, String phoneNumber, String email,
			String hwcloudId, String comment,String studentId) {
		this.inviter = inviter;
		this.classId = classId;
		this.companyName = companyName;
		this.industry = industry;
		this.name = name;
		this.title = title;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.hwcloudId = hwcloudId;
		this.comment = comment;
		this.studentId = studentId;
	}
    
	public String getInviter() {
		return inviter;
	}

	public void setInviter(String inviter) {
		this.inviter = inviter;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHwcloudId() {
		return hwcloudId;
	}

	public void setHwcloudId(String hwcloudId) {
		this.hwcloudId = hwcloudId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
}
