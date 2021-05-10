package com.quaero.qbcTest.server;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Dictionary;
import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;

import com.quaero.qbcTest.base.ErpcModelCallback.IEventDllCall;
import com.quaero.qbcTest.dto.entity.AlarmInfoDetail;
import com.quaero.qbcTest.dto.entity.Alarmcode;
import com.quaero.qbcTest.dto.entity.ReagentInfoDetail;
import com.quaero.qbcTest.dto.entity.SampleInfo;
import com.quaero.qbcTest.dto.entity.WashCell;
import com.quaero.qbcTest.dto.entity.WashReagent;
import com.quaero.qbcTest.dto.entity.WashSample;
import com.quaero.qbcTest.util.Best;
import com.quaero.qbcTest.dto.DataPackage;
import com.quaero.qbcTest.dto.DeviceDebugPackageHead;
import com.quaero.qbcTest.dto.entity.AlarmInfo;


public interface QbcTestApi {
    /**
     * 初始化连接
     */
	public boolean qbcInit(String[] machineIp,String trackIp,int localTrackPort, int localMachinePort,boolean machineState,boolean transState);
    /**
     * //设备故障
     * @return
     * @throws SQLException
     */
	public  List<AlarmInfo> getErrorMap() throws SQLException;
    /**
     * 故障列表、释义
     * @return
     */
	public List<AlarmInfoDetail> getUpAndDown();
	 /**
     * 按时间分组设备故障
     * @return
     * @throws SQLException
     */
	public List<Alarmcode> getErrorGroupTime();
	
	public boolean sendToMachine(String ip, int moduleId, String cmd, byte[] obj, int count) throws Exception;
	/**
	 * 发送数据
	 * @param moduleId
	 * @param cmd
	 * @param obj
	 * @throws IOException
	 */
	public boolean sendToMachine(String ip,int moduleId, int cmd, byte[] obj) throws Exception;
	public boolean sendToMachine(String machineIP1, int moduleId, String strId, byte[] dataIn) throws Exception;
	public boolean sendToMachine(String machineIP1, int moduleId, String strId) throws Exception;

	/**
	 * 试剂信息列表查询
	 * @param specical
	 * @return
	 */
	public List<ReagentInfoDetail> getAllModuleList(int specical) throws Exception;
	/**
	 * 试剂针交叉污染查询
	 * @return
	 * @throws Exception
	 */
	public List<WashReagent> getReagentPolluteList() throws Exception;
	
	/**
	 * 样本针交叉污染查询
	 * @return
	 * @throws Exception
	 */
	public List<WashSample> getSamplePolluteList() throws Exception;
	
	/**
	 * 反应杯交叉污染
	 * @return
	 * @throws Exception
	 */
	public List<WashCell> getReactCupPolluteList() throws Exception;
	
	public List<SampleInfo> getSampleBarcodeList(LocalDate localDate) throws Exception;
	
	/**
	 * 获取队列数据
	 * @return
	 * @throws Exception
	 */
	public Queue<DataPackage> getDic() throws Exception;
	/**
	 * 获取联机状态
	 */
	public boolean getState() throws Exception;
	/**
	 * 获取样本、搅拌、反应、试剂返回数据
	 * @param boardID
	 * @param bytg
	 * @return
	 */
	public byte[] getOptoStatus(byte boardID, DeviceDebugPackageHead head);
	/**
	 * 获取AD返回数据
	 * @param boardID
	 * @param head
	 * @return
	 */
	public byte[] getAdStatus(byte boardID, DeviceDebugPackageHead head);
	
	

}
