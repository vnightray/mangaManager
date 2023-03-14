package com.github.vnightray.acgnmanager.task;

import com.github.vnightray.acgnmanager.entity.comic.BaseEntity;
import org.springframework.jms.core.JmsTemplate;

import java.io.Serializable;

public abstract class BaseTask implements Serializable, IBaseTask {

    private static final long serialVersionUID = 1L;

//    private static String TASK_NAME = "";

    public String getTaskName() {
        return this.getClass().getName();
    }
}
