package com.huawei.hwtraining.teacher.service.api;

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
	List<Task> tasks = new LinkedList<Task>();

	@RequestMapping(path = "/tasks", method = RequestMethod.GET)
	public List<Task> getTasks(@RequestParam(value = "classId", required = true) String classId) {
		if (tasks.size() < 1) {
			tasks.add(new Task("201801", 0, "tank01", "TEACHER", "保证课堂质量", "20180129", "提前自己验证", ""));
			tasks.add(new Task("201801", 0, "tank02", "TEACHER", "制作证书", "20180129", "提前自己验证", ""));
		}
		return tasks;
	}

	@RequestMapping(path = "/tasks", method = RequestMethod.PUT)
	public boolean modifyTasks(@RequestParam(value = "classId", required = true) String classId,
			@RequestParam(value = "task") String task,
			@RequestParam(value = "handPeople", required = true) String handPeople,
			@RequestParam(value = "comment") String comment) {
				Task task0 =tasks.get(0);
				if(task0.getClassId().equals(classId)&&task0.getTask().equals(task))
				{
					task0.setComment(comment);
					task0.setStatus(1);
				}
				Task task1 =tasks.get(1);
				if(task1.getClassId().equals(classId)&&task1.getTask().equals(task))
				{
					task1.setComment(comment);
					task1.setStatus(1);
				}
		return true;
	}

}
