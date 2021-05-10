package com.quaero.qbcTest.dto.entity;

import lombok.Data;

@Data
public class WashSample{  //样本针交叉污染

   

	public int WashID;  //污染编号
    public int ModuleNo;  //模块
    public String ItemName;  //项目名称
    public int WashType;  //清洗液类型
    public int WashVol;  //清洗液量
    
    public String WashVolText;
    public WashSample()
    {
        this.ItemName = "";
    }        
    public String getWashVolText() {
    	return String.valueOf(this.WashVol / 10.0);
    }
}

