package com.quaero.qbcTest.dto.entity;

import lombok.Data;

//反应杯交叉污染
@Data
public class WashCell {


    public int WashID;  //污染编号
    public int ModuleNo;  //模块
    public String FromItemName;  //污染项目名称
    public String ToItemName;  //被污染项目名称
    public int R1Pos;  //R1清洗液位置
    public int R1Vol;  //R1清洗液量
    public int R2Pos;  //R2清洗液位置
    public int R2Vol;  //R2清洗液量

    public WashCell()
    {
        this.FromItemName = "";
        this.ToItemName = "";
    }

}
