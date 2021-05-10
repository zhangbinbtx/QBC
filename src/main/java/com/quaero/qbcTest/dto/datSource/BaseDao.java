package com.quaero.qbcTest.dto.datSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class BaseDao {

	private static String url = "jdbc:firebirdsql:localhost/3050:"+System.getProperty("user.dir")+"/QS800.FDB";
	private static String user= "SYSDBA";;
	private static String password="masterkey";
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	public Connection getConnection() {
		try {
			//Class.forName(driver);
			// 1.加载驱动程序
			   Class.forName("org.firebirdsql.jdbc.FBDriver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public void closeAll() {
		if (null != rs) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (null != pstmt) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (null != conn) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    /**
     * 修改、删除
     * @param sql
     * @param objects
     * @return
     */
	public int executeUpdate(String sql, Object... objects) {
		getConnection();
		int rows = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < objects.length; i++) {
				pstmt.setObject(i + 1, objects[i]);
			}
			rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return rows;
	}
    /**
     * 查询
     * @param sql
     * @param list
     * @return
     */
	public ResultSet executeQuery(String sql, List<Object> list) {
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				pstmt.setObject(i + 1, list.get(i));
			}
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
}	
