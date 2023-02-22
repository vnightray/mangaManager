package com.github.vnightray.acgnmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vnightray.acgnmanager.entity.comic.Library;
import com.github.vnightray.acgnmanager.mapper.LibraryMapper;
import com.github.vnightray.acgnmanager.service.ILibraryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * the root source of books and series 服务实现类
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */
@Service
public class LibraryServiceImpl extends ServiceImpl<LibraryMapper, Library> implements ILibraryService {

}
