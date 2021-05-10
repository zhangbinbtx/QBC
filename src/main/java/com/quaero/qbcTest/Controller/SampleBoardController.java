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
import com.quaero.qbcTest.Enum.DeviceDebugCommandSample;
import com.quaero.qbcTest.Enum.DeviceDebugCommandStir;
import com.quaero.qbcTest.dto.DeviceDebugPackageHead;
import com.quaero.qbcTest.dto.MessageCode.CodeEnum;
import com.quaero.qbcTest.dto.MessageCode.ResponseVO;
import com.quaero.qbcTest.server.QbcTestApi;
import com.quaero.qbcTest.util.Best;

import cn.hutool.core.util.ArrayUtil;
/**
 * 样本板
 * @author zhangbin
 *
 */
@RestController
@RequestMapping("/sample")
public class SampleBoardController extends baseController{
	@Autowired
	private QbcTestApi api;
	@Autowired
	private Best best;
	
	public boolean sendReaction(byte[] dataIn,DeviceDebugPackageHead head) throws Exception{
		head.setBoardID((byte)DeviceBoardID.Sample.getValue());
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
	 * turn 0x01	运行方向：0x01-上升； 0x00-下降
     * coord 0x02	转动步数：300-3500
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeLiftingStep/{turn}/{coord}")
	public boolean sampleProbeLiftingStep(@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入SampleProbeLiftingStep");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[3];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeLiftingStep.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 探针升降归零
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeLiftingZero")
	public boolean sampleProbeLiftingZero() throws Exception {
		System.out.println("进入SampleProbeLiftingZero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeLiftingZero.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 探针摆动运行步数
	 * turn 0x01	运行方向：0x01-顺时针； 0x00-逆时针
     * coord 0x02	转动步数：200-2500
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeSwingStep/{turn}/{coord}")
	public boolean sampleProbeSwingStep(@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入sampleProbeSwingStep");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeSwingStep.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 探针摆动归零
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeSwingZero")
	public boolean sampleProbeSwingZero() throws Exception {
		System.out.println("进入sampleProbeSwingZero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeSwingZero.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 *摆加样
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeSwingSamplingPosition")
	public boolean sampleProbeSwingSamplingPosition() throws Exception {
		System.out.println("进入SampleProbeSwingSamplingPosition");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeSwingSamplingPosition.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 *摆质控盘外圈
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeSwingDiskOut")
	public boolean sampleProbeSwingDiskOut() throws Exception {
		System.out.println("进入SampleProbeSwingDiskOut");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeSwingDiskOut.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 *摆质控盘内圈
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeSwingDiskIn")
	public boolean sampleProbeSwingDiskIn() throws Exception {
		System.out.println("进入SampleProbeSwingDiskIn");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeSwingDiskIn.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 *摆质控盘清洗
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeSwingDiskWashPosition")
	public boolean sampleProbeSwingDiskWashPosition() throws Exception {
		System.out.println("进入SampleProbeSwingDiskWashPosition");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeSwingDiskWashPosition.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 *摆轨道清洗
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeSwingTrackWashPosition")
	public boolean sampleProbeSwingTrackWashPosition() throws Exception {
		System.out.println("进入sampleProbeSwingTrackWashPosition");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeSwingTrackWashPosition.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 *摆轨道1
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeSwingTrack1")
	public boolean sampleProbeSwingTrack1() throws Exception {
		System.out.println("进入sampleProbeSwingTrack1");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeSwingTrack1.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 *摆轨道2
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeSwingTrack2")
	public boolean sampleProbeSwingTrack2() throws Exception {
		System.out.println("进入sampleProbeSwingTrack2");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeSwingTrack2.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 探针加样本动作
	 * turn 0x01	样本盘盘号：1-轨道； 2-盘子；
	 * sampleIndex 样本盘位置：1-5 161-162
	 * sampleCap  样本量：20-350（浮点型）
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeSamplingAction/{turn}/{sampleIndex}/{sampleCap}")
	public boolean sampleProbeSamplingAction(@PathVariable("turn") int turn,@PathVariable("sampleIndex") int sampleIndex,@PathVariable("sampleCap") float sampleCap) throws Exception {
		System.out.println("进入sampleProbeSamplingAction");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[6];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (sampleIndex & 0xff);
			dataIn[2] = (byte) (Float.floatToIntBits(sampleCap) & 0xff);
			dataIn[3] = (byte) ((Float.floatToIntBits(sampleCap) >> 8) & 0xFF);
			dataIn[4] = (byte) ((Float.floatToIntBits(sampleCap) >> 16) & 0xFF);
			dataIn[5] = (byte) ((Float.floatToIntBits(sampleCap) >> 24) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeSamplingAction.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 质控盘运行步数
	 * turn 0x01	运行方向：0x01-顺时针； 0x00-逆时针
	 * coord 转动步数：200-50000
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleDiskTurnStep/{turn}/{coord}")
	public boolean sampleDiskTurnStep(@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入SampleDiskTurnStep");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[3];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			dataIn[2] = (byte) ((coord >> 8) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleDiskTurnStep.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 *质控盘归零
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleDiskZero")
	public boolean sampleDiskZero() throws Exception {
		System.out.println("进入sampleDiskZero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleDiskZero.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 质控盘外圈转动个数
	 * turn 0x01	运行方向：0x01-顺时针； 0x00-逆时针
	 * count 转动步数：1-25
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleDiskOutTurnCount/{turn}/{count}")
	public boolean sampleDiskOutTurnCount(@PathVariable("turn") int turn,@PathVariable("count") int count) throws Exception {
		System.out.println("进入SampleDiskOutTurnCount");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (count & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleDiskOutTurnCount.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 质控盘内圈转动个数
	 * turn 0x01	运行方向：0x01-顺时针； 0x00-逆时针
	 * count 转动步数：1-25
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleDiskInTurnCount/{turn}/{count}")
	public boolean sampleDiskInTurnCount(@PathVariable("turn") int turn,@PathVariable("count") int count) throws Exception {
		System.out.println("进入SampleDiskInTurnCount");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (count & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleDiskInTurnCount.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 注射泵运行ul数
	 * turn 0x01	运行方向：0x01-上； 0x00-下
	 * coord 转动步数：2-100
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleInjectionPumpSuck/{turn}/{coord}")
	public boolean sampleInjectionPumpSuck(@PathVariable("turn") int turn,@PathVariable("coord") int coord) throws Exception {
		System.out.println("进入SampleInjectionPumpSuck");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (coord & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleInjectionPumpSuck.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 *注射泵归零
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleInjectionPumpZero")
	public boolean sampleInjectionPumpZero() throws Exception {
		System.out.println("进入SampleInjectionPumpZero");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleInjectionPumpZero.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	
	/**
	 * 写入补偿步数(未测试)
	 * clean 0x02	加样到质控清洗
	 * clean1 0x02 质控清洗到质控盘外
	 * clean2 0x02质控清洗到质控盘内
	 * clean3 0x02加样到轨道清洗
	 * clean4 0x02轨道清洗到轨道1
	 * clean5 0x02轨道清洗到轨道2
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/setPara/{clean}/{clean1}/{clean2}/{clean3}/{clean4}/{clean5}")
	public boolean setPara(@PathVariable("clean") int clean,@PathVariable("clean1") int clean1,@PathVariable("clean2") int clean2,
			@PathVariable("clean3") int clean3,@PathVariable("clean4") int clean4,@PathVariable("clean5") int clean5) throws Exception {
		System.out.println("进入SetPara");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[12];
			dataIn[0] = (byte) (clean & 0xff);
			dataIn[1] = (byte) (clean >>8 & 0xff);
			dataIn[2] = (byte) (clean1 & 0xff);
			dataIn[3] = (byte) (clean1 >>8 & 0xff);
			dataIn[4] = (byte) (clean2 & 0xff);
			dataIn[5] = (byte) (clean2 >>8 & 0xff);
			dataIn[6] = (byte) (clean3 & 0xff);
			dataIn[7] = (byte) (clean3 >>8 & 0xff);
			dataIn[8] = (byte) (clean4 & 0xff);
			dataIn[9] = (byte) (clean4 >>8 & 0xff);
			dataIn[10] = (byte) (clean5 & 0xff);
			dataIn[11] = (byte) (clean5 >>8 & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SetPara.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 *读取补偿步数
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getPara")
	public ResponseVO getPara() throws Exception {
		System.out.println("进入GetPara");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.GetPara.getId());
			ret=sendReaction(dataIn,head);
			Thread.sleep(1500);
			head.setBoardID((byte)DeviceBoardID.Sample.getValue());
			state=api.getOptoStatus(head.getBoardID(),head);
			byte[] newstate=new byte[4];
			for(int i=0;i<6;i++){
				int j=i*2;
				System.arraycopy(state, j, newstate, 0, 2);
				int ste=(int)((newstate[0])|(newstate[1])<<8);
				newVal.add(ste);
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(newVal);
	}
	/**
	 *获取光耦状态
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleGetOptoStatus")
	public ResponseVO sampleGetOptoStatus() throws Exception {
		System.out.println("进入SampleGetOptoStatus");
		byte[] state=null;
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleGetOptoStatus.getId());
			ret=sendReaction(dataIn,head);
			Thread.sleep(1500);
			head.setBoardID((byte)DeviceBoardID.Sample.getValue());
			state=api.getOptoStatus(head.getBoardID(),head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(state);
	}
	
	/**
	 * 内壁清洗
	 * turn 0x01	0x00-关；0x01-开
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/eletValueWashInner/{turn}")
	public boolean eletValueWashInner(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入EletValueWashInner");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.EletValueWashInner.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 外壁清洗
	 * turn 0x01	0x00-关；0x01-开
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/eletValueWashOuter/{turn}")
	public boolean eletValueWashOuter(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入EletValueWashOuter");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.EletValueWashOuter.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 条码长度设置(未测试)
	 * turn 0x01	条码长度:1-20
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/setBarcodeLength/{turn}")
	public boolean setBarcodeLength(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入SetBarcodeLength");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SetBarcodeLength.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 条码长度读取
	 * turn 0x01	条码长度:1-20
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getBarcodeLength")
	public ResponseVO getBarcodeLength() throws Exception {
		System.out.println("进入GetBarcodeLength");
		boolean ret = false;
		byte[] state=null;
		int lencount=0;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.GetBarcodeLength.getId());
			ret=sendReaction(dataIn,head);
			head.setBoardID((byte)DeviceBoardID.Sample.getValue());
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					lencount=(int)state[0];
					break;
				}else if(js>100){
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
	 * 条码阅读器检查
	 * turn 0x01	条码长度:1-20
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/checkReader")
	public ResponseVO checkReader() throws Exception {
		System.out.println("进入CheckReader");
		boolean ret = false;
		byte[] state=null;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.CheckReader.getId());
			ret=sendReaction(dataIn,head);
			head.setBoardID((byte)DeviceBoardID.Sample.getValue());
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					break;
				}else if(js>1000){
					return new ResponseVO(CodeEnum.FAILED,"条码阅读器检查获取超时！");
				}
				js++;
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(state);
	}
	/**
	 * 扫描失败重复次数设置
	 * turn 0x01	重复次数
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/setRecheckCount/{turn}")
	public boolean setRecheckCount(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入SetRecheckCount");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SetRecheckCount.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	
	/**
	 * 扫描失败重复次数读取
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getScanCount")
	public ResponseVO getScanCount() throws Exception {
		System.out.println("进入GetScanCount");
		boolean ret = false;
		byte[] state=null;
		int lencount=0;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.GetScanCount.getId());
			ret=sendReaction(dataIn,head);
			head.setBoardID((byte)DeviceBoardID.Sample.getValue());
			int js=0;
			while(true){
				state=api.getOptoStatus(head.getBoardID(),head);
				if(state!=null){
					lencount=(int)state[0];
					break;
				}else if(js>1000){
					return new ResponseVO(CodeEnum.FAILED,"扫描失败重复次数读取超时！");
				}
				js++;
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(lencount);
	}
	/**
	 * 样本探针摆加清洗液
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/moveToWash")
	public boolean moveToWash() throws Exception {
		System.out.println("进入MoveToWash");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.MoveToWash.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	
	/**
	 * 探针精度测试加样
	 * turn 0x01	重复次数
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeTest")
	public boolean sampleProbeTest() throws Exception {
		System.out.println("进入SampleProbeTest");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeTest.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	
	/**
	 * 设置探针加样精度补偿液量
	 * volume 0x02	2ul精度液量补偿（保留2位小数，范围-1到+1）
	 * volume1 0x02 5ul精度液量补偿（保留2位小数，范围-3到+3）
	 * volume2 0x02 35ul精度液量补偿（保留2位小数，范围-5到+5）
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeCompensateWrite/{volume}/{volume1}/{volume2}")
	public boolean sampleProbeCompensateWrite(@PathVariable("volume") float volume,@PathVariable("volume1") float volume1,@PathVariable("volume2") float volume2) throws Exception {
		System.out.println("进入SampleProbeCompensateWrite");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[12];
			dataIn[0] = (byte) (Float.floatToIntBits(volume) & 0xff);
			dataIn[1] = (byte) ((Float.floatToIntBits(volume) >> 8) & 0xFF);
			dataIn[2] = (byte) ((Float.floatToIntBits(volume) >> 16) & 0xFF);
			dataIn[3] = (byte) ((Float.floatToIntBits(volume) >> 24) & 0xFF);
			dataIn[4] = (byte) (Float.floatToIntBits(volume1) & 0xff);
			dataIn[5] = (byte) ((Float.floatToIntBits(volume1) >> 8) & 0xFF);
			dataIn[6] = (byte) ((Float.floatToIntBits(volume1) >> 16) & 0xFF);
			dataIn[7] = (byte) ((Float.floatToIntBits(volume1) >> 24) & 0xFF);
			dataIn[8] = (byte) (Float.floatToIntBits(volume2) & 0xff);
			dataIn[9] = (byte) ((Float.floatToIntBits(volume2) >> 8) & 0xFF);
			dataIn[10] = (byte) ((Float.floatToIntBits(volume2) >> 16) & 0xFF);
			dataIn[11] = (byte) ((Float.floatToIntBits(volume2) >> 24) & 0xFF);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeCompensateWrite.getId());
			ret=sendReaction(dataIn,head);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 读取探针加样精度补偿液量
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/SampleProbeCompensateRead")
	public ResponseVO SampleProbeCompensateRead() throws Exception {
		System.out.println("进入SampleProbeCompensateRead");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeCompensateRead.getId());
			ret=sendReaction(dataIn,head);
			Thread.sleep(1500);
			head.setBoardID((byte)DeviceBoardID.Sample.getValue());
			state=api.getOptoStatus(head.getBoardID(),head);
			byte[] newstate=new byte[4];
			for(int i=0;i<3;i++){
				int j=i*4;
				System.arraycopy(state, j, newstate, 0, 4);
				int ste=(int)((newstate[0])|(newstate[1])<<8|(newstate[2])<<16|(newstate[3])<<24);
				newVal.add(ste);
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(newVal);
	}
	/**
	 * 液面监控(未成功)
	 * turn 0x02	执行次数:1-65535
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/sampleProbeSurfaceMonitor/{turn}")
	public ResponseVO sampleProbeSurfaceMonitor(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入SampleProbeSurfaceMonitor");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		int ste=0;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (turn>>8 & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.SampleProbeSurfaceMonitor.getId());
			ret=sendReaction(dataIn,head);
			Thread.sleep(10000);
			head.setBoardID((byte)DeviceBoardID.Sample.getValue());
			state=api.getOptoStatus(head.getBoardID(),head);
			ste=(int)((state[0])|(state[1])<<8);
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return new ResponseVO(ste);
	}
	/**
	 * 条码距窗口补偿步数设置
	 * turn 0x02	外圈补偿步数（-450到450）
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/barcodecompensateWrite/{turn}")
	public boolean barcodecompensateWrite(@PathVariable("turn") int turn) throws Exception {
		System.out.println("进入BarcodecompensateWrite");
		boolean ret = false;
		try {
			byte[] dataIn = new byte[4];
			dataIn[0] = (byte) (turn & 0xff);
			dataIn[1] = (byte) (turn>>8 & 0xff);
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.BarcodecompensateWrite.getId());
			ret=sendReaction(dataIn,head);
			
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return ret;
	}
	/**
	 * 条码距窗口补偿步数读取
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/barcodecompensateRead")
	public ResponseVO barcodecompensateRead() throws Exception {
		System.out.println("进入BarcodecompensateRead");
		boolean ret = false;
		byte[] state=null;
		List newVal=new ArrayList<>();
		try {
			byte[] dataIn = new byte[4];
			DeviceDebugPackageHead head=new DeviceDebugPackageHead(); 
			head.setCommand((int)DeviceDebugCommandSample.BarcodecompensateRead.getId());
			ret=sendReaction(dataIn,head);
			Thread.sleep(1500);
			head.setBoardID((byte)DeviceBoardID.Sample.getValue());
			state=api.getOptoStatus(head.getBoardID(),head);
			byte[] newstate=new byte[4];
			for(int i=0;i<2;i++){
				int j=i*2;
				System.arraycopy(state, j, newstate, 0, 2);
				int ste=(int)((newstate[0])|(newstate[1])<<8);
				newVal.add(ste);
			}
		} catch (Exception e) {
			throw new Exception(getErrorstr(e));
		}
		return  new ResponseVO(newVal);
	}
	
	
	
}
