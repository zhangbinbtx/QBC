package com.quaero.qbcTest.Controller;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.quaero.qbcTest.Enum.DeviceBoardID;
import com.quaero.qbcTest.Enum.DeviceCommand;
import com.quaero.qbcTest.Enum.DeviceDebugCommandReact;
import com.quaero.qbcTest.dto.DeviceDebugPackageHead;
import com.quaero.qbcTest.dto.MessageCode.CodeEnum;
import com.quaero.qbcTest.dto.MessageCode.ResponseVO;
import com.quaero.qbcTest.server.QbcTestApi;
import com.quaero.qbcTest.util.Best;
import com.quaero.qbcTest.util.ByteArrayUtils;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import cn.hutool.core.util.ArrayUtil;


/**
 * 反应板
 * @author zhangbin
 *
 */
@RestController
@RequestMapping("/reactionplate")
public class ReactionplateController extends baseController{
	@Autowired
	private QbcTestApi api;
	@Autowired
	private Best best;

	/**
	 * 反应盘转动步数
	 * turn 0x01	转动方向：0x01-顺时针； 0x00-逆时针
     * coord 0x02	转动步数：300-48000
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactTurnStep/{turn}/{coord}")
	public ResponseVO reactTurnStep(@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入ReactTurnStep");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactTurnStep.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	public boolean sendReaction(byte[] dataIn,DeviceDebugPackageHead head) throws Exception{
		head.setBoardID((byte)DeviceBoardID.React.getValue());
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
	public static void main(String[] args) {
		/*byte[] dataIn = new byte[4];
		DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
		int debugHeadSize=(int)getMb(head);//对象长度
		System.out.println("对象长度="+debugHeadSize);*/
		byte[] dataIn = new byte[3];
		dataIn[0] = (byte) (1 & 0xff);
		dataIn[1] = (byte) (300 & 0xff);
		dataIn[2] = (byte) ((300 >> 8) & 0xFF);
		DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
		int debugHeadSize=head.getClass().getDeclaredFields().length;
		head.setBoardID((byte)2);
		head.setCommand((int)DeviceDebugCommandReact.ReactTurnStep.getId());
		int debugBodyLen = ((ArrayUtil.isEmpty(dataIn) ) || (0 == dataIn.length)) ? 0 : dataIn.length;
		int bytelen=debugHeadSize+debugBodyLen;
		byte[] bytg=new byte[bytelen];
		bytg[0] = (byte) (DeviceBoardID.React.getValue() & 0xff);
		bytg[1] = (byte) ((int)DeviceDebugCommandReact.ReactTurnStep.getId() & 0xff);
		bytg[2] = (byte) (((int)DeviceDebugCommandReact.ReactTurnStep.getId() >> 8) & 0xFF);
		for(int i=3;i<dataIn.length+3;i++){
			bytg[i]=dataIn[i-3];
		}
		IntByReference countOt =new  IntByReference(debugBodyLen);
		/* final Pointer p1 = new Memory((head.toString().length()+1)* Native.WCHAR_SIZE);
  		  p1.setString(0, head.toString());
         countOt.setPointer(p1);
         int ss = countOt.getValue();
        */
		/*Optional<byte[]> abd = ByteArrayUtils.objectToBytes(head);
		//bytg=objectToBytes(head);
		bytg=abd.get();*/
	}
	/**
	 * 反应盘转动个数
	 * turn 0x01	转动方向：0x01-顺时针； 0x00-逆时针
     * coord 0x01	转动步数：1-120
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactTurnCount/{turn}/{coord}")
	public ResponseVO reactTurnCount(@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入ReactTurnCount");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactTurnCount.getId());
			ret=sendReaction(dataIn,head);
			//ret = api.sendToMachine(best.getMachineIP1(), 1, DeviceDebugCommandReact.ReactTurnCount.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 反应盘归零
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactTurnZero")
	public ResponseVO reactTurnZero() throws Exception {
		System.out.println("进入ReactTurnZero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactTurnZero.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 清洗机构1运行步数
	 * turn 0x01	升降方向：0x01-上升； 0x00-下降
     * coord 0x01	运行步数：300-1600
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactWash1RunStep/{turn}/{coord}")
	public ResponseVO reactWash1RunStep(@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入ReactWash1RunStep");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactWash1RunStep.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 清洗机构1归零
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactWash1Zero")
	public ResponseVO reactWash1Zero() throws Exception {
		System.out.println("进入FanSwitch");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactWash1Zero.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 清洗机构2运行步数
	 * turn 0x01	升降方向：0x01-上升； 0x00-下降
     * coord 0x01	运行步数：300-1600
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactWash2RunStep/{turn}/{coord}")
	public ResponseVO reactWash2RunStep(@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入ReactWash2RunStep");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactWash2RunStep.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 清洗机构2归零
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactWash2Zero")
	public ResponseVO reactWash2Zero() throws Exception {
		System.out.println("进入reactWash2Zero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactWash2Zero.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 反应盘扫描(命令已改，测试未成功)
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactScan")
	public ResponseVO  ReactScan() throws Exception {
		System.out.println("进入reactScan");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReact.ReactScan.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 吸清洗液(2号针)电磁阀
	 * turn 0x01	0x01-打开； 0x00-关闭
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactEletValue1/{turn}")
	public ResponseVO  reactEletValue1(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入reactScan");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactEletValue1.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 注清洗液(2号针)电磁阀
	 * turn 0x01	0x01-打开； 0x00-关闭
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactEletValue2/{turn}")
	public ResponseVO  reactEletValue2(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ReactEletValue2");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactEletValue2.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 吸清洗液(4号针)电磁阀
	 * turn 0x01	0x01-打开； 0x00-关闭
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactEletValue3/{turn}")
	public ResponseVO  reactEletValue3(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入reactScan");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactEletValue3.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 注清洗液(4号针)电磁阀
	 * turn 0x01	0x01-打开； 0x00-关闭
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactEletValue4/{turn}")
	public ResponseVO  reactEletValue4(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ReactEletValue2");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactEletValue4.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 排淡废液电磁阀
	 * turn 0x01	0x01-打开； 0x00-关闭
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactEletValue5/{turn}")
	public ResponseVO  reactEletValue5(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ReactEletValue5");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactEletValue5.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 排浓废液电磁阀
	 * turn 0x01	0x01-打开； 0x00-关闭
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactEletValue6/{turn}")
	public ResponseVO  reactEletValue6(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ReactEletValue6");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactEletValue6.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 注纯水(8号针)电磁阀
	 * turn 0x01	0x01-打开； 0x00-关闭
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactEletValue7/{turn}")
	public ResponseVO  reactEletValue7(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ReactEletValue7");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactEletValue7.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 注纯水(6号针)电磁阀
	 * turn 0x01	0x01-打开； 0x00-关闭
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactEletValue8/{turn}")
	public ResponseVO  reactEletValue8(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ReactEletValue7");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactEletValue8.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 清洗白块(13号针)电磁阀
	 * turn 0x01	0x01-打开； 0x00-关闭
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactEletValue9/{turn}")
	public ResponseVO  reactEletValue9(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ReactEletValue9");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactEletValue9.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 *注入杯空白(10号针)电磁阀
	 * turn 0x01	0x01-打开； 0x00-关闭
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactEletValue10/{turn}")
	public ResponseVO  reactEletValue10(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ReactEletValue10");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactEletValue10.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 *吸废液电磁阀
	 * turn 0x01	0x01-打开； 0x00-关闭
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactEletValue11/{turn}")
	public ResponseVO  reactEletValue11(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ReactEletValue11");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactEletValue11.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 *电磁阀12
	 * turn 0x01	0x01-打开； 0x00-关闭
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactEletValue12/{turn}")
	public ResponseVO  reactEletValue12(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ReactEletValue12");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactEletValue12.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 *获取光耦状态
	 * turn 0x01	0x01-打开； 0x00-关闭
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reactGetOptoStatus")
	public ResponseVO  reactGetOptoStatus() throws Exception {
		System.out.println("进入ReactGetOptoStatus");
		byte[] state=null;
		boolean ret = false;
		List<Byte> aa=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactGetOptoStatus.getId());
			ret=sendReaction(dataIn,head);
			
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
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(aa);
	}
	/**
	 *单元测试
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactSimulationTest")
	public ResponseVO  reactSimulationTest() throws Exception {
		System.out.println("进入ReactSimulationTest");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			
			head.setCommand((int)DeviceDebugCommandReact.ReactSimulationTest.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 *指定反应杯转光源
	 * turn 0x01	1~160
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reactCupToLight/{turn}")
	public ResponseVO  reactCupToLight(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入reactCupToLight");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
				DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
				head.setCommand((int)DeviceDebugCommandReact.ReactCupToLight.getId());
				ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}

}

