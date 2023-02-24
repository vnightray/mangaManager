package com.github.vnightray.acgnmanager.task;

import com.github.vnightray.acgnmanager.entity.comic.BaseEntity;
import org.springframework.jms.core.JmsTemplate;

import java.io.Serializable;

public class BaseTask implements Serializable, IBaseTask {

    private static final long serialVersionUID = 1L;

    private static String TASK_NAME = "";

    public String getTaskName() {
        if ("".equals(TASK_NAME)){
            TASK_NAME = this.getClass().getName();
        }
        return TASK_NAME;
    }

    @Override
    public void runTask(BaseTask baseTask, JmsTemplate jmsTemplate) {
    }
}
