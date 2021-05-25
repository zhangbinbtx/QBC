package com.quaero.qbcTest.dto.entity;

import java.util.Date;

import lombok.Data;

@Data
public class SysLog {//系统日志

	  public int LogID;  //日志编号
      public Date SaveDate;  //日志时间
      public Date SaveTime;  //日志时间
      public int ModuleNo;  //模块
      public String Operator;  //操作者
      public int LogType;  //日志类型
      public String Content;  //日志内容

      public SysLog()
      {
          Operator = "";
          Content = "";
      }

}
