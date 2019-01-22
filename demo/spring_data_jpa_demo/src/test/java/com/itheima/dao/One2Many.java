package com.itheima.dao;

import com.itheima.entity.Customer;
import com.itheima.entity.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:springdataJPA.xml")
public class One2Many {
    @Autowired
    private LinkManDao linkManDao;
    @Autowired
    private CustomerDao customerDao;

    @Test
    @Transactional
    @Commit
    public void testSave(){
        Customer c = new Customer();
        c.setCustName("TBD云集中心");
        c.setCustLevel("VIP客户");
        c.setCustSource("网络");
        c.setCustIndustry("商业办公");
        c.setCustAddress("昌平区北七家镇");
        c.setCustPhone("010-84389340");

        LinkMan l = new LinkMan();
        l.setLkmName("TBD联系人");
        l.setLkmGender("男");
        l.setLkmMobile("13811111111");
        l.setLkmPhone("010-34785348");
        l.setLkmEmail("98354834@qq.com");
        l.setLkmPosition("老师");
        l.setLkmMemo("还行吧");

        c.getLinkManList().add(l);
        l.setCustomer(c);
        //l.setCustomer(customerDao.findOne(2L));
        customerDao.save(c);
        //linkManDao.save(l);
    }

    @Test
    @Transactional
    @Commit
    public void testCasCadeDelete(){
        customerDao.delete(100L);
    }

    @Test
    @Transactional
    public void testQuery(){
        Set<LinkMan> linkManList = customerDao.findOne(2L).getLinkManList();
        System.out.println(linkManList);
    }

    @Test
    @Transactional
    public void testLazyLoad(){
        Customer c = customerDao.findOne(2L);
        System.out.println(c.getCustName());
        System.out.println("------------------------");
        System.out.println(c.getLinkManList());
    }

    /**
     * 【需求】：使用客户名称，查询联系人信息，返回联系人数据列表。
     * 使用规格查询,可以使用内外连接,Join<A,B> ,相当于把 A,B表连起来,可以使用join.get("两张表中的任意属性")
     * 来进行条件组装
     */
    @Test
    public void testSpecQuery1(){
        Specification<LinkMan> spec = new Specification<LinkMan>() {
            @Override
            public Predicate toPredicate(Root<LinkMan> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Join<LinkMan, Customer> join = root.join("customer", JoinType.INNER);
                return cb.equal(join.get("custName").as(String.class), "TBD云集中心2");
            }
        };
        System.out.println(linkManDao.findOne(spec).getLkmPosition());
    }


    @Test
    public void testQueryValue(){
        List<Customer> list = customerDao.findAllByCustNameEquals("TBD云集中心");
        for (Customer customer : list) {
            Iterator<LinkMan> it = customer.getLinkManList().iterator();
            while (it.hasNext()){
                System.out.println(it.next().getLkmPosition());
            }
        }
    }
}
