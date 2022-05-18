package com.kytms.presco.service.impl;

import com.kytms.core.entity.*;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.ledgerdetails.dao.LedgerDetailDao;
import com.kytms.presco.PrescoSql;
import com.kytms.presco.dao.PrescoDao;
import com.kytms.presco.dao.PrescoProductDao;
import com.kytms.presco.dao.impl.PrescoProductDaoImpl;
import com.kytms.presco.service.PrescoService;
import com.kytms.receivingparty.dao.ReceivingPartyDao;
import com.kytms.single.dao.SingleDao;
import com.kytms.single.service.SingleService;
import com.kytms.single.service.impl.SingleServiceImpl;
import com.kytms.transportorder.dao.*;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service(value = "PrescoService")
public class PrescoServiceImpl extends BaseServiceImpl<Presco> implements PrescoService<Presco> {
    private final Logger log = Logger.getLogger(PrescoServiceImpl.class);//输出Log日志
    private PrescoDao<Presco> prescoDao;
    private PrescoProductDao<PrescoProduct> prescoProductDao;
    private LedgerDetailDao<LedgerDetail> ledgerDetailDao;
    private TransportOrderDao<Order> transportOrderDao;
    private OrderProductDao<OrderProduct> orderProductDao;
    private OrderRPDao<OrderReceivingParty> orderRPDao;
    private LedDao<Led> ledDao;//分段订单
    private LedRPDao<LedReceivingParty> ledRPDao;//分段订单收发货方
    private LedProductDao<LedProduct> ledProductDao;//分段订单货品明细
    private ReceivingPartyDao<ReceivingParty> receivingPartyDao;//收发货方
    private SingleDao<Single> singleDao;//派车单
    private SingleServiceImpl singleServiceimpl;
    //---------------------------------------------------------

    @Resource
    public void setSingleServiceimpl(SingleServiceImpl singleServiceimpl) {
        this.singleServiceimpl = singleServiceimpl;
    }

    @Resource(name = "SingleDao")
    public void setSingleDao(SingleDao<Single> singleDao) {
        this.singleDao = singleDao;
    }

    @Resource(name = "ReceivingPartyDao")
    public void setReceivingPartyDao(ReceivingPartyDao<ReceivingParty> receivingPartyDao) {
        this.receivingPartyDao = receivingPartyDao;
    }

    @Resource(name = "LedProductDao")
    public void setLedProductDao(LedProductDao<LedProduct> ledProductDao) {
        this.ledProductDao = ledProductDao;
    }

    @Resource(name = "LedRPDao")
    public void setLedRPDao(LedRPDao<LedReceivingParty> ledRPDao) {
        this.ledRPDao = ledRPDao;
    }

    @Resource(name = "LedDao")
    public void setLedDao(LedDao<Led> ledDao) {
        this.ledDao = ledDao;
    }

    @Resource(name = "OrderRPDao")
    public void setOrderRPDao(OrderRPDao<OrderReceivingParty> orderRPDao) {
        this.orderRPDao = orderRPDao;
    }

    @Resource(name = "OrderProductDao")
    public void setOrderProductDao(OrderProductDao<OrderProduct> orderProductDao) {
        this.orderProductDao = orderProductDao;
    }

    @Resource(name = "TransportOrderDao")
    public void setTransportOrderDao(TransportOrderDao<Order> transportOrderDao) {
        this.transportOrderDao = transportOrderDao;
    }

    @Resource(name = "LedgerDetailDao")
    public void setLedgerDetailDao(LedgerDetailDao<LedgerDetail> ledgerDetailDao) {
        this.ledgerDetailDao = ledgerDetailDao;
    }

    @Resource(name = "PrescoDao")
    public void setPrescoDao(PrescoDao<Presco> prescoDao) {
        this.prescoDao = prescoDao;
        super.setBaseDao(prescoDao);
    }

    @Resource(name = "PrescoProductDao")
    public void setPrescoProductDao(PrescoProductDao<PrescoProduct> prescoProductDao) {
        this.prescoProductDao = prescoProductDao;
    }

    /**
     * 获取预录单页面数据
     *
     * @param commModel
     * @return
     */
    public JgGridListModel getList(CommModel commModel) {
        String where = " and b.id='"+SessionUtil.getOrgId()+"'";
        if (commModel.getWhereName() == null && commModel.getStatus() == null) {
            where += " and a.status!=99 and a.status !=100";
        }
        if("100".equals(commModel.getStatus())){
            where +=" and a.status in (99,100)";
        }
        String orderBY = " ORDER BY  a.create_Time desc";
        return super.getListByPageToHql(PrescoSql.PRESCO_LIST, PrescoSql.PRESCO_COUNT, commModel, where, orderBY);

    }

    /**
     * 保存预录单(计划单)
     *
     * @param presco
     */
    public void savePresco(Presco presco) {

        if (StringUtils.isEmpty(presco.getId())) { //新增
            presco.setCode(super.getOrderCode());
            presco.setOrganization(SessionUtil.getOrg());
        }else {
            Presco presco1 = prescoDao.selectBean(presco.getId());
            int status = presco1.getStatus();
            if(status !=1 && status !=4 ){
                throw new MessageException("只能编辑生效状态单据");
            }
        }
        if(presco.getCustomer() != null){
            presco.setCustomer(presco.getCustomer());
        }else{
            presco.setCustomer(null);
        }
        List<PrescoProduct> prescoProducts = presco.getPrescoProducts();
        List<LedgerDetail> ledgerDetail = presco.getLedgerDetail();
        //删除数据库已经存在的货品明细 和台账
        //删除货品明细
        if(presco.getId() != null) {
            Presco presco1 = prescoDao.selectBean(presco.getId());
            //删除货品信息
            if (presco1.getPrescoProducts().size() > 0) {
                String hql = "DELETE FROM JC_PRESCO_PRODUCT WHERE JC_PRESCO_ID = '" + presco.getId() + "'";
                prescoProductDao.executeHql(hql, null);
            }
            //删除台账明细
            if (presco1.getLedgerDetail().size() > 0) {
                String hql1 = "DELETE FROM JC_LEDGER_DETAIL WHERE JC_PRESCO_ID = '" + presco.getId() + "'";
                ledgerDetailDao.executeHql(hql1, null);
            }
            if(presco1.getLeds().size()>0){
               List<Led> leds= presco1.getLeds();
                for (Led led:leds) {
                    if(led.getLedReceivingParties().size()>0){
                        List<LedReceivingParty> ledReceivingParties = led.getLedReceivingParties();
                        for (LedReceivingParty ledReceivingParty:ledReceivingParties) {
                                  String hql1="DELETE FROM JC_LEG_RECEIVINGPARTY WHERE ID='"+ledReceivingParty.getId()+"'";
                                  ledRPDao.executeHql(hql1,null);
                        }
                    }
                    List<LedProduct> ledProducts = led.getLedProducts();
                    if(led.getLedProducts().size()>0){
                        for (LedProduct ledProduct:ledProducts) {
                            String hql2="DELETE FROM JC_LEG_PRODUCT WHERE ID='"+ledProduct.getId()+"'";
                             ledProductDao.executeHql(hql2,null);
                        }
                    }
                    String hql3="DELETE FROM JC_LED WHERE ID='"+led.getId()+"'";
                    ledDao.executeHql(hql3,null);
                    }
                }
            }
            presco.setPrescoProducts(null); //阻断关系
            presco.setLedgerDetail(null);
            Presco presco2 = prescoDao.savaBean(presco);

        Led led = new Led();
        double w = 0; //计算重量
        double t = 0; //计算体积
        int n = 0; //计算数量
        double v = 0; //计算货值
        double j = 0; //计算货值
        //保存分段运单
        led.setStatus(12);
        led.setCode(presco2.getCode()+"-1");
        led.setOrganization(SessionUtil.getOrg());
        led.setFormOrganization(SessionUtil.getOrg());
        led.setPresco(presco2);
        led.setRelatebill1(presco2.getRelatebill1());
        led.setOrderMileage(presco2.getPickMileage());
        led.setCustomer(presco2.getCustomer());
        led.setCostomerType(presco2.getCostomerType());
        led.setCostomerName(presco2.getCostomerNameLs());
        led.setCostomerIsExceed(0);
        led.setShipmentMethod(0);
        led.setTransportPro(0);
        led.setBackNumber(0);
        led.setIsBack(0);
        led.setTime(presco2.getDateAccepted());
        led.setEndZone(presco2.getServerZone());
        led.setType(0);
        Led led11 = ledDao.savaBean(led);
        LedReceivingParty fhf = new  LedReceivingParty();
        //发货方
        fhf.setLed(led11);
        fhf.setAddress(presco2.getFH_address());
        fhf.setLtl(presco2.getFH_ltl());
        fhf.setIphone(presco2.getFH_iphone());
        fhf.setName(presco2.getFH_name());
        fhf.setContactperson(presco2.getFH_person());
        fhf.setOrderBy(1);
        fhf.setType(0);
        fhf.setDetailedAddress(presco2.getFH_detailedAddress());
        ledRPDao.savaBean(fhf);
        //收货方
        if(presco2.getSH_name() != null){
            LedReceivingParty shf= new  LedReceivingParty();
            shf.setLed(led11);
            shf.setName(presco2.getSH_name());
            shf.setContactperson(presco2.getSH_person());
            shf.setAddress(presco2.getSH_address());
            shf.setLtl(presco2.getSH_ltl());
            shf.setIphone(presco2.getSH_iphone());
            shf.setOrderBy(1);
            shf.setType(1);
            shf.setDetailedAddress(presco2.getSH_detailedAddress());
            ledRPDao.savaBean(shf);
            //查找是否有重复的收发货方，没有则新增
            String hql = "SELECT COUNT(*) FROM JC_RECEIVINGPARTY where JC_ORGANIZATION_ID='" + SessionUtil.getOrgId() + "' and name='" + presco2.getSH_name() + "' and  contactperson = '" + presco2.getSH_person() + "' and iphone = '" + presco2.getSH_iphone() + "' and address = '" + presco2.getSH_address() + "' and ltl ='" + presco2.getSH_ltl() + "'";
            long lll = receivingPartyDao.selectCountByHql(hql, null);
            if(lll<=0){
                ReceivingParty rp = new ReceivingParty();
                rp.setName(presco2.getSH_name());
                rp.setDetailedAddress(presco2.getSH_detailedAddress());
                rp.setOrganization(SessionUtil.getOrg());
                rp.setAddress(presco2.getSH_address());
                rp.setContactperson(presco2.getSH_person());
                rp.setIphone(presco2.getSH_iphone());
                rp.setLtl(presco2.getSH_ltl());
                rp.setOrderBy(0);
                receivingPartyDao.savaBean(rp);
              }
        }
       if(prescoProducts.size()>0){
          for (PrescoProduct prescoProduct : prescoProducts) {
            PrescoProduct prescoProduct1 = new PrescoProduct();
            LedProduct ledProduct = new LedProduct();
            w += prescoProduct.getWeight();
            t += prescoProduct.getVolume();
            n += prescoProduct.getNumber();
            v += prescoProduct.getValue();
            j += prescoProduct.getJzWeight();
            prescoProduct1.setPresco(presco2);
            prescoProduct1.setUnit(prescoProduct.getUnit());
            prescoProduct1.setNumber(prescoProduct.getNumber());
            prescoProduct1.setName(prescoProduct.getName());
            prescoProduct1.setValue(prescoProduct.getValue());
            prescoProduct1.setVolume(prescoProduct.getVolume());
            prescoProduct1.setWeight(prescoProduct.getWeight());
            prescoProduct1.setJzWeight(prescoProduct.getJzWeight());
            ledProduct.setName(prescoProduct.getName());
            ledProduct.setNumber(prescoProduct.getNumber());
            ledProduct.setLed(led11);
            ledProduct.setValue(prescoProduct.getValue());
            ledProduct.setVolume(prescoProduct.getVolume());
            ledProduct.setWeight(prescoProduct.getWeight());
            ledProduct.setUnit(prescoProduct.getUnit());
            ledProduct.setJzWeight(prescoProduct.getJzWeight());
            led11.setWeight(w);
            led11.setVolume(t);
            led11.setValue(v);
            led11.setNumber(n);
            led11.setJzWeight(j);
            presco2.setWeight(w);
            presco2.setVolume(t);
            presco2.setValue(v);
            presco2.setNumber(n);
            presco2.setJzWeight(j);
            prescoDao.savaBean(presco2);
              Led led1 = ledDao.savaBean(led11);
              ledProductDao.savaBean(ledProduct);
            prescoProductDao.savaBean(prescoProduct1);
         }
       }
         if(ledgerDetail.size()>0){
               for (LedgerDetail ledgerDetail1 : ledgerDetail) {
                   LedgerDetail ledgerDetail2 = new LedgerDetail();
                   ledgerDetail2.setInput(ledgerDetail1.getInput());
                   ledgerDetail2.setAmount(ledgerDetail1.getAmount());
                   ledgerDetail2.setFeeType(ledgerDetail1.getFeeType());
                   ledgerDetail2.setTaxRate(ledgerDetail1.getTaxRate());
                   ledgerDetail2.setIncome(ledgerDetail1.getIncome());
                   ledgerDetail2.setType(0);
                   ledgerDetail2.setCost(0);
                   ledgerDetail2.setPresco(presco2);
                   ledgerDetailDao.savaBean(ledgerDetail2);
               }
         }
    }

    /**
     * 保存为订单
     */
    public String savaToOrder(Presco presco) {
        Order order = new Order();
        order.setCostomerType(presco.getCostomerType());
        if (presco.getCustomer() != null) {
            order.setCustomer(presco.getCustomer());
            order.setFeeType(0);
        }
        if (presco.getRelatebill1() != null) {
            order.setRelatebill1(presco.getRelatebill1());
        }
        order.setTransportPro(0);
        order.setOrderMileage(0.0);
        order.setIsTake(0);
        order.setOrganization(presco.getOrganization());
        order.setCode(presco.getCode());
        order.setCostomerName(presco.getCostomerNameLs());
        order.setPresco(presco);
        Order order1 = transportOrderDao.savaBean(order);
        //插入发货方
        OrderReceivingParty orderReceivingParty = new OrderReceivingParty();
             orderReceivingParty.setName(presco.getFH_name());
             orderReceivingParty.setContactperson(presco.getFH_person());
             orderReceivingParty.setIphone(presco.getFH_iphone());
             orderReceivingParty.setAddress(presco.getFH_address());
             orderReceivingParty.setLtl(presco.getFH_ltl());
             orderReceivingParty.setOrder(order1);
             orderReceivingParty.setType(0);
             orderReceivingParty.setDetailedAddress(presco.getFH_detailedAddress());
        orderRPDao.savaBean(orderReceivingParty);
        //插入收货方
        if(presco.getSH_name() != null){
        OrderReceivingParty orderReceivingParty1 = new OrderReceivingParty();
            orderReceivingParty1.setName(presco.getSH_name());
            orderReceivingParty1.setContactperson(presco.getSH_person());
            orderReceivingParty1.setIphone(presco.getSH_iphone());
            orderReceivingParty1.setAddress(presco.getSH_address());
            orderReceivingParty1.setLtl(presco.getSH_ltl());
            orderReceivingParty1.setOrder(order1);
            orderReceivingParty1.setType(1);
            orderReceivingParty1.setDetailedAddress(presco.getSH_detailedAddress());
        orderRPDao.savaBean(orderReceivingParty1);
        }
        //向订单货品明细里插入数据
        OrderProduct orderProduct = new OrderProduct();
        List<PrescoProduct> prescoProducts = presco.getPrescoProducts();
        for (PrescoProduct prescoProduct:prescoProducts) {
            orderProduct.setName(prescoProduct.getName());
             orderProduct.setOrder(order1);
             orderProduct.setNumber(prescoProduct.getNumber());
             orderProduct.setUnit(prescoProduct.getUnit());
             orderProduct.setValue(prescoProduct.getValue());
             orderProduct.setVolume(prescoProduct.getVolume());
             orderProduct.setWeight(prescoProduct.getWeight());
             orderProduct.setDescription(prescoProduct.getDescription());
             orderProduct.setJzWeight(prescoProduct.getJzWeight());
            orderProductDao.savaBean(orderProduct);
        }

        //向台账明细里插入数据
        LedgerDetail ledgerDetail = new LedgerDetail();
        List<LedgerDetail> ledgerDetail1 = presco.getLedgerDetail();
        for (LedgerDetail ledgerDetail2:ledgerDetail1) {
              ledgerDetail.setInput(ledgerDetail2.getInput());
              ledgerDetail.setAmount(ledgerDetail2.getAmount());
              ledgerDetail.setFeeType(ledgerDetail2.getFeeType());
              ledgerDetail.setTaxRate(ledgerDetail2.getTaxRate());
              ledgerDetail.setType(0);
              ledgerDetail.setCost(0);
              ledgerDetail.setOrder(order1);
              ledgerDetailDao.savaBean(ledgerDetail);
        }

        //改变预录单的状态(已转正  100)
        presco.setStatus(100);
        List<Led> leds = presco.getLeds();
        for (Led led:leds) {
            led.setCode(led.getCode()+"已转正");
            led.setRelatebill1(order1.getRelatebill1());
            led.setToOrganization(order1.getToOrganization());
            led.setEndZone(order1.getEndZone());
            led.setStartZone(order1.getStartZone());
            led.setOrder(order1);
            ledDao.savaBean(led);
        }
        prescoDao.savaBean(presco);

     //返回该订单ID
        return order1.getId();
    }

    /**
     * 删除订单
     */
    public void delBean(String id) {
        String[] ids = id.split(",");
        for (int i=0;i<ids.length;i++){
          Presco presco = prescoDao.selectBean(ids[i]);
            List<PrescoProduct> prescoProducts = presco.getPrescoProducts();
            List<Led> leds = presco.getLeds();
             if(prescoProducts.size()>0){
            for (PrescoProduct prescoProduct:prescoProducts) {
                 String hql5="DELETE FROM JC_PRESCO_PRODUCT WHERE id='"+prescoProduct.getId()+"'";
                 prescoProductDao.executeHql(hql5,null);
            }
             }
            for (Led led:leds) {
            List<LedProduct> ledProducts = led.getLedProducts();
            List<LedReceivingParty> ledReceivingParties = led.getLedReceivingParties();
            if(ledProducts.size()>0){
                for (LedProduct ledProduct:ledProducts) {
                    String ledpthql ="DELETE FROM JC_LEG_PRODUCT WHERE id='"+ledProduct.getId()+"'";
                    //ledProductDao.executeQueryHql(ledpthql,null);
                    ledProductDao.executeHql(ledpthql,null);
                }
            }
            if(ledReceivingParties.size()>0){
                for (LedReceivingParty ledReceivingParty:ledReceivingParties) {
                    String ledrphql ="DELETE FROM JC_LEG_RECEIVINGPARTY WHERE id='"+ledReceivingParty.getId()+"'";

                    ledRPDao.executeHql(ledrphql,null);
                }
            }
            String ledhql="DELETE FROM JC_LED WHERE id='"+led.getId()+"'";
            ledDao.executeHql(ledhql,null);

        }
            String prescohql="DELETE FROM JC_PRESCO WHERE id='"+ids[i]+"'";
            prescoDao.executeHql(prescohql,null);
        }
    }

    public String savefzxj(String id) {
        String aa ="";
        Presco presco = prescoDao.selectBean(id);

        Presco presco1 = new Presco();

        BeanUtils.copyProperties(presco, presco1);
            presco1.setId(null);
            presco1.setCode(super.getOrderCode());
            presco1.setOrganization(SessionUtil.getOrg());

        Presco presco2 = prescoDao.savaBean(presco1);

        aa = "成功";


        return aa;
    }

}
