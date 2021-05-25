package com.quaero.qbcTest.Controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quaero.qbcTest.Enum.DeviceBoardID;
import com.quaero.qbcTest.Enum.DeviceCommand;
import com.quaero.qbcTest.Enum.DeviceDebugCommandReact;
import com.quaero.qbcTest.Enum.DeviceDebugCommandReagent;
import com.quaero.qbcTest.dto.DeviceDebugPackageHead;
import com.quaero.qbcTest.dto.MessageCode.CodeEnum;
import com.quaero.qbcTest.dto.MessageCode.ResponseVO;
import com.quaero.qbcTest.dto.MessageCode.inter.Code;
import com.quaero.qbcTest.dto.entity.DeviceDebugPumpAccuracy;
import com.quaero.qbcTest.dto.entity.ReagentBoardCompensationPara;
import com.quaero.qbcTest.server.QbcTestApi;
import com.quaero.qbcTest.util.Best;
import com.quaero.qbcTest.util.ByteUtil;
import com.quaero.qbcTest.util.CommonUtils;

import cn.hutool.core.util.ArrayUtil;

/**
 * 试剂板
 * @author zhangbin
 *
 */
@RestController
@RequestMapping("/reagent")
public class ReagentController  extends baseController{
	@Autowired
	private QbcTestApi api;
	@Autowired
	private Best best;
	
	public boolean sendReaction(int index,byte[] dataIn,DeviceDebugPackageHead head) throws Exception{
		if(index==1){
			head.setBoardID((byte)DeviceBoardID.Reagent1.getValue());
		}else{
			head.setBoardID((byte)DeviceBoardID.Reagent2.getValue());
		}
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
	 * 探针升降运行步数
	 * index 试剂针 1、2
	 * turn 0x01	转动方向：0x01-降； 0x00-升
     * coord 0x02	转动步数：300-3500
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentProbeLiftingStep/{index}/{turn}/{coord}")
	public ResponseVO reagentProbeLiftingStep(@PathVariable("index") int index,@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入ReagentProbeLiftingStep");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeLiftingStep.getId());
			ret=sendReaction(index,dataIn,head);
			//ret = api.sendToMachine(best.getMachineIP1(), 4, DeviceDebugCommandReagent.ReagentProbeLiftingStep.getStrId(), dataIn);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 探针升降归零
	 * index 试剂针 1、2
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentProbeLiftingZero/{index}")
	public ResponseVO reagentProbeLiftingZero(@PathVariable("index") int index) throws Exception {
		System.out.println("进入ReagentProbeLiftingZero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeLiftingZero.getId());
			ret=sendReaction(index,dataIn,head);
			//ret = api.sendToMachine(best.getMachineIP1(), 4, DeviceDebugCommandReagent.ReagentProbeLiftingZero.getStrId());
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 探针摆动运行步数
	 * index 试剂针 1、2
	 * turn 0x01	转动方向：0x01-顺时针； 0x00-逆时针
     * coord 0x02	转动步数：200-2500
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentProbeSwingStep/{index}/{turn}/{coord}")
	public ResponseVO reagentProbeSwingStep(@PathVariable("index") int index,@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入ReagentProbeSwingStep");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeSwingStep.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 探针摆动归零
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentProbeSwingZero/{index}")
	public ResponseVO reagentProbeSwingZero(@PathVariable("index") int index) throws Exception {
		System.out.println("进入ReagentProbeSwingZero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeSwingZero.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 试剂盘运行步数
	 * index 试剂针 1、2 
	 * turn 0x01	转动方向：0x01-顺时针； 0x00-逆时针
     * coord 0x02	转动步数：200-50000
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentDiskTurnStep/{index}/{turn}/{coord}")
	public ResponseVO reagentDiskTurnStep(@PathVariable("index") int index,@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入ReagentDiskTurnStep");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentDiskTurnStep.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 试剂盘转动个数
	 * index 试剂针 1、2 
	 * turn 0x01	转动方向：0x01-顺时针； 0x00-逆时针
     * coord 0x02	转动个数：1-45
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentDiskTurnCount/{index}/{turn}/{coord}")
	public ResponseVO reagentDiskTurnCount(@PathVariable("index") int index,@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入SampleProbeSwingDiskOut");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentDiskTurnCount.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 试剂盘归零
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentDiskZero/{index}")
	public ResponseVO reagentDiskZero(@PathVariable("index") int index) throws Exception {
		System.out.println("进入ReagentDiskZero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentDiskZero.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 注射泵运行ul数
	 * index 试剂针 1、2 
	 * turn 0x01	运行方向：0x01-顺时针； 0x00-逆时针
     * coord 0x02	转动步数：2-1000
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentInjectionPumpZero/{index}/{turn}/{coord}")
	public ResponseVO reagentInjectionPumpZero(@PathVariable("index") int index,@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入ReagentInjectionPumpZero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentInjectionPumpZero.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 注射泵归零
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentDiskTurnPosition/{index}")
	public ResponseVO reagentDiskTurnPosition(@PathVariable("index") int index) throws Exception {
		System.out.println("进入ReagentDiskTurnPosition");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentDiskTurnPosition.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 试剂盘转到指定位置
	 * index 试剂针 1、2 
	 * turn 0x01	位置：1-45
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentDiskTurnPosition/{index}/{turn}")
	public ResponseVO reagentDiskTurnPosition(@PathVariable("index") int index,@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ReagentDiskTurnPosition");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentDiskTurnPosition.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 获取光耦状态
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reagentGetOptoStatus/{index}")
	public ResponseVO reagentGetOptoStatus(@PathVariable("index") int index) throws Exception {
		System.out.println("进入reagentGetOptoStatus");
		byte[] state=null;
		boolean ret = false;
		List<Byte> aa=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentGetOptoStatus.getId());
			ret=sendReaction(index,dataIn,head);
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
	 * 探针摆1位置
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentProbeSwingPosition1/{index}")
	public ResponseVO reagentProbeSwingPosition1(@PathVariable("index") int index) throws Exception {
		System.out.println("进入ReagentProbeSwingPosition1");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeSwingPosition1.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 探针摆2位置
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentProbeSwingPosition2/{index}")
	public ResponseVO reagentProbeSwingPosition2(@PathVariable("index") int index) throws Exception {
		System.out.println("进入reagentProbeSwingPosition2");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Reagent1.getValue());
			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeSwingPosition2.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 试剂1：探针R1位摆清洗位
     * 试剂2：探针R2位摆清洗位
     * index 试剂针 1、2
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentProbeSwingWashPosition1/{index}")
	public ResponseVO reagentProbeSwingWashPosition1(@PathVariable("index") int index) throws Exception {
		System.out.println("进入ReagentProbeSwingWashPosition1");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeSwingWashPosition1.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 探针摆试剂盘位
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentProbeSwingReagentDiskPosition/{index}")
	public ResponseVO reagentProbeSwingReagentDiskPosition(@PathVariable("index") int index) throws Exception {
		System.out.println("进入ReagentProbeSwingReagentDiskPosition");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeSwingReagentDiskPosition.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 探针加试剂动作（未成功）
	 * index 试剂针 1、2 
	 * turn 0x01	试剂类型：探针1加R1或R4(探针2加R2或R3)
     * reindex 试剂盘位置：1-45
     * quantity 试剂量：20-350
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentTestAddReagent/{index}/{turn}/{reindex}/{quantity}")
	public ResponseVO reagentTestAddReagent(@PathVariable("index") int index,@PathVariable("turn") int turn,@PathVariable("reindex") int reindex,@PathVariable("quantity") int quantity) throws Exception {
		System.out.println("进入ReagentTestAddReagent");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[5];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (reindex & 0xff);
			dataIn[2] = (byte) ((reindex >> 8) & 0xFF);
			dataIn[3] = (byte) (quantity & 0xff);
			dataIn[4] = (byte) ((quantity >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentTestAddReagent.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 探针加试剂动作(试剂板1)
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentTestAddReagentTouch1/{index}")
	public ResponseVO reagentTestAddReagentTouch1(@PathVariable("index") int index) throws Exception {
		System.out.println("进入ReagentTestAddReagentTouch1");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Reagent1.getValue());
			head.setCommand((int)DeviceDebugCommandReagent.ReagentTestAddReagentTouch1.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 探针加试剂动作(试剂板2)
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentTestAddReagentTouch2/{index}")
	public ResponseVO ReagentTestAddReagentTouch2(@PathVariable("index") int index) throws Exception {
		System.out.println("进入ReagentTestAddReagentTouch2");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentTestAddReagentTouch2.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 内壁清洗
	 * index 试剂针 1、2 
	 * turn 0x01	0x00-关；0x01-开
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/eletValueWashInner/{index}/{turn}")
	public ResponseVO eletValueWashInner(@PathVariable("index") int index,@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入EletValueWashInner");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.EletValueWashInner.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 外壁清洗
	 * index 试剂针 1、2 
	 * turn 0x01	0x00-关；0x01-开
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/eletValueWashOuter/{index}/{turn}")
	public ResponseVO eletValueWashOuter(@PathVariable("index") int index,@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入EletValueWashOuter");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setBoardID((byte)DeviceBoardID.Reagent1.getValue());
			head.setCommand((int)DeviceDebugCommandReagent.EletValueWashOuter.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 试剂板补偿参数设置
	 * index 试剂针 1、2 
	 * turn   R1到清洗位置
     *        R4到清洗位置
     * 清洗位到吸试剂
     *  R1到吐清洗液位置
     * 吐清洗液到清洗位置
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/setPara/{index}")
	public ResponseVO setPara(@RequestBody ReagentBoardCompensationPara turn,@PathVariable("index")int index) throws Exception {
		System.out.println("进入SetPara");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[10];
			dataIn[0] = (byte) (turn.getR1ToWash() & 0xff);
			dataIn[1] = (byte) ((turn.getR1ToWash()>> 8) & 0xff);
			dataIn[2] = (byte) (turn.getR4ToWash() & 0xff);
			dataIn[3] = (byte) ((turn.getR4ToWash() >> 8) & 0xFF);
			dataIn[4] = (byte) (turn.getWashToAbsorption() & 0xff);
			dataIn[5] = (byte) (turn.getWashToAbsorption()>> 8 & 0xff);
			dataIn[6] = (byte) (turn.getR1ToAbsorption() & 0xff);
			dataIn[7] = (byte) ((turn.getR1ToAbsorption() >> 8) & 0xFF);
			dataIn[8] = (byte) (turn.getAbsorptionToWash() & 0xff);
			dataIn[9] = (byte) ((turn.getAbsorptionToWash() >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.SetPara.getId());
			ret=sendReaction(index,dataIn,head);
			
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 读取试剂板补偿参数
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getPara/{index}")
	public ResponseVO getPara(@PathVariable("index") int index) throws Exception {
		System.out.println("进入EletValueWashOuter");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.GetPara.getId());
			ret=sendReaction(index,dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED," 读取试剂板补偿参数超时！");
				}
				js++;
				Thread.sleep(1500);
			}
			for(int i=0;i<5;i++){
				byte[] newstate=new byte[4];
				int j=i*2;
				System.arraycopy(state, j, newstate, 0, 2);
				int ste=ByteUtil.getInt(newstate);
				//int ste=(int)((newstate[0])|(newstate[1])<<8);
				newVal.add(ste);
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(newVal);
	}
	public static void main(String[] args) throws IOException {
		/*byte[] newstate={-30,-1,-30,-1,-25,-1,-1,-1,-1,-1};
		System.out.println((int)((newstate[0])|(newstate[1])<<8));
		byte[] state=new byte[2];
		System.arraycopy(newstate, 0, state, 0, 2);
		System.out.println(Arrays.toString(state));
		System.arraycopy(newstate, 2, state, 0, 2);
		System.out.println(Arrays.toString(state));
		System.arraycopy(newstate, 4, state, 0, 2);
		System.out.println(Arrays.toString(state));*/
		/*int depvol=20*10;
		DeviceDebugPumpAccuracy ddp = new DeviceDebugPumpAccuracy();
		ddp.setVol(depvol);
		ddp.setTimes(1);
		byte[] bytg=new byte[3];
		bytg[1] = (byte)(((200)>>8)&0xffff);
		bytg[2] = (byte) (1);
		for (int i = 0; i < bytg.length; i++) {
			System.out.println(bytg[i]);
		}
		System.out.println((int)((bytg[0])|(bytg[1])<<8));*/
		/*DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
		head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeBeready.getId());
			head.setBoardID((byte)DeviceBoardID.Reagent1.getValue());
		int debugHeadSize=head.getClass().getDeclaredFields().length;
		byte[] bytg=new byte[debugHeadSize];
		bytg[0] = (byte) (head.getBoardID() & 0xff);
		bytg[1] = (byte) (head.getCommand() & 0xff);
		bytg[2] = (byte) ((head.getCommand() >> 8) & 0xFF);
		for (int i = 0; i < bytg.length; i++) {
			System.out.println(bytg[i]);
		}*/
	}
	/**
	 * 条码阅读器检查
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/checkReader/{index}")
	public ResponseVO checkReader(@PathVariable("index") int index) throws Exception {
		System.out.println("进入CheckReader");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		List<Byte> aa=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.CheckReader.getId());
			ret=sendReaction(index,dataIn,head);
			//Thread.sleep(5000);
			if(index==1){
				head.setBoardID((byte)DeviceBoardID.Reagent1.getValue());
			}else{
				head.setBoardID((byte)DeviceBoardID.Reagent2.getValue());
			}
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					for(byte s:state){
						aa.add(s);
					}
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED," 条码阅读器检查超时！");
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
	 * 条码长度设置
	 * index 试剂针 1、2 
	 * turn 0x01	条码长度
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/setBarcodeLength/{index}/{turn}")
	public ResponseVO setBarcodeLength(@PathVariable("index") int index,@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入SetBarcodeLength");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.SetBarcodeLength.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 条码长度读取
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getBarcodeLength/{index}")
	public ResponseVO getBarcodeLength(@PathVariable("index") int index) throws Exception {
		System.out.println("进入getBarcodeLength");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		int lencount=0;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.GetBarcodeLength.getId());
			ret=sendReaction(index,dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					//lencount=(int)state[0];
					byte[] state1=new byte[4];
					System.arraycopy(state,0,state1,0,1);
					lencount=ByteUtil.getInt(state1);
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"条码长度读取超时！");
				}
				js++;
				Thread.sleep(1500);
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(lencount);
	}
	/**
	 * 条码扫描失败重复次数设置
	 * index 试剂针 1、2 
	 * turn 0x01	次数
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/setRecheckCount/{index}/{turn}")
	public ResponseVO setRecheckCount(@PathVariable("index") int index,@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入SetRecheckCount");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.SetRecheckCount.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 条码扫描失败重复次数读取
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getScanCount/{index}")
	public ResponseVO getScanCount(@PathVariable("index") int index) throws Exception {
		System.out.println("进入GetScanCount");
		boolean ret = false;
		byte[] state=null;
		int lencount=0;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.GetScanCount.getId());
			ret=sendReaction(index,dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					byte[] state1=new byte[4];
					System.arraycopy(state,0,state1,0,1);
					lencount=ByteUtil.getInt(state1);
					//lencount=(int)state[0];
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"条码扫描失败重复次数读取超时！");
				}
				js++;
				Thread.sleep(1500);
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(lencount);
	}
	/**
	 * 探针精度测试加样（未成功）
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentProbeTest/{index}")
	public ResponseVO reagentProbeTest(@PathVariable("index") int index) throws Exception {
		System.out.println("进入reagentProbeTest");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeTest.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 试剂板补偿参数设置
	 * index 试剂针 1、2 
	 * turn   20ul精度液量补偿（保留2位小数，范围-5到+5）
     * turn1  350ul精度液量补偿（保留2位小数，范围-50到+50）
     * 吐清洗液到清洗位置
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentProbeCompensateWrite/{index}/{turn}/{turn1}")
	public ResponseVO reagentProbeCompensateWrite(@PathVariable("index") int index,@PathVariable("turn") float  turn,@PathVariable("turn1") float  turn1) throws Exception {
		System.out.println("进入ReagentProbeCompensateWrite");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[8];
			dataIn[0] = (byte) (Float.floatToIntBits(turn) & 0xff);
			dataIn[1] = (byte) ((Float.floatToIntBits(turn)>> 8) & 0xff);
			dataIn[2] = (byte) ((Float.floatToIntBits(turn)>> 16) & 0xff);
			dataIn[3] = (byte) ((Float.floatToIntBits(turn) >> 24) & 0xFF);
			dataIn[4] = (byte) (Float.floatToIntBits(turn1) & 0xff);
			dataIn[5] = (byte) ((Float.floatToIntBits(turn1) >> 8) & 0xff);
			dataIn[6] = (byte) ((Float.floatToIntBits(turn1) >> 16) & 0xff);
			dataIn[7] = (byte) ((Float.floatToIntBits(turn1) >> 24) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeCompensateWrite.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 读取探针加样精度补偿液量
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reagentProbeCompensateRead/{index}")
	public ResponseVO reagentProbeCompensateRead(@PathVariable("index") int index) throws Exception {
		System.out.println("进入ReagentProbeCompensateRead");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeCompensateRead.getId());
			ret=sendReaction(index,dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					for(int i=0;i<2;i++){
						byte[] newstate=new byte[4];
						int j=i*4;
						System.arraycopy(state, j, newstate, 0, 4);
						//int ste=(int)((newstate[0])|(newstate[1])<<8|(newstate[2])<<16|(newstate[3])<<24);
						int ste=ByteUtil.getInt(newstate);
						newVal.add(ste);
					}
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"读取探针加样精度补偿液量超时！");
				}
				js++;
				Thread.sleep(1500);
			}
			
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(newVal);
	}
	/**
	 * 转盘指示灯
	 * index 试剂针 1、2 
	 * turn 0x00-关；0x01-开
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentDiskLamp/{index}/{turn}")
	public ResponseVO reagentDiskLamp(@PathVariable("index") int index,@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ReagentDiskLamp");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentDiskLamp.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 试剂1：探针R4位摆清洗位或试剂2：探针R3位摆清洗位
	 * index 试剂针 1、2 
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/reagentProbeSwingWashPosition2/{index}")
	public ResponseVO reagentProbeSwingWashPosition2(@PathVariable("index") int index) throws Exception {
		System.out.println("进入ReagentProbeSwingWashPosition2");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeSwingWashPosition2.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 条码距窗口补偿步数设置
	 * index 
	 * coord 补偿步数（-450到450）
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/BarcodecompensateWrite/{index}/{coord}")
	public ResponseVO BarcodecompensateWrite(@PathVariable("index") int index,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入BarcodecompensateWrite");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (coord & 0xff);
			dataIn[1] = (byte) (coord>>8 & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.BarcodecompensateWrite.getId());
			ret=sendReaction(index,dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 条码距窗口补偿步数读取
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/BarcodecompensateRead/{index}")
	public ResponseVO BarcodecompensateRead(@PathVariable("index") int index) throws Exception {
		System.out.println("进入BarcodecompensateRead");
		boolean ret = false;
		byte[] state=null;
		int ste=0;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.BarcodecompensateRead.getId());
			ret=sendReaction(index,dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
				//	ste=(int)((state[0])|(state[1])<<8);
					byte[] state1=new byte[4];
					System.arraycopy(state,0,state1,0,2);
					ste=ByteUtil.getInt(state1);
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"条码距窗口补偿步数读取超时！");
				}
				js++;
				Thread.sleep(1500);
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(ste);
	}
	/**
	 * 探针精度测试加样就绪
	 * index 
	 * Vol 加样量
	 * count 次数
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/reagentProbeBeready/{index}/{vol}/{count}")
	public ResponseVO reagentProbeBeready(@PathVariable("index") int index,@PathVariable("vol") int vol,@PathVariable("count") int count) throws Exception {
		System.out.println("进入ReagentProbeBeready");
		boolean ret = false;
		byte[] state=null;
		int ste=0;
		try {
			if(!(vol>0&&vol<100)){
				//return;
			}if(!(count>10&&count<350)){
				//return;
			}
			int depvol=vol*10;
			DeviceDebugPumpAccuracy ddp = new DeviceDebugPumpAccuracy();
			ddp.setVol(depvol);
			ddp.setTimes(count);
			byte[] dataIn = new byte[3];
			dataIn[0] = (byte) ((depvol>>8) & 0xff);
			dataIn[1] = (byte) ((depvol) & 0xff);
			dataIn[2] = (byte) (count & 0xff);
			if(index==1){
			    api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.PumpAccuracyReagent1.getStrId(), dataIn);
			}else{
				api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.PumpAccuracyReagent2.getStrId(), dataIn);
			}
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeBeready.getId());
			if(index==1){
				head.setBoardID((byte)DeviceBoardID.Reagent1.getValue());
			}else{
				head.setBoardID((byte)DeviceBoardID.Reagent2.getValue());
			}
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					byte[] state1=new byte[4];
					System.arraycopy(state,0,state1,0,2);
					ste=ByteUtil.getInt(state1);
					break;
				}else if(js>20){
					return new ResponseVO(CodeEnum.FAILED,"探针精度测试加样就绪超时！");
				}
				js++;
				Thread.sleep(1500);
			}

//			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
//			head.setCommand((int)DeviceDebugCommandReagent.ReagentProbeBeready.getId());
//			ret=sendReaction(index,dataIn,head);
			/*if(index==1){
				head.setBoardID((byte)DeviceBoardID.Reagent1.getValue());
			}else{
				head.setBoardID((byte)DeviceBoardID.Reagent2.getValue());
			}*/
//			int debugHeadSize=head.getClass().getDeclaredFields().length;
//			byte[] bytg=new byte[debugHeadSize];
//			bytg[0] = (byte) (head.getBoardID() & 0xff);
//			bytg[1] = (byte) (head.getCommand() & 0xff);
//			bytg[2] = (byte) ((head.getCommand() >> 8) & 0xFF);
			//ret=sendReaction(index,dataIn,head);
//			Thread.sleep(1500);
//			if(index==1){
//				head.setBoardID((byte)DeviceBoardID.Reagent1.getValue());
//			}else{
//				head.setBoardID((byte)DeviceBoardID.Reagent2.getValue());
//			}
//			state=api.getOptoStatus(head.getBoardID(),head);
//			ste=(int)((state[0])|(state[1])<<8);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(ste);
	}
	
	
	
}
