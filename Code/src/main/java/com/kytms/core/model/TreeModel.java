package com.kytms.core.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 树模型
 *
 * @author 奇趣源码
 * @create 2017-11-19
 */
public class TreeModel {

    private final Logger log = Logger.getLogger(TreeModel.class);//输出Log日志

    /***/
    private String id;
    /**名称*/
    private String text;
    /***/
    private String value;
    /**图片*/
    private String img;
    /**父类ID*/
    private String parentnodes;
    /**是否显示选择框*/
    private Boolean showcheck;
    /**是否展开*/
    private Boolean isexpand;
    /***/
    private Boolean complete;
    /**是否有子类*/
    private Boolean hasChildren;
    /**是否被选中*/
    private int checkstate;

    /***/
    @JsonProperty(value = "ChildNodes")
    private List<TreeModel> ChildNodes = new ArrayList<TreeModel>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getParentnodes() {
        return parentnodes;
    }

    public void setParentnodes(String parentnodes) {
        this.parentnodes = parentnodes;
    }

    public Boolean getShowcheck() {
        return showcheck;
    }

    public void setShowcheck(Boolean showcheck) {
        this.showcheck = showcheck;
    }

    public Boolean getIsexpand() {
        return isexpand;
    }

    public void setIsexpand(Boolean isexpand) {
        this.isexpand = isexpand;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }
    @JsonIgnore
    @JSONField(name = "ChildNodes")
    public List<TreeModel> getChildNodes() {
        return ChildNodes;
    }
    public void setChildNodes(List<TreeModel> ChildNodes) {
        this.ChildNodes = ChildNodes;
    }

    public int getCheckstate() {
        return checkstate;
    }

    public void setCheckstate(int checkstate) {
        this.checkstate = checkstate;
    }
}
