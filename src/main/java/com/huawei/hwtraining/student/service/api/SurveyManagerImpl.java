package com.huawei.hwtraining.student.service.api;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huawei.hwtraining.student.service.dao.MysqlStudentServiceDbAdapterImpl;
import com.huawei.hwtraining.student.service.model.StudentSurvey;

/**
 * 
 * 
 * @author zhangchi02
 * @date 2018年1月29日
 */
@RestSchema(schemaId = "surveyManager")
@RequestMapping(path = "/hwtraining/v1")
public class SurveyManagerImpl implements SurveyManager {

	private SurveyManager surveyManager = MysqlStudentServiceDbAdapterImpl.getInstance();

	@RequestMapping(path = "/survey", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public boolean addSurvey(@RequestBody StudentSurvey survey) {

		return surveyManager.addSurvey(survey);
	}

}
