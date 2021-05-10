package com.quaero.qbcTest.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.quaero.qbcTest.util.Predicate;
import com.quaero.qbcTest.util.Predicates;

public class tEST1 {

	public static void main(String[] args) {
		 List<Number> numbers = new ArrayList<Number>();
	        List<Number> matches;
	 
	        for (int i = new Random(System.currentTimeMillis())
	                .nextInt(10); i < 100; i += 7) {
	            numbers.add(i);
	        }
	 
	        displayNumbers(numbers, "All Numbers");
	 
	        // find all the perfect squares.
	        matches = Predicates.findAll(numbers
	                .toArray(new Number[] {}),
	                new Predicate<Number>() {
	 
	                    @Override
	                    public boolean isMatch(Number n) {
//	                        double sqrt = Math.sqrt(n.doubleValue());
//	                        return Math.ceil(sqrt) == sqrt;
	                    	return n.intValue()>20;
	                    }
	                });
	 
	        displayNumbers(matches, "Perfect Squares");
	 
	        // find all numbers less than 50.
	        matches = Predicates.findAll(numbers,
	                new Predicate<Number>() {
	 
	                    @Override
	                    public boolean isMatch(Number n) {
	                        // mimic .NET delegate method;
	                        // utilize existing method for
	                        // implementation.
	                        return isLessThan50(n);
	                    }
	                });
	 
	        displayNumbers(matches, "Less Than 50");
	    }
	 
	    static boolean isLessThan50(Number n) {
	        return n.intValue() < 50;
	    }
	 
	    static void displayNumbers(List<Number> numbers, String title) {
	        System.out.printf("%s: %s.", title,
	                (numbers.size() > 0) ? Arrays.toString(
	                        numbers.toArray()).replaceAll(
	                        "^\\[|\\]$", "") : "none");
	        System.out.println("");
	    }

	}

