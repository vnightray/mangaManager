package com.github.vnightray.acgnmanager.controller;


import com.github.vnightray.acgnmanager.entity.comic.Series;
import com.github.vnightray.acgnmanager.service.ISeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * series information 前端控制器
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */
@Controller
@RequestMapping("/series")
public class SeriesController {

    @Autowired
    private ISeriesService seriesService;

    @RequestMapping("/findSeries")
    @ResponseBody
    public Series findSeries(){

        return seriesService.getById(1);

    }

}

