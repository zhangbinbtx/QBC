package com.quaero.qbcTest.Enum;

public enum ReaBottleSize
{
    None(0),
    BS20ML(1),
    BS70ML(2);
    private Integer value;
	
	private ReaBottleSize(int value) {
		this.value = value;
	}
	//获取延伸信息value
	private Integer getValue() {
		return value;
	}
	//通过延伸信息value获取Week类的一个枚举实例
	public static ReaBottleSize getWeekByValue(Integer value) {
		for(ReaBottleSize week : ReaBottleSize.values()) {
			if(value.equals(week.getValue())) {
				return week;
			}
		}
		return null;
	}
}
