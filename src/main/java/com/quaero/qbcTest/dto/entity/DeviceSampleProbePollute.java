package com.quaero.qbcTest.dto.entity;

import lombok.Data;

@Data
public class DeviceSampleProbePollute {

	public int ID; //污染项目编号
    public int WashPos; //清洗液位置
    public float WashVol; //清洗液量

}
