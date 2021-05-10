package com.quaero.qbcTest.Enum;

public enum DeviceDebugCommandStir {
	 /// <summary>
    /// 搅拌机构1升降运行步数
    /// </summary>
    Stir1LiftingStep(0x0701),
    /// <summary>
    /// 搅拌机构1升降归零
    /// </summary>
    Stir1LiftingZero(0x0702),
    /// <summary>
    /// 搅拌机构1摆动运行步数
    /// </summary>
    Stir1SwingStep(0x0703),
    /// <summary>
    /// 搅拌机构1摆动归零
    /// </summary>
    Stir1SwingZero(0x0704),
    /// <summary>
    /// 搅拌机构2升降运行步数
    /// </summary>
    Stir2LiftingStep(0x0705),
    /// <summary>
    /// 搅拌机构2升降归零
    /// </summary>
    Stir2LiftingZero(0x0706),
    /// <summary>
    /// 搅拌机构2摆动运行步数
    /// </summary>
    Stir2SwingStep(0x0707),
    /// <summary>
    /// 搅拌机构2摆动归零
    /// </summary>
    Stir2SwingZero(0x0708),
    /// <summary>
    /// 搅拌机构1摆1位置
    /// </summary>
    Stir1SwingPosition1(0x0709),
    /// <summary>
    /// 搅拌机构1摆4位置
    /// </summary>
    Stir1SwingPosition4(0x070A),
    /// <summary>
    /// 搅拌机构2摆2位置
    /// </summary>
    Stir2SwingPosition2(0x070B),
    /// <summary>
    /// 搅拌机构2摆3位置
    /// </summary>
    Stir2SwingPosition3(0x070C),
    /// <summary>
    /// 获取反应板光耦状态
    /// </summary>
    StirGetOptoStatus(0x070D),

    /// <summary>
    /// 搅拌
    /// </summary>
    StirMix(0x0008),

    /// <summary>
    /// 触发搅拌
    /// </summary>
    StirTrig(0x0607),
    /// <summary>
    /// 清洗电磁阀
    /// </summary>
    EletValueWash(0x070E),
    /// <summary>
    /// 搅拌电机1
    /// </summary>
    ElectricMotorStr1(0x070F),
    /// <summary>
    /// 搅拌电机2
    /// </summary>
    ElectricMotorStr2(0x0710);
	private long id; 
	private DeviceDebugCommandStir(long id) {
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
