package com.quaero.qbcTest.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class webContoller {
	@Autowired
    private SimpMessagingTemplate template;
	private int i=0;
	   //群发
    @MessageMapping("/test")
    //SendTo 发送至 Broker 下的指定订阅路径
    @SendTo("/user/wq/task/success")
    public String mass(){
    	System.out.println("次数="+i++);
        return "测试成功！";
    }

}
