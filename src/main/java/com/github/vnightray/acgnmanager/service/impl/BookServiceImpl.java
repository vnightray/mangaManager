package com.github.vnightray.acgnmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vnightray.acgnmanager.entity.comic.Book;
import com.github.vnightray.acgnmanager.mapper.BookMapper;
import com.github.vnightray.acgnmanager.service.IBookService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * book entity 服务实现类
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

}
