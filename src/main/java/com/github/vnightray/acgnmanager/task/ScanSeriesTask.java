package com.github.vnightray.acgnmanager.task;

import cn.hutool.core.io.FileUtil;
import com.github.vnightray.acgnmanager.entity.comic.Book;
import com.github.vnightray.acgnmanager.entity.comic.Series;
import com.github.vnightray.acgnmanager.entity.comic.SupportMediaType;
import com.github.vnightray.acgnmanager.util.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ScanSeriesTask extends BaseTask {

    private Series series;

    @Override
    public void runTask(BaseTask baseTask, JmsTemplate jmsTemplate) {
        if (null == baseTask || null == jmsTemplate) {
            return;
        }
        if (baseTask instanceof ScanSeriesTask) {
            createBookTasks(((ScanSeriesTask) baseTask).getSeries(), jmsTemplate);
        }
    }

    private void createBookTasks(Series seriesEntity, JmsTemplate jmsTemplate) {
        if (null == seriesEntity || null == jmsTemplate) {
            return;
        }
        if (null == seriesEntity.getSeriesLocation()) {
            return;
        }
        File seriesContainer = new File(seriesEntity.getSeriesLocation());
        if (!seriesContainer.exists() || seriesContainer.isFile()) {
            return;
        }
        File[] listFiles = seriesContainer.listFiles(File::isFile);
        if (null == listFiles || listFiles.length == 0) {
            return;
        }
        for (File file : listFiles) {
            if (Constants.assertExtension(FileUtil.extName(file))) {
                Book book = new Book();
                book.setBookId(null).setSeriesId(seriesEntity.getSeriesId()).setLibraryId(seriesEntity.getLibraryId())
                        .setIsPrivate(seriesEntity.getIsPrivate())
                        .setGalleryId(0L).setBookName(FileUtil.getPrefix(file)).setLocation(file.getAbsolutePath())
                        .setFileSize(BigDecimal.valueOf(FileUtil.size(file) / (1024.0 * 1024.0)).setScale(4, RoundingMode.HALF_UP).doubleValue())
                        // todo use FileUtils to generate right file extension
//                            .setExtension(Constants.distinguishExtension(file.getAbsolutePath()).toLowerCase())
                        .setExtension(FileUtil.extName(file))
                        .setMediaType(SupportMediaType.valueOf(Constants.distinguishExtension(FileUtil.extName(file))));
                book.setCreateTime(LocalDateTime.now());
                book.setModifiedTime(LocalDateTime.now());
                jmsTemplate.convertAndSend("task", new CreateBookTask().setBook(book)
//                    , message -> {
//                        message.setStringProperty("queueName", "createBook");
//                        // 设置消息组，用于有序的消费消息
//                        message.setStringProperty("JMSXGroupID","createBookGroup");
//                        // 用于表示消息消费完成后，关闭该消费组
//                        message.setIntProperty("JMSXGroupSeq", -1);
//                        // 用于重新启动消费者后清除一些缓存消息等操作的判断，
//                        // 它只在一个客户端第一次连接的时候会发送true，后面都不会发送false
//                        message.setBooleanProperty("JMSXGroupFirstForConsumer",true);
//                        return message;
//                    }
                );
            }
        }

    }

}
