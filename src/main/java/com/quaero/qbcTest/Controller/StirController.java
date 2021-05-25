package com.quaero.qbcTest.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quaero.qbcTest.Enum.DeviceBoardID;
import com.quaero.qbcTest.Enum.DeviceCommand;
import com.quaero.qbcTest.Enum.DeviceDebugCommandReact;
import com.quaero.qbcTest.Enum.DeviceDebugCommandStir;
import com.quaero.qbcTest.dto.DeviceDebugPackageHead;
import com.quaero.qbcTest.dto.MessageCode.CodeEnum;
import com.quaero.qbcTest.dto.MessageCode.ResponseVO;
import com.quaero.qbcTest.server.QbcTestApi;
import com.quaero.qbcTest.server.impl.StirApiImpl;
import com.quaero.qbcTest.util.Best;

import cn.hutool.core.util.ArrayUtil;
/**
 * 搅拌板
 * @author zhangbin
 *
 */
@RestController
@RequestMapping("/stir")
public class StirController  extends baseController{
	@Autowired
	private QbcTestApi api;
	@Autowired
	private Best best;
	/*@Autowired
	private  StirApiImpl api;*/
	public boolean sendReaction(byte[] dataIn,DeviceDebugPackageHead head) throws Exception{
		int debugHeadSize=head.getClass().getDeclaredFields().length;
		int debugBodyLen = ((ArrayUtil.isEmpty(dataIn) ) || (0 == dataIn.length)) ? 0 : dataIn.length;
		int bytelen=debugHeadSize+debugBodyLen;
		byte[] bytg=new byte[bytelen];
		bytg[0] = (byte) (head.getBoardID() & 0xff);
		bytg[1] = (byte) (head.getCommand() & 0xff);
		bytg[2] = (byte) ((head.getCommand() >> 8) & 0xFF);
		for(int i=3;i<bytelen;i++){
			bytg[i]=dataIn[i-3];
		}
		return api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.DeviceDebug.getStrId(), bytg);
	}
	/**
	 * 搅拌1升降运行步数
	 * turn 0x01	运行方向：0x01-上升； 0x00-下降
     * coord 0x02	转动步数：300-1600
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/stir1LiftingStep/{turn}/{coord}")
	public ResponseVO stir1LiftingStep(@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入Stir1LiftingStep");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.Stir1LiftingStep.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 搅拌1升降归零
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/stir1LiftingZero")
	public ResponseVO stir1LiftingZero() throws Exception {
		System.out.println("进入Stir1LiftingZero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.Stir1LiftingZero.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 搅拌1摆动运行步数
	 * turn 0x01	运行方向：0x01-顺时针； 0x00-逆时针
     * coord 0x02	转动步数：200-1600
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/Stir1SwingStep/{turn}/{coord}")
	public ResponseVO Stir1SwingStep(@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入Stir1SwingStep");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.Stir1SwingStep.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 搅拌1摆动归零
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/stir1SwingZero")
	public ResponseVO stir1SwingZero() throws Exception {
		System.out.println("进入Stir1SwingZero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.Stir1SwingZero.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 搅拌2升降运行步数
	 * turn 0x01	运行方向：0x01-上升； 0x00-下降
     * coord 0x02	转动步数：300-1600
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/stir2LiftingStep/{turn}/{coord}")
	public ResponseVO stir2LiftingStep(@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入Stir1LiftingStep");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.Stir2LiftingStep.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 搅拌2升降归零
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/stir2LiftingZero")
	public ResponseVO stir2LiftingZero() throws Exception {
		System.out.println("进入Stir1LiftingZero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.Stir2LiftingZero.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 搅拌2摆动运行步数
	 * turn 0x01	运行方向：：0x01-顺时针； 0x00-逆时针
     * coord 0x02	转动步数：200-1600
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/Stir2SwingStep/{turn}/{coord}")
	public ResponseVO Stir2SwingStep(@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入Stir1SwingStep");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.Stir2SwingStep.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 搅拌2摆动归零
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/stir2SwingZero")
	public ResponseVO stir2SwingZero() throws Exception {
		System.out.println("进入Stir2SwingZero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.Stir2SwingZero.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 搅拌1摆1位置
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/stir1SwingPosition1")
	public ResponseVO stir1SwingPosition1() throws Exception {
		System.out.println("进入Stir1SwingPosition1");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.Stir1SwingPosition1.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 搅拌1摆4位置
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/stir1SwingPosition4")
	public ResponseVO stir1SwingPosition4() throws Exception {
		System.out.println("进入stir1SwingPosition4");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.Stir1SwingPosition4.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 搅拌2摆2位置
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/Stir2SwingPosition2")
	public ResponseVO Stir2SwingPosition2() throws Exception {
		System.out.println("进入Stir2SwingPosition2");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.Stir2SwingPosition2.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 搅拌2摆3位置
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/stir2SwingPosition3")
	public ResponseVO stir2SwingPosition3() throws Exception {
		System.out.println("进入Stir2SwingPosition3");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.Stir2SwingPosition3.getId());
			ret=sendReaction(dataIn,head);
			ret = api.sendToMachine(best.getMachineIP1(), 2, DeviceDebugCommandStir.Stir2SwingPosition3.getStrId());
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 获取光耦状态
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/stirGetOptoStatus")
	public ResponseVO stirGetOptoStatus() throws Exception {
		System.out.println("进入stirGetOptoStatus");
		byte[] state=null;
		List newVal=new ArrayList<>();
		List<Byte> aa=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.StirGetOptoStatus.getId());
			boolean ret=sendReaction(dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					for(byte s:state){
						aa.add(s);
					}
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"获取光耦状态超时！");
				}
				js++;
				Thread.sleep(1500);
			}
			//state=api.getOptoStatus(head.getBoardID(),head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(aa);
	}
	/**
	 * 清洗电磁阀
	 * turn 0x01	0x01-开；0x00-关；
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/eletValueWash/{turn}")
	public ResponseVO eletValueWash(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入EletValueWash");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.EletValueWash.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 搅拌电机1
	 * turn 0x01	0x01-开；0x00-关；
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/electricMotorStr1/{turn}")
	public ResponseVO electricMotorStr1(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ElectricMotorStr1");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.ElectricMotorStr1.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 搅拌电机2
	 * turn 0x01	0x01-开；0x00-关；
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/electricMotorStr2/{turn}")
	public ResponseVO electricMotorStr2(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ElectricMotorStr2");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.ElectricMotorStr2.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * Mix
	 * turn 0x01	搅拌位置1-4
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/stirMix/{turn}")
	public ResponseVO stirMix(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入StirMix");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.StirMix.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * Trig1
	 * turn 0x01	搅拌位置1触发
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/StirTrig/{turn}")
	public ResponseVO StirTrig(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入StirMix");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			//dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
			head.setCommand((int)DeviceDebugCommandStir.StirTrig.getId()+turn);
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}


}
