package com.kytms.system.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.DataBook;
import com.kytms.core.entity.DictionaryDetail;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.utils.StringUtils;
import com.kytms.system.service.DataBookItemService;
import com.kytms.system.service.DataBookService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建日期2017/11/23 0023.
 */
@Controller
@RequestMapping("/databook")
public class DataBookAction extends BaseAction {
    private final Logger log = Logger.getLogger(DataBookAction.class);//输出Log日志
    private DataBookService<DataBook> dataBookService;
    private DataBookItemService<DictionaryDetail> dataBookItemService;
    @Resource
    public void setDataBookService(DataBookService<DataBook> dataBookService) {
        this.dataBookService = dataBookService;
    }
    @Resource
    public void setDataBookItemService(DataBookItemService<DictionaryDetail> dataBookItemService) {
        this.dataBookItemService = dataBookItemService;
    }






    @RequestMapping(value = "/saveDataBook", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveDataBook(String tableName,String obj){
        String s = super.saveBean(tableName, obj);
        dataBookService.updateCache();
        return s;
    }

    @RequestMapping(value = "/getDataBookList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    //查找数据字典数据库方法
    public String  getDataBookList(CommModel commModel){
        List list = dataBookService.getDataBookList(commModel);
        return returnJson(list);
    }

    @RequestMapping(value = "/getJgGridTree", produces = "text/json;charset=UTF-8")
    @ResponseBody
    //查找数据字典数据库方法
    public String  getJgGridTree(CommModel commModel){
        List list = dataBookService.getJgGridTree(commModel);
        return returnJson(list);
    }

    @RequestMapping(value = "/getDataBookItemList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    //查找数据字典明细数据库方法
    public String getDataBookItemList(CommModel commModel){
        DataBook dataBook = dataBookService.selectBean(commModel.getId());
        return returnJson(dataBook.getDictionaryDetails());
    }

    //编辑中的保存方法
    @RequestMapping(value = "/savaBookItem", produces = "text/json;charset=UTF-8")
    @ResponseBody
      public String savaBookItem(DictionaryDetail dictionaryDetail){
        ReturnModel returnModel = getReturnModel();
        returnModel.setObj("保存成功... ...");
        if (StringUtils.isEmpty(dictionaryDetail.getName())){
            returnModel.addError("name","用户名不能为空");
        }
        if(returnModel.isResult()){
           // dictionaryDetail.setValue(JSON.toJSONString(dictionaryDetail.getValue()));
            dataBookItemService.saveBean(dictionaryDetail);
            dataBookService.updateCache();
        }
        return returnModel.toJsonString();
    }

    /*     //字典分类中获取字典列表
    @RequestMapping(value = "/selectTreeToGrid", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String selectTreeToGrid(CommModel commModel) {
        String json = dataBookService.selectTreeToGrid(commModel);
        return json;
    }*/
}
