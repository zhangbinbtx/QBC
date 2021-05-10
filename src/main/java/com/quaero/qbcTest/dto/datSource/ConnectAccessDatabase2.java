package com.quaero.qbcTest.dto.datSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.quaero.qbcTest.dto.entity.AlarmInfoDetail;

public class ConnectAccessDatabase2 {

	public static void main(String[] args) {
		try {
//			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//			String url = "jdbc:ucanaccess://D://workspace//QBCTest//QS_ALARM.mdb";
			Class.forName("com.hxtt.sql.access.AccessDriver");
			String url = "jdbc:Access:///D:/workspace/QBCTest/QS_ALARM.mdb";
			Connection con = DriverManager.getConnection(url);//没有用户名和密码的时候直接为空
			Statement sta = con.createStatement();
			ResultSet stat = sta.executeQuery("SELECT * FROM ALARM_2052 ORDER BY CODE");//demoTable为access数据库中的一个表名
//			if(stat.next()){
//				System.out.println("纯java代码实现:" + stat.getString("ID"));
//			}
			List<AlarmInfoDetail> list = new ArrayList<AlarmInfoDetail>();
			//List list1 = new ArrayList<String>();
			List<Object> olist = new ArrayList<Object>();
			AlarmInfoDetail goddess = null;
			while(stat.next()) {
				   goddess = new AlarmInfoDetail();
				   goddess.setID(stat.getInt("ID"));
				   goddess.setCode(stat.getString("CODE"));
				   goddess.setContent(stat.getString("CONTENT"));
				   goddess.setLevel(stat.getInt("LEVEL"));
				   goddess.setDescription(stat.getString("DESCRIPTION"));
				   goddess.setSolutions(stat.getString("SOLUTIONS"));
				   goddess.setEnabled(stat.getBoolean("ENABLED"));
				   goddess.setDefEnabled(stat.getInt("DEFENABLED"));
				   goddess.setDataType(stat.getInt("DATATYPE"));
				   list.add(goddess);
			}
			System.out.println(list);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
