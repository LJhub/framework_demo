package com.test;

import com.study.utils.JpaUtil;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * JPQL复杂查询
 * 可移植性的语句,可以被编译成所有主流数据库的SQL语句
 */
public class TestJPQL {

    /**
     * 查询全部 : from Customer
     */
    @Test
    public void testQueryAll(){
        EntityManager em = JpaUtil.getEntityManager();
        String sql = "from Customer";
        Query query = em.createQuery(sql);
        List cs = query.getResultList();
        System.out.println(cs);
    }

    /**
     * 测试分页查询
     */
    @Test
    public void testQueryPage(){
        EntityManager em = JpaUtil.getEntityManager();

        //Query query = em.createQuery("from Customer limit ?,?"); 错误写法
        Query query = em.createQuery("from Customer");
        query.setFirstResult(0);
        query.setMaxResults(2);
        List c = query.getResultList();
        System.out.println(c);
    }

    /**
     * 条件查询
     */
    @Test
    public void testParameterQuery(){
        EntityManager em = JpaUtil.getEntityManager();
        Query query = em.createQuery("from Customer where custId = ?");
        query.setParameter(1,1L);
        Object c = query.getSingleResult();
        System.out.println(c);
    }

    /**
     * 排序
     */
    @Test
    public void testSort(){
        EntityManager em = JpaUtil.getEntityManager();
        Query query = em.createQuery("from Customer order by custId desc");
        List resultList = query.getResultList();
        System.out.println(resultList);
    }

    /**
     * 统计个数
     */
    @Test
    public void testCount(){
        EntityManager em = JpaUtil.getEntityManager();
        Query query = em.createQuery("select count(1) from Customer where id > ?");
        query.setParameter(1, 1L);
        System.out.println(query.getSingleResult());
        em.close();
        JpaUtil.closeFactory();
    }
}
