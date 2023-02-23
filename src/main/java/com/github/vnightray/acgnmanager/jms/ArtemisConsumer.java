package com.github.vnightray.acgnmanager.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@Slf4j
public class ArtemisConsumer {

    @JmsListener(destination = "test001", containerFactory = "queueJmsListenerContainerFactory", concurrency = "2-5")
    public void receiveMessage(String message, @Header(value = "queueName") String queueName){
        log.info("1...queueName" + queueName + "message: " + message);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @JmsListener(destination = "test002", containerFactory = "queueJmsListenerContainerFactory", concurrency = "2-5")
    public void receiveMessage2(String message, @Header(value = "queueName") String queueName){
        log.info("2...queueName" + queueName + "message: " + message);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
