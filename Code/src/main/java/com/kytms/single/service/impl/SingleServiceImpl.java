package com.kytms.single.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kytms.carrier.dao.CarrierDao;
import com.kytms.core.constants.Symbol;
import com.kytms.core.entity.*;
import com.kytms.core.exception.AppBugException;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.*;
import com.kytms.ledgerdetails.dao.LedgerDetailDao;
import com.kytms.orderabnormal.dao.AbnormalDao;
import com.kytms.orderabnormal.dao.AbnormalDetailDao;
import com.kytms.orderabnormal.service.AbnormalService;
import com.kytms.organization.dao.OrgDao;
import com.kytms.presco.dao.PrescoDao;
import com.kytms.presco.service.PrescoService;
import com.kytms.single.dao.SingleDao;
import com.kytms.single.dao.impl.SingleDaoImpl;
import com.kytms.single.service.SingleService;
import com.kytms.transportorder.OrderTrackUtil;
import com.kytms.transportorder.dao.LedDao;
import com.kytms.transportorder.dao.LedProductDao;
import com.kytms.transportorder.dao.LedRPDao;
import com.kytms.transportorder.dao.impl.LedProductDaoImpl;
import com.kytms.transportorder.dao.impl.LedRPDaoImpl;
import com.kytms.verification.dao.VerificationDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service(value = "SingleService")
public class SingleServiceImpl extends BaseServiceImpl<Single> implements SingleService<Single> {
    private final Logger log = Logger.getLogger(SingleServiceImpl.class);//输出Log日志
    private SingleDao singleDao;
    private LedgerDetailDao ledgerDetailDao;
    private CarrierDao carrierDao;
    private OrgDao orgDao;
    private AbnormalDao abnormalDao;
    private AbnormalDetailDao abnormalDetailDao;
    private AbnormalService abnormalService;
    private PrescoDao prescoDao;
    private PrescoService prescoService;
    private LedDao ledDao;
    private LedProductDao ledProductDao;
    private VerificationDao verificationDao;


    @Resource(name = "VerificationDao")
    public void setVerificationDao(VerificationDao verificationDao) {
        this.verificationDao = verificationDao;
    }

    @Resource(name = "LedDao")
    public void setLedDao(LedDao ledDao) {
        this.ledDao = ledDao;
    }
    @Resource(name = "LedProductDao")
    public void setLedProductDao(LedProductDao ledProductDao) {
        this.ledProductDao = ledProductDao;
    }

    @Resource
    public void setPrescoService(PrescoService prescoService) {
        this.prescoService = prescoService;
    }

    @Resource(name = "PrescoDao")
    public void setPrescoDao(PrescoDao prescoDao) {
        this.prescoDao = prescoDao;
    }

    @Resource()
    public void setAbnormalService(AbnormalService abnormalService) {
        this.abnormalService = abnormalService;
    }

    @Resource(name = "AbnormalDao")
    public void setAbnormalDao(AbnormalDao abnormalDao) {
        this.abnormalDao = abnormalDao;
    }
    @Resource(name = "AbnormalDetailDao")
    public void setAbnormalDetailDao(AbnormalDetailDao abnormalDetailDao) {
        this.abnormalDetailDao = abnormalDetailDao;
    }

    @Resource(name = "OrgDao")
    public void setOrgDao(OrgDao orgDao) {
        this.orgDao = orgDao;
    }

    @Resource(name = "SingleDao")
    public void setSingleDao(SingleDao singleDao) {
        this.setBaseDao(singleDao);
        this.singleDao = singleDao;
    }
    @Resource(name = "LedgerDetailDao")
    public void setLedgerDetailDao(LedgerDetailDao ledgerDetailDao) {
        this.ledgerDetailDao = ledgerDetailDao;
    }
    @Resource(name = "CarrierDao")
    public void setCarrierDao(CarrierDao carrierDao) {
        this.carrierDao = carrierDao;
    }

    /**
     * 保存提派单
     * */
    public Single saveSingle(Single sing) {
        int status = sing.getStatus();
        if(status>2){
            throw new MessageException("请使用异常修改");
        }
        if(StringUtils.isNotEmpty(sing.getId())){
            Single single1 = (Single) singleDao.selectBean(sing.getId());
            List<Led> leds = single1.getLeds();
             if(leds.size()>0){
                 throw new MessageException("已经配载订单，不能再次保存");
             }
        }
        //设置组织机构
        if (sing.getOrganization() ==null){
            sing.setOrganization(SessionUtil.getOrg());
        }
        //如果是个体司机 则承运商为空
        if (sing.getCarrierType()== 1){
            sing.setCarrier(null);
        }else{ //如果是承运商
            if ( sing.getCarrier() ==null){
                throw new MessageException("承运商必须选择");
            }
            String id = sing.getCarrier().getId();
            Carrier carrier = (Carrier) carrierDao.selectBean(id);
            Timestamp startTime = carrier.getStartTime();
            Timestamp endTime = carrier.getEndTime();
            boolean b = DateUtils.belongCalendar(sing.getDateBilling(), startTime, endTime);
            if(b){ //计算承运商是否超期
                sing.setIsOverdueCarrier(0);
            }else {
                sing.setIsOverdueCarrier(1);
            }


            int isOverdueCarrier = sing.getOrganization().getIsOverdueCarrier();

            if(isOverdueCarrier == 0){
                if (!b){
                    throw new MessageException("承运商已超期");
                }
            }
        }
        //如果是新增，则弄个CODE
        if (StringUtils.isEmpty(sing.getId())){
            sing.setCode(super.getDispatchCode());
            sing.setStatus(1);
        }

        List<LedgerDetail> ledgerDetails = sing.getLedgerDetails();
        Single s = (Single) singleDao.savaBean(sing);
        String hql="DELETE FROM JC_LEDGER_DETAIL WHERE JC_SINGLE_ID='"+s.getId()+"'";
        singleDao.executeHql(hql,null);
        s.setLedgerDetails(null);
        for (LedgerDetail ledgerDetail:ledgerDetails) {
            //重新计算税率
            ledgerDetail.setInput(MathExtend.round(ledgerDetail.getAmount() / (1+ledgerDetail.getTaxRate()) * ledgerDetail.getTaxRate(), SystemConfigUtils.getMoneyRound()));
            ledgerDetail.setSingle(s);
            ledgerDetail.setCost(0);
            ledgerDetail.setType(2);
            ledgerDetailDao.savaBean(ledgerDetail);
        }


        return s;
    }

    public List SingleDetail(String id) {
//        String hql = "SELECT  b.id,0,b.code,b.dateAccepted,SUM(c.number),SUM(c.weight),SUM(c.volume),0  FROM JC_SINGLE a left join a.prescos b left join b.prescoProducts c where a.id='"+id+"' group by b.id";
        String hll2 = "select a.id,a.type,a.code,a.time,a.number,a.weight,a.volume,a.agent from JC_LED a left join a.endZone b left join a.customer c left join a.organization e left join a.single f  where f.id='"+id+"'";
        List list = singleDao.executeQueryHql(hll2);
        return list;
    }

    public Single saveSingleAbnormal(Single s, String abnormal) {
        if (StringUtils.isEmpty(abnormal)){
            throw new MessageException("请填写异常修改原因");
        }
        boolean sb = s.getStatus() ==0 || s.getStatus() ==1;
        if (sb){
            throw new MessageException("不能异常修改保存状态的单据");
        }
        Single ns = (Single) singleDao.selectBean(s.getId());
        ns.setIsAbnormail(1);

        //生成异常单头
        Abnormal a = new Abnormal();
        a.setOrganization(SessionUtil.getOrg());
        a.setType(2);
        a.setTime(DateUtils.getTimestamp());
        a.setDescription(abnormal);
        List<AbnormalDetail> abnormalList = abnormalService.getAbnormalList(ns, s); //获取注解元素
        //利用序列化去掉 持久化

        List<LedgerDetail> ledgerDetails = s.getLedgerDetails(); //异常
        List<LedgerDetail> ledgerDetails1 = ns.getLedgerDetails(); //原数据
        for (LedgerDetail sl:ledgerDetails) { //异常
            for (LedgerDetail slns: ledgerDetails1) { //原数据
                if(sl.getFeeType().getId().equals(slns.getFeeType().getId())){
                    double amount = sl.getAmount();
                    double taxRate = sl.getTaxRate();
                    double input = sl.getInput();
                    double amount2 = slns.getAmount();
                    double taxRate2 = slns.getTaxRate();
                    double input2 = slns.getInput();
                    if(amount != amount2){
                        AbnormalDetail abnormalDetail = new AbnormalDetail();
                        abnormalDetail.setSource("feeType");
                        abnormalDetail.setTarger(slns.getFeeType().getName());
                        abnormalDetail.setSourceValue(slns.getAmount().toString());
                        abnormalDetail.setTargerValue(sl.getAmount().toString());
                        abnormalList.add(abnormalDetail); //添加费用类型
                        slns.setAmount(sl.getAmount());
                        slns.setTaxRate(sl.getTaxRate());
                        slns.setInput(MathExtend.round(slns.getAmount() / (1+slns.getTaxRate()) * slns.getTaxRate(), SystemConfigUtils.getMoneyRound()));
                    }
                }
            }

        }






        a.setSingle(ns);
        a.setNumber(abnormalList.size());
        Abnormal newAbnormal = (Abnormal) abnormalDao.savaBean(a);
        for (AbnormalDetail abnormalDetail:abnormalList) {
            abnormalDetail.setAbnormal(newAbnormal);
            abnormalDetailDao.savaBean(abnormalDetail);
        }


        return ns;
    }

    public void trimFeeType(String id,LedgerDetail ledgerDetail) {
        Single single = (Single) singleDao.selectBean(id);
        int status = single.getStatus();
        if(status == 0 || status == 1|| status == 2){
            throw new MessageException("请使用费用修改");
        }
        Abnormal abnormal = new Abnormal();
        abnormal.setTime(DateUtils.getTimestamp());
        abnormal.setDescription("费用修改");
        abnormal.setNumber(1);
        abnormal.setType(3);
        abnormal.setSingle(single);
        Abnormal o = (Abnormal) abnormalDao.savaBean(abnormal);
        AbnormalDetail abnormalDetail = new AbnormalDetail();
        abnormalDetail.setTarger("运费");
        abnormalDetail.setSource("新增");
        abnormalDetail.setSourceValue("0");
        abnormalDetail.setTargerValue(ledgerDetail.getAmount().toString());
        abnormalDetail.setAbnormal(o);
        abnormalDetailDao.savaBean(abnormalDetail);
        ledgerDetail.setSingle(single);
        ledgerDetailDao.savaBean(ledgerDetail);
    }

    public List getNotStowage(CommModel commModel) {
//       String hql = "select a.id,0,0,a.code,a.dateAccepted,c.name,sum(d.number),sum(d.weight),sum(d.volume),sum(d.value),a.costomerType,e.name from JC_PRESCO a left join a.organization b left join a.serverZone c left join a.prescoProducts d left join a.customer e where b.id='"+SessionUtil.getOrgId()+"' and a.status = 1 group by a.id";
       String hll2 = "select a.id,a.type,a.type,a.code,a.time,bz.name,a.number,a.weight,a.volume,a.value,a.costomerType,c.FH_name from JC_LED a left join a.endZone b left join b.zone bz left join a.presco c left join a.organization e where e.id='"+SessionUtil.getOrgId()+"' and (a.status=12 or a.status =10) ";
        List list = singleDao.executeQueryHql(hll2,null);
        return list;
    }

    public void addOrder(String id, String data) {
        Single single = (Single) singleDao.selectBean(id);
        if(single.getStatus() == 1 || single.getStatus() == 2){
            JSONArray jsonArray = JSONArray.parseArray(data);
            //计数器
            for (int i = 0; i <jsonArray.size() ; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int a= jsonObject.getIntValue("typeValue"); //
                    String id1 = jsonObject.getString("id");
                    //判断是否拆单
                    int number = jsonObject.getInteger("number");
                    double weight = jsonObject.getDouble("weight");
                    double volume = jsonObject.getDouble("volume");
                    double value = jsonObject.getDouble("value");
                    Led led = (Led) ledDao.selectBean(id1);
                    int status1 = led.getStatus();
                    int ordetType = status1; //保存状态


                    if(status1 == 12 || status1 == 10){

                    }else {
                        throw new MessageException("该单据状态错误，请刷新后再试");
                    }

                List list = ledDao.executeQueryHql("SELECT Sum(b.number),Sum(b.weight),Sum(b.volume) ,Sum(b.value) FROM JC_LED a left join a.ledProducts b where a.id='" + id1 + "' group by a.id");
                    Object o = list.get(0);
                    Object[] objs = (Object[]) o;
                    int vnumber = Integer.parseInt(objs[0].toString());
                    double vweight = Double.parseDouble(objs[1].toString());
                    double vvolume = Double.parseDouble(objs[2].toString());
                    double vvalue= Double.parseDouble(objs[3].toString());

                    //判断是否拆单
                    if (number == vnumber){ //不拆单
                        led.setSingle(single);
                        led.setStatus(3);
                        ledDao.savaBean(led);
                        //处理订单和计划单状态
                        if(led.getType() == 0){ //代提货 配载
                             Presco presco1 = led.getPresco();
                             presco1.setStatus(2);

                        }
                        if(led.getType() == 1){ //代送货 //配载
                            Order order = led.getOrder();
                            order.setStatus(3);
                        }
                    }else { //拆单，处理BEAN
                        if (list.size() !=1 ){
                            throw new MessageException("多货品明细目前不支持拆分");
                        }
                        if (number > vnumber || number<0){
                            throw new MessageException("拆分数量不能大于原有数量或小于1");
                        }
                        if (weight > vweight || weight<0){
                            throw new MessageException("拆分重量不能大于原有数量或小于1");
                        }
                        if (volume  > vvolume || volume <0){
                            throw new MessageException("拆分体积不能大于原有数量或小于1");
                        }
                        if (value > vvalue || value<0){
                            throw new MessageException("拆分数量不能大于原有数量或小于0");
                        }

                        Led copy = ObjectUtil.cloneObject(led, Led.class);
                        List<LedProduct> ledProducts = led.getLedProducts();
                        if(ledProducts.size()!=1){
                            throw new MessageException("多货品明细不能拆分");
                        }
                        LedProduct ledProduct = ledProducts.get(0);

                        ledProduct.setNumber(ledProduct.getNumber()-number);
                        ledProduct.setWeight(ledProduct.getWeight()-weight);
                        ledProduct.setVolume(ledProduct.getVolume()-volume);
                        ledProduct.setValue(ledProduct.getValue()-value);
                        led.setNumber(led.getNumber()-number);
                        led.setWeight(led.getWeight()-weight);
                        led.setVolume(led.getVolume()-volume);
                        led.setValue(led.getValue()-value);
                        String code = led.getCode().substring(0,led.getCode().lastIndexOf("-"));
                        long l = ledDao.selectCountByHql("select count(id) from JC_LED where code like '" + code + "%'", null);
                        //处理克隆数据
                        copy.setId(UUIDUtil.getUuidTo32());
                        copy.setCode(code+"-"+(l+1));

                        copy.setNumber(number);
                        copy.setWeight(weight);
                        copy.setValue(value);
                        copy.setVolume(volume);
                        copy.setStatus(3);
                        if(led.getType() ==0){

                            copy.setPresco(led.getPresco());
                        }
                        if(led.getType() ==1){

                            copy.setOrder(led.getOrder());
                        }
                        copy.setSingle(single);
                        Led copyles = (Led) ledDao.savaBean(copy);
                        List<LedProduct> ledProducts1 = copy.getLedProducts();
                        LedProduct ledProduct1 = ledProducts1.get(0);
                        ledProduct1.setId(null);
                        ledProduct1.setLed(copyles);
                        ledProduct1.setNumber(number);
                        ledProduct1.setWeight(weight);
                        ledProduct1.setValue(value);
                        ledProduct1.setVolume(volume);
                        ledProductDao.savaBean(ledProduct1);

                        //处理订单和计划单状态
                        if(led.getType() == 0){ //代提货 配载
                            Presco presco1 = led.getPresco();
                            presco1.setStatus(6);

                        }
                        if(led.getType() == 1){ //代送货 //配载
                            Order order = led.getOrder();
                            order.setStatus(13);
                        }

                    }


            }
            UpdataInfo(single);
        }
    }

    public void delOrder(String id, String data) {
        Single single = (Single) singleDao.selectBean(id);
        if(single.getStatus() == 2){
            JSONArray jsonArray = JSONArray.parseArray(data);
            //计数器
            for (int i = 0; i <jsonArray.size() ; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int a= jsonObject.getIntValue("typeValue"); //
                String id1 = jsonObject.getString("id");
                Led led = (Led) ledDao.selectBean(id1);
                if(led.getStatus() !=3){
                    throw new MessageException("卸载单据状态有错误，请刷新后再试");
                }
                //判定是否需要合并
                String code = led.getCode();
                String substring = code.substring(0, code.lastIndexOf("-"));
                List<Led> ledcs = ledDao.executeQueryHql("from JC_LED where code like '" + substring + "%' and code != '" + code + "' and status != 3");
                if (ledcs.size() > 0) { //说明有未配载单据 需合并
                    LedProductDao ledProductDao = SpringUtils.getBean(LedProductDaoImpl.class);
                    LedRPDao ledRPDao = SpringUtils.getBean(LedRPDaoImpl.class);
                    Led notLed = ledcs.get(0);
                    notLed.setNumber(notLed.getNumber() + led.getNumber());
                    notLed.setWeight(notLed.getWeight() + led.getWeight());
                    notLed.setVolume(notLed.getVolume() + led.getVolume());
                    notLed.setValue(notLed.getValue() + led.getValue());
                    notLed.setBackNumber(notLed.getBackNumber() + led.getBackNumber());
                    //合并货品明细
                    List<LedProduct> ledProducts = led.getLedProducts();
                    for (LedProduct ledProduct : ledProducts) { //循环装在货品明细
                        List<LedProduct> notLedProducts = ledProductDao.executeQueryHql("FROM JC_LEG_PRODUCT where led.id = '" + notLed.getId() + "' and unit ='" + ledProduct.getUnit() + "' and name='" + ledProduct.getName() + "'");
                        if (notLedProducts.size() > 0) { //有相同货品
                            LedProduct notLedProduct = notLedProducts.get(0);
                            notLedProduct.setNumber(notLedProduct.getNumber() + ledProduct.getNumber());
                            notLedProduct.setWeight(notLedProduct.getWeight() + ledProduct.getWeight());
                            notLedProduct.setVolume(notLedProduct.getVolume() + ledProduct.getVolume());
                            notLedProduct.setValue(notLedProduct.getValue() + ledProduct.getValue());
                            ledProductDao.deleteBean(ledProduct);
                        } else {
                            ledProduct.setLed(notLed);
                        }
                    }
                    //删除合并后分段单据
                    List<LedReceivingParty> ledReceivingParties = led.getLedReceivingParties();
                    for (LedReceivingParty ledReceivingParty : ledReceivingParties) {
                        ledRPDao.deleteBean(ledReceivingParty);
                    }
                    ledDao.deleteBean(led);
                }


                led.setSingle(null);
                if(a == 0) { //如果是提货
                    led.setStatus(12);
                    //修改订单状态
                    String id2 = led.getPresco().getId();
                    //查询已配载分段运单数量
                    long l = ledDao.selectCountByHql("SELECT COUNT(id) FROM JC_LED where presco.id='" + id2 + "' and status = 3", null);
                    if(l == 0){ //如果分段单据都是已配载状态 那么 修改计划单为已保存1
                        led.getPresco().setStatus(1);
                    }else { //否则进行对比
                        //查询所有分段订单数量
                        long p = ledDao.selectCountByHql("SELECT COUNT(id) FROM JC_LED where presco.id='" + id2 + "'", null);
                        if(l != p){
                            led.getPresco().setStatus(6);
                        }
                    }

                }
                if(a ==1){//如果是派送 相同思路
                    Order order = led.getOrder();
                    String id2 = order.getId();
                    //此查询语句有问题 怎么查都是0
                    String where = " and status = '3'";

                    long count = ledDao.selectCountByHql("SELECT  COUNT(id) FROM JC_LED  where order.id='" + id2 + "'", where);
                    //这里查询的状态 和数据查询出来的状态值 不一致
//                    String hql ="SELECT status FROM JC_LED  where order.id='" + id2 + "'";
//                      List list = ledDao.executeQueryHql(hql);
                    led.setStatus(10);
                    if(count == 0){
                        order.setStatus(1);
                    }else {
                        long pppp = ledDao.selectCountByHql("SELECT COUNT(id) FROM JC_LED where order.id='" + order.getId() + "'", null);
                        if(count != pppp){
                            order.setStatus(13);
                        }else{
                            order.setStatus(10);
                        }
                    }

                }
                //修正分段运单号
                List<Led> lls = ledDao.executeQueryHql("from JC_LED where code like '" + substring + "%'");
                if(lls.size() == 1){
                    Led led1 = lls.get(0);
                    String code1 = led1.getCode();
                    String substring1 = code1.substring(0, led1.getCode().lastIndexOf("-")) +"-1";
                    if(substring1.length() > 50){
                        throw new AppBugException("程序出错");
                    }
                    led1.setCode(substring1);
                }

            }
            UpdataInfo(single);

        }
    }

    public List getStowage(String id) {
//        String hql = "select a.id,0,0,a.code,a.dateAccepted,c.name,sum(d.number),sum(d.weight),sum(d.volume),sum(d.value),a.costomerType,e.name from JC_PRESCO a left join a.organization b left join a.serverZone c left join a.prescoProducts d left join a.customer e left join a.single e where e.id='"+id+"'  group by a.id";
        String hll2 = "select a.id,a.type,a.type,a.code,a.time,bz.name,a.number,a.weight,a.volume,a.value,a.costomerType,c.FH_name from JC_LED a left join a.endZone b left join b.zone bz left join a.presco c left join a.organization e left join a.single f where f.id='"+id+"'";
        List list = singleDao.executeQueryHql(hll2,null);
        return list;
    }

    /**
     * 派车单开始执行任务
     * @param commModel
     * @return
     */
    public int startExe(CommModel commModel) {
        String id = commModel.getId();
        String[] split = id.split(Symbol.COMMA);
        int b = 0;
        for (String i:split) {
            Single single = (Single) singleDao.selectBean(i);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if(single.getStatus() ==2){
                single.setPlanStartTime(timestamp);
                single.setStatus(3); //弄出执行任务
              //  List<Led> leds = single.getLeds();
                List<Presco> prescos = single.getPrescos();
                for (Presco presco:prescos) {
                    presco.setStatus(3);
                }
                b++;
            }
            List<LedgerDetail> ledgerDetails = single.getLedgerDetails();
            Double amount =0.0;
            Double input = 0.0;
            for (LedgerDetail ledgerDetail:ledgerDetails) {
                amount += ledgerDetail.getAmount();
                 input += ledgerDetail.getInput();
            }
             VerificationZb verificationZb = new VerificationZb();
                verificationZb.setSingle(single);
                verificationZb.setOrganization(SessionUtil.getOrg());
                verificationZb.setHxStatus(0);
                verificationZb.setWhxMoney(amount);
                verificationZb.setzInput(input);
                verificationZb.setzMoney(amount);
                verificationZb.setTzSource(0);
                verificationZb.setType(1);
                verificationZb.setHxMoney(0.0);
            verificationDao.savaBean(verificationZb);
            //修改单据状态
            List<Led> leds = single.getLeds();
            for (Led led:leds) {
                int type = led.getType();
                if(type == 0){ //提货
                    led.setStatus(22); //提货中
                    Presco presco = led.getPresco();
                    boolean s = false;
                    List<Led> leds1 = presco.getLeds();
                    for (Led l:leds1) {
                        int status = l.getStatus();
                        if(status == 22){ //如果等于配载和待提货
                            s = true;
                        }
                    }
                    if(s){
                        presco.setStatus(3); //部分提货
                    }
                }
                if(type == 1){ //派送
                    led.setStatus(23); //派送中
                    Order order = led.getOrder();
                    OrderTrackUtil.addTrack(order,"您的分段订单:"+led.getCode()+"  已经开始派送");
                    boolean s = false;
                    List<Led> leds1 = order.getLeds();
                    for (Led l:leds1) {
                        int status = l.getStatus();
                        if(status == 23){ //如果等于配载和待提货
                            s = true;
                        }
                    }
                    if(s){
                        order.setStatus(24);
                    }else{
                        order.setStatus(23);
                    }
                }
            }


        }
        if (b==0){
            throw new MessageException("没有执行的单据");
        }
        return b;
    }

    public int endExe(CommModel commModel) {
        String id = commModel.getId();
        String[] split = id.split(Symbol.COMMA);
        int b = 0;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        for (String i:split) {
            Single single = (Single) singleDao.selectBean(i);
            //修改单据状态
            List<Led> leds = single.getLeds();
            for (Led led:leds) {
                b++;
                int type = led.getType();
                if(type == 0){ //提货
                    led.setStatus(88); //提货完毕
                    Presco presco = led.getPresco();
                    boolean s = false;
                    List<Led> leds1 = presco.getLeds();
                    for (Led l:leds1) {
                        int status = l.getStatus();
                        if(status == 88){ //如果等于配载和待提货
                            s = true;
                        }
                    }
                    if(s){
                        presco.setStatus(4); //提货完毕
                    }
                }
                if(type == 1){ //派送
                    led.setStatus(24); //派送完毕
                    Order order = led.getOrder();
                    OrderTrackUtil.addTrack(order,"您的分段订单:"+led.getCode()+"  已经派送完毕");
                    boolean s = false;
                    List<Led> leds1 = order.getLeds();
                    for (Led l:leds1) {
                        int status = l.getStatus();
                        if(status == 24){ //如果等于配载和待提货
                            s = true;
                        }
                    }
                    if(s){
                        order.setStatus(24);
                    }
                }
                single.setStatus(4);
                single.setPlanEndTime(timestamp);
            }
        }
        if (b==0){
            throw new MessageException("没有结束的单据");
        }
        return b;
    }

    public List getMapData(CommModel commModel) {
        String hll2 = "select a.id,a.type,a.code,a.time,b.name,a.number,a.weight,a.volume,a.value,a.costomerType,c.name,s.ltl,m.ltl from JC_LED a left join a.endZone b left join a.customer c left join a.organization e left join a.ledReceivingParties s left join a.ledReceivingParties m where m.type = 1 and s.type =0 and e.id='"+SessionUtil.getOrgId()+"' and (a.status=12 or a.status =10) ";
        JSONArray jsonArray = new JSONArray();



        List list = singleDao.executeQueryHql(hll2,null);
        for (Object obj:list) {
            Object[] o = (Object[]) obj;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",o[0]);
            jsonObject.put("type",o[1]);
            if(o[10] == null){
                jsonObject.put("name","零散");
            }else {
                jsonObject.put("name",o[10]);
            }

            jsonObject.put("number",o[5]);
            jsonObject.put("weight",o[6]);
            jsonObject.put("volume",o[7]);
            String type = o[1].toString();
            int i = Integer.parseInt(type);
            String ltl = null;
            if(i == 0){ //提货
                ltl = o[11].toString();
            }else {
                ltl = o[12].toString();
            }
            JSONArray js = new JSONArray();
            String[] split = ltl.split(",");
            js.add(Double.parseDouble(split[0]));
            js.add(Double.parseDouble(split[1]));
            jsonObject.put("ltl",js);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public String getMk(String id) {
        ReturnModel returnModel = new ReturnModel();
        Led led = (Led) ledDao.selectBean(id);
        int type = led.getType();
        boolean s = false;
            if(type == 0){
                Presco presco = led.getPresco();
                List<Led> leds = presco.getLeds();
                for (Led led1:leds) {
                    if (led1.getStatus() == 12){
                        returnModel.setResult(true);
                        returnModel.setObj(led1);
                        return returnModel.toJsonString();
                    }
                }
            }else {
                Order order = led.getOrder();
                List<Led> leds = order.getLeds();
                for (Led led1:leds) {
                    if (led1.getStatus() ==10) {
                        returnModel.setResult(true);
                        returnModel.setObj(led1);
                        return returnModel.toJsonString();
                    }
                }
            }
        returnModel.setResult(s);
        return returnModel.toJsonString();
    }

    public void UpdataInfo(Single single) {
        long prescoCount = prescoDao.selectCountByHql("select count (id) from JC_LED where single.id='" + single.getId() + "' and type = 0", null);
        long ledCount = prescoDao.selectCountByHql("select count (id) from JC_LED where single.id='" + single.getId() + "' and type = 1", null);
        if (ledCount == 0 && prescoCount ==0){
            single.setStatus(1); //保存状态
        }else {
            single.setStatus(2); //装货状态
        }
        List prescosList =prescoDao.executeQueryHql("SELECT COALESCE(Sum(b.number),0),COALESCE(Sum(b.weight),0),COALESCE(Sum(b.volume),0) ,COALESCE(Sum(b.value),0) FROM JC_LED a left join a.ledProducts b left join a.single c where c.id='" + single.getId() + "' and a.type = 0");
        List ledsList = prescoDao.executeQueryHql("SELECT COALESCE(Sum(b.number),0),COALESCE(Sum(b.weight),0),COALESCE(Sum(b.volume),0) ,COALESCE(Sum(b.value),0) FROM JC_LED a left join a.ledProducts b left join a.single c where c.id='" + single.getId() + "' and a.type = 1");
        //获取提货数量
        Object o = prescosList.get(0);
        Object[] objs = (Object[]) o;
        double vnumber = Double.parseDouble(objs[0].toString());
        double vweight = Double.parseDouble(objs[1].toString());
        double vvolume = Double.parseDouble(objs[2].toString());

        Object o2 = ledsList.get(0);
        Object[] objs2 = (Object[]) o2;

        vnumber += Double.parseDouble(objs2[0].toString());
        vweight += Double.parseDouble(objs2[1].toString());
        vvolume += Double.parseDouble(objs2[2].toString());
        single.setNumber((int) vnumber);
        single.setWeight(vweight);
        single.setVolume(vvolume);

        single.setToSendInfo(prescoCount+"提/"+ledCount+"派");
        if (vvolume!=0){
            single.setReBubbleRatio("1:"+MathExtend.round(vvolume/vweight,2));
        }else {
            single.setReBubbleRatio("1:0");
        }
        //处理代收货款
        String hql = "SELECT SUM(a.agent),SUM(a.arrivePay) from JC_LED a  left join a.single b where b.id = '"+single.getId()+"' group by b.id";
        List list = ledDao.executeQueryHql(hql);
        if(list.size() == 1){
            Object[] o1 = (Object[]) list.get(0);
            Object o3 = o1[0];
            Object o4 = o1[1];
            if(o1.toString() != null){
                single.setAgent(Double.valueOf(o3.toString()));
                single.setArrivePay(Double.valueOf(o4.toString()));
            }
        }
        singleDao.savaBean(single);
    }

}
