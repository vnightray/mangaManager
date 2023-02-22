package com.github.vnightray.acgnmanager.controller;


import com.github.vnightray.acgnmanager.entity.comic.Book;
import com.github.vnightray.acgnmanager.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

    @RequestMapping("/findBook")
    @ResponseBody
    public Book findBook(){
        return bookService.getById(1);
    }

}

