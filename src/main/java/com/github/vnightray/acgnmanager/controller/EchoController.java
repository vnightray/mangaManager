package com.github.vnightray.acgnmanager.controller;

import cn.hutool.core.io.FileUtil;
import com.github.vnightray.acgnmanager.entity.comic.Book;
import com.github.vnightray.acgnmanager.entity.comic.Library;
import com.github.vnightray.acgnmanager.entity.comic.SupportMediaType;
import com.github.vnightray.acgnmanager.task.CreateBookTask;
import com.github.vnightray.acgnmanager.task.ScanLibraryTask;
import com.github.vnightray.acgnmanager.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/echo")
public class EchoController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @RequestMapping("/echo")
    public String echo() {
        return "ECHO";
    }

    @RequestMapping("/test1")
    public String test1() {

        //        AtomicInteger counter = new AtomicInteger(1);
        final Random random = new Random();
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
//                message.setJMSPriority(counter.getAndIncrement() % 9 + 1);
                message.setJMSPriority(random.nextInt(9) % 9 + 1);
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
//                message.setJMSPriority(counter.getAndIncrement() % 9 + 1);
                message.setJMSPriority(random.nextInt(9) % 9 + 1);
                return message;
            });
        }
        return "success";
    }

    @RequestMapping("/test2")
    public String test2() {
        jmsTemplate.convertAndSend("book", new Book().setBookId(null)
                        .setBookName("java cooker").setBookProfile("a book about how to use java").setIsPrivate(true)
                        .setLibraryId(1L).setSeriesId(1L)
                        .setLocation("D:\\cache\\acgnmanager").setExtension("zip").setMediaType(SupportMediaType.ZIP),
                message -> {
                    message.setStringProperty("queueName", "generateBook");
                    // 设置消息组，用于有序的消费消息
                    message.setStringProperty("JMSXGroupID","bookGroup");
                    // 用于表示消息消费完成后，关闭该消费组
                    message.setIntProperty("JMSXGroupSeq", -1);
                    // 用于重新启动消费者后清除一些缓存消息等操作的判断，
                    // 它只在一个客户端第一次连接的时候会发送true，后面都不会发送false
                    message.setBooleanProperty("JMSXGroupFirstForConsumer",true);
                    return message;
                }
        );
        return "success";
    }

    @RequestMapping("/test3")
    public String test3() {
        Book book = new Book();
        book.setBookId(null).setSeriesId(1L).setLibraryId(1L).setIsPrivate(false)
                .setGalleryId(0L).setBookName("hualalade").setLocation("D:\\cache\\acgnmanager\\hualalade.zip")
                .setFileSize(99.99)
                .setExtension("zip")
                .setMediaType(SupportMediaType.ZIP);
        book.setCreateTime(LocalDateTime.now());
        book.setModifiedTime(LocalDateTime.now());
        jmsTemplate.convertAndSend("task", new CreateBookTask().setBook(book), message -> {
            message.setStringProperty("queueName", "taskQueue");
            // 设置消息组，用于有序的消费消息
            message.setStringProperty("JMSXGroupID","taskGroup");
            // 用于表示消息消费完成后，关闭该消费组
            message.setIntProperty("JMSXGroupSeq", -1);
            // 用于重新启动消费者后清除一些缓存消息等操作的判断，
            // 它只在一个客户端第一次连接的时候会发送true，后面都不会发送false
            message.setBooleanProperty("JMSXGroupFirstForConsumer",true);
            return message;
        });
        return "success";
    }

    @RequestMapping("/test4")
    public String test4() {
        String libraryLocation = "D:\\cache\\acgnmanager\\todelete";
        jmsTemplate.convertAndSend("task",
                new ScanLibraryTask().setLibrary(new Library().setSourceLocation(libraryLocation).setIsPrivate(false)),
                message -> {
            message.setStringProperty("queueName", "scanLibrary");
            // 设置消息组，用于有序的消费消息
            message.setStringProperty("JMSXGroupID","scanLibraryGroup");
            // 用于表示消息消费完成后，关闭该消费组
            message.setIntProperty("JMSXGroupSeq", -1);
            // 用于重新启动消费者后清除一些缓存消息等操作的判断，
            // 它只在一个客户端第一次连接的时候会发送true，后面都不会发送false
            message.setBooleanProperty("JMSXGroupFirstForConsumer",true);
            return message;
        });
        return "success";
    }

    @RequestMapping("/test5")
    public String test5() {
        String libraryLocation = "D:\\cache\\delete\\delete";
        Library library = new Library()
                .setSourceLocation(libraryLocation).setIsPrivate(false);
        library.setCreateTime(LocalDateTime.now());
        library.setModifiedTime(LocalDateTime.now());
        jmsTemplate.convertAndSend("task", new ScanLibraryTask().setLibrary(library)
//                , message -> {
//                    message.setStringProperty("queueName", "scanLibrary");
//                    // 设置消息组，用于有序的消费消息
//                    message.setStringProperty("JMSXGroupID","scanLibraryGroup");
//                    // 用于表示消息消费完成后，关闭该消费组
//                    message.setIntProperty("JMSXGroupSeq", -1);
//                    // 用于重新启动消费者后清除一些缓存消息等操作的判断，
//                    // 它只在一个客户端第一次连接的时候会发送true，后面都不会发送false
//                    message.setBooleanProperty("JMSXGroupFirstForConsumer",true);
//                    return message;
//                }
                );
        return "success";
    }

}
