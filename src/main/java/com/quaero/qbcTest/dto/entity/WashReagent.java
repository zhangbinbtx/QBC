package com.quaero.qbcTest.dto.entity;

import lombok.Data;

@Data
public class WashReagent //试剂针交叉污染
{
    public int WashID;  //污染编号
    public int ModuleNo;  //模块
    public String ProName;  //试剂针名称
    public String FromItem;  //从项目名称
    public String ToItem;  //到项目名称
    public int FromType;  //从项目类型
    public int ToType;  //到项目类型
    public int Volume;  //清洗液量   

    public WashReagent()
    {
        this.ProName = "";
        this.FromItem = "";
        this.ToItem = "";
    }
}