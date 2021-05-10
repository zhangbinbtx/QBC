package com.quaero.qbcTest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quaero.qbcTest.Enum.DeviceBoardID;
import com.quaero.qbcTest.Enum.DeviceCommand;
import com.quaero.qbcTest.Enum.DeviceDebugCommandAC;
import com.quaero.qbcTest.Enum.DeviceDebugCommandSystem;
import com.quaero.qbcTest.dto.DeviceDebugPackageHead;
import com.quaero.qbcTest.dto.DeviceDebugSystemPackageHead;
import com.quaero.qbcTest.dto.entity.DeviceDebugSettingModuleType;
import com.quaero.qbcTest.server.QbcTestApi;
import com.quaero.qbcTest.util.Best;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/system")
public class SystemController extends baseController{

	@Autowired
	private QbcTestApi api;
	@Autowired
	private Best best;
	
	public boolean sendReaction(byte[] dataIn,DeviceDebugSystemPackageHead head) throws Exception{
		//head.setBoardID((byte)DeviceBoardID.Sample.getValue());
		int debugHeadSize=head.getClass().getDeclaredFields().length;
		int debugBodyLen = ((ArrayUtil.isEmpty(dataIn) ) || (0 == dataIn.length)) ? 0 : dataIn.length;
		int bytelen=debugHeadSize+debugBodyLen;
		byte[] bytg=new byte[bytelen];
		bytg[0] = (byte) (head.getCommand() & 0xff);
		for(int i=1;i<bytelen;i++){
			bytg[i]=dataIn[i-1];
		}
		return api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.DebugSystemSettings.getStrId(), bytg);

	}
	public static void main(String[] args) {
		 DeviceDebugSystemPackageHead ddph = new DeviceDebugSystemPackageHead();
		 int debugHeadSize=ddph.getClass().getDeclaredFields().length;
		 System.out.println(debugHeadSize);
	}
	/**
	 * 配置模块
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/setModuleType/{turn}")
	public boolean setModuleType(@PathVariable("turn")DeviceDebugSettingModuleType turn) throws Exception {
		System.out.println("进入SetModuleType");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn.getModule1_Type() & 0xff);
			dataIn[1] = (byte) (turn.getModule2_Type() & 0xff);
			dataIn[1] = (byte) (turn.getModule3_Type() & 0xff);
			dataIn[1] = (byte) (turn.getModule4_Type() & 0xff);
			DeviceDebugSystemPackageHead head=new DeviceDebugSystemPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSystem.SetModuleType.getId());
			ret=sendReaction(dataIn,head);
			//api.sendToMachine(best.getMachineIP1(),1, (int)DeviceDebugCommandSystem.SetModuleType.getId(), dataIn); 
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 获取模块
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getModuleType")
	public boolean getModuleType() throws Exception {
		System.out.println("进入getModuleType");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugSystemPackageHead head=new DeviceDebugSystemPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSystem.GetModuleType.getId());
			ret=sendReaction(dataIn,head);
		   
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * IP获取
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getIPPort/{turn}")
	public boolean getIPPort() throws Exception {
		System.out.println("进入GetIPPort");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugSystemPackageHead head=new DeviceDebugSystemPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSystem.GetIPPort.getId());
			ret=sendReaction(dataIn,head);
		   
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
}
