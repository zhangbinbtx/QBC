package com.quaero.qbcTest.Enum;

public enum DeviceDebugCommandAC {
	/// <summary>
    /// 水箱进水电磁阀
    /// </summary>
    BoxValueIn(0x0701),
    /// <summary>
    /// 恒温槽进水电磁阀
    /// </summary>
    SlotValueIn(0x0702),
    /// <summary>
    /// 恒温槽排水电磁阀
    /// </summary>
    SlotValueOut(0x0703),
    /// <summary>
    /// 水箱循环泵
    /// </summary>
    BoxCyclePump(0x0704),
    /// <summary>
    /// 恒温槽循环泵
    /// </summary>
    SlotCyclePump(0x0705),
    /// <summary>
    /// 真空泵
    /// </summary>
    VacuumPump(0x0706),
    /// <summary>
    /// 齿轮泵
    /// </summary>
    GearPump(0x0707),
    /// <summary>
    /// 卤素灯
    /// </summary>
    HalogenLamps(0x0708),
    /// <summary>
    /// 水箱加热棒
    /// </summary>
    BoxHeating(0x0709),
    /// <summary>
    /// 恒温槽加热棒
    /// </summary>
    SlotHeating(0x070A),
    /// <summary>
    /// 读取恒温槽温度系数
    /// </summary>
    GetSlotTemp(0x070B),
    /// <summary>
    /// 设置恒温槽温度系数
    /// </summary>
    SetSlotTemp(0x070C),
    /// <summary>
    /// 读取水箱温度系数
    /// </summary>
    GetBoxTemp(0x070D),
    /// <summary>
    /// 设置水箱温度系数
    /// </summary>
    SetBoxTemp(0x070E),
    /// <summary>
    /// 读取交流状态
    /// </summary>
    GetACStates(0x070F),
    /// <summary>
    /// 交流复位
    /// </summary>
    ACReset(0x0710),
    /// <summary>
    /// 交流停止
    /// </summary>
    ACStop(0x0711),
    /// <summary>
    /// 交流待机
    /// </summary>
    ACStandBy(0x0712),
    /// <summary>
    /// 恒温槽排水
    /// </summary>
    SlotWaterOut(0x0713),
    /// <summary>
    /// 恒温槽上水
    /// </summary>
    SlotWaterIn(0x0714),
    /// <summary>
    /// 读取交流数据
    /// </summary>
    GetACData(0x0715),
    /// <summary>
    /// 有水模式
    /// </summary>
    WaterMode(0x0716),
    /// <summary>
    /// 无水模式
    /// </summary>
    AnhydrousMode(0x0717),
    /// <summary>
    /// 孵育槽阈值写
    /// </summary>
    ThresholdWrite(0x0718),
    /// <summary>
    /// 孵育槽阈值读
    /// </summary>
    ThresholdRead(0x0719),
    /// <summary>
    /// 孵育槽系数
    /// </summary>
    GetThreshold(0x071C);
	private long id; 
	private DeviceDebugCommandAC(long id) {
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
