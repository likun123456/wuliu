package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 角色
 * @author 奇趣源码
 * @create 2017-11-17
 */
@Entity(name ="JC_SYS_ROLE")
public class Role extends BaseEntity implements Serializable{
    private List<User> users;
    private List<Menu> menus;
    private List<Button> buttons;


    private final Logger log = Logger.getLogger(Role.class);//输出Log日志




    @OrderBy("orderBy")
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "JC_SYS_ROLE_BUTTON", joinColumns = @JoinColumn(name = "JC_SYS_ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "JC_SYS_BUTTON_ID"))
    public List<Button> getButtons() {
        return buttons;
    }
    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    @JSONField(serialize = false)
    @ManyToMany(mappedBy = "roles",cascade = {CascadeType.MERGE})
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @OrderBy(value = "orderBy")
    @JSONField(serialize = false)
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "JC_SYS_ROLE_MENU", joinColumns = @JoinColumn(name = "JC_SYS_ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "JC_SYS_MENU_ID"))
    public List<Menu> getMenus() {
        return menus;
    }
    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Transient
    public List<String> getAuthorityUrl() {
        List<String> list = new ArrayList<String>();
        List<Menu> authorities = this.getMenus();
        for (Menu auth : authorities) {
            if (auth.getUrl().substring(0,1).equals("/")){
                list.add(auth.getUrl());
            }else{
                auth.setUrl("/"+auth.getUrl());
            }
        }
        return list;
    }
}
