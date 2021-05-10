package com.quaero.qbcTest.dto.entity;

import lombok.Data;

/**
 * 反应杯交叉污染
 * @author zhangbin
 *
 */
@Data
public class DeviceReactCupProbePollute
{
    public int FromID; //污染项目编号        
    public int ToID; //污染项目编号        
    public int WashVol_R1; //R1清洗液量
    public int WashVol_R2; //R2清洗液量
}
