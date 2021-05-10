package com.quaero.qbcTest.dto;

import java.util.Arrays;

import lombok.Data;

@Data
public class DataPackage {
     /**
      * 包头
      */
    public DataPackageHead Head;
    /**
     * 包体
     */
    public byte[] Body;
    /**
     * 模块
     */
    public int ModuleID;
    
    public DataPackage() {
		super();
	}


	public DataPackage(int moduleID, int cmd, int BodyLen)
    {
        Body = new byte[BodyLen];
        ModuleID = moduleID;
//        byte[] mark = new byte[2];
//        mark[0] = (byte)(0xA5 & 0xff);
//        mark[1] = 0x005A;
//        Head.Mark = mark;
////        Head.Mark[0] = (byte)(0xA5 & 0xff);
////        Head.Mark[1] = 0x005A;
//        Head.Command = (char)cmd;
//        Head.BodyLen = BodyLen;
//        Head.BodySum = 0;
//        Head.HeadSum = 0;
        byte[] mark = new byte[2];
        mark[0] = (byte)(0xA5 & 0xff);
        mark[1] = 0x005A;
        Head=new DataPackageHead(mark, cmd, BodyLen, 0, 0);
       /* byte[] mark = new byte[2];
        mark[0] = (byte)(0xA5 & 0xff);
        mark[1] = 0x005A;
        Head.setMark(mark);
        Head.setCommand((char)cmd);
        Head.setBodySum(0);
        Head.setHeadSum(0);
        Head.setBodyLen(BodyLen);*/
    }
        

    public static DataPackage GetDefault()
    {
        DataPackage dp = new DataPackage();
        byte[] mark = new byte[2];
        mark[0] = (byte)(0xA5 & 0xff);
        mark[1] = 0x005A;
        DataPackageHead head=new DataPackageHead(mark, 0, 0, 0, 0);
        dp.setHead(head);
        /*dp.Head.Mark = new byte[2];
        dp.Head.Mark[0] = (byte)0xA5;
        dp.Head.Mark[1] = (byte)0x5A;
        dp.Head.Command = 0;
        dp.Head.BodyLen = 0;
        dp.Head.BodySum = 0;
        dp.Head.HeadSum = 0;
        dp.ModuleID = 0;*/
        return dp;
    }


	@Override
	public String toString() {
		return "DataPackage [Head=" + Head + ", Body=" + Arrays.toString(Body) + ", ModuleID=" + ModuleID + "]";
	}
    
}
