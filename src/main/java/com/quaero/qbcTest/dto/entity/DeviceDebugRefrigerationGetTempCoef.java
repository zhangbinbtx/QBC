package com.quaero.qbcTest.dto.entity;

import lombok.Data;

@Data
public class DeviceDebugRefrigerationGetTempCoef {

	private int LowerLimitR1;   //温度下限
	private int UpperLimitR1;   //温度上限
	private int LowerLimitR2;   //温度下限
	private int UpperLimitR2;   //温度上限
	private int LowerLimitQC;   //温度下限
	private int UpperLimitQC;   //温度上限       

}
