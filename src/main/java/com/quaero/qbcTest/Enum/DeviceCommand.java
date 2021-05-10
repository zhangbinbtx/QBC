package com.quaero.qbcTest.Enum;

public enum DeviceCommand {
    None(0x0000),
    /**
   * 联机
    **/
    Online(0x0001),
    /**
   * 复位
    **/
    Reset(0x0002),
    /**
   * 急停
    **/
    Stop(0x0003),
    /**
   * 初始化
    **/
    Initialization(0x00061),
    /**
   * 老化测试
    **/
    MechanicalActionCheck(0x0004),
    /**
   * 报警信息
    **/
    Alarm(0x0005),
    /**
   * 仪器状态
    **/
    Status(0x0006),
    /**
   * 休眠
    **/
    Sleep(0x0007),
    /**
   * 唤醒
    **/
    Wakeup(0x0008),
    /**
     * 杯空白检查
    **/
    CupBlankCheck(0x0009),        
    /**
   * 光亮检查
    **/
    CupLightCheck(0x000A),
    /**
   * 版本信息
    **/
    Version(0x000B),
    /**
   * 加样暂停
    **/
    SamplePause(0x000C),
    /**
   * 加样继续
    **/
    SampleContinue(0x000D),
    /**
   * 试剂条码扫描
    **/
    ReagentBarcodeScan(0x000E),
    /**
   * 反应杯清洗
    **/
    ReactCupWash(0x000F),
    /**
   * AD数据
    **/
    ADData (0x0010),
    /**
   * 启动测试
    **/
    StartTest(0x0011),
    /**
   * 多试剂位置信息
    **/
    ReagentMultiPosition(0x0012),
    /**
   * 试剂针交叉污染
    **/
    ReagentProbePollute(0x0013),
    /**
   * 样本针交叉污染
    **/
    SampleProbePollute(0x0014),
    /**
   * 反应杯交叉污染
    **/
    ReactCupPollute(0x0015),
    /**
   * 条码阅读器检查
    **/
    BarcodeReaderCheck(0x0016),
    /**
   * 清洗恒温槽-放水
    **/
    ThermostatWashOut(0x0017),
    /**
   * 清洗样本针
    **/
    SampleProbeWash(0x0018),
    /**
   * 清洗水箱
    **/
    WaterTankWash(0x0019),
    /**
   * 注射泵排气
    **/
    InjectionPumpExhaust(0x001A),
    /**
   * 样本针垂直检查
    **/
    SampleProbeVerticalCheck(0x001B),
    /**
   * 样本针水平检查
    **/
    SampleProbeHorizontalCheck(0x001C),
    /**
   * 试剂针垂直检查
    **/
    ReagentProbeVerticalCheck(0x001D),
    /**
   * 试剂针水平检查
    **/
    ReagentProbeHorizontalCheck(0x001E),
    /**
   * 搅拌机构水平检查
    **/
    MixingMechanismHorizontalCheck(0x001F),
    /**
   * 试剂余量扫描
    **/
    ReagentRemainVolumeScan(0x0020),
    /**
   * 灌冲清洗液管路排气
    **/
    DetergentLineExhaust(0x0021),
    /**
   * 自动浓废液管路排气
    **/
    AutoThickLiquidPipeWash(0x0022),
    /**
   * 手动浓废液管路排气
    **/
    ManualThickLiquidPipeWash(0x0023),
    /**
   * 样本机构检查
    **/
    SampleMechanismCheck(0x0024),
    /**
   * 试剂机构检查
    **/
    ReagentMechanismCheck(0x0025),
    /**
   * 搅拌机构检查
    **/
    MixingMechanismCheck(0x0026),
    /**
   * 光学稳定性检查
    **/
    PhotoelectricStabilityCheck(0x0027),
    /**
   * 试剂消耗
    **/
    ReagentConsumption(0x0028),
    /**
   * 发送样本架测试数据
    **/
    SendTestData(0x0029),
    /**
   * 样本架暂停
    **/
    RackPause(0x002A),
    /**
   * 脱机
    **/
    Offline(0x002B),
    /**
   * 样本架条码信息
    **/
    RackBarcode(0x002C),
    /**
   * 发送测试数据完毕
    **/
    SendTestFinish(0x002D),
    /**
   * 测试项目编号
    **/
    TestItemID(0x002E),
    /**
   * 追加测试
    **/
    AppendTest(0x002F),
    /**
   * 机构调试命令
    **/
    DeviceDebug(0x0030),
    /**       
   * AD机构调试命令
    **/
    DeviceADDebug(0x0033),
    /**
   * 升级准备命令
    **/
    UpgradePrepareDebug(0x0034),
    /**
   * 升级开始命令
    **/
    UpgradeStartDebug(0x0035),
    /**
   * 升级数据命令
    **/
    UpgradeDataDebug(0x0036),
    /**
   * 升级完成命令
    **/
    UpgradeOverDebug(0x0037),
    /**
   * 读Boot版本信息
    **/
    BootVersion(0x0038),
    /**
   * 开始通采
    **/
    ReadDebugADData(0x0039),
    /**
   * 通采数据
    **/
    DebugADData(0x003A),
    /**
   * 通采完成
    **/
    DebugADDataComplete(0x003B),
    /**
   * 系统设置
    **/
    DebugSystemSettings(0x003C),
    /**
   * 浓废液管路排气
    **/
    PipelineExhausWasteLiquid(0x003D),
    /**
   * 清洗液管路排气
    **/
    PipelineExhaustCleaningLiquid(0x003E),
    /**
   * 样本针精度检查
    **/
    PumpAccuracySample(0x0040),
    /**
   * R1试剂针精度检查
    **/
    PumpAccuracyReagent1(0x0041),
    /**
   * R2试剂针精度检查
    **/
    PumpAccuracyReagent2(0x0042),
    /**
   * 样本针触碰
    **/
    SamplePrebeTouch(0x0043),
    /**
   *试剂针触碰
    **/
    ReagentPrebeTouch(0x0044),
    /**
   *蜂鸣时间设置
    **/
    SetBuzzerTime(0x0045),
    /**
   *蜂鸣时间获取
    **/
    GetBuzzerTime(0x0046),
    /**
   *蜂鸣测试
    **/
    TestBuzzer(0x0047),
    /**
   *蜂鸣关闭
    **/
    CloseBuzzer(0x0048),

    /**
   *样本跳过
    **/
    IgnoreSample(0x0049),
    /**
   *试剂跳过
    **/
    IgnoreReagent(0x004A),

    /**
   *加试剂暂停
    **/
    ReagentPause(0x004B),        
    /**
   *加试剂继续
    **/
    ReagentContinue(0x004C),

    /**
   * 多试剂位置信息
    **/
    ReagentMultiPositionPlus(0x004D),
    /**
   * 测试准备完成
    **/
    TestPrepareCompleted(0x004F),
    /**
   * 读仪器序列号
    **/
    InstrumentSerialNumberRead(0x0051),
    /**
   * 写翻门传感器报警级别
    **/
    AlarmLevelWrite(0x0052),
    /**
   * 读翻门传感器报警级别
    **/
    AlarmLevelRead(0x0053),
    /**
   * 写仪器序列号
    **/
    InstrumentSerialNumberWrite(0x0054),
    /**
   * 样本针加样完成
    **/
    AddSampleCompleted(0x0055),
    /**
   * 测试数据发完
    **/
    SendDataCompleted(0x0056),
    /**
   * 手工测试，清洗反应杯
    **/
    SendManualTestClean(0x0057),
    /**
   * 手工测试，测试杯空白
    **/
    SendManualTestBlank(0x0058),
    /**
   * 手工测试，测试AD
    **/
    SendManualTestAD(0x0059),
    /**
   * 启动测试(残留水)
    **/
    StartTestRateResidualWater(0x005A),
    /**
   * 探针升降检查
    **/
    ProbeDeclineCheck(0x005B),
    /**
   * 启动测试(反应杯携带污染率)
    **/
    StartTestRateCup(0x005D),
    /**
   * 发送给ISE模块
    **/
    IseTx(0x0055E),
    /**
   * ISE返回信息
    **/
    IseTf(0x0055F),
    /**
   * 清洗恒温槽-上水
    **/
    ThermostatWashIn(0x005E),
    /**
   * 心跳检查
    **/
    KeepAlive(0x00FF),
    /**
   * 样本条码扫描
    **/
    SampleBarcodeScan(0x0060),
    /**
   * 回收样本架
    **/
    RecycleRack(0x1102),
    /**
   * 反应盘正转
    **/
    ReactTurnForward(0x0101),
    /**
   * 反应盘反转
    **/
    ReactTurnOpposite(0x0102),
    /**
   * 反应盘归零
    **/
    ReactTurnZero(0x0103),
    /**
   * 清洗机构上升
    **/
    ReactWashUp(0x0104),
    /**
   * 清洗机构下降
    **/
    ReactWashDown(0x0105),
    /**
   * 清洗机构归零
    **/
    ReactWashZero(0x0106),
    /**
   * 搅拌机构1上升
    **/
    ReactStir1Up(0x0107),
    /**
   * 搅拌机构1下升
    **/
    ReactStir1Down(0x0108),
    /**
   * 搅拌机构1升降归零
    **/
    ReactStir1Zero(0x0109),
    /**
   * 搅拌机构1摆1位置
    **/
    ReactStir1SwingPosition1(0x010A),
    /**
   * 搅拌机构1摆4位置
    **/
    ReactStir1SwingPosition4(0x010B),
    /**
   * 搅拌机构1摆动归零
    **/
    ReactStir1SwingZero(0x010C),
    /**
   * 搅拌机构2上升
    **/
    ReactStir2Up(0x010D),
    /**
   * 搅拌机构2下升
    **/
    ReactStir2Down(0x010E),
    /**
   * 搅拌机构2升降归零
    **/
    ReactStir2Zero(0x010F),
    /**
   * 搅拌机构2摆2位置
    **/
    ReactStir2SwingPosition2(0x0110),
    /**
   * 搅拌机构2摆3位置
    **/
    ReactStir2SwingPosition3(0x0111),
    /**
   * 搅拌机构2摆动归零
    **/
    ReactStir2SwingZero(0x0112),
    /**
   * 搅拌电机1
    **/
    ReactStirMotor1(0x0113),
    /**
   * 搅拌电机2
    **/
    ReactStirMotor2(0x0114),
    /**
   * 电磁阀1
    **/
    ReactEletValue1(0x0115),
    /**
   * 电磁阀2
    **/
    ReactEletValue2(0x0116),
    /**
   * 电磁阀3
    **/
    ReactEletValue3(0x0117),
    /**
   * 电磁阀4
    **/
    ReactEletValue4(0x0118),
    /**
   * 电磁阀5
    **/
    ReactEletValue5(0x0119),
    /**
   * 电磁阀6
    **/
    ReactEletValue6(0x011A),
    /**
   * 电磁阀7
    **/
    ReactEletValue7(0x011B),
    /**
   * 电磁阀8
    **/
    ReactEletValue8(0x011C),
    /**
   * 电磁阀9
    **/
    ReactEletValue9(0x011D),
    /**
   * 电磁阀10
    **/
    ReactEletValue10(0x011E),
    /**
   * 开始
    **/
    ReactStart(0x0120),
    /**
   * 停止
    **/
    ReactStop(0x0121),
    /**
   * 清洗检查
    **/
    ReactWashCheck(0x0123),
    /**
   * 搅拌检查
    **/
    ReactStirCheck(0x0124),
    /**
   * 获取反应板光耦状态
    **/
    ReactGetOptoStatus(0x0125),

    /**
   * 样本盘正转
    **/
    SampleTurnForward(0x0201),
    /**
   * 样本盘反转
    **/
    SampleTurnOpposite(0x0202),
    /**
   * 样本盘归零
    **/
    SampleTurnZero(0x0203),
    /**
   * 探针上升
    **/
    SampleProbeUp(0x0204),
    /**
   * 探针下降
    **/
    SampleProbeDown(0x0205),
    /**
   * 探针归零
    **/
    SampleProbeZero(0x0206),
    /**
   * 探针摆动顺时针
    **/
    SampleProbeSwingForward(0x0207),
    /**
   * 探针摆动逆时针
    **/
    SampleProbeSwingOpposite(0x0208),
    /**
   * 探针摆动归零
    **/
    SampleProbeSwingZero(0x0209),
    /**
   * 探针摆动清洗液位置
    **/
    SampleProbeSwingWash(0x020A),
    /**
   * 探针摆动样本盘外
    **/
    SampleProbeSwingDiskOut(0x020B),
    /**
   * 探针摆动样本盘中
    **/
    SampleProbeSwingDisk(0x020C),
    /**
   * 探针摆动样本盘内
    **/
    SampleProbeSwingDiskIn(0x020D),
    /**
   * 注射泵吸液
    **/
    SampleInjectionPumpSuck(0x020E),
    /**
   * 注射泵吐液
    **/
    SampleInjectionPumpSpit(0x020F),
    /**
   * 注射泵归零
    **/
    SampleInjectionPumpZero(0x0210),
    /**
   * 样本板加样
    **/
    SampleTestSampling(0x0212),
    /**
   * 样本板停止
    **/
    SampleTestStop(0x0213),
    /**
   * 电磁阀内壁
    **/
    SampleEletValveIn(0x0215),
    /**
   * 电磁阀外壁
    **/
    SampleEletValveOut(0x0216),
    /**
   * 获取样本板光耦状态
    **/
    SampleGetOptoStatus(0x0217),

    /**
   * 试剂盘正转
    **/
    Reagent1TurnForward ( 0x0301),
    /**
   * 试剂盘反转
    **/
    Reagent1TurnOpposite(0x0302),
    /**
   * 试剂盘归零
    **/
    Reagent1TurnZero(0x0303),
    /**
   * 探针-上升
    **/
    Reagent1ProbeUp(0x0304),
    /**
   * 探针-下降
    **/
    Reagent1ProbeDown(0x0305),
    /**
   * 探针升降-归零
    **/
    Reagent1ProbeZero(0x0306),
    /**
   * 探针摆动-顺时针
    **/
    Reagent1ProbeSwingForward(0x0307),
    /**
   * 探针摆动-逆时针
    **/
    Reagent1ProbeSwingOpposite(0x0308),
    /**
   * 探针摆动-归零
    **/
    Reagent1ProbeSwingZero(0x0309),
    /**
   * 探针摆动-位置1
    **/
    Reagent1ProbeSwingPosition1(0x030A),
    /**
   * 探针摆动-位置4
    **/
    Reagent1ProbeSwingPosition4(0x030B),
    /**
   * 探针摆动-清洗位
    **/
    Reagent1ProbeSwingWashPosition(0x030C),
    /**
   * 探针摆动-试剂位
    **/
    Reagent1ProbeSwingReagentPosition(0x030D),
    /**
   * 探针摆动-加清洗位
    **/
    Reagent1ProbeSwingAddWashPosition(0x030E),
    /**
   * 注射泵-吸液
    **/
    Reagent1InjectionPumpSuck(0x030F),
    /**
   * 注射泵-吐液
    **/
    Reagent1InjectionPumpSpit(0x0310),
    /**
   * 注射泵-归零
    **/
    Reagent1InjectionPumpZero(0x0311),
    /**
   * 试剂板-停止
    **/
    Reagent1Stop(0x0313),
    /**
   * 试剂板-余量扫描
    **/
    Reagent1LevelScan(0x0315),
    /**
   * 试剂板-加试剂测试
    **/
    Reagent1TestAddReagent(0x0316),
    /**
   * 电磁阀-内壁
    **/
    Reagent1EletValveIn(0x0317),
    /**
   * 电磁阀-外壁
    **/
    Reagent1EletValveOut(0x0318),
    /**
   * 获取试剂板光耦状态
    **/
    Reagent1GetOptoStatus(0x0319),

    /**
   * 试剂盘正转
    **/
    Reagent2TurnForward(0x0401),
    /**
   * 试剂盘反转
    **/
    Reagent2TurnOpposite(0x0402),
    /**
   * 试剂盘归零
    **/
    Reagent2TurnZero(0x0403),
    /**
   * 探针-上升
    **/
    Reagent2ProbeUp(0x0404),
    /**
   * 探针-下降
    **/
    Reagent2ProbeDown(0x0405),
    /**
   * 探针升降-归零
    **/
    Reagent2ProbeZero(0x0406),
    /**
   * 探针摆动-顺时针
    **/
    Reagent2ProbeSwingForward(0x0407),
    /**
   * 探针摆动-逆时针
    **/
    Reagent2ProbeSwingOpposite(0x0408),
    /**
   * 探针摆动-归零
    **/
    Reagent2ProbeSwingZero(0x0409),
    /**
   * 探针摆动-位置1
    **/
    Reagent2ProbeSwingPosition1(0x040A),
    /**
   * 探针摆动-位置4
    **/
    Reagent2ProbeSwingPosition4(0x040B),
    /**
   * 探针摆动-清洗位
    **/
    Reagent2ProbeSwingWashPosition(0x040C),
    /**
   * 探针摆动-试剂位
    **/
    Reagent2ProbeSwingReagentPosition(0x040D),
    /**
   * 探针摆动-加清洗位
    **/
    Reagent2ProbeSwingAddWashPosition(0x040E),
    /**
   * 注射泵-吸液
    **/
    Reagent2InjectionPumpSuck(0x040F),
    /**
   * 注射泵-吐液
    **/
    Reagent2InjectionPumpSpit(0x0410),
    /**
   * 注射泵-归零
    **/
    Reagent2InjectionPumpZero(0x0411),
    /**
   * 试剂板-停止
    **/
    Reagent2Stop(0x0413),
    /**
   * 试剂板-余量扫描
    **/
    Reagent2LevelScan(0x0415),
    /**
   * 试剂板-加试剂测试
    **/
    Reagent2TestAddReagent(0x0416),
    /**
   * 电磁阀-内壁
    **/
    Reagent2EletValveIn(0x0417),
    /**
   * 电磁阀-外壁
    **/
    Reagent2EletValveOut(0x0418),
    /**
   * 获取试剂板光耦状态
    **/
    Reagent2GetOptoStatus(0x0419),

    /**
   * 水箱进水阀
    **/
    MaintenanceWaterTankValveIn(0x0501),
    /**
   * 水箱排水阀
    **/
    MaintenanceWaterTankValveOut(0x0502),
    /**
   * 水槽进水阀
    **/
    MaintenanceSinkValveIn(0x0503),
    /**
   * 水槽排水阀
    **/
    MaintenanceSinkValveOut(0x0504),
    /**
   * 水箱循环泵
    **/
    MaintenanceWaterTankCircluPump(0x0505),
    /**
   * 水槽循环泵
    **/
    MaintenanceSinkCircluPump(0x0506),
    /**
   * 负压泵
    **/
    MaintenancePressurePump(0x0507),
    /**
   * 齿轮泵
    **/
    MaintenanceGearPump(0x0508),
    /**
   * 水箱加热棒
    **/
    MaintenanceWaterTankHeater(0x0509),
    /**
   * 水槽加热棒
    **/
    MaintenanceSinkHeater(0x050A),
    /**
   * 卤素灯
    **/
    MaintenanceHalogenLamp(0x050B),
    /**
   * 获取状态1
    **/
    MaintenanceGetStatus1(0x050C),
    /**
   * 获取状态1
    **/
    MaintenanceGetStatus2(0x050D);
	private long id; 
	private DeviceCommand(long id) {
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
	public static DeviceCommand fromId(long id) throws CodeException {
		id = (((long)id) & 0xffffL);
		for (int i = 0; i < DeviceCommand.values().length; i++)
			if (DeviceCommand.values()[i].id == id)
				return DeviceCommand.values()[i];
		throw new CodeException(id);
	}
}
