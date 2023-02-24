package com.github.vnightray.acgnmanager.jms;

import com.github.vnightray.acgnmanager.entity.comic.Book;
import com.github.vnightray.acgnmanager.entity.comic.Library;
import com.github.vnightray.acgnmanager.entity.comic.Series;
import com.github.vnightray.acgnmanager.service.IBookService;
import com.github.vnightray.acgnmanager.service.ILibraryService;
import com.github.vnightray.acgnmanager.service.ISeriesService;
import com.github.vnightray.acgnmanager.task.BaseTask;
import com.github.vnightray.acgnmanager.task.CreateBookTask;
import com.github.vnightray.acgnmanager.task.ScanLibraryTask;
import com.github.vnightray.acgnmanager.task.ScanSeriesTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class ArtemisConsumer {

    @Autowired
    private ScanLibraryTask scanLibraryTask;

    @Autowired
    private ScanSeriesTask scanSeriesTask;

    @Autowired
    private CreateBookTask createBookTask;

    @Autowired
    private ILibraryService libraryService;

    @Autowired
    private ISeriesService seriesService;

    @Autowired
    private IBookService bookService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = "test001", containerFactory = "queueJmsListenerContainerFactory", concurrency = "2-5")
    public void receiveMessage(String message, @Header(value = "queueName") String queueName){
        log.info("1...queueName" + queueName + "message: " + message);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @JmsListener(destination = "test002", containerFactory = "queueJmsListenerContainerFactory", concurrency = "2-5")
    public void receiveMessage2(String message, @Header(value = "queueName") String queueName){
        log.info("2...queueName" + queueName + "message: " + message);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @JmsListener(destination = "task", containerFactory = "queueJmsListenerContainerFactory", concurrency = "2-5")
    public void receiveTask(BaseTask task, @Header(value = "queueName") String queueName){
        log.info("queueName: " + queueName);
        log.info("task type: " + task.getTaskName());
        if (task instanceof ScanLibraryTask){
            log.info("task content: " + task);
            if (null == ((ScanLibraryTask) task).getLibrary()){
                return;
            }
            Library tempLibrary = ((ScanLibraryTask) task).getLibrary();
            if (null == tempLibrary.getSourceLocation()){
                return;
            }
            libraryService.save(tempLibrary);
            log.info("saved library: " + tempLibrary);
            ((ScanLibraryTask) task).setLibrary(tempLibrary);
            scanLibraryTask.runTask(task, jmsTemplate);
        }
        else if (task instanceof ScanSeriesTask) {
            log.info("task content: " + task);
            Series tempSeries = ((ScanSeriesTask) task).getSeries();
            if (null == tempSeries){
                return;
            }
            seriesService.save(tempSeries);
            log.info("saved series: " + tempSeries);
            ((ScanSeriesTask) task).setSeries(tempSeries);
            scanSeriesTask.runTask(task, jmsTemplate);
        }
        else if (task instanceof CreateBookTask) {
            log.info("task content: " + task);
            Book tempBook = ((CreateBookTask) task).getBook();
            if (null == tempBook){
                return;
            }
            bookService.save(tempBook);
            log.info("saved book: " + tempBook);
//            ((CreateBookTask) task).setBook(tempBook);
//            createBookTask.runTask(task, jmsTemplate);
        } else {
            log.info("convert failed");
        }
    }

    @Transactional
    @JmsListener(destination = "book", containerFactory = "queueJmsListenerContainerFactory")
    public void receiveBook(Book book, @Header(value = "queueName") String queueName){
        log.info("queueName: " + queueName);
        log.info("generate book successfully, book content: " + book);
//        bookService.save(book);
    }

}
