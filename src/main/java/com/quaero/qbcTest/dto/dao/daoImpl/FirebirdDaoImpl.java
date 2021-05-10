package com.quaero.qbcTest.dto.dao.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.quaero.qbcTest.dto.dao.FirebirdDao;
import com.quaero.qbcTest.dto.datSource.BaseDao;
import com.quaero.qbcTest.dto.entity.AlarmInfo;
import com.quaero.qbcTest.dto.entity.Alarmcode;
import com.quaero.qbcTest.dto.entity.ReagentInfoDetail;
import com.quaero.qbcTest.dto.entity.SampleInfo;
import com.quaero.qbcTest.dto.entity.WashCell;
import com.quaero.qbcTest.dto.entity.WashReagent;
import com.quaero.qbcTest.dto.entity.WashSample;

public class FirebirdDaoImpl extends BaseDao implements FirebirdDao{
	@Override
	public  List<AlarmInfo> getTest() throws SQLException {
		String sql = "SELECT * FROM T_ALARM ORDER BY SAVEDATE DESC, SAVETIME DESC, ID DESC";
		List<AlarmInfo> list = new ArrayList<AlarmInfo>();
		List<Object> olist = new ArrayList<Object>();
		AlarmInfo goddess = null;
		try {
			ResultSet rs = this.executeQuery(sql, olist);
			while(rs.next()) {
				   goddess = new AlarmInfo();
				   goddess.setId(rs.getInt("ID"));
				   goddess.setMODULE_NO(rs.getInt("MODULE_NO"));
				   goddess.setMODULE_TYPE(rs.getInt("MODULE_TYPE"));
				   goddess.setCODE(rs.getString("CODE"));
				   goddess.setLEVEL_NO(rs.getInt("LEVEL_NO"));
				   goddess.setCONTENT(rs.getString("CONTENT"));
				   goddess.setSAVEDATE(rs.getString("SAVEDATE"));
				   goddess.setSAVETIME(rs.getString("SAVETIME"));
				   list.add(goddess);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.closeAll();
		}
		  return list;
	}

	@Override
	public List<Alarmcode> getErrorGroupTime() {
		String sql = "SELECT CODE,SAVEDATE,CONTENT,COUNT(CODE) AS NUM "+
   "FROM T_ALARM "+
    "GROUP BY  CODE,SAVEDATE,CONTENT "+
     "ORDER BY SAVEDATE ASC, CODE DESC,CONTENT DESC ";
		List<Alarmcode> list = new ArrayList<Alarmcode>();
		List<Object> olist = new ArrayList<Object>();
		Alarmcode goddess = null;
		try {
			ResultSet rs = this.executeQuery(sql, olist);
			while(rs.next()) {
				   goddess = new Alarmcode();
				   goddess.setCount(rs.getInt("NUM"));
				   goddess.setCODE(rs.getString("CODE"));
				   goddess.setSAVEDATE(rs.getString("SAVEDATE"));
				   goddess.setCONTENT(rs.getString("CONTENT"));
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
	@Override
	public List<ReagentInfoDetail> getAllModuleList(int specical) {
		 String sql = "SELECT * FROM T_REAGENT"
	                + " WHERE SPECIAL = "+specical
	                + " ORDER BY MODULE_NO, DISKNO, REA_POS";
		 List<ReagentInfoDetail> list = new ArrayList<ReagentInfoDetail>();
		 List<Object> olist = new ArrayList<Object>();
		 ReagentInfoDetail goddess=null;
		 try {
				ResultSet rs = this.executeQuery(sql, olist);
				while(rs.next()) {
					   goddess = new ReagentInfoDetail();
					   goddess.setReaID(rs.getInt("ID"));
					   goddess.setModuleNo(rs.getInt("MODULE_NO"));
					   goddess.setReaDisk(rs.getInt("DISKNO"));
					   goddess.setReaPos(rs.getInt("REA_POS"));
					   goddess.setReaName(rs.getString("REA_NAME"));
					   goddess.setReaType(rs.getInt("REA_TYPE"));
					   goddess.setBottleNo(rs.getString("BOTTLE_NO"));
					   goddess.setLotNumber(rs.getString("LOT_NUMBER"));
					   goddess.setBottleSize(rs.getInt("BOTTLE_SIZE"));
					   goddess.setRemain(rs.getString("REMAIN"));
					   goddess.setRemainCount(rs.getInt("REMAIN_COUNT"));
					   goddess.setProductionDate(rs.getDate("PRODUCTION_DATE"));
					   goddess.setProductionTime(rs.getDate("PRODUCTION_TIME"));
					   goddess.setExpirationDate(rs.getDate("EXPIRATION_DATE"));
					   goddess.setExpirationTime(rs.getDate("EXPIRATION_TIME"));
					   goddess.setOpenDate(rs.getDate("OPEN_DATE"));
					   goddess.setOpenTime(rs.getDate("OPEN_TIME"));
					   goddess.setExpirationTimeOpen(rs.getDate("EXPIRATION_DATE_OPEN"));
					   goddess.setExpirationDateOpen(rs.getDate("EXPIRATION_TIME_OPEN"));
					   goddess.setBarcode(rs.getString("BARCODE"));
					   goddess.setRemark(rs.getString("REMARK"));
					   goddess.setEnabled(rs.getBoolean("ENABLED"));
					   goddess.setSpecial(rs.getInt("SPECIAL"));
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

	@Override
	public List<WashReagent> getReagentPolluteList() {
		 String sql = "SELECT * FROM T_WASH_REAGENT ORDER BY ID";
         List<WashReagent> itemList = new ArrayList<WashReagent>();
         List<Object> olist = new ArrayList<Object>();
         WashReagent goddess=null;
		 try {
				ResultSet rs = this.executeQuery(sql, olist);
				while(rs.next()) {
					   goddess = new WashReagent();
					   goddess.setWashID(rs.getInt("ID"));
					   goddess.setModuleNo(rs.getInt("MODULE_NO"));
					   goddess.setProName(rs.getString("PRO_NAME"));
					   goddess.setFromItem(rs.getString("FROM_ITEM"));
					   goddess.setToItem(rs.getString("TO_ITEM"));
					   goddess.setFromType(rs.getInt("FROM_TYPE"));
					   goddess.setToType(rs.getInt("TO_TYPE"));
					   goddess.setVolume(rs.getInt("VOLUME"));
					   itemList.add(goddess);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				this.closeAll();
			}
		return itemList;
	}

	@Override
	public List<WashSample> getSamplePolluteList() {
		 String sql = "SELECT * FROM T_WASH_SAMPLE ORDER BY ID";
         List<WashSample> itemList = new ArrayList<WashSample>();
         List<Object> olist = new ArrayList<Object>();
         WashSample goddess=null;
		 try {
				ResultSet rs = this.executeQuery(sql, olist);
				while(rs.next()) {
					   goddess = new WashSample();
					   goddess.setWashID(rs.getInt("ID"));
					   goddess.setModuleNo(rs.getInt("MODULE_NO"));
					   goddess.setItemName(rs.getString("ITEM_NAME"));
					   goddess.setWashType(rs.getInt("WASH_TYPE"));
					   goddess.setWashVol(rs.getInt("WASH_VOL"));
					   
					   itemList.add(goddess);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				this.closeAll();
			}
		return itemList;
	}

	@Override
	public List<WashCell> getReactCupPolluteList() {
		String sql = "SELECT * FROM T_WASH_CELL ORDER BY ID";
        List<WashCell> itemList = new ArrayList<WashCell>();
        List<Object> olist = new ArrayList<Object>();
        WashCell goddess=null;
		 try {
				ResultSet rs = this.executeQuery(sql, olist);
				while(rs.next()) {
					   goddess = new WashCell();
					   goddess.setWashID(rs.getInt("ID"));
					   goddess.setModuleNo(rs.getInt("MODULE_NO"));
					   goddess.setFromItemName(rs.getString("FROM_ITEM"));
					   goddess.setToItemName(rs.getString("TO_ITEM"));
					   goddess.setR1Pos(rs.getInt("R1_POS"));
					   goddess.setR1Vol(rs.getInt("R1_VOL"));
					   goddess.setR2Pos(rs.getInt("R2_POS"));
					   goddess.setR2Vol(rs.getInt("R2_VOL"));
					   itemList.add(goddess);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				this.closeAll();
			}
		return itemList;
	}

	@Override
	public List<SampleInfo> getSampleBarcodeList(LocalDate localDate) {
		String sql = "SELECT * FROM T_SAMPLE_INFO "
                + " WHERE TESTING_DATE = "+localDate;
        List<SampleInfo> itemList = new ArrayList<SampleInfo>();
        List<Object> olist = new ArrayList<Object>();
        SampleInfo goddess=null;
		 try {
				ResultSet rs = this.executeQuery(sql, olist);
				while(rs.next()) {
					   goddess = new SampleInfo();
					   goddess.setSampleID(rs.getInt("ID"));
					   goddess.setSampleNo(rs.getInt("SAMPLE_NO"));
					   goddess.setModuleNo(rs.getInt("MODULE_NO"));
					   goddess.setSampleDisk(rs.getInt("DISKNO"));
					   goddess.setSamplePos(rs.getInt("SAMPLE_POS"));
					   goddess.setRack(rs.getInt("RACK"));
		               goddess.setRackPos(rs.getInt("RACKPOS"));
		               goddess.setIsRack(rs.getInt("IS_RACK"));
		               goddess.setBarcode(rs.getString("BARCODE"));
		               goddess.setSampleType(rs.getInt("SAMPLE_TYPE"));
		               goddess.setCupType(rs.getInt("CUP_TYPE"));
		               goddess.setSamplingDate(rs.getDate("SAMPLING_DATE"));
		               goddess.setSamplingTime(rs.getDate("SAMPLING_TIME"));
		               goddess.setSendingDate(rs.getDate("SENDING_DATE"));
		               goddess.setSendingTime(rs.getDate("SENDING_TIME"));
		               goddess.setTestingDate(rs.getDate("TESTING_DATE"));
		               goddess.setTestingTime(rs.getDate("TESTING_DATE"));
		               goddess.setPatientType(rs.getString("PATIENT_TYPE"));
		               goddess.setDepartment(rs.getString("DEPARTMENT"));
		               goddess.setArea(rs.getString("AREA"));
		               goddess.setWard(rs.getString("WARD"));
		               goddess.setBedNo(rs.getString("BEDNO"));
		               goddess.setDoctor(rs.getString("DOCTOR"));
		               goddess.setChecker(rs.getString("CHECKER"));
		               goddess.setAssessor(rs.getString("ASSESSOR"));
		               goddess.setClinic(rs.getString("CLINIC"));
		               goddess.setRemark(rs.getString("REMARK"));
		               goddess.setAuditingFlag(rs.getBoolean("AUDITING_FLAG"));
		               goddess.setPrintingFlag(rs.getBoolean("PRINTING_FLAG"));
		               goddess.setEmerFlag(rs.getBoolean("EMERGENCY_FLAG"));
		               goddess.setBlankFlag(rs.getBoolean("SAMPLE_BLANK_FLAG"));
		               goddess.setDeleteFlag(rs.getBoolean("DELETE_FLAG"));
		               goddess.setRepeat(rs.getInt("REPEAT"));
		               goddess.setPatCaseNo(rs.getString("PAT_CASENO"));
		               goddess.setPatName(rs.getString("PAT_NAME"));
		               goddess.setPatGender(rs.getInt("PAT_GENDER"));
		               goddess.setPatAge(rs.getInt("PAT_AGE"));
		               goddess.setAgeUnit(rs.getInt("AGE_UNIT"));
		               goddess.setPatNation(rs.getString("PAT_NATION"));
		               goddess.setPatBirthDate(rs.getDate("PAT_BIRTHDATE"));
		               goddess.setLisNo(rs.getString("MODULE_NO"));
					   itemList.add(goddess);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				this.closeAll();
			}
		return itemList;
	}



}
