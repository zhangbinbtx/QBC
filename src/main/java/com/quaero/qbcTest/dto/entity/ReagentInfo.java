package com.quaero.qbcTest.dto.entity;

import java.util.Date;

import lombok.Data;

// 试剂信息
@Data
public class ReagentInfo // 试剂信息
{
	public int ReaID; // 试剂编号
	public int ModuleNo; // 模块
	public int ReaDisk; // 盘好
	public int ReaPos; // 位置
	public String ReaName; // 试剂名称
	public int ReaType; // 试剂类型
	public String BottleNo; // 瓶号
	public String LotNumber; // 批号
	public int BottleSize; // 瓶规格
	public String Remain; // 试剂剩余量
	public int RemainCount; // 剩余次数
	public Date ProductionDate; // 生产日期
	public Date ProductionTime; // 生产时间
	public Date ExpirationDate; // 有效截止日期
	public Date ExpirationTime; // 有效截止时间
	public Date OpenDate; // 开瓶日期
	public Date OpenTime; // 开瓶时间
	public Date ExpirationDateOpen; // 开瓶效期日期
	public Date ExpirationTimeOpen; // 开瓶效期时间
	public String Barcode; // 条码
	public boolean Enabled; // 是否启用
	public String Remark; // 备注
	public int Special; // 是否是特殊测试

	public String GetReaNamePos() {
		return String.format("%d(%s)", ReaPos, ReaName);
	}

	public ReagentInfo() {
		ReaName = "";
		BottleNo = "";
		LotNumber = "";
		Remain = "";
		Barcode = "";
		Remark = "";
	}
}