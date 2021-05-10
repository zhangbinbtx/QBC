package com.quaero.qbcTest.dto.entity;

import lombok.Data;

@Data
public class ElectricMachineryMove {
	 private byte boardID; //模块编号
	 private byte ID; //电机编号
	 private byte DirectionId; //方向
	 private int Coordinate; //坐标
	 private int Speed; //最高频率

}
