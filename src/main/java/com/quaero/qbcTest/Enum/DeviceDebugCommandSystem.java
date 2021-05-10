package com.quaero.qbcTest.Enum;

public enum DeviceDebugCommandSystem {

	 /// <summary>
    /// 模块类型设置
    /// </summary>
    SetModuleType(0x0A01),
    /// <summary>
    /// 读取模块类型
    /// </summary>
    GetModuleType(0x0A02),
    /// <summary>
    /// IP端口设置
    /// </summary>
    SetIPPort(0x0A03),
    /// <summary>
    /// 读取IP端口
    /// </summary>
    GetIPPort(0x0A04);
	private long id; 
	private DeviceDebugCommandSystem(long id) {
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
}
