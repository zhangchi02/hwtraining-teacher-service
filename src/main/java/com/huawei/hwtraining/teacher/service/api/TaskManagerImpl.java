package com.huawei.hwtraining.teacher.service.api;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huawei.hwtraining.teacher.service.dao.MysqlTeacherServiceDbAdapterImpl;
import com.huawei.hwtraining.teacher.service.model.Task;

/**
 * 
 * 
 * @author TankTian
 * @date 2018年1月27日
 */
@RestSchema(schemaId = "taskManager")
@RequestMapping(path = "/hwtraining/v1", produces = MediaType.APPLICATION_JSON)
public class TaskManagerImpl implements TaskManager {
	private TaskManager taskManager =(TaskManager) MysqlTeacherServiceDbAdapterImpl.getInstance();

	@RequestMapping(path = "/tasks", method = RequestMethod.GET)
	public List<Task> getTasks(@RequestParam(value = "classId", required = true) String classId) {
		return taskManager.getTasks(classId);
	}

	@RequestMapping(path = "/tasks", method = RequestMethod.PUT)
	public boolean modifyTasks(@RequestParam(value = "classId", required = true) String classId,
			@RequestParam(value = "task") String task,
			@RequestParam(value = "handPeople", required = true) String handPeople,
			@RequestParam(value = "comment") String comment) {
		return taskManager.modifyTasks(classId, task, handPeople, comment);
	}

}
