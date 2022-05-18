package com.kytms.system.service.Impl;

import com.kytms.core.constants.Entity;
import com.kytms.core.entity.DataBook;
import com.kytms.core.entity.DictionaryDetail;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.system.dao.DataBookDao;
import com.kytms.system.service.DataBookService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/11/23 0023.
 */

@Service(value = "DataBookService")
public class DataBookServiceImpl extends BaseServiceImpl<DataBook> implements DataBookService<DataBook> {
    private final Logger log = Logger.getLogger(DataBookServiceImpl.class);//输出Log日志
    private final Boolean COMPLETE = true;
    private final Boolean IS_EXPANED=true;
    private final Boolean SHOW_CHECK=true;
    private static final Map<String,Map<String,String>> dataBookCache = new HashMap<String, Map<String, String>>(5000);
    private DataBookDao<DataBook> dataBookDao;
    @Resource(name = "DataBookDao")
    public void setDataBookDao(DataBookDao dataBookDao) {
        super.setBaseDao(dataBookDao);
        this.dataBookDao = dataBookDao;
    }




    public List<TreeModel> getDataBookList(CommModel commModel) {
           // 查询权限 形成树
        List<DataBook> dataBooks = dataBookDao.executeQueryHql(" From JC_SYS_DATA_DICTIONARY Where 1=1 and zid = 'root'  order by orderBy");
            List<TreeModel> treeModels = TreeToDataBook(dataBooks);
            return treeModels;
        }

    public List<DataBook> getJgGridTree(CommModel commModel) {
//        String orderBy = " order By create_Time,code";//排序条件
//        if (StringUtils.isNotEmpty(commModel.getSidx()) && StringUtils.isNotEmpty(commModel.getSord())) {
//            orderBy = " order By " + commModel.getSidx() + " " + commModel.getSord();
//        }
//        String whereName = " and dataBook.id = ?";
//        //String whereName=" and id != ?";
//        List<String> whereValue = new ArrayList<String>();
//        whereValue.add(Entity.TREE_ROOT);
//        if (StringUtils.isNotEmpty(commModel.getWhereValue()) && StringUtils.isNotEmpty(commModel.getWhereName())) {
//            whereName = whereName + " and " + commModel.getWhereName() + " like ?";
//            whereValue.add("%" + commModel.getWhereValue() + "%");
//        }

        DataBook o = dataBookDao.selectBean(Entity.TREE_ROOT);
        List<DataBook> dataBooks = o.getDataBooks();
        //开始处理JGgrid树形
        List<DataBook> ss = new ArrayList<DataBook>();
        List<DataBook> sys_dataBooks1 = JgGirdToTree(dataBooks, ss);
        return ss;
    }

    public Map<String, Map<String, String>> getDataBookJson() {
        Map<String,Map<String,String>> databookJson = null;
        if (dataBookCache.size() == 0) {
            updateCache();
        }
        databookJson=this.dataBookCache;
        return databookJson;
    }

    //更新缓存
    public void updateCache() {
        Map<String,Map<String,String>> map = new HashMap<String, Map<String, String>>();
        List<DataBook> dataBooks = super.selectList(new CommModel());
        for (DataBook dataBook:dataBooks) {
            List<DictionaryDetail> dictionaryDetails = dataBook.getDictionaryDetails();
            if ( dictionaryDetails != null && dictionaryDetails.size() != 0){
            Map<String,String> sm = new HashMap<String, String>();
            for (DictionaryDetail dd :dictionaryDetails) {
                sm.put(dd.getName(),dd.getValue());
            }
            map.put(dataBook.getCode(),sm);
            }
        }
        this.dataBookCache.putAll(map);
    }

    public String getDataBookValue(String dataBookName,String key) {
        Map<String, String> map = dataBookCache.get(dataBookName);
        String s = map.get(key);
        return s;
    }

    /**
     * 数据字典递归函数
     * @return
     */
    public List<TreeModel> TreeToDataBook(List<DataBook> sys_dataBook) {
        List<TreeModel> treeList = new ArrayList<TreeModel>();
        for (int i = 0; i < sys_dataBook.size(); i++) {
            DataBook book = sys_dataBook.get(i);
            TreeModel tree = new TreeModel();
            tree.setId(book.getId());
            tree.setText(book.getName());
            tree.setComplete(COMPLETE);
            tree.setIsexpand(IS_EXPANED);
            tree.setShowcheck(SHOW_CHECK);
            if (book.getDataBook() != null) {
                tree.setParentnodes(book.getDataBook().getId());
            }
            //tree.setHasChildren();
            if (book.getDataBooks().size() > 0) {
                tree.setHasChildren(true);
                List<DataBook> dataBooks = book.getDataBooks();
                List<TreeModel> treeModels = TreeToDataBook(dataBooks);//开始递归
                tree.setChildNodes(treeModels);
            } else {
                tree.setHasChildren(false);
            }
            treeList.add(tree);
        }
        return treeList;
    }

    /**
     * JGgird转换成树形
     *
     * @param sys_dataBooks
     */
    private List<DataBook> JgGirdToTree(List<DataBook> sys_dataBooks, List<DataBook> ss) {
        for (int i = 0; i < sys_dataBooks.size(); i++) {
            DataBook dataBook = sys_dataBooks.get(i);
            dataBook.setParent(dataBook.getDataBook().getId());
            dataBook.setLaoded(true);
            //dataBook.setLft(number);
            ss.add(dataBook);
            if (dataBook.getDataBooks() != null && dataBook.getDataBooks().size() > 0) {
                JgGirdToTree(dataBook.getDataBooks(), ss);
                dataBook.setExpanded(true);
                dataBook.setLeaf(false);
            } else {
                dataBook.setExpanded(false);
                dataBook.setLeaf(true);
            }
            //number++;
            //dataBook.setRgt(number);
        }
        return ss;
    }
}
