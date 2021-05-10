package com.quaero.qbcTest.dto.datSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.quaero.qbcTest.util.LoadPropertiesFileUtil;

public class AccbaseDao {

	//private static String driver="org.objectweb.rmijdbc.Driver";
	//private static String url = "jdbc:rmi://10.23.10.6/jdbc:odbc:accessDatabase";
	private static String url = "jdbc:Access:///"+LoadPropertiesFileUtil.getPropert("AccessUrl");
	private static String user= "";;
	private static String password="";
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public Connection getConnection() {
		try {
			//Class.forName(driver);
			// 1.加载驱动程序
			   Class.forName("com.hxtt.sql.access.AccessDriver");
			   conn = DriverManager.getConnection(url);//获取数据库连接
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
	public static void main(String[] args) {
		String sql="SELECT * FROM ALARM_2052 ORDER BY CODE";
		List<Object> olist = new ArrayList<Object>();
		ResultSet rs = new AccbaseDao().executeQuery(sql,olist);
		System.out.println(rs);
	}

}
