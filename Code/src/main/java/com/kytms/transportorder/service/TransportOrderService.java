package com.kytms.transportorder.service;

import com.alibaba.fastjson.JSONArray;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * @author 奇趣源码
 * @create 2018-01-05
 */
public interface TransportOrderService<Order> extends BaseService<Order> {
    JgGridListModel getOrderGrid(CommModel commModel);

    /**
     * 保存订单
     *
     * @param order1
     */
    Order saveOrder(Order order1);

    /**
     * 确认订单
     *
     * @param id
     */
    void confirmOrder(String id) throws MessageException;

    /**
     * 订单异常
     *
     * @param order1
     * @param time
     * @param text
     */
    Order saveAbnormalOrder(Order order1, Timestamp time, String text);

    /**
     * 复制新建订单
     *
     * @param id
     */
    void copyOrder(String id) throws MessageException;

    /**
     * 分段訂單 批量生成運單
     *
     * @param ids
     * @param templateId
     * @throws MessageException
     */
    void peizaiShip(String[] ids, String templateId) throws MessageException;

    /**
     * 国际台账上传
     *
     * @param id
     * @param f
     */
    void upload(String id, File f);

    /**
     * 处理收发货方
     *
     * @param name
     * @return
     */
    JSONArray queryRP(String name);

    Long selectCont();

    List<Map<String,Object>> orderUploadNew(XSSFWorkbook sdsds);

    List<Map<String, Object>> tongbuTMS(String ids);

    JgGridListModel getLedgerdetailsList(CommModel commModel);

    void affirmOrder(String id,Timestamp date);

    void fanaffirmOrder(String id);

    void addSubJgGird(String id, Boolean shipmentSign);

    void dellSubJgGird(String id);

    void editFree(HttpServletRequest request);
}
