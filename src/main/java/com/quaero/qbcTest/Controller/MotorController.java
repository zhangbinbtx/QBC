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
import com.quaero.qbcTest.Enum.DeviceDebugCommandElectricMachineryMacroDefinition;
import com.quaero.qbcTest.Enum.DeviceDebugCommandReact;
import com.quaero.qbcTest.dto.DeviceDebugPackageHead;
import com.quaero.qbcTest.dto.MessageCode.CodeEnum;
import com.quaero.qbcTest.dto.MessageCode.ResponseVO;
import com.quaero.qbcTest.dto.entity.ElectricMachineryMove;
import com.quaero.qbcTest.dto.entity.FlashStruct;
import com.quaero.qbcTest.server.QbcTestApi;
import com.quaero.qbcTest.util.Best;
import com.quaero.qbcTest.util.ByteUtil;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/motor")
public class MotorController extends baseController {

	@Autowired
	private QbcTestApi api;
	@Autowired
	private Best best;
	/**
	 * 获取电机状态
	 * moudleId 模块 0反应板 1搅拌板 2样本板 3试剂针1 4试剂针2
     * motorId 电机ID
     *   反应板(反应盘电机(0x01)、清洗机构1电机(0x02)、清洗机构2电机(0x03)、碱性清洗液注射泵电机(0x04)、酸性清洗液注射泵电机(0x05))	
     *   搅拌板(搅拌1摆动电机(0x01)\搅拌1升降电机(0x02)\搅拌2摆动电机(0x03)\搅拌2升降电机(0x04))		
     *   样本板(本针摆动电机(0x1)、样本针升降电机(0x02)、质控盘电机(0x03)、样本注射泵电机(0x04))	
     *   试剂板R1(R1试剂针摆动电机(0x01)、R1试剂针升降电机(0x02)、R1试剂盘电机(0x03)、R1注射泵电机(0x04))
     *   试剂板R2(R2试剂针摆动电机(0x01)、R2试剂针升降电机(0x02)、R2试剂盘电机(0x03)、R2注射泵电机(0x04))	
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/electricMachineryState/{moudleId}/{motorId}")
	public ResponseVO electricMachineryState(@PathVariable("moudleId") int moudleId,@PathVariable("moudleId") int motorId) throws Exception {
		System.out.println("进入ElectricMachineryState");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[1];
			dataIn[0] = (byte) (motorId & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			setBoardID(moudleId,head);
			head.setCommand((int)DeviceDebugCommandElectricMachineryMacroDefinition.ElectricMachineryState.getId());
			ret=sendReaction(dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"获取电机状态超时！");
				}
				js++;
				Thread.sleep(1500);
			}
			for(int i=0;i<4;i++){
				byte[] newstate=new byte[4];
				Object ste=0;
				if(i<2){
					ste=state[i];
				}else if(i==2){
					int j=i;
					System.arraycopy(state, j, newstate, 0, 4);
					ste=ByteUtil.getFloat(newstate);
				}else if(i==3){
					int j=i*2;
					System.arraycopy(state, j, newstate, 0, 4);
					ste=ByteUtil.getFloat(newstate);
				}
				newVal.add(ste);
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(newVal);
	}
	/**
	 * 控制电机
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/electricMachineryMove")
	public ResponseVO electricMachineryMove(@RequestBody ElectricMachineryMove turn) throws Exception {
		System.out.println("进入ElectricMachineryMove");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		String ste=null;
		try {
			byte[] dataIn = new byte[6];
			dataIn[0] = (byte) (turn.getID() & 0xff);
			dataIn[1] = (byte) (turn.getDirectionId() & 0xff);
			dataIn[2] = (byte) (turn.getCoordinate() & 0xff);
			dataIn[3] = (byte) ((turn.getCoordinate()>>8) & 0xff);
			dataIn[4] = (byte) (turn.getSpeed() & 0xff);
			dataIn[5] = (byte) ((turn.getSpeed()>>8) & 0xff);
			
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			setBoardID(turn.getBoardID(),head);
			head.setCommand((int)DeviceDebugCommandElectricMachineryMacroDefinition.ElectricMachineryMove.getId());
			ret=sendReaction(dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"控制电机超时！");
				}
				js++;
				Thread.sleep(1500);
			}
			ste=state[0]==1?"执行完成":"执行失败";
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(ste);
	}
	
	/**
	 *读取Flash电机参数
	 * moudleId 模块 0反应板 1搅拌板 2样本板 
     * motorId 电机ID
     *   反应板(反应盘电机(0x01)、清洗机构1电机(0x02)、清洗机构2电机(0x03)、碱性清洗液注射泵电机(0x04)、酸性清洗液注射泵电机(0x05))	
     *   搅拌板(搅拌1摆动电机(0x01)\搅拌1升降电机(0x02)\搅拌2摆动电机(0x03)\搅拌2升降电机(0x04))		
     *   样本板(本针摆动电机(0x1)、样本针升降电机(0x02)、质控盘电机(0x03)、样本注射泵电机(0x04))	
     *   试剂板R1(R1试剂针摆动电机(0x01)、R1试剂针升降电机(0x02)、R1试剂盘电机(0x03)、R1注射泵电机(0x04))
     *   试剂板R2(R2试剂针摆动电机(0x01)、R2试剂针升降电机(0x02)、R2试剂盘电机(0x03)、R2注射泵电机(0x04))	
	 * @return
	 * 电机编号、最大速度（1500-30000）、最小速度（1500-30000）、加速步数（50-700）、减速步数（50-700）
	 * @throws Exception
	 */
	@GetMapping("/flashRead/{moudleId}/{motorId}")
	public ResponseVO flashRead(@PathVariable("moudleId") int moudleId,@PathVariable("moudleId") int motorId) throws Exception {
		System.out.println("进入FlashRead");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[1];
			dataIn[0] = (byte) (motorId & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			setBoardID(moudleId,head);
			head.setCommand((int)DeviceDebugCommandElectricMachineryMacroDefinition.FlashRead.getId());
			ret=sendReaction(dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"读取Flash电机参数超时！");
				}
				js++;
				Thread.sleep(1500);
			}
			for(int i=0;i<5;i++){
				byte[] newstate=new byte[4];
				Object ste=0;
				if(i==0){
					ste=state[i];
				}else {
					int j=i*2-1;
					System.arraycopy(state, j, newstate, 0, 2);
					ste=ByteUtil.getInt(newstate);
				}
				newVal.add(ste);
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(newVal);
	}
	/**
	 * 写入Flash电机参数
	 * @param trun flash电机参数结构
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/flashWrite")
	public ResponseVO flashWrite(@RequestBody FlashStruct turn) throws Exception {
		System.out.println("进入FlashWrite");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		Object ste=0;
		try {
			byte[] dataIn = new byte[6];
			dataIn[0] = (byte) (turn.getMachineryID() & 0xff);
			dataIn[1] = (byte) (turn.getFlashParameter()[0] & 0xff);
			dataIn[2] = (byte) ((turn.getFlashParameter()[0]>>8) & 0xff);
			dataIn[3] = (byte) (turn.getFlashParameter()[1] & 0xff);
			dataIn[4] = (byte) ((turn.getFlashParameter()[1]>>8) & 0xff);
			dataIn[5] = (byte) (turn.getFlashParameter()[2] & 0xff);
			dataIn[6] = (byte) ((turn.getFlashParameter()[2]>>8) & 0xff);
			dataIn[7] = (byte) (turn.getFlashParameter()[3] & 0xff);
			dataIn[8] = (byte) ((turn.getFlashParameter()[3]>>8) & 0xff);
			dataIn[9] = (byte) (turn.getFlashParameter()[4] & 0xff);
			
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			setBoardID(turn.getBoardID(),head);
			head.setCommand((int)DeviceDebugCommandElectricMachineryMacroDefinition.FlashWrite.getId());
			ret=sendReaction(dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"控制电机超时！");
				}
				js++;
				Thread.sleep(1500);
			}
			ste=state[0];
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(ste);
	}
	public void setBoardID(int moudleId,DeviceDebugPackageHead head){
		if(moudleId==0){
			head.setBoardID((byte)DeviceBoardID.React.getValue());
		}else if(moudleId==1){
			head.setBoardID((byte)DeviceBoardID.Stir.getValue());
		}else if(moudleId==2){
			head.setBoardID((byte)DeviceBoardID.Sample.getValue());
		}else if(moudleId==3){
			head.setBoardID((byte)DeviceBoardID.Reagent1.getValue());
		}else if(moudleId==4){
			head.setBoardID((byte)DeviceBoardID.Reagent2.getValue());
		}
	}
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

}
