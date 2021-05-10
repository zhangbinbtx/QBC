package com.quaero.qbcTest.Enum;

public enum DeviceDebugCommandElectricMachineryMacroDefinition {
	 /// <summary>
    /// 读电机状态
    /// </summary>
    ElectricMachineryState(0x0725),
    /// <summary>
    /// 读取模块类型
    /// </summary>
    ElectricMachineryMove(0x0726),
    /// <summary>
    /// 写入参数
    /// </summary>
    MacroDefinitionWrite(0x0727),
    /// <summary>
    /// 读取参数
    /// </summary>
    MacroDefinitionRead(0x0728),
    /// <summary>
    /// 写入参数
    /// </summary>
    FlashRead(0x0729),
    /// <summary>
    /// 读取参数
    /// </summary>
    FlashWrite(0x072A);
	private long id; 
	private DeviceDebugCommandElectricMachineryMacroDefinition(long id) {
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
