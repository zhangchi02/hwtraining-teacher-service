package com.huawei.hwtraining.teacher.service.api;

import java.util.List;

import org.springframework.http.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.huawei.hwtraining.teacher.service.dao.MysqlTeacherServiceDbAdapterImpl;
import com.huawei.hwtraining.teacher.service.model.Student;

/**
 * 
 * 
 * @author TankTian
 * @date 2018年1月27日
 */
@RestSchema(schemaId = "studentManager")
@RequestMapping(path = "/hwtraining/v1")
public class StudentManagerImpl implements StudentManager {
	private StudentManager studentManager =(StudentManager) MysqlTeacherServiceDbAdapterImpl.getInstance();
	@RequestMapping(path = "/students", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Student> getStudents(@RequestParam(value = "classId", required = true) String classId) {
		return studentManager.getStudents(classId);
	}
	
	@RequestMapping(path = "/student", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Student> getStudent(@RequestParam(value = "studentId", required = true) String studentId) {
		return studentManager.getStudent(studentId);
	}

	@RequestMapping(path = "/student", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public boolean addStudent(@RequestBody Student student) {
		return studentManager.addStudent(student);
	}
	
	@RequestMapping(path = "/import", produces = MediaType.TEXT_PLAIN_VALUE, method = RequestMethod.POST)
	public boolean importStudent(@RequestPart(name = "file") MultipartFile file) {
		return studentManager.importStudent(file);
	}
	
	@RequestMapping(path = "/student", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public boolean updateStudent(@RequestBody Student student) {
		return studentManager.updateStudent(student);
	}

	@RequestMapping(path = "/student", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	public boolean deleteStudent(@RequestParam(value = "classId", required = true) String classId,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "phoneNumber") String phoneNumber,
		    @RequestParam(value = "studentId") String studentId) {
		return studentManager.deleteStudent(classId, name, phoneNumber,studentId);
	}
	
	@RequestMapping(path = "/currentclassid", produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
	@Override
	public String getCurrentClassId() {
		return studentManager.getCurrentClassId();
	}

}
