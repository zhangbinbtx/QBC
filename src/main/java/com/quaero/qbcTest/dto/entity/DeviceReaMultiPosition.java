package com.quaero.qbcTest.dto.entity;

import lombok.Data;

/**
 * 多试剂位置信息
 */
@Data
public class DeviceReaMultiPosition {
	public int ID; // 试剂编号
	public byte Order; // 优先级: 0未使用，1优先级最高
}