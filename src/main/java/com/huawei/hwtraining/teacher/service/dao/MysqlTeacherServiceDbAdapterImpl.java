package com.huawei.hwtraining.teacher.service.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.hwtraining.teacher.service.model.Student;
import com.huawei.hwtraining.teacher.service.model.Task;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

/**
 * 
 * 
 * @author TankTian
 * @date 2018年1月30日
 */
public class MysqlTeacherServiceDbAdapterImpl implements TeacherServiceDbAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(MysqlTeacherServiceDbAdapterImpl.class);
	private String taskTableName = "hwtraining_teacher_task";
	private String studentinfTableName = "hwtraining_teacher_task";
	private String databaseName = "hwtraining_teacher";
	private static Connection con = null;

	public MysqlTeacherServiceDbAdapterImpl() {
		if (con == null) {
			try {
				con = initDb(null, 0, null, null, databaseName);
			} catch (SQLException e) {
				if (e instanceof MySQLSyntaxErrorException) {
					if (e.getMessage().contains("Unknown database")) {

						Statement stmt = null;
						try {
							stmt = con.createStatement();
							stmt.execute("create database " + databaseName);
							stmt.execute("use " + databaseName);
							stmt.execute("CREATE TABLE " + taskTableName
									+ " ( classId varchar(50), status int, handPeople varchar(255), role varchar(255), task varchar(255), deadline varchar(20),detail varchar(500),comment varchar(255) )");
							stmt.execute("CREATE TABLE " + studentinfTableName
									+ " ( classId varchar(50), name varchar(50), companyName varchar(255), title varchar(255), phoneNumber varchar(30), email varchar(100),hwcloudId varchar(100),comment varchar(255) )");

						} catch (SQLException e1) {
							LOGGER.error("execute sql error: ", e1);
						} finally {
							try {
								if (stmt != null) {
									stmt.close();
								}
							} catch (SQLException e2) {
								LOGGER.error("closed statement error: ", e2);
							}
						}
					}
				} else {
					LOGGER.error("execute sql error: ", e);
				}
			}
		}
	}

	@Override
	public List<Task> getTasks(String classId) {

		Statement stmt = null;
		String sql = "SELECT * FROM " + taskTableName + " WHERE classId='" + classId + "'";
		try {
			stmt = con.createStatement();
			ResultSet re = stmt.executeQuery(sql);
			return populate(re, Task.class);
		} catch (SQLException e) {
			LOGGER.error("getTasks  error: ", e);
		} catch (InstantiationException e) {
			LOGGER.error("getTasks  error: ", e);
		} catch (IllegalAccessException e) {
			LOGGER.error("getTasks  error: ", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOGGER.error("close statement error", e);
				}
			}
		}
		return null;
	}

	@Override
	public boolean modifyTasks(String classId, String task, String handPeople, String comment) {

		Statement stmt = null;
		String sql = "UPDATE " + taskTableName + " SET handPeople='" + handPeople + "',comment='" + comment
				+ "'  WHERE classId='" + classId + "' and task='" + task + "'";
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			LOGGER.error("updateOrder  error: ", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOGGER.error("close statement error: ", e);
				}
			}
		}
		return false;
	}

	@Override
	public List<Student> getStudents(String classId) {
		Statement stmt = null;
		String sql = "SELECT * FROM " + studentinfTableName + " WHERE classId='" + classId + "'";
		try {
			stmt = con.createStatement();
			ResultSet re = stmt.executeQuery(sql);
			return populate(re, Student.class);
		} catch (SQLException e) {
			LOGGER.error("getStudents  error: ", e);
		} catch (InstantiationException e) {
			LOGGER.error("getStudents  error: ", e);
		} catch (IllegalAccessException e) {
			LOGGER.error("getStudents  error: ", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOGGER.error("close statement error", e);
				}
			}
		}
		return null;
	}

	@Override
	public boolean addStudent(Student student) {
		Statement stmt = null;
		String sql = "INSERT INTO " + studentinfTableName + " VALUES ('" + student.getClassId() + "', " + "'"
				+ student.getName() + "', '" + student.getCompanyName() + "', '" + student.getTitle() + "', '"
				+ student.getPhoneNumber() + "', '" + student.getEmail() + "', '" + student.getHwcloudId() + "', '"
				+ student.getComment() + "')";
		try {

			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			LOGGER.error("addStudent  error: ", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOGGER.error("close statement error: ", e);
				}
			}
		}
		return false;
	}

	@Override
	public boolean deleteStudent(String classId, String name, String phoneNumber) {

		Statement stmt = null;
		String sql = "DELETE FROM " + studentinfTableName + " WHERE classId='" + classId + "' and name='" + name
				+ "' and phoneNumber='" + phoneNumber + "'";
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			LOGGER.error("deleteStudent  error: ", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOGGER.error("close statement error: ", e);
				}
			}
		}
		return false;
	}
}
