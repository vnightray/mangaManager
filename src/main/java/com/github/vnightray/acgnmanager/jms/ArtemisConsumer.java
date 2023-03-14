package com.github.vnightray.acgnmanager.jms;

import com.github.vnightray.acgnmanager.entity.comic.*;
import com.github.vnightray.acgnmanager.service.IBookService;
import com.github.vnightray.acgnmanager.service.ILibraryService;
import com.github.vnightray.acgnmanager.service.ISeriesService;
import com.github.vnightray.acgnmanager.service.IThumbnailService;
import com.github.vnightray.acgnmanager.task.*;
import com.github.vnightray.acgnmanager.util.DecompressTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;


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
    private GenerateThumbnailTask generateThumbnailTask;

    @Autowired
    private ILibraryService libraryService;

    @Autowired
    private ISeriesService seriesService;

    @Autowired
    private IBookService bookService;

    @Autowired
    private IThumbnailService thumbnailService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional(rollbackFor = {Exception.class})
    @JmsListener(destination = "test001", containerFactory = "queueJmsListenerContainerFactory", concurrency = "2-5")
    public void receiveMessage(String message, @Header(value = "queueName") String queueName){
        log.info("1...queueName" + queueName + "message: " + message);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @JmsListener(destination = "test002", containerFactory = "queueJmsListenerContainerFactory", concurrency = "2-5")
    public void receiveMessage2(String message, @Header(value = "queueName") String queueName){
        log.info("2...queueName" + queueName + "message: " + message);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @JmsListener(destination = "task", containerFactory = "queueJmsListenerContainerFactory", concurrency = "2-8")
    public void receiveTask(BaseTask task){
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
            ((CreateBookTask) task).setBook(tempBook);
            createBookTask.runTask(task, jmsTemplate);
        } else {
            log.info("convert failed");
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @JmsListener(destination = "generateThumbnail", containerFactory = "queueJmsListenerContainerFactory", concurrency = "2-8")
    public void generateThumbnail(Book book){
        assert null != book.getLocation();
        try {
            if (SupportMediaType.ZIP.equals(book.getMediaType()) || SupportMediaType.RAR.equals(book.getMediaType())) {
                List<String> contentNameList = DecompressTool.getCompressedFileContentNameList(book.getLocation(), "");
                if (!contentNameList.isEmpty()){
                    book.setPageCount(contentNameList.size());
                    Thumbnail thumbnail = new Thumbnail();
                    thumbnail.setBookId(book.getBookId());
                    for (String contentName : contentNameList) {
                        PageContent pageContent = DecompressTool.getEncryptionFileContentByName(book.getLocation(), contentName, "");
                        List<Byte> content = pageContent.getContent();
                        if (null != content && !content.isEmpty()) {
                            byte[] bytes = new byte[content.size()];
                            for (int i = 0; i < bytes.length; i++) {
                                bytes[i] = content.get(i);
                            }
                            if (null != pageContent.getExtension()) {
                                thumbnail.setExtension(pageContent.getExtension());
                            }
                            thumbnail.setThumbnail(Base64.getEncoder().encodeToString(bytes));
                            break;
                        }
                    }
                    thumbnailService.save(thumbnail);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @JmsListener(destination = "book", containerFactory = "queueJmsListenerContainerFactory", concurrency = "2-8")
    public void receiveBook(Book book, @Header(value = "queueName") String queueName){
        log.info("queueName: " + queueName);
        log.info("generate book successfully, book content: " + book);

//        bookService.save(book);
    }

}
