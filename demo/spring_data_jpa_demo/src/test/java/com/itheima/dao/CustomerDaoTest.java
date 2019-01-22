package com.itheima.dao;

import com.itheima.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springdataJPA.xml")
public class CustomerDaoTest {
    @Autowired
    private CustomerDao dao;

    @Test
    public void testSave(){
        Customer c = new Customer();
        c.setCustName("曹操");
        c.setCustAddress("许昌");
        c.setCustIndustry("魏国");
        c.setCustLevel("VIP");
        c.setCustPhone("0755-789789");
        c.setCustSource("枭雄");
        dao.save(c);
    }

    @Test
    public void testDelete() {
        dao.delete(1L);
    }

    @Test
    public void testUpdate(){
        Customer c = dao.findOne(2L);
        c.setCustName("小备备");
        dao.save(c);
    }

    @Test
    public void testQuery(){
        System.out.println(dao.findAll());
    }

    @Test
    public void testFindOne(){
        System.out.println(dao.findOneByName("小备备"));
    }

    @Test
    public void testRegular(){
        System.out.println(dao.findByCustName("小备备"));
    }

    @Test
    public void testCount() {
        Specification<Customer> spec = (root, query, cb) -> cb.like(root.get("custLevel").as(String.class),"%客户%");
        System.out.println(dao.count(spec));
    }
    
    @Test
    public void testNativeSql(){
        System.out.println(dao.findOneByCustId(2L));
    }

    @Test
    public void testPage(){
        Pageable pageable = new PageRequest(0, 2);
        System.out.println(dao.queryPage("%云集%", pageable).getTotalPages());
    }

    /**
     * 排序
     */
    @Test
    public void testQuerySort() {
        Sort sort = new Sort(Sort.Direction.DESC,"custId");
        List<Customer> all = dao.findAll(sort);
        for (Customer customer : all) {
            System.out.println(customer);
        }
    }

    /**
     * 第一大杀手锏
     */
    @Test
    public void testKiller(){
        System.out.println(dao.findAllByCustNameLikeAndCustLevelEquals("%云集%", "哈哈"));
    }

    @Test
    public void testKiller1(){
        System.out.println(dao.findAllByCustIdGreaterThanEqual(2L));
    }
}