package com.huawei.hwtraining.teacher.service.api;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.huawei.hwtraining.teacher.service.model.Student;

/**
 * 
 * @author TankTian
 *
 *         2018年1月27日
 */
public interface StudentManager {

	String getCurrentClassId();

	List<Student> getStudents(String classId);
	
	List<Student> getStudent(String studentId);

	boolean addStudent(Student student);
	
	boolean importStudent(MultipartFile file);

	boolean updateStudent(Student student);

	boolean deleteStudent(String classId, String name, String phoneNumber, String studentId);
}
