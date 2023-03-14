package com.github.vnightray.acgnmanager.task;

import cn.hutool.core.io.FileUtil;
import com.github.vnightray.acgnmanager.entity.comic.BaseEntity;
import com.github.vnightray.acgnmanager.entity.comic.Library;
import com.github.vnightray.acgnmanager.entity.comic.Series;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Component
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ScanLibraryTask extends BaseTask{

    private Library library;

    @Override
    public void runTask(BaseTask baseTask, JmsTemplate jmsTemplate) {
        if (null == baseTask || null == jmsTemplate) {
            return;
        }
        if (baseTask instanceof ScanLibraryTask){
            scanLibrary((ScanLibraryTask) baseTask, jmsTemplate);
        }
    }

    public void scanLibrary(ScanLibraryTask scanLibraryTask, JmsTemplate jmsTemplate){
        if (null == scanLibraryTask || null == jmsTemplate){
            return;
        }
        if (null == scanLibraryTask.getLibrary()){
            return;
        }
        Library root = scanLibraryTask.getLibrary();
        createSeriesTasks(root, new File(root.getSourceLocation()), jmsTemplate);
    }

    private void createSeriesTasks(Library library, File seriesLocation, JmsTemplate jmsTemplate){
        if (null == library || null == seriesLocation || null == jmsTemplate){
            return;
        }
        if (!seriesLocation.exists() || seriesLocation.isFile()){
            return;
        }
        File[] listFiles = seriesLocation.listFiles();
        if (null == listFiles || listFiles.length == 0){
            return;
        }

        if (Arrays.stream(listFiles).anyMatch(File::isFile)){
            Series rootSeries = new Series();
            rootSeries.setSeriesId(null).setLibraryId(rootSeries.getLibraryId()).setSeriesLocation(seriesLocation.getAbsolutePath())
//                    .setSeriesName(FileUtil.getName(seriesLocation.getAbsolutePath().substring(seriesLocation.getAbsolutePath().lastIndexOf(File.separator) + 1)))
                    .setSeriesName(FileUtil.getName(seriesLocation.getAbsolutePath()))
                    .setIsPrivate(library.getIsPrivate());
            rootSeries.setCreateTime(LocalDateTime.now());
            rootSeries.setModifiedTime(LocalDateTime.now());
            jmsTemplate.convertAndSend("task",
                    new ScanSeriesTask().setSeries(rootSeries)
//                    , message -> {
//                        message.setStringProperty("queueName", "scanSeries");
//                        // 设置消息组，用于有序的消费消息
//                        message.setStringProperty("JMSXGroupID","scanSeriesGroup");
//                        // 用于表示消息消费完成后，关闭该消费组
//                        message.setIntProperty("JMSXGroupSeq", -1);
//                        // 用于重新启动消费者后清除一些缓存消息等操作的判断，
//                        // 它只在一个客户端第一次连接的时候会发送true，后面都不会发送false
//                        message.setBooleanProperty("JMSXGroupFirstForConsumer",true);
//                        return message;
//                    }
                    );
        }
        Arrays.stream(listFiles).filter(File::isDirectory).forEach(dir -> createSeriesTasks(library, dir, jmsTemplate));
    }
}
