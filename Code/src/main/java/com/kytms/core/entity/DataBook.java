package com.kytms.core.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.kytms.core.annotation.Tree;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/11/23
 * 数据字典
 */
@Tree(name = "dataBooks")
@Entity(name ="JC_SYS_DATA_DICTIONARY")
public class DataBook extends BaseTreeJgGrid implements Serializable {
    private int orderBy;//排序字段
    private DataBook dataBook;//自关联
    private List<DataBook> dataBooks;
    private List<DictionaryDetail> dictionaryDetails;

    private Logger log = Logger.getLogger(DataBook.class);//输出Log日志




    @OrderBy(value = "orderBy")
    @JSONField(serialize = false)
    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, mappedBy = "dataBook")
    public List<DictionaryDetail> getDictionaryDetails() {
        return dictionaryDetails;
    }
    public void setDictionaryDetails(List<DictionaryDetail> dictionaryDetails) {
        this.dictionaryDetails = dictionaryDetails;
    }

    @Column(name = "ORDER_BY", nullable = false, length = 5)
    public int getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ZID")
    public DataBook getDataBook() {
        return dataBook;
    }
    public void setDataBook(DataBook dataBook) {
        this.dataBook = dataBook;
    }

    @JSONField(serialize = false)
    @OneToMany(targetEntity = DataBook.class, mappedBy = "dataBook", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    public List<DataBook> getDataBooks() {
        return dataBooks;
    }
    public void setDataBooks(List<DataBook> dataBooks) {
        this.dataBooks = dataBooks;
    }
}
