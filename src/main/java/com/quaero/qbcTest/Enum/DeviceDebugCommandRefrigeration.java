package com.quaero.qbcTest.Enum;

public enum DeviceDebugCommandRefrigeration {
	  /// <summary>
    /// 温度、电流、水箱浮子状态
    /// </summary>
    RefrigerationStatus(0x0701),
    /// <summary>
    /// 设置温度范围
    /// </summary>
    SetTemperatureRange(0x0702),
    /// <summary>
    /// 读取温度范围
    /// </summary>
    GetTemperatureRange(0x0703),
    /// <summary>
    /// 帕尔贴开关
    /// </summary>
    ParpSwitch(0x0704),
    /// <summary>
    /// 风扇开关
    /// </summary>
    FanSwitch(0x0705),
    /// <summary>
    /// 制冷复位
    /// </summary>
    RefrigerationReset(0x0706),
    /// <summary>
    /// 制冷停止
    /// </summary>
    RefrigerationStop(0x0707);
	private long id; 
	private DeviceDebugCommandRefrigeration(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public String getStrId() {
		return Integer.toHexString((int) id).toUpperCase();
	}
	public String toString() {
		return String.format("%0#6x", (((long)id) & 0xffffL));
	}
	/*public static DeviceDebugCommandRefrigeration fromId(long id) throws CodeException {
		id = (((long)id) & 0xffffL);
		for (int i = 0; i < DeviceDebugCommandRefrigeration.values().length; i++)
			if (DeviceDebugCommandRefrigeration.values()[i].id == id)
				return DeviceDebugCommandRefrigeration.values()[i];
		throw new CodeException(id);
	}*/
}
