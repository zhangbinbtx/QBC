package com.quaero.qbcTest.dto.entity;

import lombok.Data;

@Data
public class DeviceDataStatus {

	public int ModuleID;
    public DeviceDataModuleStatus Modules;
    public DeviceDataStatus(int moduleID)
    {
        ModuleID = moduleID;
        Modules = new DeviceDataModuleStatus();
    }

}
