package com.hyxt.websocket;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created by serv on 2015/1/6.
 */
@Component
public class AsynService {

    private Logger log = LoggerFactory.getLogger(AsynService.class);

    @Autowired
    SimpMessagingTemplate template;

    @Async
    public void printLog() throws InterruptedException {


        while (true){
            Thread.sleep(1000);
            log.info("log : "+UUID.randomUUID().toString());
            template.convertAndSend("/topic/greetings",new Greeting("Hello, " + UUID.randomUUID().toString() + "!"));
        }
    }
    @Async
    public void sendLog(){
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusManager sm = context.getStatusManager();

        while (true){

            List<Status> statusList = sm.getCopyOfStatusList();
            for (Status status : statusList) {
                long timestamp = status.getDate();
                if (System.currentTimeMillis() - timestamp < 300) {
                    print(status);
                }
            }
            sm.clear();
        }
    }


    private void print(Status status) {
        StringBuilder sb = new StringBuilder();
        StatusPrinter.buildStr(sb, "", status);
        template.convertAndSend("/topic/greetings",new Greeting(sb.toString()));
    }



}
