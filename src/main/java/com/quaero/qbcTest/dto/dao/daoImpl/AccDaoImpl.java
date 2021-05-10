package com.quaero.qbcTest.dto.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.quaero.qbcTest.dto.dao.AccDao;
import com.quaero.qbcTest.dto.datSource.AccbaseDao;
import com.quaero.qbcTest.dto.entity.AlarmInfoDetail;
import com.quaero.qbcTest.dto.entity.ReagentInfoDetail;

public class AccDaoImpl extends AccbaseDao implements AccDao{



	@Override
	public List<AlarmInfoDetail> getUpAndDown() {
		String sql = "SELECT * FROM ALARM_2052 ORDER BY CODE";
		List<AlarmInfoDetail> list = new ArrayList<AlarmInfoDetail>();
		List<AlarmInfoDetail> list1 = new ArrayList<AlarmInfoDetail>();
		List<Object> olist = new ArrayList<Object>();
		AlarmInfoDetail goddess = null;
		try {
			ResultSet rs = this.executeQuery(sql, olist);
			while(rs.next()) {
				   goddess = new AlarmInfoDetail();
				   goddess.setID(rs.getInt("ID"));
				   goddess.setCode(rs.getString("CODE"));
				   goddess.setContent(rs.getString("CONTENT"));
				   goddess.setLevel(rs.getInt("LEVEL"));
				   goddess.setDescription(rs.getString("DESCRIPTION"));
				   goddess.setSolutions(rs.getString("SOLUTIONS"));
				   goddess.setEnabled(rs.getBoolean("ENABLED"));
				   goddess.setDefEnabled(rs.getInt("DEFENABLED"));
				   goddess.setDataType(rs.getInt("DATATYPE"));
				   list.add(goddess);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			this.closeAll();
		}
		return list;
	}
	

}
