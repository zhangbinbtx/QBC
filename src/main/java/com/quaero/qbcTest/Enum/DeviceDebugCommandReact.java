package com.quaero.qbcTest.Enum;

public enum DeviceDebugCommandReact {
	 /// <summary>
    /// 反应盘转动步数
    /// </summary>
    ReactTurnStep(0x0701),
    /// <summary>
    /// 反应盘转动个数
    /// </summary>
    ReactTurnCount(0x0702),
    /// <summary>
    /// 反应盘归零
    /// </summary>
    ReactTurnZero(0x0703),
    /// <summary>
    /// 清洗机构1运行步数
    /// </summary>
    ReactWash1RunStep(0x0704),
    /// <summary>
    /// 清洗机构1归零
    /// </summary>
    ReactWash1Zero(0x0705),
    /// <summary>
    /// 清洗机构2运行步数
    /// </summary>
    ReactWash2RunStep(0x0706),
    /// <summary>
    /// 清洗机构2归零
    /// </summary>
    ReactWash2Zero(0x0707),
    /// <summary>
    /// 反应盘扫描
    /// </summary>
    ReactScan(0x0708),
    /// <summary>
    /// 电磁阀1
    /// </summary>
    ReactEletValue1(0x0709),
    /// <summary>
    /// 电磁阀2
    /// </summary>
    ReactEletValue2(0x070A),
    /// <summary>
    /// 电磁阀3
    /// </summary>
    ReactEletValue3(0x070B),
    /// <summary>
    /// 电磁阀4
    /// </summary>
    ReactEletValue4(0x070C),
    /// <summary>
    /// 电磁阀5
    /// </summary>
    ReactEletValue5(0x070D),
    /// <summary>
    /// 电磁阀6
    /// </summary>
    ReactEletValue6(0x070E),
    /// <summary>
    /// 电磁阀7
    /// </summary>
    ReactEletValue7(0x070F),
    /// <summary>
    /// 电磁阀8
    /// </summary>
    ReactEletValue8(0x0710),
    /// <summary>
    /// 电磁阀9
    /// </summary>
    ReactEletValue9(0x0711),
    /// <summary>
    /// 电磁阀10
    /// </summary>
    ReactEletValue10(0x0712),
    /// <summary>
    /// 电磁阀11
    /// </summary>
    ReactEletValue11(0x0713),
    /// <summary>
    /// 电磁阀12
    /// </summary>
    ReactEletValue12(0x0714),
    /// <summary>
    /// 获取反应板光耦状态
    /// </summary>
    ReactGetOptoStatus(0x0715),
    /// <summary>
    /// 模拟测试
    /// </summary>
    ReactSimulationTest(0x0716),
    /// <summary>
    /// 转动反应杯到光源
    /// </summary>
    ReactCupToLight(0x0717);
	private long id; 
	private DeviceDebugCommandReact(long id) {
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
