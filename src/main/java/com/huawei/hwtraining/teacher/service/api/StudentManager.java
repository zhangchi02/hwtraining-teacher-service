package com.huawei.hwtraining.teacher.service.api;

import java.util.List;

import com.huawei.hwtraining.teacher.service.model.Student;
/**
 * 
 * @author TankTian
 *
 * 2018年1月27日
 */
public interface StudentManager {

	List<Student> getStudents(String classId);

	boolean addStudent(Student student);

	boolean deleteStudent(String classId, String name, String phoneNumber);
}
