package com.quaero.qbcTest.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quaero.qbcTest.Enum.DeviceBoardID;
import com.quaero.qbcTest.Enum.DeviceCommand;
import com.quaero.qbcTest.Enum.DeviceDebugCommandAC;
import com.quaero.qbcTest.Enum.DeviceDebugCommandRefrigeration;
import com.quaero.qbcTest.dto.DeviceDebugPackageHead;
import com.quaero.qbcTest.dto.MessageCode.CodeEnum;
import com.quaero.qbcTest.dto.MessageCode.ResponseVO;
import com.quaero.qbcTest.dto.entity.DeviceDebugRefrigerationGetTempCoef;
import com.quaero.qbcTest.server.QbcTestApi;
import com.quaero.qbcTest.util.Best;
import com.quaero.qbcTest.util.ByteUtil;

import cn.hutool.core.util.ArrayUtil;

/**
 * 制冷
 * 
 * @author zhangbin
 *
 */
@RestController
@RequestMapping("/refrigeration")
public class RefrigerationController extends baseController {

	@Autowired
	private QbcTestApi api;
	@Autowired
	private Best best;

	public boolean sendReaction(byte[] dataIn, DeviceDebugPackageHead head) throws Exception {
		head.setBoardID((byte) DeviceBoardID.Refrigeration.getValue());
		int debugHeadSize = head.getClass().getDeclaredFields().length;
		int debugBodyLen = ((ArrayUtil.isEmpty(dataIn)) || (0 == dataIn.length)) ? 0 : dataIn.length;
		int bytelen = debugHeadSize + debugBodyLen;
		byte[] bytg = new byte[bytelen];
		bytg[0] = (byte) (head.getBoardID() & 0xff);
		bytg[1] = (byte) (head.getCommand() & 0xff);
		bytg[2] = (byte) ((head.getCommand() >> 8) & 0xFF);
		for (int i = 3; i < bytelen; i++) {
			bytg[i] = dataIn[i - 3];
		}
		return api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.DeviceDebug.getStrId(), bytg);

	}

	/**
	 * 温度、电流、水箱浮子状态
	 * 
	 * @return R1温度 R2温度 质控温度 环境温度 帕尔贴1电流 帕尔贴2电流 帕尔贴3电流 帕尔贴4电流 帕尔贴5电流 帕尔贴6电流
	 *         浮子状态；0x01-开；0x00-关
	 * @throws Exception
	 */
	@GetMapping("/refrigerationStatus")
	public ResponseVO refrigerationStatus() throws Exception {
		System.out.println("进入refrigerationStatus");
		boolean ret = false;
		byte[] state = null;
		List newVal = new ArrayList<>();

		byte[] dataIn = new byte[4];
		DeviceDebugPackageHead head = new DeviceDebugPackageHead();
		head.setCommand((int) DeviceDebugCommandRefrigeration.RefrigerationStatus.getId());
		ret = sendReaction(dataIn, head);
		int js = 0;
		while (true) {
			state = api.getOptoStatus(head.getBoardID(), head);
			if (state != null) {
				break;
			} else if (js > 5) {
				return new ResponseVO(CodeEnum.FAILED, "温度、电流、水箱浮子状态超时！");
			}
			js++;
			Thread.sleep(1500);
		}
		for (int i = 0; i < 11; i++) {
			byte[] newstate = new byte[4];
			Object ste = 0;
			if (i < 10) {
				int j = i * 4;
				System.arraycopy(state, j, newstate, 0, 4);
				// ste=(int)((newstate[0])|(newstate[1])<<8);
				ste = ByteUtil.getFloat(newstate);
			}
			if (i == 10) {
				ste = state[i * 4];
			}
			newVal.add(ste);
		}
		return new ResponseVO(newVal);
	}

	/**
	 * 设置温度上下限 DeviceDebugRefrigerationGetTempCoef R1温度下限 R1温度上限 R2温度下限 R2温度上限
	 * 质控温度下限 质控温度上限
	 * 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/setTemperatureRange")
	public ResponseVO setTemperatureRange(@RequestBody DeviceDebugRefrigerationGetTempCoef turn) throws Exception {
		System.out.println("进入setTemperatureRange");
		boolean ret = false;

		byte[] dataIn = new byte[6];
		dataIn[0] = (byte) (turn.getLowerLimitR1() & 0xff);
		dataIn[1] = (byte) (turn.getUpperLimitR1() & 0xff);
		dataIn[2] = (byte) (turn.getLowerLimitR2() & 0xff);
		dataIn[3] = (byte) (turn.getUpperLimitR2() & 0xff);
		dataIn[4] = (byte) (turn.getLowerLimitQC() & 0xff);
		dataIn[5] = (byte) (turn.getUpperLimitQC() & 0xff);
		DeviceDebugPackageHead head = new DeviceDebugPackageHead();
		head.setCommand((int) DeviceDebugCommandRefrigeration.SetTemperatureRange.getId());
		ret = sendReaction(dataIn, head);
		return sendSuccess(ret);
	}

	/**
	 * 读取温度上下限
	 * 
	 * @return R1温度下限 R1温度上限 R2温度下限 R2温度上限 质控温度下限 质控温度上限
	 * @throws Exception
	 */
	@GetMapping("/getTemperatureRange")
	public ResponseVO getTemperatureRange() throws Exception {
		System.out.println("进入GetTemperatureRange");
		boolean ret = false;
		Object ste = 0;
		List newVal = new ArrayList<>();
		byte[] state = null;

		byte[] dataIn = new byte[4];
		DeviceDebugPackageHead head = new DeviceDebugPackageHead();
		head.setCommand((int) DeviceDebugCommandRefrigeration.GetTemperatureRange.getId());
		ret = sendReaction(dataIn, head);
		int js = 0;
		while (true) {
			state = api.getOptoStatus(head.getBoardID(), head);
			if (state != null) {
				break;
			} else if (js > 5) {
				return new ResponseVO(CodeEnum.FAILED, "读取温度上下限超时！");
			}
			js++;
			Thread.sleep(1500);
		}
		for (int i = 0; i < 6; i++) {
			ste = state[i];
			newVal.add(ste);
		}

		return new ResponseVO(newVal);
	}

	/**
	 * 帕尔贴制冷开关 turn ；0x01-开；0x00-关
	 * 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/parpSwitch/{turn}")
	public ResponseVO parpSwitch(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ParpSwitch");
		boolean ret = false;

		byte[] dataIn = new byte[4];
		dataIn[0] = (byte) (turn & 0xff);
		DeviceDebugPackageHead head = new DeviceDebugPackageHead();
		head.setCommand((int) DeviceDebugCommandRefrigeration.ParpSwitch.getId());
		ret = sendReaction(dataIn, head);
		return sendSuccess(ret);
	}

	/**
	 * 风扇和循环泵开关 turn ；0x01-开；0x00-关
	 * 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/fanSwitch/{turn}")
	public ResponseVO fanSwitch(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入FanSwitch");
		boolean ret = false;

		byte[] dataIn = new byte[4];
		dataIn[0] = (byte) (turn & 0xff);
		DeviceDebugPackageHead head = new DeviceDebugPackageHead();
		head.setCommand((int) DeviceDebugCommandRefrigeration.FanSwitch.getId());
		ret = sendReaction(dataIn, head);
		return sendSuccess(ret);
	}

	/**
	 * 制冷初始化
	 * 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/refrigerationReset")
	public ResponseVO refrigerationReset() throws Exception {
		System.out.println("进入RefrigerationReset");
		boolean ret = false;

		byte[] dataIn = new byte[4];
		DeviceDebugPackageHead head = new DeviceDebugPackageHead();
		head.setCommand((int) DeviceDebugCommandRefrigeration.RefrigerationReset.getId());
		ret = sendReaction(dataIn, head);
		return sendSuccess(ret);
	}

	/**
	 * 停止制冷
	 * 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/refrigerationStop")
	public ResponseVO refrigerationStop() throws Exception {
		System.out.println("进入refrigerationStop");
		boolean ret = false;

		byte[] dataIn = new byte[4];
		DeviceDebugPackageHead head = new DeviceDebugPackageHead();
		head.setCommand((int) DeviceDebugCommandRefrigeration.RefrigerationStop.getId());
		ret = sendReaction(dataIn, head);

		return sendSuccess(ret);
	}

}
