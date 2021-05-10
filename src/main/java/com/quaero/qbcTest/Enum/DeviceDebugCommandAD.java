package com.quaero.qbcTest.Enum;

public enum DeviceDebugCommandAD {
	 /// <summary>
    /// 读取AD数据
    /// </summary>
    ReadADData(0x0015),
    /// <summary>
    /// 设置仪器AD系数
    /// </summary>
    WriteADDeviceFactor(0x0016),
    /// <summary>
    /// 读取AD机器系数
    /// </summary>
    ReadADDeviceFactor(0x0017),
    /// <summary>
    /// 自动设置光学AD系数
    /// </summary>
    AutoSetPhotoADFactor(0x0018),
    /// <summary>
    /// 手动设置光学AD系数
    /// </summary>
    ManualSetPhotoADFactor(0x0019),        
    /// <summary>
    /// 读取AD测试参数
    /// </summary>
    GetADPara(0x001E),
    /// <summary>
    /// 设置AD测试参数
    /// </summary>
    SetADPara(0x001D),
    /// <summary>
    /// 设置AD通采参数
    /// </summary>
    SetADPara1(0x0020),
    /// <summary>
    /// 读取AD通采参数
    /// </summary>
    GetADPara1(0x0021),
    /// <summary>
    /// 读光学AD系数
    /// </summary>
    GetPhotoADFactor(0x0022),
    /// <summary>
    /// 模拟测试
    /// </summary>
    SimulationTest(0x0051);
	private long id; 
	private DeviceDebugCommandAD(long id) {
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
