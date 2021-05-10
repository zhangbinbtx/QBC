package com.quaero.qbcTest.Enum;

/// <summary>
/// 模块信息
/// </summary>
public abstract class ModuleInfo
{
    protected String serialNumber = "";

    protected boolean isNeedReleaseTrans = false;

    /// <summary>
    /// 模块类型
    /// </summary>
    protected ModuleType moduleType;
    /// <summary>
    /// 模块索引（从0开始）
    /// </summary>
    protected int moduleIndex;
    /// <summary>
    /// 模块状态
    /// </summary>
    protected ModuleState moduleState = null;

    protected abstract int GetModuleKey();

    protected String GetModuleName()
    {
        return ModuleToText(moduleType, moduleIndex + 1);
    }
    protected void SetModuleType(ModuleType value)
    {
        moduleType = value;
    }

    public void Clear()
    {
        moduleState.Clear();
    }
    
    

    public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public boolean isNeedReleaseTrans() {
		return isNeedReleaseTrans;
	}

	public void setNeedReleaseTrans(boolean isNeedReleaseTrans) {
		this.isNeedReleaseTrans = isNeedReleaseTrans;
	}

	public ModuleType getModuleType() {
		return moduleType;
	}

	public void setModuleType(ModuleType moduleType) {
		this.moduleType = moduleType;
	}

	public int getModuleIndex() {
		return moduleIndex;
	}

	public void setModuleIndex(int moduleIndex) {
		this.moduleIndex = moduleIndex;
	}

	public ModuleState getModuleState() {
		return moduleState;
	}

	public void setModuleState(ModuleState moduleState) {
		this.moduleState = moduleState;
	}

	public int getModuleID() {
		return moduleIndex + 1;
	}

	public void setModuleID(int moduleID) {
		moduleIndex = moduleID - 1;
	}

	public int getModuleKey() {
		return GetModuleKey();
	}


	public ModuleState getState() {
		return this.moduleState;
	}


	public String getModuleName() {
		return GetModuleName();
	}

   public static String ModuleToText(ModuleType moduleType, int moduleID)
    {
        String sText = "";
        switch (moduleType)
        {
            case Chem:
                sText = "生化" + moduleID+"";
                break;
            case Imm:
                sText = "免疫" + moduleID+"";
                break;
            case ISE:
                sText = "ISE";
                break;
            case SamplePlatform:
                sText = "轨道";
                break;
            default:
                sText = "未知";
                break;
        }
        return sText;
    }
}
