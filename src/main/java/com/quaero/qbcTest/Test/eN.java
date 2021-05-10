package com.quaero.qbcTest.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
@PropertySource(value = {"classpath:/config/application-test.yml"}, encoding = "utf-8")
public class eN {

	 @Autowired 
	   private Environment environment;
		 public  Environment getEn(){
			return this.environment;
		 }
}
