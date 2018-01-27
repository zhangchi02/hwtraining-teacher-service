package com.huawei.hwtraining.teacher.service;

import java.util.List;

import com.huawei.hwtraining.teacher.service.model.Task;
/**
 * 
 * @author TankTian
 *
 * 2018年1月27日
 */
public interface TaskManager {

	List<Task> getTasks(String classId);

	boolean modifyTasks(String classId, String task, String handPeople, String comment);
}
