package com.quaero.qbcTest.base;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quaero.qbcTest.dto.DataPackage;
import com.quaero.qbcTest.server.QbcTestApi;
import com.quaero.qbcTest.server.impl.QbcTestApiImpl;
import com.sun.jna.Callback;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErpcModelCallback {
	private static int sequence = 0;
	private static QbcTestApi api=new QbcTestApiImpl();
	public interface IEventDllCall extends Callback {
		public void QBC_CS_Callback(byte moduleID, IntByReference cmd, IntByReference dataIn, int countIn, IntByReference dataOt, IntByReference countOt) throws IOException;
		//public void QBC_CS_Callback(byte moduleID, ByteByReference cmd, IntByReference dataIn, int countIn, IntByReference dataOt, IntByReference countOt);

	}
	public static void main(String[] args) {
		IntByReference cmd=new IntByReference();
		cmd.setValue(909127728);
		String s=cmd.getPointer().getString(0).substring(0, 4);
		System.out.println("aa="+s);
	}
	public static class EventDllCallImpl  implements IEventDllCall{
		private Dictionary<Integer, DataPackage> commandDict = new Hashtable<>();
		public Queue<Dictionary<Integer, DataPackage>> addCache = new ConcurrentLinkedQueue<>();
		public EventDllCallImpl() {
	    	super();
		}
		@Override
		public void QBC_CS_Callback(byte moduleID, IntByReference cmd, IntByReference dataIn, int countIn,
				IntByReference dataOt, IntByReference countOt) throws IOException {
			  sequence++;
			  System.out.println("moduleID="+moduleID+",cmd="+cmd.getValue()+",dataIn="+dataIn.getValue()+
					  ",countIn="+countIn+",dataOt="+dataOt.getValue()+",countOt="+countOt.getValue());
             
			  byte[] data = new byte[countIn];
              data =dataIn.getPointer().getByteArray(0, countIn);
              int[] data1 = dataIn.getPointer().getIntArray(0, countIn);
              String ccd=cmd.getPointer().getString(0);
              
              int ncmd=Integer.parseInt(ccd.toLowerCase(),16);
              
             System.out.println("cmdcode="+ncmd);
              DataPackage pack = new DataPackage((int)moduleID, ncmd, countIn);
              pack.Head.Command = ncmd;
             
              byte[] bytes = new byte[countIn + 3];
              bytes[0] = moduleID;
              bytes[1] = (byte)(ncmd & 0xFF);
              bytes[2] = (byte)(ncmd >> 8 & 0xFF);
              System.arraycopy(data, 0, bytes, 0,countIn);
             
              int cot=dataOt.getValue() > countIn ? countIn : countOt.getValue();
              System.out.println("cot="+cot);
              countOt =new  IntByReference(cot);
       		  final Pointer p1 = new Memory((cot+1)* Native.WCHAR_SIZE);
       		  p1.setString(0, data.toString());
              countOt.setPointer(p1);
             // data =dataIn.getPointer().getByteArray(0, cot);
              //data =dataIn.getPointer().getStringArray(0, cot);
//              MonitorNetworkData nmd = new MonitorNetworkData(NetworkDataType.Recv, moduleID, ncmd, data);
//               MessageHelper.BroadcastMessage(Message.CommounicateMonitor.ordinal(), nmd, 0);
              if (countIn > 0)
              {
            	  System.arraycopy(data, 0, pack.Body, 0,countIn);
            	  for(int i=0;i<data.length;i++){
            		  System.out.println("data【"+i+"】="+data[i]);
            	  }
            	  for(int i=0;i<pack.Body.length;i++){
            		  System.out.println("pack.Body【"+i+"】="+pack.Body[i]);
            	  }
                  pack.Head.BodyLen = countIn;
                  OnDataPackageEvent(pack);
              }


		}
	    private List<String> lStr=new ArrayList<>(); 
	    private int cmdnum=0;
		private void OnDataPackageEvent(DataPackage pack) throws IOException {
			
			SendUdp(pack);
		}
		/**
		 * 广播发送
		 */
		public void  SendUdp(DataPackage pack) throws IOException{
			//广播的实现 :由客户端发出广播，服务器端接收
	        String host = "255.255.255.255";//广播地址
	        int port = 9999;//广播的目的端口
	      //  String message = "test";//用于发送的字符串
	        //String message=pack.toString();
	        ObjectMapper mapper = new ObjectMapper();
	        //3.转换
	        String message = mapper.writeValueAsString(pack);
	        try
	        {
	            InetAddress adds = InetAddress.getByName(host);
	            DatagramSocket ds = new DatagramSocket();
	            DatagramPacket dp = new DatagramPacket(message.getBytes(),message.length(), adds, port);
	            ds.send(dp);
	            ds.close();
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
		}

		 public void AddDataPackage(DataPackage pack)
	        {
			 
	        }
	}

}
