package com.quaero.qbcTest.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.Date;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

import lombok.Data;

public class JnaDll {

	public interface LoadServer  extends Library{
		@SuppressWarnings("deprecation")
		LoadServer INSTANCEP = (LoadServer) Native.loadLibrary("libqmod_DemoErpcS_csD.dll", LoadServer.class);
		public void  QBC_S_CS_start_server(Callback c, int port);
	}
	
	public interface ExecuteDll  extends Library{
		@SuppressWarnings("deprecation")
		ExecuteDll INSTANCE = (ExecuteDll) Native.loadLibrary("libqmod_QBCCtrlErpc_csD.dll", ExecuteDll.class);
		public  int  QBC_CS_execute(String ip, byte moduleID, String cmd,byte[] dataIn,int countIn, IntByReference dataOt, IntByReference countOt);

	}
	
	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		/*IntByReference knu=new IntByReference(8);
		LoadServer.INSTANCEP.QBC_S_CS_start_server(knu, 7654);
//    	
//	 	Thread.sleep(1000);
	 	byte moduleID=1;
    	String cmd="1";
    	long time=new Date().getTime();
    	byte[] dataIn=ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(time).array();
    	System.out.println("aacc="+dataIn.length);
        int countIn=dataIn.length;
        int countOut = 0;
        Integer str = new Integer(0);
        System.out.println("$$$$$$$");
        coutnews coutnews= new coutnews();
        Coutnew coutnew= new Coutnew();
    	int ss=ExecuteDll.INSTANCE.QBC_CS_execute("erpc://192.168.3.152:6000",moduleID,cmd,dataIn,countIn, new IntByReference(100), new IntByReference(0));
         System.out.println("ss="+ss);
         
        
         Thread.sleep(1000);
         String cmd1="2";
     	byte[] dataIn1=new byte[1];
         dataIn1[0]=(byte) (255&0xff);
         System.out.println("joioih="+dataIn1[0]);
         int countIn1=dataIn1.length;
         System.out.println("******");
         Integer str1 = new Integer(0);
        
     	int ss1=ExecuteDll.INSTANCE.QBC_CS_execute("erpc://192.168.3.152:6000",moduleID,cmd1,dataIn1,countIn1, new IntByReference(9),  new IntByReference(0));
          System.out.println("ss="+ss1);
        while(true){
         Thread.sleep(1000);
         System.out.println("------");
        }*/
	}

}
