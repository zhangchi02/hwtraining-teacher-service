package com.huawei.hwtraining.student.service.model;

/**
 * 
 * 
 * @author zhangchi02
 * @date 2018年1月29日
 */
public class StudentSurvey {
	private String classId;
	private String day;
	private String comment;

	public StudentSurvey() {
	};

	public StudentSurvey(String classId, String day, String comment) {
		super();
		this.classId = classId;
		this.day = day;
		this.comment = comment;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
