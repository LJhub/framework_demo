package com.study.service;

import com.study.pojo.User;

import java.util.List;

/**
 * @author lj
 * date : 2019/1/18 21:36
 */
public interface UserService {
    List<User> findAll();
    User findOne(Integer id);
    void updateUser(User user);
}
