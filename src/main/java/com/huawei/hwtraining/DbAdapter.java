package com.huawei.hwtraining;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * docker exec -it mysql-tank /bin/sh mysql -u root -phuawei@123; create
 * database hwtraining_teacher; use hwtraining_teacher; CREATE TABLE
 * hwtraining_teacher_task ( classId varchar(50), status int, handPeople
 * varchar(255), role varchar(255), task varchar(255), deadline
 * varchar(20),detail varchar(500),comment varchar(255) ); INSERT INTO
 * hwtraining_teacher_task VALUES ( '201801',1,'tank01','TEACHER','good
 * job','20180129','test ok','test comment' );select * from
 * hwtraining_teacher_task; CREATE TABLE hwtraining_teacher_studentinf ( classId
 * varchar(50), name varchar(50), companyName varchar(255), title varchar(255),
 * phoneNumber varchar(30), email varchar(100),hwcloudId varchar(100),comment
 * varchar(255) ); INSERT INTO hwtraining_teacher_studentinf VALUES (
 * '201801','huawei','tank','pm','123','fsd@gmail.com','hwcse','tank tian test'
 * );select * from hwtraining_teacher_studentinf;
 * 
 * create database hwtraining_student; use hwtraining_student; CREATE TABLE
 * hwtraining_student_studentscore ( classId varchar(50), name varchar(50),
 * subject1 int, subject2 int,subject3 int,subject4 int,subject5 int,subject6
 * int,subject7 int,subject8 int,subject9 int); INSERT INTO
 * hwtraining_student_studentscore VALUES (
 * '201801','tank',15,15,15,15,15,15,15,15,20 );select * from
 * hwtraining_student_studentscore;
 * 
 * CREATE TABLE hwtraining_student_forum ( classId varchar(50), name
 * varchar(50), tenant varchar(50), time varchar(50), content varchar(2000),
 * path varchar(255)); INSERT INTO hwtraining_student_forum VALUES (
 * '201801','tank','hwcse','2018-01-30-10-05','tank tian
 * test','https://www.baidu.com/img/bd_logo1.png' );select * from
 * hwtraining_student_forum;
 * 
 * 
 * @author TankTian
 * @date 2018年1月30日
 */
public interface DbAdapter {
	static final Logger LOGGER = LoggerFactory.getLogger(DbAdapter.class);
	String ip = "mysql";
	int port = 3306;
	String dbUserName = "root";
	String dbPassword = "huawei@123";
	String jdbcName = "com.mysql.jdbc.Driver";

	default <T> List<T> populate(ResultSet rs, Class<T> clazz)
			throws SQLException, InstantiationException, IllegalAccessException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
		List<T> list = new ArrayList<T>();
		Field[] fields = clazz.getDeclaredFields();
		while (rs.next()) {
			T obj = clazz.newInstance();
			for (int i = 1; i <= colCount; i++) {
				Object value = rs.getObject(i);
				for (int j = 0; j < fields.length; j++) {
					Field f = fields[j];
					if (f.getName().equalsIgnoreCase(rsmd.getColumnName(i))) {
						boolean flag = f.isAccessible();
						f.setAccessible(true);
						f.set(obj, value);
						f.setAccessible(flag);
					}
				}
			}
			list.add(obj);
		}
		return list;
	}

	default Connection initDb(String ip, int port, String dbUserName, String dbPassword, String databaseName)
			throws SQLException {
		String tempIp = null != ip ? ip : DbAdapter.ip;
		tempIp = null != System.getenv("MYSQL_DB_IP") ? System.getenv("MYSQL_DB_IP") : tempIp;

		int tempPort = port > 0 ? port : DbAdapter.port;
		tempPort = null != System.getenv("MYSQL_DB_PORT") ? Integer.parseInt(System.getenv("MYSQL_DB_PORT")) : tempPort;

		String tempDbUserName = DbAdapter.dbUserName;
		String tempDbPassword = DbAdapter.dbPassword;
		LOGGER.info("mysql ip: {} port: {}", tempIp, tempPort);

		if (null != dbUserName && !dbUserName.isEmpty()) {
			tempDbUserName = dbUserName;
		}
		if (null != dbPassword && !dbPassword.isEmpty()) {
			tempDbPassword = dbPassword;
		}

		Statement stmt;
		Connection con = null;
		try {
			Class.forName(jdbcName);
			LOGGER.info("DB driver load success.");
		} catch (ClassNotFoundException e) {
			LOGGER.error("DB driver load error:", e);
		}
		try {
			LOGGER.info("Get DB connection start.");
			con = DriverManager.getConnection("jdbc:mysql://" + tempIp + ":" + tempPort + "/", tempDbUserName,
					tempDbPassword);
			LOGGER.info("Get DB connection success.");
			stmt = con.createStatement();
		} catch (SQLException e) {
			LOGGER.error("Get DB connection error: ", e);
			return null;
		}
		stmt.execute("use " + databaseName);
		return con;
	}
}
