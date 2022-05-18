package com.kytms.transportorder.service.Impl;

import com.kytms.core.entity.*;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.orderabnormal.dao.AbnormalDao;
import com.kytms.orderabnormal.dao.AbnormalDetailDao;
import com.kytms.transportorder.LedSql;
import com.kytms.transportorder.OrderSql;
import com.kytms.transportorder.action.OrderForm;
import com.kytms.transportorder.dao.*;
import com.kytms.transportorder.service.LedService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service(value = "LedService")
public class LedServiceImpl extends BaseServiceImpl<Led> implements LedService<Led> {
    private final Logger log = Logger.getLogger(LedServiceImpl.class);//输出Log日志
    private LedDao  ledDao;
    private TransportOrderDao transportOrderDao;
    private LedProductDao ledProductDao;
    private LedRPDao ledRPDao;
    private OrderProductDao orderProductDao;
    private OrderRPDao orderRPDao;
    private AbnormalDao abnormalDao;
    private AbnormalDetailDao abnormalDetailDao;

    @Resource(name = "AbnormalDao")
    public void setAbnormalDao(AbnormalDao abnormalDao) {
        this.abnormalDao = abnormalDao;
    }
    @Resource(name = "AbnormalDetailDao")
    public void setAbnormalDetailDao(AbnormalDetailDao abnormalDetailDao) {
        this.abnormalDetailDao = abnormalDetailDao;
    }

    @Resource(name = "LedRPDao")
    public void setLedRPDao(LedRPDao ledRPDao) {
        this.ledRPDao = ledRPDao;
    }
    @Resource(name = "OrderProductDao")
    public void setOrderProductDao(OrderProductDao orderProductDao) {
        this.orderProductDao = orderProductDao;
    }

    @Resource(name = "OrderRPDao")
    public void setOrderRPDao(OrderRPDao orderRPDao) {
        this.orderRPDao = orderRPDao;
    }

    @Resource(name = "LedProductDao")
    public void setLedProductDao(LedProductDao ledProductDao) {
        this.ledProductDao = ledProductDao;
    }

    @Resource(name = "TransportOrderDao")
    public void setTransportOrderDao(TransportOrderDao transportOrderDao) {
        this.transportOrderDao = transportOrderDao;
    }

    @Resource(name = "LedDao")
    public void setLedDao(LedDao ledDao) {
        super.setBaseDao(ledDao);
        this.ledDao = ledDao;
    }




    public JgGridListModel getList(CommModel commModel) {
        String where = " and b.id = '"+SessionUtil.getOrgId()+"'";
        String orderBy = " group by a.id,f.field3 order by a.create_Time desc";
        return super.getListByPageToHql(OrderSql.LED_LIST,OrderSql.LEDCOUNT,commModel,where,orderBy);
    }

    public JgGridListModel getDZLedList(String id) {
        CommModel commModel = new CommModel();
            commModel.setRows(30);
        String where = "  and ship.id = '" + id +"' and a.status not in (5,9,10,11)";
        String orderBy = " group by a.id,f.field3 order by a.create_Time desc";
        return super.getListByPageToHql(LedSql.DZ_LEDLIST,LedSql.DZ_LEDCOUNT,commModel,where,orderBy);
    }

    public String getOrderDy(CommModel commModel) {
        JgGridListModel listByPageToHql=null;
        String where = null;
        String orderBy= null;
          if(StringUtils.isEmpty(commModel.getId())){
              where = " and b.id = '"+SessionUtil.getOrgId()+"' and aor.status =1112";
              orderBy = " group by aor.code";
            listByPageToHql = super.getListByPageToHql(LedSql.TY_LEDLIST, LedSql.TY_LEDLIST, commModel, where, orderBy);
          }
        if("operational".equals(commModel.getId())){//待运库
            where = " and b.id = '"+SessionUtil.getOrgId()+"' and aor.status = 2 ";
            orderBy = " group by aor.code";
             listByPageToHql = super.getListByPageToHql(LedSql.TY_LEDLIST, LedSql.TY_LEDCOUNT, commModel, where, orderBy);
        }
        if("since".equals(commModel.getId())){//自提库
            where = " and b.id = '"+SessionUtil.getOrgId()+"' and aor.status =9 and aor.handoverType=0 ";
            orderBy = " group by aor.code";
            listByPageToHql = super.getListByPageToHql(LedSql.ZPZ_LEDLIST, LedSql.ZPZ_LEDCOUNT, commModel, where, orderBy);
        }
        if("dispatching".equals(commModel.getId())){//派送库
            where = " and b.id = '"+SessionUtil.getOrgId()+"' and aor.status =10 and aor.handoverType=1 ";
            orderBy = " group by aor.code";
            listByPageToHql = super.getListByPageToHql(LedSql.ZPZ_LEDLIST, LedSql.ZPZ_LEDCOUNT, commModel, where, orderBy);
        }
        if("ransfer".equals(commModel.getId())){//中转库
            where = " and b.id = '"+SessionUtil.getOrgId()+"' and aor.status=11 ";
            orderBy = " group by aor.code";
            listByPageToHql = super.getListByPageToHql(LedSql.ZPZ_LEDLIST, LedSql.ZPZ_LEDCOUNT, commModel, where, orderBy);
        }
        if("tomention".equals(commModel.getId())){//待提库
            where = " and b.id = '"+SessionUtil.getOrgId()+"' and aor.status =12 ";
            listByPageToHql = super.getListByPageToHql(LedSql.TY_LEDLIST, LedSql.TY_LEDCOUNT, commModel, where, orderBy);
        }
        return listByPageToHql.toJSONString();
    }

    /**
     * 签收功能
     * @param id
     * @param qsperson
     * @param qsTime
     */
    public void saveLed(String id, String qsperson, Timestamp qsTime) {
                 Led led = (Led) ledDao.selectBean(id);
                        led.setQsTime(qsTime);
                        led.setQsperson(qsperson);
                        led.setStatus(20);
                        ledDao.savaBean(led);
                Order order = led.getOrder();
        List<Led> leds = order.getLeds();
        boolean aa = false;
        if(leds.size()>0) {
            for (Led led1 : leds) {
                if (led1.getQsperson() == null) {
                    order.setStatus(21);
                    break;
                } else {
                    aa = true;
                }
            }
            if (aa == true) {
                order.setStatus(20);
            }
        }else{
            throw new MessageException("未找到对应的分段订单");
        }
        transportOrderDao.savaBean(order);
    }

    public JgGridListModel getOrderYS(CommModel commModel) {
        String where = null;
        String orderBy = null;
        where = " and forg.id = '" + SessionUtil.getOrgId() + "' and a.status in(24,5)";

        return super.getListByPageToHql(LedSql.QS_LEDLIST,LedSql.QS_LEDCOUNT,commModel,where,orderBy);
    }

    public JgGridListModel getOrderYQS(CommModel commModel) {
        String where = null;
        String orderBy = " group by a.id";
        where = " and b.id = '" + SessionUtil.getOrgId() + "' and a.backNumber>0 and a.status not in(0,1,2)";

        return super.getListByPageToHql(OrderSql.ORDER_LIST,OrderSql.ORDER_COUNT,commModel,where,orderBy);
    }

    /**
     * 修改分段订单件重尺，然后反写给订单和运单
     * @param id
     * @param oldnumber
     * @param oldweight
     * @param oldvolume
     * @param newnumber
     * @param newweight
     * @param newvolume
     * @return
     */
    public String updateJZC(String id, Integer oldnumber, Double oldweight, Double oldvolume, Integer newnumber, Double newweight, Double newvolume) {

        // 计算输入值 和原值的 差异值
        int numbercz = newnumber - oldnumber;
        double weightcz = newweight - oldweight;
        double volumecz = newvolume - oldvolume;

        Led led = (Led) ledDao.selectBean(id);
            led.setNumber(newnumber.intValue());
            led.setWeight(newweight.doubleValue());
            led.setVolume(newvolume.doubleValue());
            if(newweight.doubleValue()>=newvolume.doubleValue()/3){
                led.setJzWeight(newweight.doubleValue());
            }else{
                led.setJzWeight(newvolume/3);
            }

        List<LedProduct> ledProducts = led.getLedProducts();
        for (LedProduct ledProduct:ledProducts) {
              ledProduct.setNumber(newnumber.intValue());
              ledProduct.setWeight(newweight.doubleValue());
              ledProduct.setVolume(newvolume.doubleValue());
        }
        List<Shipment> shipments = led.getShipments();
        if(shipments.size()>0){
            Shipment shipment = shipments.get(0);
            double shNumber = shipment.getNumber() + numbercz;
            double spWeight = shipment.getWeight() + weightcz;
            double spVolume = shipment.getVolume() + volumecz;
            if(spWeight>=spVolume/3){
                shipment.setJzWeight(spWeight);
            }else{
                shipment.setJzWeight(spVolume/3);
            }
            shipment.setNumber(shNumber);
            shipment.setWeight(spWeight);
            shipment.setVolume(spVolume);
        }
        String orderid = led.getOrder().getId();
        Integer number = 0;
        Double weight = 0.00;
        Double volume = 0.00;
        Abnormal abnormal1= null;
        String iddd = null;
        if(orderid != null){
            Order order = (Order) transportOrderDao.selectBean(orderid);
            List<Led> leds = order.getLeds();
            for (Led led1:leds) {
                 weight += led1.getWeight();
                 number += led1.getNumber();
                 volume += led1.getVolume();
            }
            if(number != order.getNumber() || weight != order.getWeight() || volume != order.getVolume()){
                  if(weight>volume/3){
                      order.setJzWeight(weight);
                  }else if(weight<=volume/3){
                      order.setJzWeight(volume/3);
                  }
                  order.setNumber(number);
                  order.setWeight(weight);
                  order.setVolume(volume);
                  order.setIsAbnormal(1);
                List<Abnormal> abnormals = order.getAbnormals();
                 if(abnormals.size()<1){
                     Abnormal abnormal = new Abnormal();
                     abnormal.setOrder(order);
                     abnormal.setOrganization(SessionUtil.getOrg());
                     abnormal.setType(0);
                     abnormal.setTime(new Timestamp(System.currentTimeMillis()));
                     abnormal.setNumber(1);
                     abnormal.setDescription("修改件重尺");
                    abnormal1 = (Abnormal) abnormalDao.savaBean(abnormal);
                     iddd = abnormal1.getId();
                     if(oldnumber.intValue() != newnumber.intValue()){
                         AbnormalDetail abnormalDetail = new AbnormalDetail();
                         abnormalDetail.setAbnormal(abnormal1);
                         abnormalDetail.setSource("number");
                         abnormalDetail.setSourceValue(oldnumber.toString());
                         abnormalDetail.setTarger("数量");
                         abnormalDetail.setTargerValue(newnumber.toString());
                         abnormalDetailDao.savaBean(abnormalDetail);
                     }
                     if(oldweight.doubleValue() != oldweight.doubleValue()){
                         AbnormalDetail abnormalDetail = new AbnormalDetail();
                         abnormalDetail.setAbnormal(abnormal1);
                         abnormalDetail.setSource("weight");
                         abnormalDetail.setSourceValue(oldweight.toString());
                         abnormalDetail.setTarger("重量");
                         abnormalDetail.setTargerValue(newweight.toString());
                         abnormalDetailDao.savaBean(abnormalDetail);
                     }
                     if(oldvolume.doubleValue() != newvolume.doubleValue()){
                         AbnormalDetail abnormalDetail = new AbnormalDetail();
                         abnormalDetail.setAbnormal(abnormal1);
                         abnormalDetail.setSource("volume");
                         abnormalDetail.setSourceValue(oldweight.toString());
                         abnormalDetail.setTarger("体积");
                         abnormalDetail.setTargerValue(newweight.toString());
                         abnormalDetailDao.savaBean(abnormalDetail);
                     }
                 }else{
                     if(oldnumber.intValue() != newnumber.intValue()){
                         AbnormalDetail abnormalDetail = new AbnormalDetail();
                         abnormalDetail.setAbnormal(abnormals.get(0));
                         abnormalDetail.setSource("number");
                         abnormalDetail.setSourceValue(oldnumber.toString());
                         abnormalDetail.setTarger("数量");
                         abnormalDetail.setTargerValue(newnumber.toString());
                         abnormalDetailDao.savaBean(abnormalDetail);
                     }
                     if(oldweight.doubleValue() != newweight.doubleValue()){
                         AbnormalDetail abnormalDetail = new AbnormalDetail();
                         abnormalDetail.setAbnormal(abnormals.get(0));
                         abnormalDetail.setSource("weight");
                         abnormalDetail.setSourceValue(oldweight.toString());
                         abnormalDetail.setTarger("重量");
                         abnormalDetail.setTargerValue(newweight.toString());
                         abnormalDetailDao.savaBean(abnormalDetail);
                     }
                     if(oldvolume.doubleValue() != newvolume.doubleValue()){
                         AbnormalDetail abnormalDetail = new AbnormalDetail();
                         abnormalDetail.setAbnormal(abnormals.get(0));
                         abnormalDetail.setSource("volume");
                         abnormalDetail.setSourceValue(oldweight.toString());
                         abnormalDetail.setTarger("体积");
                         abnormalDetail.setTargerValue(newweight.toString());
                         abnormalDetailDao.savaBean(abnormalDetail);
                     }
                     iddd = abnormals.get(0).getId();
                     String hql="select count(a.id) from JC_ABNORMAL_DETAIL a left join a.abnormal b where b.id ='"+iddd+"'";
                     Long lss = abnormalDetailDao.selectCountByHql(hql, null);
                     abnormals.get(0).setNumber(lss.intValue());
                 }
            }
        }
        return "成功";
    }
}
