package com.github.vnightray.acgnmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.vnightray.acgnmanager.entity.comic.User;
import com.github.vnightray.acgnmanager.mapper.UserMapper;
import com.github.vnightray.acgnmanager.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
