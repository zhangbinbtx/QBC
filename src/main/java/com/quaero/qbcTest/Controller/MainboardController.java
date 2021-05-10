package com.quaero.qbcTest.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.quaero.qbcTest.Enum.DeviceBoardID;
import com.quaero.qbcTest.Enum.DeviceCommand;
import com.quaero.qbcTest.Enum.ReaType;
import com.quaero.qbcTest.base.ErpcModelCallback;
import com.quaero.qbcTest.base.ErpcModelCallback.EventDllCallImpl;
import com.quaero.qbcTest.dto.DataPackage;
import com.quaero.qbcTest.dto.entity.DeviceDiskReaMultiPosition;
import com.quaero.qbcTest.dto.entity.DeviceModuleReaMultiPosition;
import com.quaero.qbcTest.dto.entity.DeviceReaMultiPosition;
import com.quaero.qbcTest.dto.entity.DeviceReactCupProbePollute;
import com.quaero.qbcTest.dto.entity.DeviceReagentProbePollute;
import com.quaero.qbcTest.dto.entity.DeviceReagentScan;
import com.quaero.qbcTest.dto.entity.DeviceSampleProbePollute;
import com.quaero.qbcTest.dto.entity.DeviceSampleScan;
import com.quaero.qbcTest.dto.entity.ItemIDManager;
import com.quaero.qbcTest.dto.entity.ReagentIDManager;
import com.quaero.qbcTest.dto.entity.ReagentInfo;
import com.quaero.qbcTest.dto.entity.ReagentInfoDetail;
import com.quaero.qbcTest.dto.entity.SampleInfo;
import com.quaero.qbcTest.dto.entity.WashCell;
import com.quaero.qbcTest.dto.entity.WashReagent;
import com.quaero.qbcTest.dto.entity.WashSample;
import com.quaero.qbcTest.server.QbcTestApi;
import com.quaero.qbcTest.util.Best;
import com.quaero.qbcTest.util.Predicate;
import com.quaero.qbcTest.util.Predicates;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/mainboard")
public class MainboardController extends baseController {
	@Autowired
	private QbcTestApi api;
	@Autowired
	private Best best;
	private boolean getcon = false;
	private ErpcModelCallback.IEventDllCall evc = new EventDllCallImpl();

	/**
	 * 查询状态
	 * 
	 * @throws Exception
	 */
	@GetMapping("/status")
	public boolean getCon() throws Exception {
		// byte[] dataIn = new byte[4];
		byte[] dataIn = new byte[1];
		boolean initstate = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.Status.getStrId(), dataIn, 0);
		log.info("logback的--info日志--输出了");
		initstate=api.getState();
		System.out.println("initstate1=" + initstate);
		return initstate;
	}

	/**
	 * 连接时查询连接状态，若连接去脱机，若脱机去连接，主要是防止重复连接导致项目挂掉
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/init")
	public String getInit() throws Exception {
		System.out.println("进入init");
		String mess = "";
		try {
			String[] machineIp = new String[4];
			machineIp[0] = best.getMachineIP1();
			String trackIp = best.getTrackIP();
			int localMachine = best.getLocalMachinePort();
			int localTrack = best.getLocalTrackPort();
			boolean ret = false;
			boolean initstate = api.qbcInit(machineIp, trackIp, localTrack, localMachine, true, true);
			System.err.println("initstate=" + initstate);
			Thread.sleep(1000);
			initstate=api.getState();
			if (initstate == false) {
				for(int i=0;i<4;i++){
					long time = System.currentTimeMillis();
					byte[] dataIn = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(time).array();
					ret = api.sendToMachine(best.getMachineIP1(), i, DeviceCommand.Online.getStrId(), dataIn);
					if (ret == true) {
						mess = "联机成功 ！";
					}
				}
				
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}

		return mess;

	}

	/**
	 * 脱机(可执行)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/offline")
	public String offline() throws Exception {
		System.out.println("进入Offline");
		boolean ret = false;
		String  mess="";
		try {
			byte[] dataIn = new byte[4];
		    ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.Offline.getStrId(), dataIn);
		    Thread.sleep(1000);
		    boolean initstate=api.getState();
				if (initstate == false) {
					mess = "脱机成功！";
				}
			
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return mess;
	}

	/**
	 * 复位(可执行)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reset")
	public boolean getReset() throws Exception {
		System.out.println("进入reset");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[1];
			dataIn[0] = (byte) (0xff & 0xff);
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.Reset.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 急停(可执行)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/suddenStop")
	public boolean suddenStop() throws Exception {
		System.out.println("进入suddenStop");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[1];
			dataIn[0] = 9;
			System.out.println("dataIn[0]=" + dataIn[0]);
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.Stop.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 模拟测试(可执行)
	 * 
	 * @param index
	 *            执行次数
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/mechanicalActionCheck/{index}")
	public boolean mechanicalActionCheck(@PathVariable("index") int index) throws Exception {
		System.out.println("进入mechanicalActionCheck");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (index & 0xff);
			dataIn[1] = (byte) ((index >> 8) & 0xFF);
			dataIn[2] = (byte) ((index >> 16) & 0xFF);
			dataIn[3] = (byte) ((index >> 24) & 0xFF);
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.MechanicalActionCheck.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 报警信息(可执行)
	 * 
	 * @param index
	 *            0x01 板号
	 * @param level
	 *            0x01 级别
	 * @param code
	 *            0x02 报警码-高位大类，低位小类 0x0C 预留
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/alarm/{index}/{level}/{code}")
	public boolean alarm(@PathVariable("index") int index, @PathVariable("level") int level,
			@PathVariable("code") int code) throws Exception {
		System.out.println("进入Alarm");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[10];
			dataIn[0] = (byte) (index & 0xff);
			dataIn[1] = (byte) (level & 0xff);
			dataIn[2] = (byte) (code & 0xff);
			dataIn[3] = (byte) ((code >> 8) & 0xFF);
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.Alarm.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 杯空白测试(可执行)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/cupBlankCheck")
	public boolean cupBlankCheck() throws Exception {
		System.out.println("进入CupBlankCheck");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.CupBlankCheck.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 光亮检查(可执行)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/shinecheck")
	public boolean shinecheck() throws Exception {
		System.out.println("进入shinecheck");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.CupLightCheck.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 版本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/version")
	public String version() throws Exception {
		System.out.println("进入version");
		boolean ret = false;
		String mess = "";
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = 1;
			DeviceCommand cdmstr = DeviceCommand.Version;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.Version.getStrId(), dataIn);
			Thread.sleep(1000);
			Queue<DataPackage> commandDict = api.getDic();
			int queuenum = commandDict.size();
			if (queuenum > 0) {
				for (int i = 0; i < queuenum; i++) {
					DataPackage ck = commandDict.poll();
					String version = "";
					String headstr = "";
					if (ck.getHead().getCommand() == cdmstr.getId()) {
						byte[] bytes = ck.getBody();
						for (int j = 0; j < bytes.length; j++) {
							if (j == (bytes.length - 1)) {
								version += bytes[j];
							} else if (j == 0) {
								headstr = GetBoardText(bytes[j]);
							} else {
								version += bytes[j] + ".";
							}
						}
						version = String.format("模块: 生化%s,板子%s,应用程序版本信息: v%s", 1, headstr, version);
						System.out.println("版本信息=" + version);
						mess += version + System.getProperty("line.separator");
					}

				}
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return mess;
	}

	private String GetBoardText(int BoardNo) {
		String BoardText = "";

		switch (DeviceBoardID.getWeekByValue(BoardNo)) {
		case AD:
			BoardText = "AD板";
			break;
		case Comm:
			BoardText = "中位机";
			break;
		case Main:
			BoardText = "主控";
			break;
		case AC:
			BoardText = "交流板";
			break;
		case React:
			BoardText = "反应板";
			break;
		case Reagent1:
			BoardText = "试剂板1";
			break;
		case Reagent2:
			BoardText = "试剂板2";
			break;
		case Refrigeration:
			BoardText = "制冷板";
			break;
		case Sample:
			BoardText = "样本板";
			break;
		case Stir:
			BoardText = "搅拌板";
			break;
		case Track:
			BoardText = "轨道";
			break;
		default:
			break;
		}
		return BoardText;
	}

	public static void main(String[] args) {
		// String aa =
		// Integer.toHexString(DeviceCommand.Version.getId()).toUpperCase();
		String ccd = "000B";
		int t = Integer.parseInt(ccd.toLowerCase(), 16);
		// Long ccb=Long.valueOf("0X"+ccd);
		// System.out.println("cmdcode1="+Long.toHexString(ccb).toUpperCase());
		System.out.println(t);
	}

	/**
	 * 加样暂停
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/addSampleSuspend")
	public boolean addSampleSuspend() throws Exception {
		System.out.println("进入addSampleSuspend");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.SamplePause.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 加样继续
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/addSampleContinue")
	public boolean addSampleContinue() throws Exception {
		System.out.println("进入addSampleContinue");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.SampleContinue.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 试剂条码扫描(未完成)
	 * 
	 * @param diskPostionList1
	 *            内圈
	 * @param diskPostionList2
	 *            外圈
	 * @return
	 * @throws Exception
	 */
	// @GetMapping("/reagentBarcodeScan/{DiskPostionList1}/{DiskPostionList2}")
	// public boolean reagentBarcodeScan(@PathVariable("DiskPostionList1")
	// List<Boolean> diskPostionList1,@PathVariable("DiskPostionList2")
	// List<Boolean> diskPostionList2) throws Exception {
	@GetMapping("/reagentBarcodeScan")
	public boolean reagentBarcodeScan() throws Exception {
		System.out.println("进入reagentBarcodeScan");
		boolean ret = false;
		// if((diskPostionList1.size()+diskPostionList2.size())>0){
		// throw new Exception("请选择要扫描的试剂条码位置！");
		// }
		try {
			DeviceReagentScan drc = new DeviceReagentScan();
			drc.DiskPostion1 = new byte[80];
			drc.DiskPostion2 = new byte[80];
			// for (int i = 0; i < diskPostionList1.size(); i++) {// 默认都扫描
			for (int i = 0; i < 80; i++) {// 默认都扫描
				// if(diskPostionList1.get(i)==true)
				drc.DiskPostion1[i] = 1;
			}
			// for (int i = 0; i < diskPostionList2.size(); i++) {// 默认都扫描
			for (int i = 0; i < 80; i++) {// 默认都扫描
				// if(diskPostionList2.get(i)==true)
				drc.DiskPostion2[i] = 1;
			}
			byte[] dataIn = JSON.toJSONString(drc).getBytes();
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ReagentBarcodeScan.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 样本盘扫描(未完成)
	 * 
	 * @param diskPostionList
	 *            样本盘
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/Initialization/{DiskPostionList}")
	public boolean Initialization(@PathVariable("DiskPostionList1") List<Boolean> diskPostionList) throws Exception {
		System.out.println("进入Initialization");
		boolean ret = false;
		if (diskPostionList.size() > 0) {
			throw new Exception("请选择要扫描的试剂条码位置！");
		}
		try {
			// byte[] dataIn = new byte[4];
			DeviceSampleScan drc = new DeviceSampleScan();
			drc.SampleDiskPostion = new byte[50];
			for (int i = 0; i < diskPostionList.size(); i++) {// 默认都扫描
				if (diskPostionList.get(i) == true)
					drc.SampleDiskPostion[i] = 1;
			}
			byte[] dataIn = JSON.toJSONString(drc).getBytes();
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.Initialization.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 查询样本条码
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<SampleInfo> getSampleBarcodeList() throws Exception {
		System.out.println("进入getSampleBarcodeList");
		// long time=new Date().getTime();
		// long time = System.currentTimeMillis();
		List<SampleInfo> samlist = api.getSampleBarcodeList(LocalDateTime.now().toLocalDate());
		return samlist;
	}

	/**
	 * 清洗反应杯
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reactCupWash")
	public boolean reactCupWash() throws Exception {
		System.out.println("进入ReactCupWash");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ReactCupWash.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 开始测试(未完成)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/ADData")
	public boolean ADData() throws Exception {
		System.out.println("进入ADData");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ADData.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 启动测试 index 0正常测试 1性能测试
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/StartTest/{index}")
	public boolean StartTest(@PathVariable("index") byte index) throws Exception {
		System.out.println("进入StartTest");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[1];
			dataIn[0] = index;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.StartTest.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 多试剂位(未完成)
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reagentMultiPosition/{moduleID}")
	public boolean reagentMultiPosition(@PathVariable("moduleID") int moduleID) throws Exception {
		System.out.println("进入StartTest");
		boolean ret = false;
		try {
			DeviceModuleReaMultiPosition dmrmp = new DeviceModuleReaMultiPosition();
			dmrmp.ReagentLimit = 0x0A;
			dmrmp.Disk = new DeviceDiskReaMultiPosition[2];
			String[] logs = new String[3];
			logs[0] = String.format("SendModuleReaMultiPosition: %s", moduleID);
			for (int i = 0; i < 2; i++) {
				FillDiskReaMultiPositions(moduleID, i + 1, dmrmp.Disk[i]);
				logs[i + 1] = String.format("Disk=%d, [", i + 1);
				for (int k = 0; k < dmrmp.Disk[i].Positions.length; k++) {
					logs[i + 1] += String.format("<%d,%d,%d>", k, dmrmp.Disk[i].Positions[k].ID,
							dmrmp.Disk[i].Positions[k].Order);
				}
				logs[i + 1] += logs[i + 1] + "]";
			}
			byte[] dataIn = new byte[1];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ReagentMultiPosition.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/// <summary>
	/// 试剂信息列表
	/// </summary>
	// private static List<ReagentInfoDetail> _reagentList = new
	/// ArrayList<ReagentInfoDetail>();
	private void FillDiskReaMultiPositions(int moduleID, int disk, DeviceDiskReaMultiPosition diskReaMultiPosition)
			throws Exception {
		List<ReagentInfoDetail> _reagentList = api.getAllModuleList(0);
		List<ReagentInfoDetail> reaList;
		reaList = Predicates
				.findAll(_reagentList.toArray(new ReagentInfoDetail[] {}), new Predicate<ReagentInfoDetail>() {
					@Override
					public boolean isMatch(ReagentInfoDetail n) {
						return n.getModuleNo() == moduleID && n.ReaDisk == disk
								&& ((n.ReaName != null && !"".equals(n.ReaName) && n.RemainCount > 0))
								|| ((n.ReaType == ReaType.Diluent.getValue()) && (Double.parseDouble(n.Remain) > 0));
					}
				}).stream()
				.sorted(Comparator.comparing(ReagentInfoDetail::getReaName).thenComparing(ReagentInfoDetail::getReaType)
						.thenComparing(ReagentInfoDetail::getExpirationDate)
						.thenComparing(ReagentInfoDetail::getRemainCount).thenComparing(ReagentInfoDetail::getReaPos))
				.collect(Collectors.toList());

		DeviceReaMultiPosition[] pos = new DeviceReaMultiPosition[80];
		for (int i = 0; i < 80; i++) {
			pos[i].ID = 0;
			pos[i].Order = 0;
			// diskReaMultiPosition.Positions[i].ID = 0;
			// diskReaMultiPosition.Positions[i].Order = 0;
		}
		diskReaMultiPosition.Positions = pos;
		String lastReaName = "";
		int lastReaType = 0;
		int order = 0;
		for (int i = 0; i < reaList.size(); i++) {
			if ((reaList.get(i).ReaPos >= 1) && (reaList.get(i).ReaPos <= 80)) {
				if ((0 != reaList.get(i).ReaName.compareTo(lastReaName)) || (reaList.get(i).ReaType != lastReaType)) {
					order = 1;
				} else {
					order++;
				}
				lastReaName = reaList.get(i).ReaName;
				lastReaType = reaList.get(i).ReaType;
				int index = reaList.get(i).ReaPos - 1;
				int id = 0;
				switch (ReaType.getWeekByValue(reaList.get(i).ReaType)) {
				case Diluent:
					id = ReagentIDManager.GetDiluentID(moduleID, reaList.get(i).ReaName);
					break;
				case AlkalineDetergent:
					id = 0xFFFE;// ReagentIDManager.GetAlkalineDetergentID(reaList[i].ReaName);
					break;
				case PhosphorFreeDetergent:
					id = ReagentIDManager.GetPhosphorFreeDetergentID(moduleID, reaList.get(i).ReaName);
					break;
				case R1:
				case R2:
				case R3:
				case R4:
					id = ReagentIDManager.GetReaTypeID(moduleID, reaList.get(i).ReaName,
							ReaType.getWeekByValue(reaList.get(i).ReaType));
					break;
				}
				diskReaMultiPosition.Positions[index].ID = id;
				diskReaMultiPosition.Positions[index].Order = (byte) order;
			}
		}
	}

	/**
	 * 试剂针交叉污染
	 * 
	 * @param moduleID
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reagentProbePollute/{moduleID}")
	public boolean reagentProbePollute(@PathVariable("moduleID") int moduleID) throws Exception {
		boolean ret = false;
		System.out.println("进入reagentProbePollute");
		try {
			List<WashReagent> itemList = api.getReagentPolluteList();
			List<Object> dataList = new ArrayList<Object>();
			for (WashReagent item : itemList) {
				DeviceReagentProbePollute drpp = new DeviceReagentProbePollute();
				drpp.FromID = ReagentIDManager.GetReaTypeID(moduleID, item.FromItem,
						ReaType.getWeekByValue(item.FromType));
				drpp.ToID = ReagentIDManager.GetReaTypeID(moduleID, item.ToItem, ReaType.getWeekByValue(item.ToType));
				drpp.WashVol = item.Volume;
				dataList.add(drpp);
			}
			// byte[] data =(byte[]) ArrayUtils.toPrimitive(dataList);
			byte[] data = listTobyte(dataList);
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ReagentProbePollute.getStrId(), data,
					data == null ? 0 : data.length);
			// byte[] dataIn=new byte[6*n];

		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 样本针交叉污染
	 * 
	 * @param moduleID
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbePollute/{moduleID}")
	public boolean sampleProbePollute(@PathVariable("moduleID") int moduleID) throws Exception {
		System.out.println("进入sampleProbePollute");
		boolean ret = false;
		try {
			List<WashSample> itemList = api.getSamplePolluteList();
			List<Object> dataList = new ArrayList<Object>();
			for (WashSample item : itemList) {
				DeviceSampleProbePollute dspp = new DeviceSampleProbePollute();
				dspp.ID = ItemIDManager.GetItemID(item.ItemName);
				dspp.WashPos = 0xFFFF;
				dspp.WashVol = item.WashVol / 10;
				dataList.add(dspp);
			}
			byte[] data = listTobyte(dataList);
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.SampleProbePollute.getStrId(), data,
					data == null ? 0 : data.length);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	public byte[] listTobyte(List dataList) throws IOException {
		byte[] data = null;
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(dataList);
		data = bo.toByteArray();
		bo.close();
		oo.close();
		return data;
	}

	/**
	 * 反应杯交叉污染(未完成)
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reactCupPollute")
	public boolean reactCupPollute() throws Exception {
		System.out.println("进入ReactCupPollute");
		boolean ret = false;
		List<WashCell> itemList = api.getReactCupPolluteList();
		List<Object> dataList = new ArrayList<Object>();
		for (WashCell item : itemList) {
			DeviceReactCupProbePollute dspp = new DeviceReactCupProbePollute();
			dspp.FromID = ItemIDManager.GetItemID(item.FromItemName);
			dspp.ToID = ItemIDManager.GetItemID(item.ToItemName);
			dspp.WashVol_R1 = item.R1Vol;
			dspp.WashVol_R2 = item.R2Vol;
			dataList.add(dspp);
		}
		byte[] data = listTobyte(dataList);
		ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ReactCupPollute.getStrId(), data,
				data == null ? 0 : data.length);
		return ret;
	}

	/**
	 * 清洗恒温槽
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/thermostatWashOut")
	public boolean thermostatWashOut() throws Exception {
		System.out.println("进入ThermostatWashOut");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			// dataIn[0]=index;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ThermostatWashOut.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 样本针清洗
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeWash")
	public boolean sampleProbeWash() throws Exception {
		System.out.println("进入SampleProbeWash");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			// dataIn[0]=index;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.SampleProbeWash.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 清洗水箱
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/waterTankWash")
	public boolean waterTankWash() throws Exception {
		System.out.println("进入WaterTankWash");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			// dataIn[0]=index;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.WaterTankWash.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 注射泵排气
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/injectionPumpExhaust")
	public boolean injectionPumpExhaust() throws Exception {
		System.out.println("进入InjectionPumpExhaust");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			// dataIn[0]=index;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.InjectionPumpExhaust.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 样本针位置校准(水平检查)
	 * 
	 * @param index
	 *            0x01-内圈；0x02-外圈；0x03-外圈；0x04-样本架
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeHorizontalCheck/{index}")
	public boolean sampleProbeHorizontalCheck(@PathVariable("index") byte index) throws Exception {
		System.out.println("进入sampleProbeHorizontalCheck");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[1];
			dataIn[0] = index;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.SampleProbeHorizontalCheck.getStrId(),
					dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 试剂容量校准
	 * 
	 * @param index
	 *            0x01-R1；0x02-R2；0x03-R1+R2；
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reagentProbeVerticalCheck/{index}")
	public boolean reagentProbeVerticalCheck(@PathVariable("index") byte index) throws Exception {
		System.out.println("进入ReagentProbeVerticalCheck");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[1];
			dataIn[0] = index;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ReagentProbeVerticalCheck.getStrId(),
					dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 试剂针位置校准
	 * 
	 * @param index
	 *            0x01-R1；0x02-R2；0x03-R1+R2；
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reagentProbeHorizontalCheck/{index}")
	public boolean reagentProbeHorizontalCheck(@PathVariable("index") byte index) throws Exception {
		System.out.println("进入ReagentProbeHorizontalCheck");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[1];
			dataIn[0] = index;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ReagentProbeHorizontalCheck.getStrId(),
					dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 试剂余量扫描(未完成)
	 * 
	 * @param positionFirst
	 * @return positionSecond
	 * @throws Exception
	 */
	@GetMapping("/reagentRemainVolumeScan/{positionFirst}/{positionSecond}")
	public boolean reagentRemainVolumeScan(@PathVariable("positionFirst") List<ReagentInfo> positionFirst,
			@PathVariable("positionSecond") List<ReagentInfo> positionSecond) throws Exception {
		System.out.println("进入ReagentRemainVolumeScan");
		boolean ret = false;
		DeviceReagentScan drc = new DeviceReagentScan();
		drc.DiskPostion1 = new byte[80];
		drc.DiskPostion2 = new byte[80];

		// for (int i = 0; i < positionFirst.size(); i++)
		// {
		// if (positionFirst.get(i).getI==true)
		// {
		// ReagentInfo reagent = cbxPosition1.Items[i] as ReagentInfo;
		// drc.DiskPostion1[reagent.ReaPos - 1] = 1;
		// }
		// }
		//
		// for (int i = 0; i < positionSecond.size(); i++)
		// {
		// if (cbxPosition2.GetItemChecked(i))
		// {
		// ReagentInfo reagent = cbxPosition2.Items[i] as ReagentInfo;
		// drc.DiskPostion2[reagent.ReaPos - 1] = 1;
		// }
		// }
		// byte moduleID =
		// (byte)((Bil.BLL.Protocol.OnlineModuleInfo)(((Model.DataTypes.ListItem)cbbModule.SelectedItem).ID)).ModuleID;
		// try {
		// byte[] dataIn = new byte[0xA0];
		// ret = api.sendToMachine(best.getMachineIP1(), 1,
		// DeviceCommand.ReagentRemainVolumeScan.getStrId(),
		// dataIn);
		// } catch (Exception e) {
		// throw new Exception(getErrorstr(e));
		// }
		return ret;
	}

	/**
	 * 光电稳定性检查
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/photoelectricStabilityCheck")
	public boolean photoelectricStabilityCheck() throws Exception {
		System.out.println("进入PhotoelectricStabilityCheck");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.PhotoelectricStabilityCheck.getStrId(),
					dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 试剂消耗(未完成)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reagentConsumption")
	public boolean reagentConsumption() throws Exception {
		System.out.println("进入ReagentConsumption");
		boolean ret = false;
		// try {
		// byte[] dataIn=new byte[0x04*N];
		// ret=api.sendToMachine(best.getMachineIP1(),1,
		// DeviceCommand.ReagentConsumption.getStrId(), dataIn);
		// } catch (Exception e) {
		// throw new Exception(getErrorstr(e));
		// }
		return ret;
	}

	/**
	 * 发送测试数据(未完成)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sendTestData")
	public boolean sendTestData() throws Exception {
		System.out.println("进入SendTestData");
		boolean ret = false;
		// try {
		// byte[] dataIn=new byte[0x2B*N];
		// ret=api.sendToMachine(best.getMachineIP1(),1,
		// DeviceCommand.SendTestData.getStrId(), dataIn);
		// } catch (Exception e) {
		// throw new Exception(getErrorstr(e));
		// }
		return ret;
	}

	/**
	 * 追加测试
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/appendTest")
	public boolean appendTest() throws Exception {
		System.out.println("进入AppendTest");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.AppendTest.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * AD通采命令(未完成)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/debugADData")
	public boolean debugADData() throws Exception {
		System.out.println("进入DebugADData");
		boolean ret = false;
		// try {
		// byte[] dataIn=new byte[0x02*N];
		// ret=api.sendToMachine(best.getMachineIP1(),1,
		// DeviceCommand.DebugADData.getStrId(), dataIn);
		// } catch (Exception e) {
		// throw new Exception(getErrorstr(e));
		// }
		return ret;
	}

	/**
	 * AD通采结束(可执行)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/debugADDataComplete")
	public boolean debugADDataComplete() throws Exception {
		System.out.println("进入DebugADDataComplete");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.DebugADDataComplete.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * AD通采数据(可执行)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/readDebugADData")
	public boolean readDebugADData() throws Exception {
		System.out.println("进入ReadDebugADData");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ReadDebugADData.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 系统设置命令(未完成)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/debugSystemSettings")
	public boolean debugSystemSettings() throws Exception {
		System.out.println("进入DebugSystemSettings");
		boolean ret = false;
		// try {
		// byte[] dataIn=new byte[0x02+N];
		//
		// ret=api.sendToMachine(best.getMachineIP1(),1,
		// DeviceCommand.DebugSystemSettings.getStrId(), dataIn);
		// } catch (Exception e) {
		// throw new Exception(getErrorstr(e));
		// }
		return ret;
	}

	/**
	 * 浓废液管路排气
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/pipelineExhausWasteLiquid")
	public boolean pipelineExhausWasteLiquid() throws Exception {
		System.out.println("进入PipelineExhausWasteLiquid");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.PipelineExhausWasteLiquid.getStrId(),
					dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 清洗液管路排气
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/pipelineExhaustCleaningLiquid")
	public boolean PipelineExhaustCleaningLiquid() throws Exception {
		System.out.println("进入pipelineExhaustCleaningLiquid");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.PipelineExhaustCleaningLiquid.getStrId(),
					dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 样本针精度
	 * 
	 * @param count
	 *            次数1-100
	 * @param sampleSize
	 *            样本量（下位机接收后除以10）；取值范围：2-35
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/pumpAccuracySample/{sampleSize}/{count}")
	public boolean pumpAccuracySample(@PathVariable("sampleSize") int sampleSize, @PathVariable("count") int count)
			throws Exception {
		System.out.println("进入PumpAccuracySample");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[3];
			dataIn[0] = (byte) (sampleSize & 0xFF);
			dataIn[1] = (byte) ((sampleSize >> 8) & 0xFF);
			dataIn[2] = (byte) count;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.PumpAccuracySample.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * R1试剂针精度
	 * 
	 * @param count
	 *            次数1-100
	 * @param sampleSize
	 *            试剂量(取值范围：20-350)
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/pumpAccuracyReagent1/{sampleSize}/{count}")
	public boolean pumpAccuracyReagent1(@PathVariable("sampleSize") int sampleSize, @PathVariable("count") int count)
			throws Exception {
		System.out.println("进入PumpAccuracyReagent1");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[3];
			dataIn[0] = (byte) (sampleSize & 0xFF);
			dataIn[1] = (byte) ((sampleSize >> 8) & 0xFF);
			dataIn[2] = (byte) count;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.PumpAccuracyReagent1.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * R2试剂针精度
	 * 
	 * @param count
	 *            次数1-100
	 * @param sampleSize
	 *            试剂量(取值范围：20-350)
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/pumpAccuracyReagent2/{sampleSize}/{count}")
	public boolean pumpAccuracyReagent2(@PathVariable("sampleSize") int sampleSize, @PathVariable("count") int count)
			throws Exception {
		System.out.println("进入PumpAccuracyReagent1");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[3];
			dataIn[0] = (byte) (sampleSize & 0xFF);
			dataIn[1] = (byte) ((sampleSize >> 8) & 0xFF);
			dataIn[2] = (byte) count;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.PumpAccuracyReagent2.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 样本针触碰
	 * 
	 * @param positon
	 *            位置
	 * @param samplenum
	 *            架号
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/samplePrebeTouch/{samplenum}/{count}")
	public boolean samplePrebeTouch(@PathVariable("samplenum") int samplenum, @PathVariable("positon") int positon)
			throws Exception {
		System.out.println("进入SamplePrebeTouch");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[5];
			dataIn[0] = (byte) (samplenum & 0xFF);
			dataIn[1] = (byte) ((samplenum >> 8) & 0xFF);
			dataIn[2] = (byte) ((samplenum >> 16) & 0xFF);
			dataIn[3] = (byte) ((samplenum >> 24) & 0xFF);
			dataIn[4] = (byte) positon;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.SamplePrebeTouch.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 试剂针触碰
	 * 
	 * @param positon
	 *            位置
	 * @param samplenum
	 *            架号
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reagentPrebeTouch/{samplenum}/{count}")
	public boolean reagentPrebeTouch(@PathVariable("samplenum") int samplenum, @PathVariable("positon") int positon)
			throws Exception {
		System.out.println("进入ReagentPrebeTouch");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[5];
			dataIn[0] = (byte) (samplenum & 0xFF);
			dataIn[1] = (byte) ((samplenum >> 8) & 0xFF);
			dataIn[2] = (byte) ((samplenum >> 16) & 0xFF);
			dataIn[3] = (byte) ((samplenum >> 24) & 0xFF);
			dataIn[4] = (byte) positon;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ReagentPrebeTouch.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 蜂鸣时间获取
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getBuzzerTime")
	public boolean getBuzzerTime() throws Exception {
		System.out.println("进入GetBuzzerTime");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.GetBuzzerTime.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 蜂鸣测试
	 * 
	 * @param index
	 *            0x00-无蜂鸣；0x01-短时间蜂鸣；0x02-长时间蜂鸣
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/testBuzzer/{index}")
	public boolean testBuzzer(@PathVariable("index") byte index) throws Exception {
		System.out.println("进入TestBuzzer");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[2];
			dataIn[0] = (byte) (index & 0xFF);
			dataIn[1] = (byte) ((index >> 8) & 0xFF);
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.TestBuzzer.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 蜂鸣时间关闭
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/closeBuzzer")
	public boolean closeBuzzer() throws Exception {
		System.out.println("进入CloseBuzzer");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.CloseBuzzer.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 样本跳过测试项(未完成)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/ignoreSample")
	public boolean ignoreSample() throws Exception {
		System.out.println("进入IgnoreSample");
		boolean ret = false;
		// try {
		// byte[] dataIn=new byte[0x04*N];
		//
		// ret=api.sendToMachine(best.getMachineIP1(),1,
		// DeviceCommand.IgnoreSample.getStrId(), dataIn);
		// } catch (Exception e) {
		// throw new Exception(getErrorstr(e));
		// }
		return ret;
	}

	/**
	 * 试剂跳过测试项(未完成)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/ignoreReagent")
	public boolean ignoreReagent() throws Exception {
		System.out.println("进入IgnoreReagent");
		boolean ret = false;
		// try {
		// byte[] dataIn=new byte[0x04*N];
		//
		// ret=api.sendToMachine(best.getMachineIP1(),1,
		// DeviceCommand.IgnoreReagent.getStrId(), dataIn);
		// } catch (Exception e) {
		// throw new Exception(getErrorstr(e));
		// }
		return ret;
	}

	/**
	 * 加试剂暂停
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reagentPause")
	public boolean reagentPause() throws Exception {
		System.out.println("进入ReagentPause");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[0];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ReagentPause.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 加试剂继续
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reagentContinue")
	public boolean reagentContinue() throws Exception {
		System.out.println("进入ReagentContinue");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[0];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ReagentContinue.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 测试中多试剂位(未完成)
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reagentMultiPositionPlus")
	public boolean reagentMultiPositionPlus() throws Exception {
		System.out.println("进入ReagentMultiPositionPlus");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[0xA1];

			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ReagentMultiPositionPlus.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 读取仪器序列号
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/instrumentSerialNumberRead")
	public boolean instrumentSerialNumberRead() throws Exception {
		System.out.println("进入InstrumentSerialNumberRead");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[0];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.InstrumentSerialNumberRead.getStrId(),
					dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 写翻门传感器级别
	 * 
	 * @param index
	 *            0-注意；1-停止；
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/alarmLevelWrite/{index}")
	public boolean alarmLevelWrite(@PathVariable("index") byte index) throws Exception {
		System.out.println("进入AlarmLevelWrite");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[1];
			dataIn[0] = index;
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.AlarmLevelWrite.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 读翻门传感器级别
	 * 
	 * @param index
	 * @return 0-注意；1-停止；
	 * @throws Exception
	 */
	@GetMapping("/alarmLevelRead")
	public boolean alarmLevelRead() throws Exception {
		System.out.println("进入AlarmLevelRead");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[0];
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.AlarmLevelRead.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 写入仪器序列号
	 * 
	 * @param index
	 * @return 0-注意；1-停止；
	 * @throws Exception
	 */
	@GetMapping("/instrumentSerialNumberWrite/{serID}")
	public boolean instrumentSerialNumberWrite(@PathVariable("serID") String serID) throws Exception {
		System.out.println("进入InstrumentSerialNumberWrite");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[14];
			dataIn = serID.getBytes();
			// System.Text.Encoding.Default.GetBytes ( serID );
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.InstrumentSerialNumberWrite.getStrId(),
					dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 测试数据发完
	 * 
	 * @param index
	 * @return 0-注意；1-停止；
	 * @throws Exception
	 */
	@GetMapping("/sendDataCompleted")
	public boolean sendDataCompleted() throws Exception {
		System.out.println("进入SendDataCompleted");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[0];
			// System.Text.Encoding.Default.GetBytes ( serID );
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.SendDataCompleted.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 开始测试(手动测试)
	 * 
	 * @param index
	 *            1~20
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sendManualTestClean/{index}")
	public boolean sendManualTestClean(@PathVariable("index") int index) throws Exception {
		System.out.println("进入SendManualTestClean");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[1];
			dataIn[0] = (byte) index;
			// System.Text.Encoding.Default.GetBytes ( serID );
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.SendManualTestClean.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 加空白完成（手动测试）
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sendManualTestBlank")
	public boolean SendManualTestBlank() throws Exception {
		System.out.println("进入SendManualTestBlank");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[0];
			// System.Text.Encoding.Default.GetBytes ( serID );
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.SendManualTestBlank.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 加试剂完成（手动测试）
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sendManualTestAD")
	public boolean sendManualTestAD() throws Exception {
		System.out.println("进入SendManualTestAD");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[0];
			// System.Text.Encoding.Default.GetBytes ( serID );
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.SendManualTestAD.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 开始测试（残留水）
	 * 
	 * @param index
	 *            1~8
	 * @param indexnum
	 *            高浓度试剂编号
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/startTestRateResidualWater/{index}/{indexnum}")
	public boolean startTestRateResidualWater(@PathVariable("index") int index, @PathVariable("indexnum") int indexnum)
			throws Exception {
		System.out.println("进入StartTestRateResidualWater");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[3];
			dataIn[0] = (byte) (index & 0xFF);
			dataIn[1] = (byte) ((indexnum >> 8) & 0xFF);
			dataIn[2] = (byte) ((indexnum >> 16) & 0xFF);
			// System.Text.Encoding.Default.GetBytes ( serID );
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.StartTestRateResidualWater.getStrId(),
					dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 探针探液老化程序(未完成) 0x01 0x01-样本针老化；0x00-样本针不老化 0x01 0x01-试剂针1老化；0x00-试剂针1不老化
	 * 0x01 0x01-试剂针2老化；0x00-试剂针2不老化 0x02 探针老化次数
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/probeDeclineCheck")
	public boolean probeDeclineCheck() throws Exception {
		System.out.println("进入ProbeDeclineCheck");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[5];
			// System.Text.Encoding.Default.GetBytes ( serID );
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.ProbeDeclineCheck.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

	/**
	 * 开始测试（反应杯携带污染率） * @param index 1~8
	 * 
	 * @param indexnum
	 *            高浓度试剂编号
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/startTestRateCup/{index}/{indexnum}")
	public boolean startTestRateCup(@PathVariable("index") int index, @PathVariable("indexnum") int indexnum)
			throws Exception {
		System.out.println("进入StartTestRateCup");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[3];
			dataIn[0] = (byte) (index & 0xFF);
			dataIn[1] = (byte) ((indexnum >> 8) & 0xFF);
			dataIn[2] = (byte) ((indexnum >> 16) & 0xFF);
			// System.Text.Encoding.Default.GetBytes ( serID );
			ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.StartTestRateCup.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}

}
