package com.quaero.qbcTest.dto.entity;

import lombok.Data;
/**
 * flash电机参数结构
 * @author zhangbin
 *
 */
@Data
public class FlashStruct {
	 private byte boardID; //模块编号
	 private byte MachineryID;//电机编号
	 private int[] FlashParameter;

}
