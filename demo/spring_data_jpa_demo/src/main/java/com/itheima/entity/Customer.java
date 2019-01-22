package com.itheima.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author MyPC
 */
@Entity
@Table(name="cst_customer")
public class Customer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cust_id")
	private Long custId;

	@Column(name="cust_name") //指定和表中cust_name字段的映射关系
	private String custName;

	@Column(name="cust_source")//指定和表中cust_source字段的映射关系
	private String custSource;

	@Column(name="cust_industry")//指定和表中cust_industry字段的映射关系
	private String custIndustry;

	@Column(name="cust_level")//指定和表中cust_level字段的映射关系
	private String custLevel;

	@Column(name="cust_address")//指定和表中cust_address字段的映射关系
	private String custAddress;

	@Column(name="cust_phone")//指定和表中cust_phone字段的映射关系
	private String custPhone;

	/**
	 * 客户对应联系人(一对多)
	 */
	//@OneToMany(targetEntity = LinkMan.class)
	//@JoinColumn(name="lkm_cust_id",referencedColumnName = "cust_id")
	@OneToMany(mappedBy = "customer",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
	private Set<LinkMan> linkManList = new HashSet<>(0);


	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustSource() {
		return custSource;
	}
	public void setCustSource(String custSource) {
		this.custSource = custSource;
	}
	public String getCustIndustry() {
		return custIndustry;
	}
	public void setCustIndustry(String custIndustry) {
		this.custIndustry = custIndustry;
	}
	public String getCustLevel() {
		return custLevel;
	}
	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	public Set<LinkMan> getLinkManList() {
		return linkManList;
	}

	public void setLinkManList(Set<LinkMan> linkManList) {
		this.linkManList = linkManList;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"custId=" + custId +
				", custName='" + custName + '\'' +
				", custSource='" + custSource + '\'' +
				", custIndustry='" + custIndustry + '\'' +
				", custLevel='" + custLevel + '\'' +
				", custAddress='" + custAddress + '\'' +
				", custPhone='" + custPhone + '\'' +
				", linkManList=" + linkManList +
				'}';
	}
}

