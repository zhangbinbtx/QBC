package com.quaero.qbcTest.dto.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class DeviceDebugPumpAccuracy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1099436174424509570L;
	public int Vol; //加样量
    public int Times;   //加样次数

}
