package com.quaero.qbcTest.dto.entity;

import lombok.Data;

@Data
public class AlarmInfoDetail {

	 public int ID;
     public String Code;
     public int Level;
     public String Content;
     public String Description;
     public String Solutions;
     public boolean Enabled;
     public int DefEnabled;
     public int DataType;


}
