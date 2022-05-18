package com.kytms.system.service;

import com.kytms.core.model.CommModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/11/23 0023.
 */
public interface DataBookService<DataBook> extends BaseService<DataBook> {
    List<TreeModel> getDataBookList(CommModel commModel);

    List<DataBook> getJgGridTree(CommModel commModel);

    Map<String, Map<String, String>> getDataBookJson();

    /**
     * 更新缓存
     */
    void updateCache();

    String getDataBookValue(String dataBookName,String key);
    // String selectTreeToGrid(CommModel commModel);
}
