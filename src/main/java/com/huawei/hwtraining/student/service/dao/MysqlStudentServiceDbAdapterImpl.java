package com.huawei.hwtraining.student.service.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.huawei.hwtraining.student.service.model.ForumContent;
import com.huawei.hwtraining.student.service.model.StudentScore;
import com.huawei.hwtraining.teacher.service.model.Student;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

/**
 * 
 * 
 * @author TankTian
 * @date 2018年1月30日
 */
public class MysqlStudentServiceDbAdapterImpl implements StudentServiceDbAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(MysqlStudentServiceDbAdapterImpl.class);
	private String forumTableName = "hwtraining_student_forum";
	private String studentscoreTableName = "hwtraining_student_studentscore";
	private String databaseName = "hwtraining_student";
	private static Connection con = null;
	private RestTemplate restTemplate = RestTemplateBuilder.create();

	public MysqlStudentServiceDbAdapterImpl() {

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
							stmt.execute("CREATE TABLE " + forumTableName
									+ " ( classId varchar(50), name varchar(50), tenant varchar(50), time varchar(50), content varchar(2000), path varchar(255))");
							stmt.execute("CREATE TABLE " + studentscoreTableName
									+ " ( classId varchar(50), name varchar(50), subject1 int, subject2 int,subject3 int,subject4 int,subject5 int,subject6 int,subject7 int,subject8 int,subject9 int)");

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

	public boolean addStudentScore(String classId) {

		List<Student> students = restTemplate.getForObject(
				"cse://hwtraining-teacher-service" + "/hwtraining/v1/students?&classId=" + classId, List.class);

		Statement stmt = null;

		try {

			for (Student student : students) {
				StudentScore studentScore = new StudentScore();
				studentScore.setClassId(student.getClassId());
				studentScore.setName(student.getName());
				String sql = "INSERT INTO " + studentscoreTableName + " VALUES ('" + studentScore.getClassId() + "', "
						+ "'" + studentScore.getName() + "', '" + studentScore.getSubject1() + "', '"
						+ studentScore.getSubject2() + "', '" + studentScore.getSubject3() + "', '"
						+ studentScore.getSubject4() + "', '" + studentScore.getSubject5() + "', '"
						+ studentScore.getSubject6() + "', '" + studentScore.getSubject7() + "', '"
						+ studentScore.getSubject8() + "', '" + studentScore.getSubject9() + "')";
				stmt = con.createStatement();
				stmt.executeUpdate(sql);
			}
			return true;
		} catch (SQLException e) {
			LOGGER.error("addStudentScore  error: ", e);
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
	public List<StudentScore> getStudentScore(String classId) {

		Statement stmt = null;
		String sql = "SELECT * FROM " + studentscoreTableName + " WHERE classId='" + classId + "'";
		try {
			stmt = con.createStatement();
			ResultSet re = stmt.executeQuery(sql);
			if (re.getFetchSize() == 0) {
				addStudentScore(classId);
				 re = stmt.executeQuery(sql);
			}
			return populate(re, StudentScore.class);
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
	public boolean modifyStudentScore(StudentScore student) {

		Statement stmt = null;
		String sql = "UPDATE " + studentscoreTableName + " SET subject1='" + student.getSubject1() + "',subject2='"
				+ student.getSubject2() + "',subject3='" + student.getSubject3() + "',subject4='"
				+ student.getSubject4() + "',subject5='" + student.getSubject5() + "',subject6='"
				+ student.getSubject6() + "',subject7='" + student.getSubject7() + "',subject8='"
				+ student.getSubject8() + "',subject9='" + student.getSubject9() + "'  WHERE classId='"
				+ student.getClassId() + "' and name='" + student.getName() + "'";
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
	public String uploadFile(MultipartFile file) {

		return null;
	}

	@Override
	public boolean addForumContent(ForumContent forumContent) {
		Statement stmt = null;
		String sql = "INSERT INTO " + forumTableName + " VALUES ('" + forumContent.getClassId() + "', " + "'"
				+ forumContent.getName() + "', '" + forumContent.getTenant() + "', '" + forumContent.getTime() + "', '"
				+ forumContent.getContent() + "', '" + forumContent.getPath() + "')";
		try {

			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			LOGGER.error("addForumContent  error: ", e);
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
	public List<ForumContent> getForumContent(String classId) {

		Statement stmt = null;
		String sql = "SELECT * FROM " + forumTableName + " WHERE classId='" + classId + "'";
		try {
			stmt = con.createStatement();
			ResultSet re = stmt.executeQuery(sql);
			return populate(re, ForumContent.class);
		} catch (SQLException e) {
			LOGGER.error("getForumContent  error: ", e);
		} catch (InstantiationException e) {
			LOGGER.error("getForumContent  error: ", e);
		} catch (IllegalAccessException e) {
			LOGGER.error("getForumContent  error: ", e);
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

}
