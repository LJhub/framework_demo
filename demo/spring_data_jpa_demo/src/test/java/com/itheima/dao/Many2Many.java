package com.itheima.dao;

import com.itheima.entity.Role;
import com.itheima.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springdataJPA.xml")
public class Many2Many {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    @Test
    @Transactional
    @Rollback(false)
    public void testSaveMany2Many(){
        User u = new User();
        u.setUserName("用户3");
        User u1 = new User();
        u1.setUserName("用户4");

        Role r = new Role();
        r.setRoleName("角色7");

        Role r2 = new Role();
        r2.setRoleName("角色8");

        Role r3 = new Role();
        r3.setRoleName("角色9");

        u.getRoles().add(r);
        u.getRoles().add(r2);

        u1.getRoles().add(r2);
        u1.getRoles().add(r3);

        //r.getUsers().add(u);
        //r2.getUsers().add(u);
        //r2.getUsers().add(u1);
        //r3.getUsers().add(u1);

        userDao.save(u);
        userDao.save(u1);
        //roleDao.save(r);
        //roleDao.save(r2);
        //roleDao.save(r3);
    }


    @Test
    @Transactional
    @Rollback(false)
    public void testDelete(){
        userDao.delete(1L);
    }

    @Test
    @Transactional
    public void testQueryOne(){
        User u = userDao.findOne(3L);
        Set<Role> roles = u.getRoles();
        Iterator<Role> it = roles.iterator();
        while (it.hasNext()){
            System.out.println(it.next().getRoleName());
        }
    }

    @Test
    @Transactional
    public void testQuery(){
        User user = userDao.findByUserNameEquals("用户1");
        Iterator<Role> it = user.getRoles().iterator();
        while (it.hasNext()){
            System.out.println(it.next().getRoleName());
        }
    }
}
