package com.quaero.qbcTest.dto.entity;

import lombok.Data;

/**
 * 模块多试剂位置信息
 */
@Data
public class DeviceModuleReaMultiPosition {
	public byte ReagentLimit;// 试剂最小量
	public DeviceDiskReaMultiPosition[] Disk; // 试剂盘多试剂位置信息
}
