package com.huawei.hwtraining.teacher.service.api;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.huawei.hwtraining.teacher.service.dao.MysqlTeacherServiceDbAdapterImpl;
import com.huawei.hwtraining.teacher.service.model.Student;

/**
 * 
 * 
 * @author TankTian
 * @date 2018年1月27日
 */
@RestSchema(schemaId = "studentManager")
@RequestMapping(path = "/hwtraining/v1", produces = MediaType.APPLICATION_JSON)
public class StudentManagerImpl implements StudentManager {
	private StudentManager studentManager =(StudentManager) MysqlTeacherServiceDbAdapterImpl.getInstance();
	@RequestMapping(path = "/students", method = RequestMethod.GET)
	public List<Student> getStudents(@RequestParam(value = "classId", required = true) String classId) {
		return studentManager.getStudents(classId);
	}

	@RequestMapping(path = "/student", method = RequestMethod.POST)
	public boolean addStudent(@RequestBody Student student) {
		return studentManager.addStudent(student);
	}

	@RequestMapping(path = "/student", method = RequestMethod.DELETE)
	public boolean deleteStudent(@RequestParam(value = "classId", required = true) String classId,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "phoneNumber") String phoneNumber) {
		return studentManager.deleteStudent(classId, name, phoneNumber);
	}
	
	@RequestMapping(path = "/currentclassid", method = RequestMethod.GET)
	@Override
	public String getCurrentClassId() {
		return studentManager.getCurrentClassId();
	}

}
