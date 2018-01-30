package com.huawei.hwtraining.teacher.service.api;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	List<Student> students = new LinkedList<Student>();

	@RequestMapping(path = "/students", method = RequestMethod.GET)
	public List<Student> getStudents(@RequestParam(value = "classId", required = true) String classId) {
		// TODO Auto-generated method stub
		// private String classId;
		// private String companyName;
		// private String name;
		// private String title;
		// private String phoneNumber;
		// private String email;
		// private String hwcloudId;
		// private String comment;
	
		return students;
	}

	@RequestMapping(path = "/student", method = RequestMethod.POST)
	public boolean addStudent(@RequestBody Student student) {
		return students.add(student);
	}

	@RequestMapping(path = "/student", method = RequestMethod.DELETE)
	public boolean deleteStudent(@RequestParam(value = "classId", required = true) String classId,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "phoneNumber") String phoneNumber) {
		for(Student student:students)
		{
			if(student.getClassId().equals(classId)&&student.getName().equals(name))
			{
				students.remove(student);
				break;
			}
		}
		return true;
	}

}
