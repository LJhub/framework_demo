package com.itheima.dao;

import com.itheima.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:springdataJPA.xml")
public class SpecQuery {
    @Autowired
    private CustomerDao dao;

    @Test
    public void testAdd(){
        Customer c = new Customer();
        c.setCustName("刘备1");
        c.setCustAddress("平原县1");
        c.setCustIndustry("蜀国1");
        c.setCustLevel("VIP");
        c.setCustPhone("0755-789789");
        c.setCustSource("老实人1");
        dao.save(c);
    }

    @Test
    public void testSpecQuery(){
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.like(root.get("custName").as(String.class), "%备备%");
            }
        };
        System.out.println(dao.findAll(spec));
    }

    /**
     * 分页查询
     * 统计次数
     */
    @Test
    public void testPage(){
        Specification<Customer> spec = (root, query, cb) -> cb.between(root.get("custId").as(Long.class), 0L, 7L);
        Pageable pageRequest = new PageRequest(1, 5);
        Page<Customer> page = dao.findAll(spec, pageRequest);
        System.out.println(page.getContent());
        System.out.println(page.getTotalPages());
        System.out.println(page.getTotalElements());
    }


    @Test
    public void testManyOptions() {
        Specification<Customer> spec = (root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            Predicate p1 = cb.between(root.get("custId").as(Long.class), 3L, 5L);
            list.add(p1);
            Predicate p2 = cb.like(root.get("custName").as(String.class), "%云集%");
            list.add(p2);

            //Predicate[] predicates = new Predicate[]{};
            return cb.and(list.toArray(new Predicate[0]));
        };
        List<Customer> cs = dao.findAll(spec);
        for (Customer c : cs) {
            System.out.println(c);
        }
    }

    /**
     * 规格条件查询
     * 排序,分页,条件 展示数据
     */
    @Test
    public void testSpecPage(){
        Specification<Customer> spec = (root, query, cb) -> cb.like(root.get("custName").as(String.class), "%云集%");
        Sort sort = new Sort(Sort.Direction.DESC, "custId");
        Pageable pageable = new PageRequest(0, 2, sort);
        Page<Customer> page = dao.findAll(spec, pageable);
        for (Customer customer : page.getContent()) {
            System.out.println(customer);
        }
    }
}
