package com.quaero.qbcTest.Enum;

public  enum ReaType
{
    None  (0),
    R1  (1),
    R2  (2),
    R3  (3),
    R4  (4),
    PhosphorFreeDetergent  (5),
    AlkalineDetergent  (6),        
    Diluent  (7);
    private int value;
	
	private ReaType(int value) {
		this.value = value;
	}
	//获取延伸信息value
	public int getValue() {
		return value;
	}
	public void setValue(int value) {this.value = value;}
	//通过延伸信息value获取Week类的一个枚举实例
	public static ReaType getWeekByValue(Integer value) {
		for(ReaType week : ReaType.values()) {
			if(value.equals(week.getValue())) {
				return week;
			}
		}
		return null;
	}
	
//	private long id;
//	private ReaType(long id) {this.id = id;}
//	public long getId() {return id;}
//	public void setId(long id) {this.id = id;}
//	public String toString() {return String.format("%0#10x", (((long)id) & 0xffffffffL));}
}
