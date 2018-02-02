package com.huawei.hwtraining.teacher.service.model;
/**
 * 
 * @author TankTian
 *
 * 2018年1月27日
 */
public class Task {
	private String classId;
	private int status;
	private String handPeople;
	private String role;
	private String task;
	private String deadline;
	private String detail;
	private String comment;

	public Task() {
	}

	public Task(String classId, int status, String handPeople, String role, String task, String deadline, String detail,
			String comment) {
		this.classId = classId;
		this.status = status;
		this.handPeople = handPeople;
		this.role = role;
		this.task = task;
		this.deadline = deadline;
		this.detail = detail;
		this.comment = comment;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getHandPeople() {
		return handPeople;
	}

	public void setHandPeople(String handPeople) {
		this.handPeople = handPeople;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
