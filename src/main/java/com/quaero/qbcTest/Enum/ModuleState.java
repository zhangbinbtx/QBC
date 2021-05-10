package com.quaero.qbcTest.Enum;

/// <summary>
/// 模块状态
/// </summary>
public abstract class ModuleState
{
    protected int workState = 0;
    protected int testState = 0;
    protected boolean isOnline = false;
    protected boolean enabled = false;        

    protected  ModuleSimpleStatus GetSimpleStatus()
    {
        if (!enabled)
        {
            return ModuleSimpleStatus.Disabled;
        }
        else if (IsConnecting())
        {
            return ModuleSimpleStatus.None;
        }
        else if (IsPrepareStandBy())
        {
            return ModuleSimpleStatus.None;
        }
        else if (IsError())
        {
            return ModuleSimpleStatus.Error;
        }
        else if (IsSleep())
        {
            return ModuleSimpleStatus.Sleeling;
        }
        else if ((IsSamplingPause()) || (IsReagentPause()))
        {
            return ModuleSimpleStatus.Pausing;
        }
        else if (IsTesting())
        {
            return ModuleSimpleStatus.Testing;
        }
        else if (IsWorking())
        {
            return ModuleSimpleStatus.Working;
        }
        else if (IsStandBy())
        {
            return ModuleSimpleStatus.StandBy;
        }
        else
        {
            return ModuleSimpleStatus.Unknown;
        }
    }        
     
    
    public int getWorkState() {
		return workState;
	}

	public void setWorkState(int workState) {
		this.workState = workState;
	}

    
	public int getTestState() {
		return testState;
	}


	public void setTestState(int testState) {
		this.testState = testState;
	}

	public String StateText() {
		return GetStateText();
	}


    public void Clear()
    {
        workState = 0;
        testState = 0;
        isOnline = false;
        //_enabled = false;
    }

    public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
    
    public boolean isOnline() {
		return isOnline;
	}


	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

    public boolean IsOffline()
    {
        return (!isOnline);
    }
   
    public ModuleSimpleStatus getSimpleStatus() {
		return GetSimpleStatus();
	}

    protected String GetStateText()
    {
        String value;
        if (enabled)
        {
            if (isOnline)
            {
                value = "联机";
            }
            else
            {
                value = "脱机";
            }
        }
        else
        {
            value = "未启用";
        }
        return String.format("%s ...", value);
    }

    public  boolean IsStandBy()
    {
        return false;
    }

    public  boolean IsConnecting()
    {
        return false;
    }

    public boolean IsPrepareStandBy()
    {
        return false;
    }

    public boolean IsTesting()
    {
        return false;
    }

    public boolean IsTestProcessing()
    {
        return false;
    }

    public boolean IsError()
    {
        return false;
    }

    public boolean IsSleep()
    {
        return false;
    }

    public boolean IsSamplingPause()
    {
        return false;
    }

    public boolean IsReagentPause()
    {
        return false;
    }

    public boolean IsWorking()
    {
        return false;
    }

    public boolean IsAllowOffline()
    {
        return (!enabled) || ((enabled) && (isOnline) && ((IsStandBy()) || (IsError()))); 
        /*if ((!_enabled) || (!_isOnline))
        {
            return true;
        }
        return (!IsWorking());*/
    }

    public boolean IsAllowTest()
    {
        return (IsStandBy() || (IsTestProcessing()));
    }

    public boolean IsAllowPauseContinue()
    {
        return false;
    }

    public boolean IsAllowPause()
    {
        return ((IsSamplingPause()) || (IsReagentPause()));
    }

    public boolean IsAllowTestBlank()
    {
        return false;
    }

    public boolean IsAllowTestAD()
    {
        return false;
    }

    public boolean IsAllowStop()
    {
        return (IsWorking());
    }

    public boolean IsAllowSleep()
    {
        return (IsStandBy());
    }

    public boolean IsAllowWakeup()
    {
        return IsSleep();
    }

    public boolean IsAllowReset()
    {
        return ((enabled) && (isOnline) && ((IsStandBy()) || (!IsWorking() && !IsConnecting())));
    }

    public boolean IsAllowSamplePause()
    {
        return (IsTestProcessing());
    }

    public boolean IsAllowSampleContinue()
    {
        return IsSamplingPause();
    }

    public boolean IsAllowReagentPause()
    {
        return (IsTestProcessing());
    }

    public boolean IsAllowReagentContinue()
    {
        return (IsReagentPause());
    }

    public boolean IsAllowExit()
    {
        return ((!enabled) || (!isOnline) || (IsAllowOffline()));
    }
}
