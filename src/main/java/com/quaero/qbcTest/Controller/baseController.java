package com.quaero.qbcTest.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.lucene.util.RamUsageEstimator;

import com.quaero.qbcTest.Enum.ReaBottleSize;
import com.quaero.qbcTest.Enum.ReaType;
import com.quaero.qbcTest.dto.MessageCode.CodeEnum;
import com.quaero.qbcTest.dto.MessageCode.ResponseVO;
import com.quaero.qbcTest.dto.MessageCode.inter.Code;

public class baseController {

	public String getErrorstr(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
	public ResponseVO sendSuccess(boolean ret){
		if(ret==true){
			return new ResponseVO(CodeEnum.SUCCESS,"发送完成");
		}else{
			return new ResponseVO(CodeEnum.FAILED,"发送异常");
		}
	}
	 public static String DiskToString(int value)
     {
         return String.format("试剂盘%d", value);
     }
	 public static String PositionToString(int value)
     {
		 return value+"";
     }
	 public static String ReaTypeToString(int reaType)
     {
         String value;
         switch (ReaType.getWeekByValue(reaType))
         {
             case R1:
                 value = "R1";
                 break;
             case R2:
                 value = "R2";
                 break;
             case R3:
                 value = "R3";
                 break;
             case R4:
                 value = "R4";
                 break;
             case Diluent:
                 value = "稀释液";
                 break;
             case AlkalineDetergent:
                 value = "碱性清洗液";
                 break;
             case PhosphorFreeDetergent:
                 value = "抗菌无磷清洗液";
                 break;
             default:
                 value = "";
                 break;
         }
         return value;
     }
	 public static String BottleSizeToString(int bottleSize)
     {
		 String value;
         switch (ReaBottleSize.getWeekByValue(bottleSize))
         {
             case BS20ML:
                 value = "20";
                 break;
             case BS70ML:
                 value = "70";
                 break;
             default:
                 value = "";
                 break;
         }
         return value;
     }
		/**
		 * 对象转字节数组
		 */
		public static byte[] objectToBytes(Object obj) throws IOException {
		            ByteArrayOutputStream out = new ByteArrayOutputStream();
		            ObjectOutputStream sOut = new ObjectOutputStream(out);
		        sOut.writeObject(obj);
		        sOut.flush();
		        byte[] bytes = out.toByteArray();
		        return bytes;
		}
		/**
		 * 计算内存大小
		 * @param obj
		 * @return
		 */
		public static int getMb(Object obj) {

		    if (obj == null) {
		        return 0;
		    }
		    //计算指定对象本身在堆空间的大小，单位字节
		    long byteCount = RamUsageEstimator.shallowSizeOf(obj);
		    if (byteCount == 0) {
		        return 0;
		    }
		    double oneMb = 1 * 1024 * 1024;

		    if (byteCount < oneMb) {
		        return 1;
		    }

		    Double v = Double.valueOf(byteCount) / oneMb;
		    return v.intValue();
		}

}
