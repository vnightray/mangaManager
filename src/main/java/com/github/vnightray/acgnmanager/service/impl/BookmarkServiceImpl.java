package com.github.vnightray.acgnmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vnightray.acgnmanager.entity.comic.Bookmark;
import com.github.vnightray.acgnmanager.mapper.BookmarkMapper;
import com.github.vnightray.acgnmanager.service.IBookmarkService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * the bookmark of book 服务实现类
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */
@Service
public class BookmarkServiceImpl extends ServiceImpl<BookmarkMapper, Bookmark> implements IBookmarkService {

}
