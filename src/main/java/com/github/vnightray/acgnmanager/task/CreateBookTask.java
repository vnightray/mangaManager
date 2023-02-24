package com.github.vnightray.acgnmanager.task;

import com.github.vnightray.acgnmanager.entity.comic.Book;
import com.github.vnightray.acgnmanager.util.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CreateBookTask extends BaseTask{

    private static final long serialVersionUID = 1L;

    private Book book;

    @Override
    public void runTask(BaseTask baseTask, JmsTemplate jmsTemplate) {
        if (null == baseTask){
            return;
        }
        if (baseTask instanceof CreateBookTask){
            generateBook((CreateBookTask) baseTask, jmsTemplate);
        }
    }

    private void generateBook(CreateBookTask createBookTask, JmsTemplate jmsTemplate) {
        if (null == createBookTask || null == jmsTemplate){
            return;
        }
        if (null == createBookTask.getBook()){
            return;
        }
        Book bookEntity = createBookTask.getBook();
        if (!Constants.assertExtension(bookEntity.getExtension())){
            return;
        }
        try {
            jmsTemplate.convertAndSend("book",
                    bookEntity,
                    message -> {
                        message.setStringProperty("queueName", "generateBook");
                        // 设置消息组，用于有序的消费消息
                        message.setStringProperty("JMSXGroupID","generateBookGroup");
                        // 用于表示消息消费完成后，关闭该消费组
                        message.setIntProperty("JMSXGroupSeq", -1);
                        // 用于重新启动消费者后清除一些缓存消息等操作的判断，
                        // 它只在一个客户端第一次连接的时候会发送true，后面都不会发送false
                        message.setBooleanProperty("JMSXGroupFirstForConsumer",true);
                        return message;
                    }
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
