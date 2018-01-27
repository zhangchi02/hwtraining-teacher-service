package com.huawei.hwtraining.teacher.service;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	@RequestMapping(path = "/tasks", method = RequestMethod.GET)
	public List<Task> getTasks(@RequestParam(value = "classId", required = true) String classId) {
		List<Task> tasks = new LinkedList<Task>();
		tasks.add(new Task("201801", 1, "tank", "TEACHER", "保证课堂质量", "20180129", "提前自己验证", "已经完成了"));
		
		return tasks;
	}

	@RequestMapping(path = "/tasks", method = RequestMethod.PUT)
	public boolean modifyTasks(@RequestParam(value = "classId", required = true) String classId,
			@RequestParam(value = "task") String task,
			@RequestParam(value = "handPeople", required = true) String handPeople,
			@RequestParam(value = "comment") String comment) {
		// TODO Auto-generated method stub
		return true;
	}

}
