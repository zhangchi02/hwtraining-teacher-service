package com.huawei.hwtraining.teacher.service.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.huawei.hwtraining.RandomString;
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
	private String studentinfTableName = "hwtraining_teacher_studentinf";
	private String classidTableName = "hwtraining_teacher_classid";
	private String databaseName = "hwtraining_teacher";
	private static Connection con = null;
	private static MysqlTeacherServiceDbAdapterImpl mysqlTeacherServiceDbAdapterImpl = new MysqlTeacherServiceDbAdapterImpl();

	public static synchronized MysqlTeacherServiceDbAdapterImpl getInstance() {
		if (mysqlTeacherServiceDbAdapterImpl == null) {
			mysqlTeacherServiceDbAdapterImpl = new MysqlTeacherServiceDbAdapterImpl();
		}
		return mysqlTeacherServiceDbAdapterImpl;
	}

	private Connection getConnection(Connection connection) {
		Statement stmt = null;
		try {
			if (null == connection || connection.isClosed()) {

				connection = initDb(null, 0, null, null);
				stmt = connection.createStatement();
				stmt.execute("use " + databaseName);
			}
		} catch (SQLException e) {
			if (e instanceof MySQLSyntaxErrorException) {
				if (e.getMessage().contains("Unknown database")) {

					try {
						stmt.execute("create database " + databaseName);
						stmt.execute("use " + databaseName);
						stmt.execute("CREATE TABLE " + taskTableName
								+ " ( classId varchar(50), status int, handPeople varchar(255), role varchar(255), task varchar(255), deadline varchar(20),detail varchar(500),comment varchar(255) )");
						stmt.execute("CREATE TABLE " + studentinfTableName
								+ " ( inviter varchar(50), classId varchar(50), name varchar(50), companyName varchar(255), industry varchar(100), title varchar(255), phoneNumber varchar(30), email varchar(100),hwcloudId varchar(100),comment varchar(255),studentId varchar(50) NOT NULL, createTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)");
						stmt.execute("CREATE TABLE " + classidTableName + "  ( classId varchar(50) )");
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
		return connection;
	}

	private MysqlTeacherServiceDbAdapterImpl() {
		con = getConnection(con);
	}

	@Override
	public List<Task> getTasks(String classId) {
		Statement stmt = null;
		String sql = "SELECT * FROM " + taskTableName + " WHERE classId='" + classId + "'";
		try {
			stmt = getConnection(con).createStatement();
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
		return new LinkedList<>();
	}

	@Override
	public boolean modifyTasks(String classId, String task, String handPeople, String comment) {

		Statement stmt = null;
		String sql = "UPDATE " + taskTableName + " SET handPeople='" + handPeople + "',status=" + 1 + ",comment='"
				+ comment + "'  WHERE classId='" + classId + "' and task='" + task + "'";
		try {
			stmt = getConnection(con).createStatement();
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
			stmt = getConnection(con).createStatement();
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
		return new LinkedList<>();
	}

	@Override
	public List<Student> getStudent(String studentId) {
		Statement stmt = null;
		String sql = "SELECT * FROM " + studentinfTableName + " WHERE studentId='" + studentId + "'";
		try {
			stmt = getConnection(con).createStatement();
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
		return new LinkedList<>();
	}

	@Override
	public boolean addStudent(Student student) {
		Statement stmt = null;
		String sql = "";
		if (student.getStudentId().equals("studentId")) {
			sql = "INSERT INTO " + studentinfTableName + " VALUES ('" + student.getInviter() + "','"
					+ student.getClassId() + "', " + "'" + student.getName() + "', '" + student.getCompanyName() + "', '"
					+ student.getIndustry() + "','" + student.getTitle() + "', '" + student.getPhoneNumber() + "', '"
					+ student.getEmail() + "', '" + student.getHwcloudId() + "', '" + student.getComment() + "', '"
					+ (Integer.parseInt(this.getMaxStudentId())+1) + "',now(),now())";
		} else {
			sql = "UPDATE " + studentinfTableName + " SET name='" + student.getName() + "',companyName='"
					+ student.getCompanyName() + "',industry='" + student.getIndustry() + "',title='" + student.getTitle()
					+ "',phoneNumber='" + student.getPhoneNumber() + "',email='" + student.getEmail() + "',hwcloudId='"
					+ student.getHwcloudId() + "',comment='" + student.getComment() + "' where studentId='"
					+ student.getStudentId() + "'";
		}
		try {
			stmt = getConnection(con).createStatement();
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
	public boolean importStudent(MultipartFile file) {
		Workbook workbook;
		Statement stmt = null;
		String sql;
		InputStream inputStream = null ;
		try {
			int maxStudentId = Integer.parseInt(this.getMaxStudentId());
			inputStream = file.getInputStream();
			workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			
			DataFormatter formatter = new DataFormatter();
			stmt = getConnection(con).createStatement();
			for (Row row : sheet) {
				String inviter = formatter.formatCellValue(row.getCell(0));
				String classId = formatter.formatCellValue(row.getCell(1));
				String companyName = formatter.formatCellValue(row.getCell(2));
				String industry = formatter.formatCellValue(row.getCell(3));
				String name = formatter.formatCellValue(row.getCell(4));
				String title = formatter.formatCellValue(row.getCell(5));
				String phoneNumber = formatter.formatCellValue(row.getCell(6));
				String email = formatter.formatCellValue(row.getCell(7));
				String hwcloudId = formatter.formatCellValue(row.getCell(8));
				String comment = formatter.formatCellValue(row.getCell(9));
				//String studentId = formatter.formatCellValue(row.getCell(10));
				sql = "insert into hwtraining_teacher_studentinf value('" + inviter + "','" + classId + "','"
						+ companyName + "','" + industry + "','" + name + "','" + title + "','" + phoneNumber + "','"
						+ email + "','" + hwcloudId + "','" + comment + "','" + ++maxStudentId + "',now(),now())";
				stmt.executeUpdate(sql);
			}
			return true;
		} catch (EncryptedDocumentException e) {
			LOGGER.error("parse file error: ", e);
		} catch (InvalidFormatException e) {
			LOGGER.error("parse file error: ", e);
		} catch (IOException e) {
			LOGGER.error("file not found  error: ", e);
		} catch (SQLException e) {
			LOGGER.error("importStudent  error: ", e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block  
				e1.printStackTrace();  
			}
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
	public boolean updateStudent(Student student) {
		Statement stmt = null;
		String sql = "UPDATE" + studentinfTableName + " SET name='" + student.getName() + "', companyName='"
				+ student.getCompanyName() + "',industry='" + student.getIndustry() + "',title='" + student.getTitle()
				+ "',phoneNumber'" + student.getPhoneNumber() + "',email='" + student.getEmail() + "',hwcloudId='"
				+ student.getHwcloudId() + "',comment='" + student.getComment() + "' where studentId='"
				+ RandomString.getRandomString(30) + "'";
		try {
			stmt = getConnection(con).createStatement();
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			LOGGER.error("updateStudent  error: ", e);
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
	public boolean deleteStudent(String classId, String name, String phoneNumber, String studentId) {

		Statement stmt = null;
		String sql = "DELETE FROM " + studentinfTableName + " WHERE classId='" + classId + "' and name='" + name
				+ "' and phoneNumber='" + phoneNumber + "' and studentId='" + studentId + "'";
		try {
			stmt = getConnection(con).createStatement();
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

	@Override
	public String getCurrentClassId() {
		String classId = "0";
		Statement stmt = null;
		String sql = "SELECT * FROM " + classidTableName;
		try {
			stmt = getConnection(con).createStatement();
			ResultSet re = stmt.executeQuery(sql);
			if (re.next()) {
				classId = re.getString(1);
			}
			if("0".equals(classId) || "".equals(classId) || null == classId){
				Date currentTime = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
				classId = formatter.format(currentTime);
			}
			return classId;
		} catch (SQLException e) {
			LOGGER.error("getCurrentClassId  error: ", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOGGER.error("close statement error", e);
				}
			}
		}
		return classId;
	}
	
	public String getMaxStudentId() {
		String studentId = "0";
		Statement stmt = null;
		String sql = "SELECT MAX(STUDENTID) FROM " + studentinfTableName;
		try {
			stmt = getConnection(con).createStatement();
			String currentClassId = this.getCurrentClassId();
			sql += " WHERE CLASSID='" + currentClassId +"'";
			ResultSet re = stmt.executeQuery(sql);
			if (re.next()) {
				studentId = re.getString(1);
			}
			if("".equals(studentId) || null == studentId){
				studentId = currentClassId + "000";
			}
			return studentId;
		} catch (SQLException e) {
			LOGGER.error("getMaxStudentId  error: ", e);
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					LOGGER.error("close statement error", e);
				}
			}
		}
		return studentId;
	}
}
