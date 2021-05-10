package com.quaero.qbcTest.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class DeviceDebugPackageHead implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -7651831407495259086L;
	public byte BoardID; //板号
     public int Command; //调试子命令

}
