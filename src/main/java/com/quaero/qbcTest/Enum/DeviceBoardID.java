package com.quaero.qbcTest.Enum;

public enum DeviceBoardID {
	            Main (0x01),//主控板
		        React (0x02),//反应板
		        AD (0x03),//AD板
		        Stir (0x04),//搅拌板
		        Sample (0x05),//样本板
		        Reagent1 (0x06),//试剂板1
		        Reagent2 (0x07),//试剂板2
		        Refrigeration (0x08),//制冷板
		        AC (0x09), //交流
		        Comm (0x20),//中位机
		        Track (0x23),//轨道      
		        Unknown (0xFF);//未知  
	 private long value;
		
		private DeviceBoardID(long value) {
			this.value = value;
		}
		//获取延伸信息value
		public long getValue() {
			return value;
		}
		public void setValue(long value) {this.value = value;}
		//通过延伸信息value获取Week类的一个枚举实例
		public static DeviceBoardID getWeekByValue(long value) {
			for(DeviceBoardID week : DeviceBoardID.values()) {
				if(value==week.getValue()) {
					return week;
				}
			}
			return null;
		}
}
