package com.github.vnightray.acgnmanager.task;

import com.github.vnightray.acgnmanager.entity.comic.Book;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class GenerateThumbnailTask extends BaseTask{

    private static final long serialVersionUID = 1L;

    private Book book;

    @Override
    public void runTask(BaseTask baseTask, JmsTemplate jmsTemplate) {

    }
}
