package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**辽宁捷畅物流有限公司  集团信息部
 * 孙德增
 * Created by sundezeng on 2017/12/8.
 * 注册公司
 */
@Entity(name = "JC_REGISTRATION")
public class JcRegistration extends BaseEntity implements Serializable {
    private String taxcode;//税号
    private String address;//地址
    private String legalperson;//法人
    private List<Customer> customers; //合同档案

    private Logger log = Logger.getLogger(JcRegistration.class);//输出Log日志



    @Column(name = "TAXCODE", length = 50)
    public String getTaxcode() {
        return taxcode;
    }
    public void setTaxcode(String taxcode) {
        this.taxcode = taxcode;
    }

    @Column(name = "ADDRESS", length = 150)
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "LEGALPERSON", length = 50)
    public String getLegalperson() {
        return legalperson;
    }
    public void setLegalperson(String legalperson) {
        this.legalperson = legalperson;
    }

    @JSONField(serialize=false)
    @OneToMany(cascade = { CascadeType.REFRESH},mappedBy ="jcRegistration")
    public List<Customer> getCustomers() {
        return customers;
    }
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

}
