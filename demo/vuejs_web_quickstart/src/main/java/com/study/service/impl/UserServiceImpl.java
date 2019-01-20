package com.study.service.impl;

import com.study.dao.UserDao;
import com.study.pojo.User;
import com.study.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lj
 * date : 2019/1/18 21:39
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findOne(Integer id) {
        return userDao.findByIdEquals(id);
    }

    @Override
    public void updateUser(User user) {

        userDao.save(user);
    }
}
