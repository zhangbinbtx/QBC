package com.quaero.qbcTest.util;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


import lombok.Getter;

@Component
//@Getter
//@PropertySource(value = {"classpath:application-test.yml"}, encoding = "utf-8")
@ConfigurationProperties(prefix = "Best")
public class Best {
	
     private String MachineIP1; 
     private String MachineIP2; 
     private String MachineIP3; 
     private String MachineIP4;
     private int LocalMachinePort;
     private String TrackIP;
     private int LocalTrackPort;
     private String QxAutoAccumulated;
     
     public String getMachineIP1(){
    	 return  LoadPropertiesFileUtil.getPropert("MachineIP1");
		//return this.MachineIP1;
     }
     public String getMachineIP2(){
    	 return  LoadPropertiesFileUtil.getPropert("MachineIP2");
     }
     public String getMachineIP3(){
    	 return  LoadPropertiesFileUtil.getPropert("MachineIP3");
     }
     public String getMachineIP4(){
    	 return  LoadPropertiesFileUtil.getPropert("MachineIP4");
     }
     public int getLocalMachinePort(){
    	 return  Integer.valueOf(LoadPropertiesFileUtil.getPropert("LocalMachinePort"));
     }
     public String getTrackIP(){
    	 return  LoadPropertiesFileUtil.getPropert("TrackIP");
     }
     public String getQxAutoAccumulated(){
    	 return  LoadPropertiesFileUtil.getPropert("QxAutoAccumulated");
     }
     public int getLocalTrackPort(){
    	 return   Integer.valueOf(LoadPropertiesFileUtil.getPropert("LocalTrackPort"));
     }
     

}
