package com.quaero.qbcTest.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quaero.qbcTest.Enum.DeviceBoardID;
import com.quaero.qbcTest.Enum.DeviceCommand;
import com.quaero.qbcTest.Enum.DeviceDebugCommandAC;
import com.quaero.qbcTest.Enum.DeviceDebugCommandSample;
import com.quaero.qbcTest.dto.DeviceDebugPackageHead;
import com.quaero.qbcTest.dto.MessageCode.CodeEnum;
import com.quaero.qbcTest.dto.MessageCode.ResponseVO;
import com.quaero.qbcTest.server.QbcTestApi;
import com.quaero.qbcTest.util.Best;
import com.quaero.qbcTest.util.ByteUtil;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
/**
 * 交流板
 * @author zhangbin
 *
 */
@RestController
@Slf4j
@RequestMapping("/acboard")
public class AcBoardController extends baseController{
	@Autowired
	private QbcTestApi api;
	@Autowired
	private Best best;
	
	public boolean sendReaction(byte[] dataIn,DeviceDebugPackageHead head) throws Exception{
		head.setBoardID((byte)DeviceBoardID.AC.getValue());
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
	 * 水箱进水电磁阀开关
	 * turn 0x01	0x01-开； 0x00-关
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/boxValueIn/{turn}")
	public ResponseVO boxValueIn(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入BoxValueIn");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.BoxValueIn.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 水槽进水电磁阀开关
	 * turn 0x01	0x01-开； 0x00-关
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/slotValueIn/{turn}")
	public ResponseVO slotValueIn(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入SlotValueIn");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.SlotValueIn.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 水槽排水电磁阀开关
	 * turn 0x01	0x01-开； 0x00-关
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/slotValueOut/{turn}")
	public ResponseVO slotValueOut(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入SlotValueOut");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.SlotValueOut.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 水箱循环泵开关
	 * turn 0x01	0x01-开； 0x00-关
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/boxCyclePump/{turn}")
	public ResponseVO boxCyclePump(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入boxCyclePump");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.BoxCyclePump.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 水槽循环泵开关
	 * turn 0x01	0x01-开； 0x00-关
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/slotCyclePump/{turn}")
	public ResponseVO slotCyclePump(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入SlotCyclePump");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.SlotCyclePump.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 真空泵开关
	 * turn 0x01	0x01-开； 0x00-关
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/vacuumPump/{turn}")
	public ResponseVO vacuumPump(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入VacuumPump");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.VacuumPump.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 齿轮泵开关
	 * turn 0x01	0x01-开； 0x00-关
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/gearPump/{turn}")
	public ResponseVO gearPump(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入GearPump");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.GearPump.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 卤素灯开关
	 * turn 0x01	0x01-开； 0x00-关
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/halogenLamps/{turn}")
	public ResponseVO halogenLamps(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入HalogenLamps");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.HalogenLamps.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	
	/**
	 * 水箱加热棒
	 * turn 0x01	0x01-开； 0x00-关
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/boxHeating/{turn}")
	public ResponseVO boxHeating(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入BoxHeating");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.BoxHeating.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 水槽加热棒
	 * turn 0x01	0x01-开； 0x00-关
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/slotHeating/{turn}")
	public ResponseVO slotHeating(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入SlotHeating");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.SlotHeating.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 读取水槽温度系数
	 * @return 系数K，系数B
	 * @throws Exception
	 */
	@GetMapping("/getSlotTemp")
	public ResponseVO getSlotTemp() throws Exception {
		System.out.println("进入GetSlotTemp");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.GetSlotTemp.getId());
			ret=sendReaction(dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					int j=0;
					for(int i=0;i<2;i++){
						byte[] newstate=new byte[4];
						j=i*4;
						System.arraycopy(state, j, newstate, 0, 4);
						float ste=ByteUtil.getFloat(newstate);
						//int ste=(int)((newstate[0])|(newstate[1])<<8);
						newVal.add(ste);
					}
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"读取水槽温度系数超时！");
				}
				js++;
				Thread.sleep(1500);
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(newVal);
	}
	public static void main(String[] args) {
		/*byte[] newstate=new byte[4];
		byte[] state={114,78,-20,60,-109,112,-35,63};
		int j=0;
		for(int i=0;i<2;i++){
			j=i*4;
			System.arraycopy(state, j, newstate, 0, 4);
			//int ste=(int)((newstate[0])|(newstate[1]<<8)|(newstate[1]<<16)|(newstate[1]<<24));
			//long ste=ByteUtil.getInt(newstate);
			float ste=ByteUtil.getFloat(newstate);
			System.out.println("1222="+ste);
		}*/
		/*byte[] state={-59, 4, -54, -51, 19, 66, 0, 0, 0, 0, 50, 12, 50, 12, 111,
				0, 1, -15, 67, 20, 66, 0, 111, 0, 35, -82, -110, 63};
		int j=0;
		for(int i=0;i<6;i++){
			byte[] newstate=new byte[4];
			Object ste=0;
			if(i==0||i==2||i==4){
				j=i*3;
				System.arraycopy(state, j, newstate, 0, 2);
				ste=ByteUtil.getInt(newstate);
			}else if(i==1||i==3||i==5){
				j=i*3-1;
				System.arraycopy(state, j, newstate, 0, 4);
				ste=ByteUtil.getFloat(newstate);
			}
			System.out.println("ste="+ste);
		}*/
		byte[] state={-51, -52, 108, 65, -51, -52, 108, 65, -102, -103, 57, 65, -51, -52, -40, 65, 49, 1, 29, 64, 25, -4, 24, 64, 32, -100, 16, 64, 64, 65, 12, 64, -55, -20, 12, 64, -13, -24, 9, 64, 1};
		for(int i=0;i<11;i++){
			byte[] newstate=new byte[4];
			Object ste=0;
			if(i<10){
			int j=i*4;
			System.arraycopy(state, j, newstate, 0, 4);
			// ste=(int)((newstate[0])|(newstate[1])<<8);
			   ste=ByteUtil.getFloat(newstate);
			}if(i==10){
				 ste=state[i*4];
			}
			System.out.println("ste="+ste);
			//newVal.add(ste);
		}
	}
	/**
	 * 设置水槽温度系数
	 * temp 水槽温度系数K值
	 * temp1 水槽温度系数B值
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/setSlotTemp/{temp}/{temp1}")
	public ResponseVO setSlotTemp(@PathVariable("temp") float temp,@PathVariable("temp1") float temp1) throws Exception {
		System.out.println("进入SetSlotTemp");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[8];
			dataIn[0] = (byte) (Float.floatToIntBits(temp) & 0xff);
			dataIn[1] = (byte) ((Float.floatToIntBits(temp)>>8) & 0xff);
			dataIn[2] = (byte) ((Float.floatToIntBits(temp)>>16) & 0xff);
			dataIn[3] = (byte) ((Float.floatToIntBits(temp)>>24) & 0xff);
			dataIn[4] = (byte) (Float.floatToIntBits(temp1) & 0xff);
			dataIn[5] = (byte) ((Float.floatToIntBits(temp1)>>8) & 0xff);
			dataIn[6] = (byte) ((Float.floatToIntBits(temp1)>>16) & 0xff);
			dataIn[7] = (byte) ((Float.floatToIntBits(temp1)>>24) & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.SetSlotTemp.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 * 读取水箱温度系数
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getBoxTemp")
	public ResponseVO getBoxTemp() throws Exception {
		System.out.println("进入GetBoxTemp");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.GetBoxTemp.getId());
			ret=sendReaction(dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					int j=0;
					for(int i=0;i<2;i++){
						byte[] newstate=new byte[4];
						j=i*4;
						System.arraycopy(state, j, newstate, 0, 4);
						float ste=ByteUtil.getFloat(newstate);
						//int ste=(int)((newstate[0])|(newstate[1])<<8);
						newVal.add(ste);
					}
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"读取水箱温度系数超时！");
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
	 * 设置水箱温度系数
	 * temp 水槽温度系数K值
	 * temp1 水槽温度系数B值
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/setBoxTemp/{temp}/{temp1}")
	public ResponseVO setBoxTemp(@PathVariable("temp") float temp,@PathVariable("temp1") float temp1) throws Exception {
		System.out.println("进入SetBoxTemp");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[8];
			dataIn[0] = (byte) (Float.floatToIntBits(temp) & 0xff);
			dataIn[1] = (byte) ((Float.floatToIntBits(temp)>>8) & 0xff);
			dataIn[2] = (byte) ((Float.floatToIntBits(temp)>>16) & 0xff);
			dataIn[3] = (byte) ((Float.floatToIntBits(temp)>>24) & 0xff);
			dataIn[4] = (byte) (Float.floatToIntBits(temp1) & 0xff);
			dataIn[5] = (byte) ((Float.floatToIntBits(temp1)>>8) & 0xff);
			dataIn[6] = (byte) ((Float.floatToIntBits(temp1)>>16) & 0xff);
			dataIn[7] = (byte) ((Float.floatToIntBits(temp1)>>24) & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.SetBoxTemp.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 *读取交流状态
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getACStates")
	public ResponseVO getACStates() throws Exception {
		System.out.println("进入GetACStates");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.GetACStates.getId());
			ret=sendReaction(dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					int j=0;
					for(int i=0;i<19;i++){
						byte[] newstate=new byte[4];
						Object ste=0;
						if(i<17){
							ste=(int)(state[i]);
							System.out.println("ste"+i+"="+ste);
						}else if(i==17){
							System.arraycopy(state, i, newstate, 0, 4);
							ste=ByteUtil.getFloat(newstate);
							System.out.println("ste1="+ste);
						}else if(i==18){
							j=i+3;
							System.arraycopy(state, j, newstate, 0, 4);
							ste=ByteUtil.getFloat(newstate);
							System.out.println("ste2="+ste);
						}
						newVal.add(ste);
					}
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"读取交流状态超时！");
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
	 *交流复位
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/acReset")
	public ResponseVO acReset() throws Exception {
		System.out.println("进入ACReset");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.ACReset.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 *交流停止
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/acStop")
	public ResponseVO acStop() throws Exception {
		System.out.println("进入ACStop");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.ACStop.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 *交流待机
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/acCStandBy")
	public ResponseVO acStandBy() throws Exception {
		System.out.println("进入ACStandBy");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.ACStandBy.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	
	/**
	 *水槽排水
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/slotWaterOut")
	public ResponseVO slotWaterOut() throws Exception {
		System.out.println("进入SlotWaterOut");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.SlotWaterOut.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 *水槽上水
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/slotWaterIn")
	public ResponseVO slotWaterIn() throws Exception {
		System.out.println("进入SlotWaterIn");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.SlotWaterIn.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 *读取交流数据
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getACData")
	public ResponseVO getACData() throws Exception {
		System.out.println("进入GetACData");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.GetACData.getId());
			ret=sendReaction(dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					int j=0;
					for(int i=0;i<6;i++){
						byte[] newstate=new byte[4];
						Object ste=0;
						if(i==0||i==2||i==4){
							j=i*3;
							System.arraycopy(state, j, newstate, 0, 2);
							ste=ByteUtil.getInt(newstate);
						}else if(i==1||i==3||i==5){
							j=i*3-1;
							System.arraycopy(state, j, newstate, 0, 4);
							ste=ByteUtil.getFloat(newstate);
						}
						System.out.println("ste="+ste);
						newVal.add(ste);
					}
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"读取交流数据超时！");
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
	 *正常模式
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/waterMode")
	public ResponseVO waterMode() throws Exception {
		System.out.println("进入WaterMode");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.WaterMode.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 *无水模式
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/anhydrousMode")
	public ResponseVO anhydrousMode() throws Exception {
		System.out.println("进入AnhydrousMode");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.AnhydrousMode.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	
	/**
	 * 孵育槽阈值设置(无)
	 * turn 0x02	阈值0-999
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/thresholdWrite/{turn}")
	public ResponseVO thresholdWrite(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ThresholdWrite");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[0] = (byte) (turn>>8 & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.ThresholdWrite.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return sendSuccess(ret);
	}
	/**
	 *孵育槽阈值读取(无)
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/thresholdRead")
	public ResponseVO thresholdRead() throws Exception {
		System.out.println("进入ThresholdRead");
		boolean ret = false;
		byte[] state=null;
		int ste=0;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.ThresholdRead.getId());
			ret=sendReaction(dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					byte[] newstate=new byte[4];
					System.arraycopy(state, 0, newstate, 0, 4);
					ste=ByteUtil.getInt(newstate);
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"孵育槽阈值读取超时！");
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
	 *孵育槽系数读取(无)
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getThreshold")
	public ResponseVO getThreshold() throws Exception {
		System.out.println("进入GetThreshold");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		int ste=0;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAC.GetThreshold.getId());
			ret=sendReaction(dataIn,head);
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					byte[] newstate=new byte[4];
					System.arraycopy(state, 0, newstate, 0, 4);
					ste=ByteUtil.getInt(newstate);
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"孵育槽系数读取超时！");
				}
				js++;
				Thread.sleep(1500);
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(newVal);
	}
	
	
	
	
	
	
}