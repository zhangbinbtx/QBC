package com.quaero.qbcTest.dto.entity;

import java.util.Date;

import lombok.Data;

@Data
public class SampleInfo  //样本信息
{
    public int SampleID;  //样本编号
    public int SampleNo;  //样本号
    public String LisNo; //LIS编号
    public int ModuleNo;  //模块
    public int SampleDisk;  //盘号
    public int SamplePos;  //位置
    public int Rack;  //样本架号
    public int RackPos;  //样本架位置
    public int IsRack;  //是否是样本架
    public String Barcode;  //条码
    public int SampleType;  //样本类型
    public int CupType;  //杯类型，0-标准杯 1-试管
    public Date SamplingDate;  //采样日期
    public Date SamplingTime;  //采样时间
    public Date SendingDate;  //送检日期
    public Date SendingTime;  //送检时间
    public Date TestingDate;  //测试日期
    public Date TestingTime;  //测试时间
    public Date ReportDate;  //报告日期
    public Date ReportTime;  //报告时间
    public String PatientType;  //就诊类型
    public String Department;  //送检科室
    public String Area;  //病区
    public String Ward;  //病房
    public String BedNo;  //病床
    public String Doctor;  //送检医生
    public String Checker;  //检验医生
    public String Assessor;  //审核医生
    public String Clinic;  //临床诊断
    public String Remark;  //备注
    public boolean AuditingFlag;  //审核标志
    public boolean PrintingFlag;  //打印标志
    public boolean EmerFlag;  //急诊标志
    public boolean BlankFlag;  //样本空白标志
    public boolean DeleteFlag;  //删除标志
    public int Repeat;  //重复测试次数
    public String PatCaseNo;  //病历号
    public String PatName;  //患者姓名
    public int PatGender;  //患者性别(0-男 1-女)
    public int PatAge;  //患者年龄
    public int AgeUnit;  //年龄单位
    public String PatNation;  //患者民族

    public Date PatBirthDate; //出生日期

    public SampleInfo()
    {
        Barcode = "";
        PatientType = "";
        Department = "";
        Area = "";
        Ward = "";
        BedNo = "";
        Doctor = "";
        Checker = "";
        Assessor = "";
        Clinic = "";
        Remark = "";
        PatCaseNo = "";
        PatName = "";
        PatNation = "";
        PatBirthDate = new Date();
    }
}