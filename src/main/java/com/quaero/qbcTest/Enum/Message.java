package com.quaero.qbcTest.Enum;
/**
 * 自定义消息
 * @author zhangbin
 *
 */
public enum Message
{
    None,
    /// <summary>
    /// 通讯超时
    /// </summary>
    CommounicateTimeOut,
    /// <summary>
    /// 通讯错误
    /// </summary>
    CommounicateError,
    /// <summary>
    /// 样本通知
    /// </summary>
    SampleNotify,
    /// <summary>
    /// 仪器状态
    /// </summary>
    DeviceStatus,
    /// <summary>
    /// 同步模块
    /// </summary>
    DeviceSyncModules,
    /// <summary>
    /// 设备报警
    /// </summary>
    DeviceAlarm,
    /// <summary>
    /// LIS状态改变
    /// </summary>
    LIS,
    /// <summary>
    /// 网络数据监控
    /// </summary>
    CommounicateMonitor,
    /// <summary>
    /// 质控登记
    /// </summary>
    QcRegister,
    /// <summary>
    /// 质控结果
    /// </summary>
    QcResult,
    /// <summary>
    /// 质控吸光度反应曲线
    /// </summary>
    QcAbsCurve,
    /// <summary>
    /// 样本吸光度反应曲线
    /// </summary>
    SampleAbsCurve,
    /// <summary>
    /// 校准吸光度反应曲线
    /// </summary>
    CalAbsCurve,
    /// <summary>
    /// 杯空白/光量检查
    /// </summary>
    CupBlank,
    /// <summary>
    /// 反应杯状态
    /// </summary>
    ReactCup,
    /// <summary>
    /// 反应盘刷新
    /// </summary>
    ReactDisk,
    /// <summary>
    /// 样本结果
    /// </summary>
    SampleResult,
    /// <summary>
    /// 校准登记
    /// </summary>
    CalRegister,
    /// <summary>
    /// 校准结果
    /// </summary>
    CalResult,
    /// <summary>
    /// 试剂瓶刷新
    /// </summary>
    ReagentBottle,
    /// <summary>
    /// 试剂盘刷新
    /// </summary>
    ReagentDisk,
    /// <summary>
    /// 试剂剩余量扫描
    /// </summary>
    ReagentLevel,
    /// <summary>
    /// 项目复查
    /// </summary>
    ItemRecheck,
    /// <summary>
    /// 刷新项目复查
    /// </summary>
    RefreshRecheck,
    /// <summary>
    /// 试剂消耗
    /// </summary>
    ReagentConsumption,
    /// <summary>
    /// 试剂条码扫描
    /// </summary>
    ReagentBarCodeScan,
    /// <summary>
    /// 蜂鸣器设置
    /// </summary>
    BuzzerSettings,
    /// <summary>
    /// 样本盘
    /// </summary>
    SampleDisk,
    /// <summary>
    /// 样本杯
    /// </summary>
    SampleCup,
    /// <summary>
    /// 报警级别设置
    /// </summary>
    AlarmLevel,

    /// <summary>
    /// 样本板调试
    /// </summary>
    DebugDeviceSample,
    /// <summary>
    /// 试剂板调试
    /// </summary>
    DebugDeviceReagent,
    /// <summary>
    /// 反应板调试
    /// </summary>
    DebugDeviceReact,
    /// <summary>
    /// 搅拌板调试
    /// </summary>
    DebugDeviceStir,
    /// <summary>
    /// 维护调试获取状态1
    /// </summary>
    DebugDeviceMaintenanceStatus1,
    /// <summary>
    /// <summary>
    /// 维护调试获取状态2
    /// </summary>
    DebugDeviceMaintenanceStatus2,
    /// <summary>
    /// 调试AD板
    /// </summary>
    DebugDeviceAD,
    /// <summary>
    /// Debug升级准备
    /// </summary>
    DebugUpgradePrepare,
    /// <summary>
    /// Debug升级开始
    /// </summary>
    DebugUpgradeStart,
    /// <summary>
    /// Debug升级数据
    /// </summary>
    DebugUpgradeData,
    /// <summary>
    /// Debug升级结束
    /// </summary>
    DebugUpgradeOver,
    /// <summary>
    /// 调试AC板
    /// </summary>
    DebugDeviceAC,
    /// <summary>
    /// Boot版本信息
    /// </summary>
    BootVersion,
    /// <summary>
    /// 应用程序版本信息
    /// </summary>
    Version,
    /// <summary>
    /// 轨道调试
    /// </summary>
    DebugDeviceTrack,
    /// <summary>
    /// 通采数据
    /// </summary>
    DebugDeviceADData,
    /// <summary>
    /// 通采完成
    /// </summary>
    DebugADDataComplete,

    /// <summary>
    /// 样本板补偿参数
    /// </summary>
    DebugSamplePara,
    /// <summary>
    /// 试剂板补偿参数
    /// </summary>
    DebugReagentPara,
    /// <summary>
    /// 系统设置
    /// </summary>
    DebugSystemSettings,
    /// <summary>
    /// 试剂条码阅读器检查
    /// </summary>
    DebugReagentBarcodeReaderCheck,
    /// <summary>
    /// 试剂条码长度
    /// </summary>
    DebugReagentBarcodeLength,
    /// <summary>
    /// 条码重复扫描次数
    /// </summary>
    DebugReagentScanCount,
    /// <summary>
    /// 制冷板数据
    /// </summary>
    DebugDeviceRefrigerationData,
    /// <summary>
    /// 杂散光
    /// </summary>
    DebugDeviceScatteredLight,
    /// <summary>
    /// 线性
    /// </summary>
    DebugDeviceLineark2Cr2O7,
    DebugDeviceLinearOrangeG,
    /// <summary>
    /// 准确度
    /// </summary>
    DebugDeviceAccuracy05,
    DebugDeviceAccuracy10,
    /// <summary>
    /// 稳定性
    /// </summary>
    DebugDeviceStabilityOrangeG,
    DebugDeviceStabilityCuSO4,
    /// <summary>
    /// 重复性
    /// </summary>
    DebugDeviceRepeatability,
    /// <summary>
    /// 携带污染率
    /// </summary>
    DebugDevicePollutionRate,
    /// <summary>
    /// 样本条码阅读器检查
    /// </summary>
    DebugSampleBarcodeReaderCheck,
    /// <summary>
    /// 样本条码长度
    /// </summary>
    DebugSampleBarcodeLength,
    /// <summary>
    /// 样本条码重复扫描次数
    /// </summary>
    DebugSampleScanCount,
    /// <summary>
    /// 系统维护中
    /// </summary>
    DebugMaintenancing,
    /// <summary>
    /// 样本针准备就绪
    /// </summary>
    SampleProbeBeready,
    /// <summary>
    /// 试剂针准备就绪
    /// </summary>
    ReagentProbeBeready,
    /// <summary>
    /// 样本针精度参数
    /// </summary>
    SampleProbeAccuracy,
    /// <summary>
    /// 试剂针针精度参数
    /// </summary>
    ReagentProbeAccuracy,
    /// <summary>
    /// 样本针携带污染率
    /// </summary>
    SampleProbePollutionRate,
    /// <summary>
    /// 试剂针携带污染率
    /// </summary>
    ReagentProbePollutionRate,
    /// <summary>
    /// 搅拌棒携带污染率
    /// </summary>
    StirProbePollutionRate,
    /// <summary>
    /// 样本针监控
    /// </summary>
    SampleProbeSurfaceMonitor,
    /// <summary>
    /// 手工测试
    /// </summary>
    ManualTestResult,
    /// <summary>
    /// 残留水
    /// </summary>
    ResidualWater,
    /// <summary>
    /// 探针升降检查
    /// </summary>
    ProbeDeclineCheck,
    /// <summary>
    /// 电机状态
    /// </summary>
    ElectricMachineryState,
    /// <summary>
    /// 反应杯携带污染率
    /// </summary>
    CupPollutionRate,
    /// <summary>
    /// 刷新试剂信息
    /// </summary>
    RefreshReagent,
    /// <summary>
    /// 读取宏定义
    /// </summary>
    MacroDefinitionRead,
    /// <summary>
    /// 更改窗体样式
    /// </summary>
    FormBorderStyleChange,
    /// <summary>
    /// 接收Flash参数
    /// </summary>
    FlashRead;
}
