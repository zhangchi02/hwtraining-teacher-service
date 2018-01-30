package com.huawei.hwtraining.student.service.api;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.huawei.hwtraining.student.service.model.StudentScore;
import com.huawei.hwtraining.teacher.service.model.Student;

/**
 * 
 * 
 * @author TankTian
 * @date 2018年1月29日
 */
@RestSchema(schemaId = "studentScoreManager")
@RequestMapping(path = "/hwtraining/v1", produces = MediaType.APPLICATION_JSON)
public class StudentScoreManagerImpl implements StudentScoreManager {
	private RestTemplate restTemplate = RestTemplateBuilder.create();
	 List<StudentScore>  studentsScore=new LinkedList<StudentScore>();
	 
	@RequestMapping(path = "/studentscore", method = RequestMethod.GET)
	public List<StudentScore> getStudentScore(@RequestParam(value = "classId", required = true)String classId) {
		List<Student> students = restTemplate.getForObject(
				"cse://hwtraining-teacher-service" + "/hwtraining/v1/students?&classId="+classId, List.class);

		System.out.println(students.get(0).getName());
		 StudentScore studentScore=new StudentScore();
		 studentScore.setName(students.get(0).getName());
		 studentScore.setClassId(students.get(0).getClassId());
		 studentsScore.add(studentScore); 
		return studentsScore;
	}
	
	@RequestMapping(path = "/studentscore", method = RequestMethod.PUT)
	public boolean modifyStudentScore(@RequestBody StudentScore studentScore) {
		
		 studentsScore.add(studentScore); 
		return studentsScore.add(studentScore);
	}

}
