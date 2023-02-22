package com.github.vnightray.acgnmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vnightray.acgnmanager.entity.comic.Favorite;
import com.github.vnightray.acgnmanager.mapper.FavoriteMapper;
import com.github.vnightray.acgnmanager.service.IFavoriteService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author vnightray
 * @since 2023-02-22
 */
@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements IFavoriteService {

}
