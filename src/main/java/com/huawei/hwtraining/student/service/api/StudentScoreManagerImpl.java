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

import com.huawei.hwtraining.student.service.dao.MysqlStudentServiceDbAdapterImpl;
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
	private StudentScoreManager studentScoreManager = MysqlStudentServiceDbAdapterImpl.getInstance();

	@RequestMapping(path = "/studentscore", method = RequestMethod.GET)
	public List<StudentScore> getStudentScore(@RequestParam(value = "classId", required = true) String classId) {
		return studentScoreManager.getStudentScore(classId);
	}

	@RequestMapping(path = "/studentscore", method = RequestMethod.PUT)
	public boolean modifyStudentScore(@RequestBody StudentScore studentScore) {
		return studentScoreManager.modifyStudentScore(studentScore);
	}

}
