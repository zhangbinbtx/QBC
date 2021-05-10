package com.quaero.qbcTest.dto.entity;

import java.util.Date;

import com.quaero.qbcTest.Controller.baseController;
import com.quaero.qbcTest.Enum.ModuleInfo;
import com.quaero.qbcTest.Enum.ModuleType;

import lombok.Getter;

 
@Getter
public class ReagentInfoDetail extends ReagentInfo {
	public String ModuleText;

    public String DiskText;

    public String PositionText;

    public String ReaTypeText;

    public String BottleSizeText;

    public Date ExpirDate;

    public String ExpirDateText;

    public boolean IsExpired;

    private String getModuleText()
    {
        return ModuleInfo.ModuleToText(ModuleType.Chem, this.ModuleNo);
    }

    private String getDiskText()
    {
        return baseController.DiskToString(this.ReaDisk);
    }

    private String getPositionText()
    {
        return this.ReaPos+"";
    }

    private String getReaTypeText()
    {
        return baseController.ReaTypeToString(this.ReaType);
    }

    private String getBottleSizeText()
    {
        return baseController.BottleSizeToString(this.BottleSize);
    }

    private Date getExpirDate()
    {
        return (this.ExpirationDate.before(this.ExpirationDateOpen)) ? this.ExpirationDate : this.ExpirationDateOpen;
    }

    private String getExpirDateText()
    {
        return String.format("{0:yyyy/MM/dd}", getExpirDate()); 
    }

    private boolean getExpired()
    {
        return (getExpirDate().after(new Date()));
    }
}
