package com.test;

import com.study.pojo.Customer;
import com.study.pojo.LinkMan;
import com.study.pojo.Role;
import com.study.pojo.User;
import com.study.utils.JpaUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TestJpa {

    private EntityManager em;
    private EntityTransaction tx;
    @Before
    public void testBefore(){
        em = JpaUtil.getEntityManager();
        tx = JpaUtil.getEntityTransaction();
        tx.begin();
    }

    @After
    public void testAfter(){
        tx.commit();
        em.close();
        JpaUtil.closeFactory();
    }

    /**
     * 保存角色
     */
    @Test
    public void testSaveRole() {
        Role r = new Role();
        r.setRoleName("角色1");
        em.persist(r);
    }

    /**
     * 保存用户
     */
    @Test
    public void testSaveUser(){
        User u = new User();
        u.setUserName("用户1");
        em.persist(u);
    }


    /**
     * 新建LinkMan表
     */
    @Test
    public void testMyself(){
        tx.begin();
        LinkMan l = new LinkMan();
        l.setLkmName("TBD联系人");
        l.setLkmGender("male");
        l.setLkmMobile("13811111111");
        l.setLkmPhone("010-34785348");
        l.setLkmEmail("98354834@qq.com");
        l.setLkmPosition("老师");
        l.setLkmMemo("还行吧");
        em.persist(l);
    }

    /**
     * 增
     */
    @Test
    public void testSave() {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = JpaUtil.getEntityTransaction();
        try {
            tx.begin();
            Customer c = new Customer();
            c.setCustName("刘备");
            c.setCustAddress("平原县");
            c.setCustIndustry("蜀国");
            c.setCustLevel("VVVVVIP");
            c.setCustPhone("0755-520520");
            c.setCustSource("披着羊皮的狼");
            em.persist(c);
            tx.commit();
        } catch(Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
            JpaUtil.closeFactory();
        }
    }

    /**
     * 删
     */
    @Test
    public void testDelete(){
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = JpaUtil.getEntityTransaction();
        try {
            tx.begin();
            Customer c = em.find(Customer.class, 2L);
            em.remove(c);
            tx.commit();
        } catch(Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
            JpaUtil.closeFactory();
        }
    }

    /**
     * 改
     */
    @Test
    public void testUpdate(){
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = JpaUtil.getEntityTransaction();
        try {
            tx.begin();
            Customer c = em.find(Customer.class, 2L);
            c.setCustName("无耻刘备");
            em.merge(c);
            tx.commit();
        } catch(Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
            JpaUtil.closeFactory();
        }
    }

    /**
     * 查
     */
    @Test
    public void testQuery(){
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = JpaUtil.getEntityTransaction();
        try {
            em = JpaUtil.getEntityManager();
            tx = JpaUtil.getEntityTransaction();
            tx.begin();
            Customer c = em.find(Customer.class, 3L);
            Customer c2 = em.find(Customer.class, 3L);
            System.out.println(c2);
            System.out.println(c==c2);
            tx.commit();
        } catch(Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            em.close();
            JpaUtil.closeFactory();
        }
    }

    /**
     * 测试懒加载
     */
    @Test
    public void testLazyLoad(){
        EntityManager em = JpaUtil.getEntityManager();
        Customer c = em.getReference(Customer.class, 3L);
        em.close();
        JpaUtil.closeFactory();
        System.out.println(c);
    }


}
