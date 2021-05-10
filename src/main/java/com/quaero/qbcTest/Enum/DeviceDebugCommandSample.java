package com.quaero.qbcTest.Enum;

public enum DeviceDebugCommandSample {
	 /// <summary>
    /// 探针升降运行步数
    /// </summary>
    SampleProbeLiftingStep(0x0701),
    /// <summary>
    /// 探针升降归零
    /// </summary>
    SampleProbeLiftingZero(0x0702),
    /// <summary>
    /// 探针摆动运行步数
    /// </summary>
    SampleProbeSwingStep(0x0703),
    /// <summary>
    /// 探针摆动归零
    /// </summary>
    SampleProbeSwingZero(0x0704),
    /// <summary>
    /// 摆加样
    /// </summary>
    SampleProbeSwingSamplingPosition(0x0705),
    /// <summary>
    /// 探针摆动质控盘外圈
    /// </summary>
    SampleProbeSwingDiskOut(0x0706),
    /// <summary>
    /// 探针摆动质控盘内圈
    /// </summary>
    SampleProbeSwingDiskIn(0x0707),
    /// <summary>
    /// 探针摆动质控盘清洗液位置
    /// </summary>
    SampleProbeSwingDiskWashPosition(0x0708),
    /// <summary>
    /// 探针摆动轨道清洗
    /// </summary>
    SampleProbeSwingTrackWashPosition(0x0709),
    /// <summary>
    /// 探针摆动轨道1
    /// </summary>
    SampleProbeSwingTrack1(0x070A),
    /// <summary>
    /// 探针摆动轨道2
    /// </summary>
    SampleProbeSwingTrack2(0x070B),
    /// <summary>
    /// 探针加样本动作
    /// </summary>
    SampleProbeSamplingAction(0x070C),
    /// <summary>
    /// 质控盘运行步数
    /// </summary>
    SampleDiskTurnStep(0x070D),
    /// <summary>
    /// 质控盘归零
    /// </summary>
    SampleDiskZero(0x070E),
    /// <summary>
    /// 质控盘外圈运行个数
    /// </summary>
    SampleDiskOutTurnCount(0x070F),
    /// <summary>
    /// 质控盘内圈运行个数
    /// </summary>
    SampleDiskInTurnCount(0x0710),        
    /// <summary>
    /// 注射泵运行ul数
    /// </summary>
    SampleInjectionPumpSuck(0x711),
    /// <summary>
    /// 注射泵归零
    /// </summary>
    SampleInjectionPumpZero(0x712),
    /// <summary>
    /// 设置补偿参数
    /// </summary>
    SetPara(0x0713),
    /// <summary>
    /// 读取补偿参数
    /// </summary>
    GetPara(0x0714),
    /// <summary>
    /// 获取样本板光耦状态
    /// </summary>
    SampleGetOptoStatus(0x0715),
    /// <summary>
    /// 内壁清洗
    /// </summary>
    EletValueWashInner(0x0716),
    /// <summary>
    /// 外壁清洗
    /// </summary>
    EletValueWashOuter(0x0717),
    /// <summary>
    /// 设置条码长度
    /// </summary>
    SetBarcodeLength(0x0718),
    /// <summary>
    /// 读取条码长度
    /// </summary>
    GetBarcodeLength(0x0719),
    /// <summary>
    /// 试剂条码阅读器检查
    /// </summary>
    CheckReader(0x071A),
    /// <summary>
    /// 设置重复检查次数
    /// </summary>
    SetRecheckCount(0x071B),
    /// <summary>
    /// 读取重复扫描次数
    /// </summary>
    GetScanCount(0x071C),
    /// <summary>
    /// 样本探针摆加清洗液
    /// </summary>
    MoveToWash(0x071F),
    /// <summary>
    /// 探针精度测试加样就绪
    /// </summary>
    SampleProbeBeready(0x0720),
    /// <summary>
    /// 探针精度测试加样
    /// </summary>
    SampleProbeTest(0x0721),
    /// <summary>
    /// 设置探针加样精度补偿液量
    /// </summary>
    SampleProbeCompensateWrite(0x0722),
    /// <summary>
    /// 读取探针加样精度补偿液量
    /// </summary>
    SampleProbeCompensateRead(0x0723),
    /// <summary>
    /// 样本板液面监控
    /// </summary>
    SampleProbeSurfaceMonitor(0x0724),
    /// <summary>
    /// 样本探针糖化清洗孔1
    /// </summary>
    SampleSacCleaning1(0x0740),
    /// <summary>
    /// 样本探针糖化清洗孔2
    /// </summary>
    SampleSacCleaning2(0x0741),
    /// <summary>
    /// 样本条码扫描开关
    /// </summary>
    SampleBarScanControl(0x072D),
    /// <summary>
    /// 条码补偿写
    /// </summary>
    BarcodecompensateWrite(0x072B),
    /// <summary>
    /// 条码补偿读
    /// </summary>
    BarcodecompensateRead(0x072C);
	private long id; 
	private DeviceDebugCommandSample(long id) {
		this.id = id;
	}
	public long getId() {
		return id;
	}
	public String toString() {
		return String.format("%0#6x", (((long)id) & 0xffffL));
	}
	public String getStrId() {
		return Integer.toHexString((int) id).toUpperCase();
	}
	/*public static DeviceDebugCommandSample fromId(long id) throws CodeException {
		id = (((long)id) & 0xffffL);
		for (int i = 0; i < DeviceCommand.values().length; i++)
			if (DeviceDebugCommandSample.values()[i].id == id)
				return DeviceDebugCommandSample.values()[i];
		throw new CodeException(id);
	}*/
}
