package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 按钮类
 * @author 奇趣源码
 * @create 2017-11-20
 */
@Entity(name = "JC_SYS_BUTTON")
public class Button extends BaseEntity implements Serializable {
    private String url;
    private int orderBy;
    private Menu menu;
    private List<Role> roles;


    private Logger log = Logger.getLogger(Button.class);//输出Log日志

    @JSONField(serialize=false)
    @ManyToMany(mappedBy = "buttons")
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Column(name = "URL", length = 50)
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "ORDER_BY", length = 5)
    public int getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "JC_SYS_ROLE_MENU_ID")
    public Menu getMenu() {
        return menu;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
