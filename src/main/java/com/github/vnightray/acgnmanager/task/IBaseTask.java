package com.github.vnightray.acgnmanager.task;

import com.github.vnightray.acgnmanager.entity.comic.BaseEntity;
import org.springframework.jms.core.JmsTemplate;

public interface IBaseTask {
    void runTask(BaseTask baseTask, JmsTemplate jmsTemplate);
}
