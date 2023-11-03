package com.ssafy.ux.finalpj.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/UX?serverTimezone=UTC";
	private static final String DB_ID = "root";
	private static final String DB_PWD = "ssafy";

//	private static final String DRIVER = "org.h2.Driver";
//	private static final String URL = "jdbc:h2:tcp://localhost/~/test;MODE=MySQL"; //jdbc:h2:~/test
//	private static final String DB_ID = "sa";
//	private static final String DB_PWD = "";
	
	private static DBUtil instance = new DBUtil();

	private DBUtil() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static DBUtil getInstance() {
		return instance;
	}
	

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, DB_ID, DB_PWD);
	}
	
//	public static void close(PreparedStatement pstmt, Connection conn) {
//		try {
//			if(pstmt != null)
//				pstmt.close();
//			if(conn != null)
//				conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
//		try {
//			if(rs != null)
//				rs.close();
//			if(pstmt != null)
//				pstmt.close();
//			if(conn != null)
//				conn.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void close(AutoCloseable... autoCloseables) {
		for(AutoCloseable ac : autoCloseables) {
			if(ac != null) {
				try {
					ac.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}