package com.quaero.qbcTest.server.impl;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quaero.qbcTest.base.ErpcModelCallback;
import com.quaero.qbcTest.base.ErpcModelCallback.IEventDllCall;
import com.quaero.qbcTest.base.JnaDll.ExecuteDll;
import com.quaero.qbcTest.base.JnaDll.LoadServer;
import com.quaero.qbcTest.dto.DataPackage;
import com.quaero.qbcTest.dto.DeviceDebugPackageHead;
import com.quaero.qbcTest.dto.dao.AccDao;
import com.quaero.qbcTest.dto.dao.FirebirdDao;
import com.quaero.qbcTest.dto.dao.daoImpl.AccDaoImpl;
import com.quaero.qbcTest.dto.dao.daoImpl.FirebirdDaoImpl;
import com.quaero.qbcTest.dto.entity.AlarmInfo;
import com.quaero.qbcTest.dto.entity.AlarmInfoDetail;
import com.quaero.qbcTest.dto.entity.Alarmcode;
import com.quaero.qbcTest.dto.entity.DeviceDataModuleStatus;
import com.quaero.qbcTest.dto.entity.DeviceDataStatus;
import com.quaero.qbcTest.dto.entity.ReagentInfoDetail;
import com.quaero.qbcTest.dto.entity.SampleInfo;
import com.quaero.qbcTest.dto.entity.WashCell;
import com.quaero.qbcTest.dto.entity.WashReagent;
import com.quaero.qbcTest.dto.entity.WashSample;
import com.quaero.qbcTest.server.QbcTestApi;
import com.quaero.qbcTest.util.Best;
import com.sun.jna.ptr.IntByReference;

@Service
public class QbcTestApiImpl implements QbcTestApi {
	@Autowired
	private Best best;
	// @Autowired
	private FirebirdDao testdao = new FirebirdDaoImpl();
	private AccDao accDao = new AccDaoImpl();
	private ErpcModelCallback.IEventDllCall iev = null;
	public Queue<DataPackage> addCache = new LinkedList<>();
	private boolean initstate=false;//false未联机
	//protected Map<Integer, byte[]> optocoupler=new HashMap<>();//光耦状态
	//protected Map<Integer, byte[]> adcoupler=new HashMap<>();
	protected Map<byte[], byte[]> optocoupler1=new HashMap<>();
	protected Map<byte[], byte[]> adcoupler1=new HashMap<>();
    
	@Override
	public List<AlarmInfo> getErrorMap() throws SQLException {
		return testdao.getTest();
	}

	@Override
	public Queue<DataPackage> getDic() throws Exception{
		return addCache;
	}
	/**
	 * 广播接收
	 */
	public void rev() {
		while (true) {
			int port = 9999;// 开启监听的端口
			DatagramSocket ds = null;
			DatagramPacket dp = null;
			byte[] buf = new byte[1024];// 存储发来的消息
			StringBuffer sbuf = new StringBuffer();
			try {
				// 绑定端口的
				ds = new DatagramSocket(port);
				dp = new DatagramPacket(buf, buf.length);
				System.out.println("监听广播端口打开：");
				ds.receive(dp);
				ds.close();
				int i;
				for (i = 0; i < 1024; i++) {
					if (buf[i] == 0) {
						break;
					}
					sbuf.append((char) buf[i]);
				}
				System.out.println("收到广播消息：" + sbuf.toString());
				ObjectMapper mapper = new ObjectMapper();
				// 3.转换为Java对象 DataPackage对象
				DataPackage pack = mapper.readValue(sbuf.toString(), DataPackage.class);
				System.out.println("pack.Head.Command="+pack.Head.Command);
				if(pack.Head.Command==5){
					System.out.println("报警信息待处理");
					 byte[] bytes = pack.getBody();
					 for (int j = 0; j < bytes.length; j++) {
						 System.out.println("byte1[]="+bytes[j]);
					 }
				}else if(pack.Head.Command==6){
					 //DeviceDataStatus dds = new DeviceDataStatus(1);
					 DeviceDataModuleStatus dsp=new DeviceDataModuleStatus();
					 byte[] bytes = pack.getBody();
					 for (int j = 0; j < bytes.length; j++) {
						 System.out.println("byte[]="+bytes[j]);
						 if(j==0){
							 dsp.setWorkStatus(bytes[j]);
							 this.initstate=bytes[j]==1?true:false;
						 }else if(j==1){
							 dsp.setReactTemperature(bytes[j]);
						 }else if(j==2){
							 dsp.setRefTemperature(bytes[j]);
						 }
					 }
					System.out.println("工作状态="+dsp.getWorkStatus());
					System.out.println("反应槽温度="+dsp.GetReactTemperatureText());
					System.out.println("制冷温度="+dsp.GetRefTemperatureText());
				}else if(pack.Head.Command==48){//反应板、搅拌板、样本板、试剂板、制冷数据返回
					//optocoupler.put(Integer.valueOf(pack.getBody()[0]), pack.getBody());
					byte[] kvalues=new byte[3];
					System.arraycopy(pack.getBody(), 0, kvalues, 0, 3);//获取byte数组中的前三位
					optocoupler1.put(kvalues, pack.getBody());
				}else if(pack.Head.Command==51){//AD
					//adcoupler.put(Integer.valueOf(pack.getBody()[0]), pack.getBody());
					byte[] kvalues=new byte[3];
					System.arraycopy(pack.getBody(), 0, kvalues, 0, 3);//获取byte数组中的前三位
					adcoupler1.put(kvalues, pack.getBody());
				}else{
					addCache.offer(pack);
					System.out.println("其他信息待处理");
				}
				System.out.println("收到广播消息1：" + pack.Head.Command);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean qbcInit(String[] machineIp, String trackIp, int localTrackPort, int localMachinePort,
			boolean machineState, boolean transState) {
		// best.getMachineIP();
		boolean initflag = false;
		try {
			if (iev == null) {
				iev = new ErpcModelCallback.EventDllCallImpl();
				new Thread() {
					public void run() {
						rev();
					}
				}.start();
				machineState = true;
				transState = true;
				LoadServer.INSTANCEP.QBC_S_CS_start_server(iev, localMachinePort);
			}
			initflag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return initflag;
	}

	

	@Override
	public List<AlarmInfoDetail> getUpAndDown() {
		return accDao.getUpAndDown();
	}

	@Override
	public List<Alarmcode> getErrorGroupTime() {
		// TODO Auto-generated method stub
		return testdao.getErrorGroupTime();
	}

	/*
	 * @Override public boolean sendToMachine(String ip, int moduleId, int cmd,
	 * byte[] obj, int count) { // TODO Auto-generated method stub return
	 * sendToMachine(moduleId, cmd, obj, count); }
	 */
	@Override
	public boolean sendToMachine(String ip, int moduleId, String cmd, byte[] dataIn, int count) throws Exception {
		int countIn = dataIn.length;
		IntByReference dataOt = new IntByReference(100);
		IntByReference countOt = new IntByReference(count);
		boolean resflag = SendData(ip, (byte) moduleId, cmd + "", dataIn, countIn, dataOt, countOt);
		System.out.println("resflag=" + resflag);
		return resflag;
	}
	@Override
	public boolean sendToMachine(String ip, int moduleId, String strId) throws Exception {
		return sendToMachine(ip, moduleId, strId,new byte[4]);
	}
	@Override
	public boolean sendToMachine(String ip, int moduleId, int cmd, byte[] dataIn) throws Exception {
		int countIn = dataIn.length;
		System.out.println("sendcmd=" + cmd);
		IntByReference dataOt = new IntByReference(100);
		IntByReference countOt = new IntByReference(0);
		boolean resflag = SendData(ip, (byte) moduleId, cmd + "", dataIn, countIn, dataOt, countOt);
		System.out.println("resflag=" + resflag);
		return true;
	}

	@Override
	public boolean sendToMachine(String ip, int moduleId, String cmd, byte[] dataIn) throws Exception {
		int countIn = dataIn.length;
		System.out.println("sendcmd=" + cmd);
		IntByReference dataOt = new IntByReference(100);
		IntByReference countOt = new IntByReference(0);
		boolean resflag = SendData(ip, (byte) moduleId, cmd, dataIn, countIn, dataOt, countOt);
		System.out.println("resflag=" + resflag);
		return true;
	}

	private boolean SendData(String ip, byte moduleId, String cmd, byte[] dataIn, int countIn, IntByReference dataOt,
			IntByReference countOt) throws Exception {
		System.out.println("ip=" + ip + ",moduleId=" + moduleId + ",cmd=" + cmd + ",dataIn=" + Arrays.toString(dataIn)
				+ ",countIn=" + countIn + ",dataOt.getValue()=" + dataOt.getValue() + ",countOt=" + countOt);
		long ret = ExecuteDll.INSTANCE.QBC_CS_execute(ip, moduleId, cmd, dataIn, countIn, dataOt, countOt);
		System.out.println("返回值=" + ret);
		if (0 != ret) {
			String s = String.format("SendToData failed: IP:%s, ModuleID:%s, ErrorCode:%s", ip, moduleId, ret);
			throw new Exception(s);
		}
		return true;
	}

	public static void main(String[] args) {
		// String hex = Integer.toHexString((int)109493766363797824L);
		// BigInteger bigint=new BigInteger(hex, 16);
		// int numb=bigint.intValue();
		// System.out.println("日志信息为：="+numb);

//		String s = String.format("SendToData failed: IP:%s, ModuleID:%s, ErrorCode:%s", "192.168.3.221", 1, 2);
//		System.out.println("日志信息为：=" + s);
		 DeviceDataModuleStatus dsp=new DeviceDataModuleStatus();
		dsp.setWorkStatus((byte)1);
		dsp.setReactTemperature(37);
		dsp.setRefTemperature(36);
		System.out.println("工作状态="+dsp.getWorkStatus());
		System.out.println("反应槽温度="+dsp.GetReactTemperatureText());
		System.out.println("反应槽温度="+dsp.GetRefTemperatureText());
	}

	@Override
	public List<ReagentInfoDetail> getAllModuleList(int specical) throws Exception {
		// TODO Auto-generated method stub
		return testdao.getAllModuleList(0);
	}

	@Override
	public List<WashReagent> getReagentPolluteList() throws Exception {
		// TODO Auto-generated method stub
		return testdao.getReagentPolluteList();
	}

	@Override
	public List<WashSample> getSamplePolluteList() throws Exception {
		// TODO Auto-generated method stub
		return testdao.getSamplePolluteList();
	}

	@Override
	public List<WashCell> getReactCupPolluteList() throws Exception {
		// TODO Auto-generated method stub
		return testdao.getReactCupPolluteList();
	}

	@Override
	public List<SampleInfo> getSampleBarcodeList(LocalDate localDate) {
		return testdao.getSampleBarcodeList(localDate);
	}

	@Override
	public boolean getState() throws Exception {
		return initstate;
	}
	/*public byte[] getOptoStatus(byte boardID, int bytg) throws Exception{
		byte[] opstate=null;
		Iterator<Map.Entry<Integer, byte[]>> it = optocoupler.entrySet().iterator();  
        while(it.hasNext()){  
            Map.Entry<Integer, byte[]> entry=it.next();  
            int key=entry.getKey();  
            if((byte)key==boardID){
        		byte[] opstatus=(byte[]) entry.getValue();
        		opstate=new byte[opstatus.length-bytg];
        		int j=0;
        		for(int i=0;i<opstatus.length;i++){
        			if(i>bytg){
        				opstate[j]=opstatus[i];
        				j++;
        			}
        		}
        		it.remove(); 
        	}
        }  
		return opstate;
	}*/

	@Override
	public byte[] getOptoStatus(byte boardID, DeviceDebugPackageHead head) {
		byte[] opstate=handles(boardID,head,optocoupler1);
		return opstate;
	}

	@Override
	public byte[] getAdStatus(byte boardID, DeviceDebugPackageHead head) {
		byte[] opstate=handles(boardID,head,adcoupler1);
		return opstate;
	}
	public byte[] handle(byte boardID, DeviceDebugPackageHead head,Map<Integer, byte[]> keyValue){
		int debugHeadSize=head.getClass().getDeclaredFields().length;
		byte[] bytg=new byte[debugHeadSize];
		bytg[0] = (byte) (head.getBoardID() & 0xff);
		bytg[1] = (byte) (head.getCommand() & 0xff);
		bytg[2] = (byte) ((head.getCommand() >> 8) & 0xFF);
		
		byte[] opstate=null;
		Iterator<Map.Entry<Integer, byte[]>> it = keyValue.entrySet().iterator();  
	    while(it.hasNext()){  
	        Map.Entry<Integer, byte[]> entry=it.next();  
	        int key=entry.getKey();  
	        if((byte)key==boardID){
	    		byte[] opstatus=(byte[]) entry.getValue();
	    		byte[] newstate=new byte[debugHeadSize];
	    		System.arraycopy(opstatus, 0, newstate, 0, debugHeadSize);//获取byte数组中的前三位
	    		if(Arrays.equals(newstate, bytg)){
	    			int kcount=opstatus.length-debugHeadSize;
	    			opstate=new byte[kcount];
	    			System.arraycopy(opstatus, debugHeadSize, opstate, 0, kcount);
	        		it.remove(); 
	        		break;
	    		}
	    	}
	    }  
		return opstate;
	}
	public byte[] handles(byte boardID, DeviceDebugPackageHead head,Map<byte[], byte[]> keyValue){
		int debugHeadSize=head.getClass().getDeclaredFields().length;
		byte[] bytg=new byte[debugHeadSize];
		bytg[0] = (byte) (head.getBoardID() & 0xff);
		bytg[1] = (byte) (head.getCommand() & 0xff);
		bytg[2] = (byte) ((head.getCommand() >> 8) & 0xFF);
		
		byte[] opstate=null;
		Iterator<Map.Entry<byte[], byte[]>> it = keyValue.entrySet().iterator();  
	    while(it.hasNext()){  
	        Map.Entry<byte[], byte[]> entry=it.next();  
	           byte[] key=entry.getKey();  
	    		if(Arrays.equals(key, bytg)){
	    			byte[] opstatus=(byte[]) entry.getValue();
	    			int kcount=opstatus.length-debugHeadSize;
	    			opstate=new byte[kcount];
	    			System.arraycopy(opstatus, debugHeadSize, opstate, 0, kcount);
	        		it.remove(); 
	        		break;
	    		}
	    }  
		return opstate;
	}

	

}

class udpRunnable implements Runnable {
	MulticastSocket ds;

	public udpRunnable(MulticastSocket ds) {
		this.ds = ds;
	}

	public void run() {
		byte buf[] = new byte[1024];
		DatagramPacket dp = new DatagramPacket(buf, 1024);
		while (true) {
			try {
				ds.receive(dp);
				System.out.println("receive client message : " + new String(buf, 0, dp.getLength()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
