package com.quaero.qbcTest.dto.entity;

import lombok.Data;

@Data
public class DeviceDebugADPara_Tongcai_Setting {

	 public byte TestType;//测试类型 1-测试 2-通采
     public int CollectCount;//采集总次数
     public int UploadCount;//上传数量
     public byte Interval;//采集脉冲间隔

}
