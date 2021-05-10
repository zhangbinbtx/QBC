package com.quaero.qbcTest.dto.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.quaero.qbcTest.dto.entity.AlarmInfoDetail;
import com.quaero.qbcTest.dto.entity.Alarmcode;
import com.quaero.qbcTest.dto.entity.ReagentInfoDetail;
import com.quaero.qbcTest.dto.entity.SampleInfo;
import com.quaero.qbcTest.dto.entity.WashCell;
import com.quaero.qbcTest.dto.entity.WashReagent;
import com.quaero.qbcTest.dto.entity.WashSample;
import com.quaero.qbcTest.dto.entity.AlarmInfo;

public interface FirebirdDao {

	    public  List<AlarmInfo> getTest() throws SQLException;

		public List<Alarmcode> getErrorGroupTime();
		public List<ReagentInfoDetail> getAllModuleList(int specical);

		public List<WashReagent> getReagentPolluteList();

		public List<WashSample> getSamplePolluteList();

		public List<WashCell> getReactCupPolluteList();

		public List<SampleInfo> getSampleBarcodeList(LocalDate localDate);
		


}
