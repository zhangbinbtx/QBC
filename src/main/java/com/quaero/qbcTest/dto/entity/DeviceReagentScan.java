package com.quaero.qbcTest.dto.entity;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 试剂剩余量扫描/试剂条码扫描
 */
@Data
@AllArgsConstructor
public class DeviceReagentScan {
	public byte[] DiskPostion1; // 盘1试剂位
	public byte[] DiskPostion2; // 盘1试剂位

	@Override
	public String toString() {
		return "DeviceReagentScan [DiskPostion1=" + Arrays.toString(DiskPostion1) + ", DiskPostion2="
				+ Arrays.toString(DiskPostion2) + "]";
	}

	public DeviceReagentScan() {
		super();
	}

}