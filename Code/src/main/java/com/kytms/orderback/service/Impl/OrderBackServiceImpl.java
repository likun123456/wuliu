package com.kytms.orderback.service.Impl;

import com.kytms.core.constants.Symbol;
import com.kytms.core.entity.Order;
import com.kytms.core.entity.OrderBack;
import com.kytms.core.entity.OrderBackImages;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.UUIDUtil;
import com.kytms.orderback.OrderBackHQL;
import com.kytms.orderback.dao.OrderBackDao;
import com.kytms.orderback.dao.OrderBackImagesDao;
import com.kytms.orderback.service.OrderBackService;
import com.kytms.transportorder.OrderSql;
import com.kytms.transportorder.OrderStatus;
import com.kytms.transportorder.dao.TransportOrderDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 * @author 奇趣源码
 * @create 2018-01-19
 */
@Service(value = "OrderBackService")
public class OrderBackServiceImpl extends BaseServiceImpl<OrderBack> implements OrderBackService<OrderBack> {
    private final Logger log = Logger.getLogger(OrderBackServiceImpl.class);//输出Log日志
    private OrderBackDao orderBackDao;
    private TransportOrderDao transportOrderDao;
    private OrderBackImagesDao orderBackImagesDao;
    @Resource(name = "OrderBackImagesDao")
    public void setOrderBackImagesDao(OrderBackImagesDao orderBackImagesDao) {
        this.orderBackImagesDao = orderBackImagesDao;
    }

    @Resource(name = "TransportOrderDao")
    public void setTransportOrderDao(TransportOrderDao transportOrderDao) {
        this.transportOrderDao = transportOrderDao;
    }

    @Resource(name = "OrderBackDao")
    public void setOrderBackDao(OrderBackDao orderBackDao) {
        super.setBaseDao(orderBackDao);
        this.orderBackDao = orderBackDao;
    }



    public JgGridListModel getOrderBackList(CommModel commModel) {
        String where = " and organization.id ='"+ SessionUtil.getOrgId()+"'";
        String orderBY = " ORDER BY create_time desc";
        return super.getListByPage(commModel,where,orderBY);
    }

    //接收回单
    public void receive(CommModel commModel) throws MessageException {
        String id = commModel.getId();
        String  ss = null;
        String[] ids = id.split(Symbol.COMMA);
        for (int i = 0; i < ids.length; i++) {
            String id1 = ids[i];
            String hql ="select b.id from JC_ORDER_BACK a left join a.order b  where a.id = '"+id1+"'";
            List list = orderBackDao.executeQueryHql(hql);
               if(list.size()>0){
                     ss = (String) list.get(0);
               }
               if(ss != null){
                   Order order = (Order) transportOrderDao.selectBean(ss);
                   OrderBack orderBack = (OrderBack) orderBackDao.selectBean(id1);
                   if (orderBack.getStatus() != 15){
                       orderBack.setStatus(15);
                       order.setStatus(15);
                   }else{
                       throw  new MessageException("只能操作未接收状态的单据");
                   }

               }
        }
    }
//回单交单
    public void submit(CommModel commModel) throws MessageException {
        String id = commModel.getId();
        String  ss = null;
        String[] ids = id.split(Symbol.COMMA);
        for (int i = 0; i < ids.length; i++) {
            String id1 = ids[i];
            String hql ="select b.id from JC_ORDER_BACK a left join a.order b  where a.id = '"+id1+"'";
            List list = orderBackDao.executeQueryHql(hql);
            if(list.size()>0){
                ss = (String) list.get(0);
                OrderBack orderBack = (OrderBack) orderBackDao.selectBean(id1);
                if (orderBack.getStatus() != 15){
                    throw  new MessageException("只能操作接收状态的单据");
                }else{
                    Order order = (Order) transportOrderDao.selectBean(ss);
                    orderBack.setStatus(16);
                    order.setStatus(16);
                }

            }
        }
    }

    public File driverUpload(MultipartFile file, String path, String id,String url) throws IOException {

        File targetFile=null;
        String fileName=file.getOriginalFilename();//获取文件名加后缀
        if (fileName != null && fileName != "") {
            //文件后缀
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            if(!(fileF.equals(".JPG")|| fileF.equals(".GIF")|| fileF.equals(".gif")||fileF.equals(".PNG")||fileF.equals(".png")||fileF.equals(".jpg")||fileF.equals(".jpeg"))){
                throw new MessageException("请上传图片格式！");
            }
            //新的文件名
            String newFileName = UUIDUtil.getUuidTo32()+fileF;
            targetFile = new File(path);
            FileOutputStream imgOut = new FileOutputStream(new File(targetFile, newFileName));
            imgOut.write(file.getBytes());//返回一个字节数组文件的内容】
            imgOut.close();

            OrderBack orderBack2 = (OrderBack) orderBackDao.selectBean(id);
            OrderBackImages orderBackImages = new OrderBackImages();
            orderBackImages.setName(fileName);
            orderBackImages.setUrl(url+newFileName);
            orderBackImages.setOrderBack(orderBack2);
            orderBackImagesDao.savaBean(orderBackImages);
            orderBack2.setIsUpload(1);
            orderBackDao.savaBean(orderBack2);
        }
        return targetFile;
    }

    public List<Map> getImages(String id) {
        List<Map> list = new ArrayList<Map>();
        String hql ="select id from JC_ORDER_BACK where id = '"+id+"'";
        List l = orderBackDao.executeQueryHql(hql);
        if(l.size()>0){
            List list1 = orderBackImagesDao.executeQueryHql("select a from JC_ORDER_BACK_IMAGES a where a.orderBack.id='"+id+"'", null);
            for (Object obj:list1) {
                OrderBackImages orderBackImages = (OrderBackImages) obj;
                Map<String,String> map = new HashMap<String, String>();
                map.put("src",orderBackImages.getUrl());
                map.put("title",orderBackImages.getName());
                list.add(map);
            }

        }
        return list;
    }

    public JgGridListModel getOrderYQS(CommModel commModel) {
        String where = null;
        String orderBy = " group by a.id";
        where = " and b.id = '" + SessionUtil.getOrgId() + "'";
        return super.getListByPageToHql(OrderBackHQL.ORDER_BACK_LIST,OrderBackHQL.ORDER_BACK_COUNT,commModel,where,orderBy);
    }
}