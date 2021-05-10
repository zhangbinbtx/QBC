package com.quaero.qbcTest.Enum;


public class CodeException extends Exception {
	//private static final long serialVersionUID = 3042385399923245867L;
	private long code;
	private String name;
	public CodeException(long code) {
		new CodeException(code, "command");
	}
	public CodeException(long code, String name) {
		this.code = code;
		this.name = name;
	}
	public String toString() {
		return String.format("Unknown %s code: %0#10x", name, code);
	}
}