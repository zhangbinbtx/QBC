package com.quaero.qbcTest.util;

public interface Predicate<T> {
	    
	    /**
	     * @param obj - object to test against predicate criteria.
	     * @return boolean flag indicating a match (true) or no match (false).
	     */
	    public boolean isMatch(T obj);
	}