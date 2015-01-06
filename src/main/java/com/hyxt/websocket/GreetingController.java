package com.hyxt.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    @Autowired
    AsynService asynService;

    @Autowired
    SimpMessagingTemplate template;



    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception { //Map<String,String>

        asynService.printLog();

        return new Greeting("Hello, " + message.getName() + "!");
    }



//    @MessageMapping("/hello")
//    public void greeting(HelloMessage message) throws Exception { //Map<String,String>
//
//        asynService.printLog();
//        asynService.sendLog();
//
//    }



}