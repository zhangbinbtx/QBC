package com.quaero.qbcTest.dto.entity;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor //全参构造函数
@NoArgsConstructor //无参构造函数
@Accessors(chain = true) //链式访问
public class AlarmInfo {
   private int id;
   private int MODULE_NO;
   private int MODULE_TYPE;
   private String CODE;
   private int LEVEL_NO;
   private String CONTENT;
   private String SAVEDATE;
   private String SAVETIME;
   
   
   
	
}
