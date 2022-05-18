package com.kytms.core.entity;

import org.apache.log4j.Logger;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author  奇趣源码
 * @create 2017-11-27
 */
@MappedSuperclass
public class BaseTreeJgGrid extends BaseEntity implements Serializable {
    //    level：number 表示此数据在哪一级，
//    isLeaf：boolean 表示此数据是否为叶子节点
//    parent：mixed  表示父节点是哪个，如果当前节点为父节点，则值为null，不是父节点则           为其父节点ID
//    laoded：boolean 表示是否加载完成，设置为True表示加载完成，不需要在加载
//    注意：1、一般设置此值为True，这样在点击树节点就不会再次调用后台数据，加载数                据，避免数据重复
//    expanded： boolean 表示此节点是否展开
    private boolean isLeaf;
    private boolean expanded;
    private String parent;
    private int level;
    private boolean laoded;
    private int lft;
    private int rgt;

    private Logger log = Logger.getLogger(BaseTreeJgGrid.class);//输出Log日志

    @Transient
    public boolean isLaoded() {
        return laoded;
    }

    public void setLaoded(boolean laoded) {
        this.laoded = laoded;
    }

    @Transient
    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    @Transient
    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Transient
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
    @Transient
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Transient
    public int getLft() {
        return lft;
    }

    public void setLft(int lft) {
        this.lft = lft;
    }

    @Transient
    public int getRgt() {
        return rgt;
    }

    public void setRgt(int rgt) {
        this.rgt = rgt;
    }
}
