package com.study.dao;

import com.study.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author lj
 * date : 2019/1/18 21:32
 */
public interface UserDao extends JpaRepository<User,Integer> {
    public User findByIdEquals(Integer id);
}
