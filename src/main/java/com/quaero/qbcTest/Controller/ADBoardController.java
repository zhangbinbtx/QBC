package com.quaero.qbcTest.Controller;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quaero.qbcTest.Enum.DeviceBoardID;
import com.quaero.qbcTest.Enum.DeviceCommand;
import com.quaero.qbcTest.Enum.DeviceDebugCommandAC;
import com.quaero.qbcTest.Enum.DeviceDebugCommandAD;
import com.quaero.qbcTest.dto.DeviceDebugPackageHead;
import com.quaero.qbcTest.dto.MessageCode.CodeEnum;
import com.quaero.qbcTest.dto.MessageCode.ResponseVO;
import com.quaero.qbcTest.dto.entity.DeviceDebugADPara_Tongcai_Setting;
import com.quaero.qbcTest.server.QbcTestApi;
import com.quaero.qbcTest.util.Best;
import com.quaero.qbcTest.util.ByteUtil;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import cn.hutool.core.util.ArrayUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
/**
 * AD板
 * @author zhangbin
 *
 */
@RestController
@Slf4j
@RequestMapping("/adboard")
public class ADBoardController extends baseController{
	@Autowired
	private QbcTestApi api;
	@Autowired
	private Best best;
	
	public boolean sendReaction(byte[] dataIn,DeviceDebugPackageHead head) throws Exception{
		head.setBoardID((byte)DeviceBoardID.AD.getValue());
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
		return api.sendToMachine(best.getMachineIP1(), 1, DeviceCommand.DeviceADDebug.getStrId(), bytg);
	}
	/**
	 * 读取AD数据
	 * turn AD数据类型：0x01-补偿值；0x02-原始值
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/readADData/{turn}")
	public ResponseVO readADData(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入ReadADData");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			dataIn[0]=(byte)(turn& 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAD.ReadADData.getId());
			ret=sendReaction(dataIn,head);
			Thread.sleep(1500);
			head.setBoardID((byte)DeviceBoardID.AD.getValue());
			int js=0;
			while(true){
				state=api.getAdStatus(head.getBoardID(),head);
				if(state!=null){
					for(int i=0;i<16;i++){
						byte[] newstate=new byte[4];
						int j=i*2;
						System.arraycopy(state, j, newstate, 0, 2);
						//long ste=(long)((newstate[0])|(newstate[1])<<8|(newstate[2])<<16|(newstate[3])<<24);
						long ste=ByteUtil.getInt(newstate);
						newVal.add(ste);
					}
					break;
				}else if(js>100){
					return new ResponseVO(CodeEnum.FAILED,"读取AD数据超时！");
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
		byte[] newstate=new byte[4];
//		newstate[0] = (byte) (10957 & 0xff);
//		newstate[1] = (byte) ((10957>>8) & 0xff);
//		newstate[2] = (byte) ((10957>>16) & 0xff);
//		newstate[3] = (byte) ((10957>>24) & 0xff);
//		System.out.println(Arrays.toString(newstate));
//		//int ste=(int)((newstate[3])<<24)|(newstate[2]<<16)|(newstate[1]<<8)|(newstate[0]);
//		int ste=getInt(newstate);
////		int ste=ByteBuffer.wrap(newstate).getInt();
//		System.out.println(ste);
		
		byte[] state={53, -39, 86, -41, 81, -42, 91, -34, 55, -31, 32, -27, 10, -34, 66, -22, 61, -36, 4, 4, 36, -16, 83, -1, -1, -1, -1, -1, -1, -1, -1, -1};
		System.arraycopy(state, 0, newstate, 0, 2);
		//int ste=getInt(newstate);
	    int ste=(int)((newstate[0])|(newstate[1])<<8);
	    System.out.println(ste);
	}
	public static int getInt(byte[] bytes)
	  {
	    return (0xff & bytes[0]) | (0xff00 & (bytes[1] << 8)) | (0xff0000 & (bytes[2] << 16)) | (0xff000000 & (bytes[3] << 24));
	  }
	/**
	 * 设置AD数据
	 * turn byte 16个参数
	 * 0x02	第1路波长补偿值    0x02	第9路波长补偿值
	 * 0x02	第2路波长补偿值    0x02	第10路波长补偿值
	 * 0x02	第3路波长补偿值    0x02	第11路波长补偿值
	 * 0x02	第4路波长补偿值    0x02	第12路波长补偿值
	 * 0x02	第5路波长补偿值    0x02	第13路波长补偿值
	 * 0x02	第6路波长补偿值    0x02	第14路波长补偿值
	 * 0x02	第7路波长补偿值    0x02	第15路波长补偿值
	 * 0x02	第8路波长补偿值    0x02	第16路波长补偿值
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/WriteADDeviceFactor")
	public boolean writeADDeviceFactor(@RequestBody byte[] turn) throws Exception {
		System.out.println("进入WriteADDeviceFactor");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[32];
			int index=0;
			for(int i=0;i<turn.length;i++){
				dataIn[index++] = (byte) (turn[i] & 0xff);
				dataIn[index++] = (byte) ((turn[i]>>8) & 0xff);
			}
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAD.WriteADDeviceFactor.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 读取机器AD系数（无）
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/readADDeviceFactor")
	public ResponseVO readADDeviceFactor() throws Exception {
		System.out.println("进入ReadADDeviceFactor");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAD.ReadADDeviceFactor.getId());
			ret=sendReaction(dataIn,head);
			head.setBoardID((byte)DeviceBoardID.AD.getValue());
			int js=0;
			while(true){
				state=api.getAdStatus(head.getBoardID(),head);
				if(state!=null){
					for(int i=0;i<16;i++){
						byte[] newstate=new byte[4];
						int j=i*2;
						System.arraycopy(state, j, newstate, 0, 2);
						//int ste=(int)((newstate[0])|(newstate[1])<<8);
						long ste=ByteUtil.getInt(newstate);
						newVal.add(ste);
					}
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"读取机器AD系数超时！");
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
	 * 自动设置光学AD系数
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/autoSetPhotoADFactor")
	public boolean autoSetPhotoADFactor() throws Exception {
		System.out.println("进入AutoSetPhotoADFactor");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAD.AutoSetPhotoADFactor.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 设置AD数据
	 * turn byte 16个参数
	 * 0x02	第1路波长补偿值    0x02	第9路波长补偿值
	 * 0x02	第2路波长补偿值    0x02	第10路波长补偿值
	 * 0x02	第3路波长补偿值    0x02	第11路波长补偿值
	 * 0x02	第4路波长补偿值    0x02	第12路波长补偿值
	 * 0x02	第5路波长补偿值    0x02	第13路波长补偿值
	 * 0x02	第6路波长补偿值    0x02	第14路波长补偿值
	 * 0x02	第7路波长补偿值    0x02	第15路波长补偿值
	 * 0x02	第8路波长补偿值    0x02	第16路波长补偿值
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/manualSetPhotoADFactor")
	public boolean manualSetPhotoADFactor(@RequestBody byte[] turn) throws Exception {
		System.out.println("进入ManualSetPhotoADFactor");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[32];
			int index=0;
			for(int i=0;i<turn.length;i++){
				dataIn[index++] = (byte) (turn[i] & 0xff);
				dataIn[index++] = (byte) ((turn[i]>>8) & 0xff);
			}
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAD.ManualSetPhotoADFactor.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 读取AD测试参数
	 * @return  [脉冲间隔、开始杯号、*、一次上传杯子个数、开始采集脉冲数、总杯子数]
	 * @throws Exception
	 */
	@GetMapping("/getADPara")
	public ResponseVO getADPara() throws Exception {
		System.out.println("进入GetADPara");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAD.GetADPara.getId());
			ret=sendReaction(dataIn,head);
			Thread.sleep(1500);
			head.setBoardID((byte)DeviceBoardID.AD.getValue());
			int js=0;
			while(true){
				state=api.getAdStatus(head.getBoardID(),head);
				if(state!=null){
					byte[] newstate=new byte[4];
					for(int i=0;i<6;i++){
						if(i==0||i==3||i==4){
							newVal.add(state[i]);
						}else{
							int ste=0;
							if(i==1){
								System.arraycopy(state, 1, newstate, 0, 2);
								ste=(int)((newstate[0])|(newstate[1])<<8);
							}else if(i==5){
								System.arraycopy(state, 6, newstate, 0, 2);
								ste=getInt(newstate);
							}
							newVal.add(ste);
						}
					}
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"读取AD测试参数超时！");
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
	 * 设置AD测试参数
	 * getTestType 1-测试
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/setADPara")
	public boolean setADPara(@PathVariable("turn") DeviceDebugADParaSetting turn) throws Exception {
		System.out.println("进入SetADPara");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn.getTestType() & 0xff);
			dataIn[1] = (byte) (turn.getStartCupNo() & 0xff);
			dataIn[2] = (byte) (turn.getStartCupNo()>>8 & 0xff);
			dataIn[3] = (byte) (turn.getCount() & 0xff);
			dataIn[4] = (byte) (turn.getCollect() & 0xff);
			dataIn[5] = (byte) (turn.getInterval() & 0xff);
			dataIn[6] = (byte) (turn.getCupCount() & 0xff);
			dataIn[7] = (byte) (turn.getCupCount()>>8 & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			if(turn.getTestType()==1){
				head.setCommand((int)DeviceDebugCommandAD.SetADPara.getId());
			}else if(turn.getTestType()==2){
				head.setCommand((int)DeviceDebugCommandAD.SetADPara1.getId());
			}
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 设置AD通采参数
	 * getTestType 2-通采；
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/SetADPara1")
	public boolean SetADPara1(@PathVariable("turn") DeviceDebugADPara_Tongcai_Setting turn) throws Exception {
		System.out.println("进入SetADPara1");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn.getTestType() & 0xff);
			dataIn[1] = (byte) (turn.getCollectCount() & 0xff);
			dataIn[2] = (byte) ((turn.getCollectCount()>>8) & 0xff);
			dataIn[3] = (byte) (turn.getUploadCount() & 0xff);
			dataIn[4] = (byte) ((turn.getUploadCount()>>8) & 0xff);
			dataIn[5] = (byte) (turn.getInterval() & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
				head.setCommand((int)DeviceDebugCommandAD.SetADPara1.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 读取AD通采参数(有流程、未完成)
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getADPara1")
	public ResponseVO getADPara1() throws Exception {
		System.out.println("进入GetADPara1");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAD.GetADPara1.getId());
			ret=sendReaction(dataIn,head);
			head.setBoardID((byte)DeviceBoardID.AD.getValue());
			int js=0;
			while(true){
				state=api.getAdStatus(head.getBoardID(),head);
				if(state!=null){
					for(int i=0;i<4;i++){
						byte[] newstate=new byte[2];
						if(i==0||i==4){
							newVal.add(state[i]);
						}else{
							if(i==1){
								System.arraycopy(state, 1, newstate, 0, 2);
							}else if(i==2){
								System.arraycopy(state, 3, newstate, 0, 2);
							}
							int ste=(int)((newstate[0])|(newstate[1])<<8);
							newVal.add(ste);
						}
					}
					break;
				}else if(js>10){
					return new ResponseVO(CodeEnum.FAILED,"读取AD通采参数超时！");
				}
				js++;
				Thread.sleep(1500);
			}
			
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(ret);
	}
	/**
	 * 读取光学AD系数
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getPhotoADFactor")
	public ResponseVO getPhotoADFactor() throws Exception {
		System.out.println("进入GetPhotoADFactor");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAD.GetPhotoADFactor.getId());
			ret=sendReaction(dataIn,head);
			head.setBoardID((byte)DeviceBoardID.AD.getValue());
			int js=0;
			while(true){
				state=api.getAdStatus(head.getBoardID(),head);
				if(state!=null){
					int j=0;
					for(int i=0;i<16;i++){
						byte[] newstate=new byte[4];
						j=i*2;
						System.arraycopy(state, j, newstate, 0, 2);
						int ste=(int)((newstate[0])|(newstate[1])<<8);
						newVal.add(ste);
					}
					break;
				}else if(js>5){
					return new ResponseVO(CodeEnum.FAILED,"读取AD通采参数超时！");
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
	 * 模拟测试
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/simulationTest")
	public boolean simulationTest() throws Exception {
		System.out.println("进入SimulationTest");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandAD.SimulationTest.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	
	
}
@Data
class DeviceDebugADParaSetting
{
    public byte TestType;//测试类型 1-测试 2-通采
    public int StartCupNo;//开始杯号
    public byte Count;//一次上次杯子数
    public byte Collect;//采集脉冲
    public byte Interval;//脉冲间隔
    public int CupCount;//杯子总数
}
