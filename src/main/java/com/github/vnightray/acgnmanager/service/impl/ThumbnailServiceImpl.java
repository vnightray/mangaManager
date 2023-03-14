package com.github.vnightray.acgnmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vnightray.acgnmanager.entity.comic.Thumbnail;
import com.github.vnightray.acgnmanager.mapper.ThumbnailMapper;
import com.github.vnightray.acgnmanager.service.IThumbnailService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * thumbnail information 服务实现类
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */

@Service
public class ThumbnailServiceImpl extends ServiceImpl<ThumbnailMapper, Thumbnail> implements IThumbnailService {
}
