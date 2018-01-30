package com.huawei.hwtraining.student.service.api;

import java.util.List;

import com.huawei.hwtraining.student.service.model.StudentScore;

/**
 * 
 * 
 * @author TankTian
 * @date 2018年1月29日
 */
public interface StudentScoreManager {

	List<StudentScore> getStudentScore(String classId);

	boolean modifyStudentScore(StudentScore student);

}
