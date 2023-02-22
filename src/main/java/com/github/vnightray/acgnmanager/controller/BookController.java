package com.github.vnightray.acgnmanager.controller;


import com.github.vnightray.acgnmanager.entity.comic.Book;
import com.github.vnightray.acgnmanager.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * book entity 前端控制器
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */
@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @RequestMapping("/findBook")
    @ResponseBody
    public Book findBook(){
        jmsTemplate.convertAndSend("test001", "wahahahaha");
        return bookService.getById(1);
    }

}

