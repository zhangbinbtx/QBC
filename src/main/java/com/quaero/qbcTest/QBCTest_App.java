package com.quaero.qbcTest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

import com.quaero.qbcTest.server.QbcTestApi;
import com.quaero.qbcTest.server.impl.QbcTestApiImpl;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan(basePackages = "com.quaero.qbcTest.dto.dao.*")
//@PropertySource(value = {"classpath:application-test.yml"}, encoding = "utf-8")
public class QBCTest_App {
	 public static void main(String[] args){
		SpringApplication.run(QBCTest_App.class,args);
//	        Best student= (Best) applicationContext.getBean(Best.class);
//	        System.out.println("message:"+student.getMachineIP());
	    }
	
}
