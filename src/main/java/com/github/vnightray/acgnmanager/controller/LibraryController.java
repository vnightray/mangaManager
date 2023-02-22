package com.github.vnightray.acgnmanager.controller;


import com.github.vnightray.acgnmanager.entity.comic.Library;
import com.github.vnightray.acgnmanager.service.ILibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * the root source of books and series 前端控制器
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */
@Controller
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private ILibraryService libraryService;

    @RequestMapping("/findLibrary")
    @ResponseBody
    public Library findLibrary(){
        return libraryService.getById(1);
    }

}

