package com.quaero.qbcTest.dto.entity;

import lombok.Data;

@Data
public class  DeviceReagentProbePollute
{
    public int FromID; //污染试剂编号
    public int ToID; //被污染试剂编号
    public int WashVol; //清洗液量
}
