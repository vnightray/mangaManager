package com.github.vnightray.acgnmanager.controller;


import com.github.vnightray.acgnmanager.entity.comic.Book;
import com.github.vnightray.acgnmanager.entity.comic.Comment;
import com.github.vnightray.acgnmanager.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

/**
 * <p>
 * book entity 前端控制器
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */
@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @RequestMapping("/findBook")
    @ResponseBody
    public Book findBook(){
        for (int i = 0; i < 25; i++) {
            jmsTemplate.convertAndSend("test001", "wahahaha: " + i, message -> {
                message.setStringProperty("queueName", "test001");
                // 设置消息组，用于有序的消费消息
                message.setStringProperty("JMSXGroupID","testGroup001");
                // 用于表示消息消费完成后，关闭该消费组
                message.setIntProperty("JMSXGroupSeq", -1);
                // 用于重新启动消费者后清除一些缓存消息等操作的判断，
                // 它只在一个客户端第一次连接的时候会发送true，后面都不会发送false
                message.setBooleanProperty("JMSXGroupFirstForConsumer",true);
                return message;
            });
        }

        for (int i = 0; i < 25; i++) {
            jmsTemplate.convertAndSend("test002", "wahahaha: " + i, message -> {
                message.setStringProperty("queueName", "test002");
                // 设置消息组，用于有序的消费消息
                message.setStringProperty("JMSXGroupID","testGroup002");
                // 用于表示消息消费完成后，关闭该消费组
                message.setIntProperty("JMSXGroupSeq", -1);
                // 用于重新启动消费者后清除一些缓存消息等操作的判断，
                // 它只在一个客户端第一次连接的时候会发送true，后面都不会发送false
                message.setBooleanProperty("JMSXGroupFirstForConsumer",true);
                return message;
            });
        }

        return bookService.getById(1);
    }

}

