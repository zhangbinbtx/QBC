package com.quaero.qbcTest.Enum;

public enum DeviceDebugCommandReagent {
	 /// <summary>
    /// 探针升降运行步数
    /// </summary>
    ReagentProbeLiftingStep(0x0701),
    /// <summary>
    /// 探针升降归零
    /// </summary>
    ReagentProbeLiftingZero(0x0702),
    /// <summary>
    /// 探针摆动运行步数
    /// </summary>
    ReagentProbeSwingStep(0x0703),
    /// <summary>
    /// 探针摆动归零
    /// </summary>
    ReagentProbeSwingZero(0x0704),
    /// <summary>
    /// 试剂盘运行步数
    /// </summary>
    ReagentDiskTurnStep(0x0705),
    /// <summary>
    /// 试剂盘转动个数
    /// </summary>
    ReagentDiskTurnCount(0x0706),
    /// <summary>
    /// 试剂盘归零
    /// </summary>
    ReagentDiskZero(0x0707),
    /// <summary>
    /// 注射泵运行ul数
    /// </summary>
    ReagentInjectionPumpSuck(0x708),
    /// <summary>
    /// 注射泵归零
    /// </summary>
    ReagentInjectionPumpZero(0x709),
    /// <summary>
    /// 试剂盘转到指定位置
    /// </summary>
    ReagentDiskTurnPosition(0x070A),
    /// <summary>
    /// 获取试剂盘光耦状态
    /// </summary>
    ReagentGetOptoStatus(0x070B),
    /// <summary>
    /// 探针摆动位置1
    /// </summary>
    ReagentProbeSwingPosition1(0x070C),
    /// <summary>
    /// 探针摆动位置2
    /// </summary>
    ReagentProbeSwingPosition2(0x070D),
    /// <summary>
    /// 探针摆动清洗位R1
    /// </summary>
    ReagentProbeSwingWashPosition1(0x070E),
    /// <summary>
    /// 探针摆动清洗位R4
    /// </summary>
    ReagentProbeSwingWashPosition2(0x0721),
    /// <summary>
    /// 探针摆动试剂盘位
    /// </summary>
    ReagentProbeSwingReagentDiskPosition(0x070F),
    /// <summary>
    /// 试剂板加试剂测试
    /// </summary>
    ReagentTestAddReagent(0x0710),
    /// <summary>
    /// 触发加试剂测试试剂板1
    /// </summary>
    ReagentTestAddReagentTouch1(0x0602),
    /// <summary>
    /// 触发加试剂测试试剂板1
    /// </summary>
    ReagentTestAddReagentTouch2(0x0603),
    /// <summary>
    /// 内壁清洗
    /// </summary>
    EletValueWashInner(0x0713),
    /// <summary>
    /// 外壁清洗
    /// </summary>
    EletValueWashOuter(0x0714),
    /// <summary>
    /// 设置补偿参数
    /// </summary>
    SetPara(0x0715),
    /// <summary>
    /// 读取补偿参数
    /// </summary>
    GetPara(0x0716),
    /// <summary>
    /// 试剂条码阅读器检查
    /// </summary>
    CheckReader(0x0717),
    /// <summary>
    /// 设置条码长度
    /// </summary>
    SetBarcodeLength(0x0718),
    /// <summary>
    /// 读取条码长度
    /// </summary>
    GetBarcodeLength(0x0719),
    /// <summary>
    /// 设置重复检查次数
    /// </summary>
    SetRecheckCount(0x071A),
    /// <summary>
    /// 读取重复扫描次数
    /// </summary>
    GetScanCount(0x071B),
    /// <summary>
    /// 探针精度测试加样就绪
    /// </summary>
    ReagentProbeBeready(0x071C),
    /// <summary>
    /// 探针精度测试加样
    /// </summary>
    ReagentProbeTest(0x071D),
    /// <summary>
    /// 设置探针加样精度补偿液量
    /// </summary>
    ReagentProbeCompensateWrite(0x071E),
    /// <summary>
    /// 读取探针加样精度补偿液量
    /// </summary>
    ReagentProbeCompensateRead(0x071F),
    /// <summary>
    /// 转盘指示灯
    /// </summary>
    ReagentDiskLamp(0x0720),
  /// <summary>
    /// 条码补偿写
    /// </summary>
    BarcodecompensateWrite(0x0722),
    /// <summary>
    /// 条码补偿读
    /// </summary>
    BarcodecompensateRead(0x0723);
	private long id; 
	private DeviceDebugCommandReagent(long id) {
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
