package com.kytms.core.entity;

import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/11/23 0023.
 * 数据字典明细
 */
@Entity(name = "JC_SYS_DATA_DICTIONARY_DETAIL")
public class DictionaryDetail extends BaseEntityNoCode implements Serializable {
    private String value;//值
    private DataBook dataBook;
    private Integer orderBy;//排序字段


    private Logger log = Logger.getLogger(DictionaryDetail.class);//输出Log日志



    @Column(name = "VALUE",nullable = false, length = 255)
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "ORDER_BY",nullable = false, length = 5)
    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    @ManyToOne(cascade={CascadeType.PERSIST },fetch = FetchType.LAZY)
    @JoinColumn(name = "JC_SYS_DATA_DICTIONARY_ID")
    public DataBook getDataBook() {
        return dataBook;
    }
    public void setDataBook(DataBook dataBook) {
        this.dataBook = dataBook;
    }
}
