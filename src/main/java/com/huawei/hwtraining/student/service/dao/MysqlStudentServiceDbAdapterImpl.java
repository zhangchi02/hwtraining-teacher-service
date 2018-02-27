package com.huawei.hwtraining.student.service.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.huawei.hwtraining.student.service.model.ForumContent;
import com.huawei.hwtraining.student.service.model.StudentScore;
import com.huawei.hwtraining.student.service.model.StudentSurvey;
import com.huawei.hwtraining.teacher.service.model.Student;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;

/**
 * 
 * 
 * @author TankTian
 * @date 2018年1月30日
 */
public class MysqlStudentServiceDbAdapterImpl implements StudentServiceDbAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(MysqlStudentServiceDbAdapterImpl.class);
	private String endPoint = "obs.myhwclouds.com";
	private String ak = "SQXZYPSDEZAWY10RACO7";
	private String sk = "zMgxdKKD9Fh5owFeZGNSBhgeE1iHk7YmLLm0J4Ns";
	private String forumTableName = "hwtraining_student_forum";
	private String surveyTableName = "hwtraining_student_survey";
	private String studentscoreTableName = "hwtraining_student_studentscore";
	private String databaseName = "hwtraining_student";
	private static Connection con = null;
	private RestTemplate restTemplate = RestTemplateBuilder.create();
	private static MysqlStudentServiceDbAdapterImpl mysqlStudentServiceDbAdapterImpl = new MysqlStudentServiceDbAdapterImpl();

	public static synchronized MysqlStudentServiceDbAdapterImpl getInstance() {
		if (mysqlStudentServiceDbAdapterImpl == null) {
			mysqlStudentServiceDbAdapterImpl = new MysqlStudentServiceDbAdapterImpl();
		}
		return mysqlStudentServiceDbAdapterImpl;
	}

	private MysqlStudentServiceDbAdapterImpl() {
		Statement stmt = null;
		if (con == null) {
			try {
				con = initDb(null, 0, null, null, databaseName);
				stmt = con.createStatement();
				stmt.execute("use " + databaseName);
			} catch (SQLException e) {
				if (e instanceof MySQLSyntaxErrorException) {
					if (e.getMessage().contains("Unknown database")) {

						
						try {
							//stmt = con.createStatement();
							stmt.execute("create database " + databaseName);
							stmt.execute("use " + databaseName);
							stmt.execute("CREATE TABLE " + forumTableName
									+ " ( classId varchar(50), forumusername varchar(50), name varchar(50), tenant varchar(50), time varchar(50), content varchar(2000), path varchar(255))");
							stmt.execute("CREATE TABLE " + surveyTableName
									+ " ( classId varchar(50), day varchar(50), comment varchar(2000))");
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

	public boolean deleteStudentScore(List<StudentScore> studentScores) {
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			for (StudentScore studentScore : studentScores) {
				String sql = "DELETE FROM " + studentscoreTableName + " WHERE classId='" + studentScore.getClassId()
						+ "' and name='" + studentScore.getName() + "'";
				stmt.executeUpdate(sql);
			}
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

	public boolean addStudentScore(List<Student> students) {

		Statement stmt = null;
		try {
			stmt = con.createStatement();
			for (Student student : students) {
				if (null == student.getName() || student.getName().isEmpty()) {
					break;
				}
				StudentScore studentScore = new StudentScore();
				studentScore.setClassId(student.getClassId());
				studentScore.setName(student.getName());
				String sql = "INSERT INTO " + studentscoreTableName + " VALUES ('" + studentScore.getClassId() + "', "
						+ "'" + studentScore.getName() + "', '" + studentScore.getSubject1() + "', '"
						+ studentScore.getSubject2() + "', '" + studentScore.getSubject3() + "', '"
						+ studentScore.getSubject4() + "', '" + studentScore.getSubject5() + "', '"
						+ studentScore.getSubject6() + "', '" + studentScore.getSubject7() + "', '"
						+ studentScore.getSubject8() + "', '" + studentScore.getSubject9() + "')";
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

			List<Student> students = restTemplate.getForObject(
					"cse://hwtraining-teacher-service" + "/hwtraining/v1/students?&classId=" + classId, List.class);
			re.last();// 對於女對比一下數量對不對
			if (students.size() < 1) {
				return new ArrayList<>();
			}
			if (re.getRow() < 1) {
				addStudentScore(students);
				re = stmt.executeQuery(sql);
			} else {
				List<Student> tempStudents = new LinkedList<>();
				re.beforeFirst();
				List<StudentScore> studentScores = populate(re, StudentScore.class);
				for (Student student : students) {
					boolean isExist = false;
					for (StudentScore studentScore : studentScores) {
						if (student.getName().equals(studentScore.getName())) {
							isExist = true;
							break;
						}
					}
					if (!isExist) {
						tempStudents.add(student);
					}
				}
				List<StudentScore> tempStudentScores = new LinkedList<>();
				for (StudentScore studentScore : studentScores) {
					boolean isExist = false;
					for (Student student : students) {
						if (student.getName().equals(studentScore.getName())) {
							isExist = true;
							break;
						}

					}
					if (!isExist) {
						tempStudentScores.add(studentScore);
					}
				}
				deleteStudentScore(tempStudentScores);
				addStudentScore(tempStudents);
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
		return new ArrayList<>();
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
		// 创建ObsClient实例
		ObsClient obsClient = new ObsClient(ak, sk, endPoint);
		try {
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateNowStr = simpleDateFormat.format(date);
			String bucketName = "hwtraining";
			String path = "forum/" + dateNowStr + "-" + file.getOriginalFilename();
			obsClient.putObject(bucketName, path, file.getInputStream());
			String downloadUrl = "https://" + bucketName + ".obs.myhwclouds.com/" + path;
			return downloadUrl;
		} catch (ObsException | IOException e) {
			LOGGER.error("putObject  error: ", e);
		} finally {
			try {
				obsClient.close();
			} catch (IOException e) {
				LOGGER.error("close obsClient error: ", e);
			}

		}

		return null;
	}

	@Override
	public boolean addForumContent(ForumContent forumContent) {
		Statement stmt = null;
		String sql = "INSERT INTO " + forumTableName + " VALUES ('" + forumContent.getClassId() + "', '" + forumContent.getForumusername() + "','"
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
		return new LinkedList<ForumContent>();
	}

	@Override
	public boolean addSurvey(StudentSurvey survey) {
		  
		Statement stmt = null;
		String sql = "INSERT INTO " + surveyTableName + " VALUES ('" + survey.getClassId() + "', " + "'"
				+ survey.getDay() + "', '" + survey.getComment() + "')";
		try {

			stmt = con.createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			LOGGER.error("addSurvey  error: ", e);
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
