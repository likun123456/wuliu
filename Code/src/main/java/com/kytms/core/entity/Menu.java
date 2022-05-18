package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 系统菜单
 * @author 奇趣源码
 * @create 2017-11-19
 */
@Entity(name = "JC_SYS_MENU")
public class Menu extends BaseEntity implements Serializable {
    private String img;
    private String url;
    private String target;
    private Menu menu;
    private List<Menu> menus;
    private int orderBy;
    private List<Role> roles;
    private List<Button> buttons;

    private Logger log = Logger.getLogger(Menu.class);//输出Log日志


    @Column(name = "IMG",length = 50)
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }

    @Column(name = "URL", nullable = false, length = 50)
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "TARGER", nullable = false, length = 10)
    public String getTarget() {
        return target;
    }
    public void setTarget(String target) {
        this.target = target;
    }

    @ManyToOne(cascade={CascadeType.REFRESH})
    @JoinColumn(name="ZID")
    public Menu getMenu() {
        return menu;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @OrderBy(value = "orderBy")
    @JSONField(serialize=false)
    @OneToMany(targetEntity=Menu.class,cascade={CascadeType.REFRESH},mappedBy="menu",fetch= FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    public List<Menu> getMenus() {
        return menus;
    }
    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Column(name = "ORDER_BY", length = 5)
    public int getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    @JSONField(serialize=false)
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(targetEntity = Button.class, cascade = {CascadeType.REMOVE}, mappedBy = "menu", fetch = FetchType.LAZY)
    public List<Button> getButtons() {
        return buttons;
    }
    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    @JSONField(serialize=false)
    @ManyToMany(mappedBy = "menus")
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
