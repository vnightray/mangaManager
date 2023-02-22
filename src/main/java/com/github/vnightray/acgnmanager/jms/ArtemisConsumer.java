package com.github.vnightray.acgnmanager.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class ArtemisConsumer {

    @JmsListener(destination = "test001", concurrency = "2")
    public void receiveMessage(String message){
        System.out.println("message: " + message);
    }

}
