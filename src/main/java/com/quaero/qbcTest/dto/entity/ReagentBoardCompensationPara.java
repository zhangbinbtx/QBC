package com.quaero.qbcTest.dto.entity;

import lombok.Data;

@Data
public class ReagentBoardCompensationPara {

	 public int R1ToWash;//R1到清洗位置
     public int R4ToWash;//R4到清洗位置
     public int WashToAbsorption;//清洗到吸液
     public int R1ToAbsorption;//R1到吸液
     public int AbsorptionToWash;//吸液到清洗        

}
