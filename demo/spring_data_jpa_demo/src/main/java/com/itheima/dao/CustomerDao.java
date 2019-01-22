package com.itheima.dao;

import com.itheima.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.Size;
import java.util.List;


public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    @Query(value = "from Customer where custName = ?1")
    public Customer findOneByName(String custName);

    public Customer findByCustName(String custName);

    @Query("from Customer c where custName like ?1")
    public Page<Customer> queryPage(String custName, Pageable pageable);

    @Query(nativeQuery = true,value = "select * from cst_customer where cust_id = ?")
    public Customer findOneByCustId(Long cust_id);

    public List<Customer> findAllByCustNameLikeAndCustLevelEquals(String custName,String custLevel);

    public List<Customer> findAllByCustIdGreaterThanEqual(Long custId);

    public List<Customer> findAllByCustNameEquals(String custName);
}
