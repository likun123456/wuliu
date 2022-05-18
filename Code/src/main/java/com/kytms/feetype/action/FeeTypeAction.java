package com.kytms.feetype.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.FeeType;
import com.kytms.core.entity.LedgerDetail;
import com.kytms.core.entity.Organization;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.SpringUtils;
import com.kytms.core.utils.StringUtils;
import com.kytms.feetype.service.FeeTypeSerivce;
import com.kytms.organization.dao.OrgDao;
import com.kytms.organization.dao.impl.OrgDaoImpl;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 费用类型action
 * @author 奇趣源码
 * @create 2018-01-04
 */
@Controller
@RequestMapping("/feetype")
public class FeeTypeAction extends BaseAction{
    private final Logger logger = LoggerFactory.getLogger(FeeTypeAction.class);
    private static final Map<String,Object> FEE_TYPE_CACHE = new HashMap<String, Object>(1000); //缓存
    private FeeTypeSerivce feeTypeSerivce;
    public FeeTypeSerivce getFeeTypeSerivce() {
        return feeTypeSerivce;
    }
    @Resource
    public void setFeeTypeSerivce(FeeTypeSerivce feeTypeSerivce) {
        this.feeTypeSerivce = feeTypeSerivce;
    }





    @RequestMapping(value = "/getList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getList(CommModel commModel){
        logger.info("费用类型缓存大小:"+FEE_TYPE_CACHE.size());
        JgGridListModel jgGridListModel = feeTypeSerivce.selectFeeTypeList(commModel);
        return returnJson(jgGridListModel);
    }

    @RequestMapping(value = "/saveFeeType", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveFeeType(FeeType feeType){
        ReturnModel returnModel = getReturnModel();
        returnModel.setObj("保存成功……");
        returnModel.codeUniqueAndNull(feeType,feeTypeSerivce);
        Boolean empty = StringUtils.isNotEmpty(feeType.getOrgId());
        //处理组织机构关联
        List<Organization> organizations = null;
        if (empty){
            OrgDao orgDao = SpringUtils.getBean(OrgDaoImpl.class);
            organizations = new ArrayList<Organization>();
            String orgId = feeType.getOrgId();
            String[] split = orgId.split("-");
            for (int i = 0; i <split.length ; i++) {
                String s = split[i];
                Organization o = (Organization) orgDao.selectBean(s);
                organizations.add(o);
            }
        }
        feeType.setOrganizations(organizations);
        //处理字符串
        if (StringUtils.isNotEmpty(feeType.getOrgName())){
            feeType.setOrgName(feeType.getOrgName().replace(",","-"));
        }
        if (StringUtils.isEmpty(feeType.getName())){
            returnModel.addError("name","名称不能为空");
        }
        if(returnModel.isResult()){
            feeTypeSerivce.saveBean(feeType);
            FEE_TYPE_CACHE.clear();// 保存数据后对缓存进行清空
        }
        return returnModel.toJsonString();
    }

    /**
     * 获取各模块的东西
     * @param commModel
     * @return
     */
    @RequestMapping(value = "/getModelFeeType", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getModelFeeType(CommModel commModel){
        logger.info("费用类型缓存大小:"+FEE_TYPE_CACHE.size());
        if (StringUtils.isEmpty(commModel.getId())){
            return null;
        }
        if (FEE_TYPE_CACHE.get(FeeType.TRANSPORT_ORDER+ SessionUtil.getOrgId()) == null){
            updateCache(commModel);
        }
        if (FEE_TYPE_CACHE.get(FeeType.TRANSPORT_SHIPMENT + SessionUtil.getOrgId()) == null){
            updateCache(commModel);
        }
//        if (FEE_TYPE_CACHE.get(FeeType.TRANSPORT_DISPATCH + SessionUtil.getOrgId()) == null){
//            updateCache(commModel);
//        }
        return returnJson(FEE_TYPE_CACHE.get(commModel.getId()+ SessionUtil.getOrgId()));
    }

    @RequestMapping(value = "/getFeeType", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getFeeType(CommModel commModel){
        logger.info("费用类型缓存大小:"+FEE_TYPE_CACHE.size());
        if (StringUtils.isEmpty(commModel.getId())){
            return null;
        }
        if (FEE_TYPE_CACHE.get(FeeType.TRANSPORT_MIX_ORDER ) == null){
            updateCache(commModel);
        }
        if (FEE_TYPE_CACHE.get(FeeType.TRANSPORT_MIX_SHIPMENT ) == null){
            updateCache(commModel);
        }
//        if (FEE_TYPE_CACHE.get(FeeType.TRANSPORT_MIX_DISPATCH ) == null){
//            updateCache(commModel);
//        }
        return returnJson(FEE_TYPE_CACHE.get(commModel.getId()));
    }

    /**
     * 更新缓存
     */
    private void updateCache(CommModel commModel){
        String where = " and status = 1  ";
        if ((FeeType.TRANSPORT_ORDER+ SessionUtil.getOrgId()).equals(commModel.getId()+ SessionUtil.getOrgId()) || FeeType.TRANSPORT_MIX_ORDER.equals(commModel.getId())){
            //更新字段费用类型缓存
            where = " and transportOrder = 1 and cost = 0";
            List <FeeType> list2 = feeTypeSerivce.selectList(commModel, where, null);
            Map<String,Object> map = new HashedMap();
            for (FeeType feeType:list2) {
                map.put(feeType.getId(),feeType.getName());
            }
            this.FEE_TYPE_CACHE.put(FeeType.TRANSPORT_MIX_ORDER,map);
            //更新数据表缓存
            String Hql = "Select a FROM JC_FEE_TYPE a left join a.organizations b where b.id = '"+SessionUtil.getOrgId()+"' and a.transportOrder = 1 and a.cost = 0";
            List<FeeType> list = feeTypeSerivce.selectListByHql(Hql);
            List<LedgerDetail> ledgerDetails = toLedgerDetail(list);
            this.FEE_TYPE_CACHE.put(FeeType.TRANSPORT_ORDER+ SessionUtil.getOrgId(),ledgerDetails);
        }
        if ((FeeType.TRANSPORT_SHIPMENT+SessionUtil.getOrgId()).equals(commModel.getId()+SessionUtil.getOrgId()) || FeeType.TRANSPORT_MIX_SHIPMENT.equals(commModel.getId()) ){
            //更新字段费用类型缓存
            where += " and shipmentModule = 1 and cost = 1";
            Map<String,Object> map = new HashedMap();
            List <FeeType> list2 = feeTypeSerivce.selectList(commModel, where, null);
            for (FeeType feeType:list2) {
                map.put(feeType.getId(),feeType.getName());
            }
            this.FEE_TYPE_CACHE.put(FeeType.TRANSPORT_MIX_SHIPMENT,map);
            //更新数据表缓存
            String Hql = "Select a  FROM JC_FEE_TYPE a left join a.organizations b where b.id = '"+SessionUtil.getOrgId()+"' and a.shipmentModule = 1 and a.cost = 1";
            List<FeeType> list = feeTypeSerivce.selectListByHql(Hql);
            List<LedgerDetail> ledgerDetails = toLedgerDetail(list);
            this.FEE_TYPE_CACHE.put(FeeType.TRANSPORT_SHIPMENT+SessionUtil.getOrgId(),ledgerDetails);
        }
        if ((FeeType.TRANSPORT_DISPATCH+SessionUtil.getOrgId()).equals(commModel.getId()+SessionUtil.getOrgId())){
            String Hql = "Select a  FROM JC_FEE_TYPE a left join a.organizations b where b.id = '"+SessionUtil.getOrgId()+"' and a.dispatch = 1 and a.cost = 1";
            List<FeeType> list = feeTypeSerivce.selectListByHql(Hql);
            this.FEE_TYPE_CACHE.put(FeeType.TRANSPORT_DISPATCH+SessionUtil.getOrgId(),list);
        }
    }

    private List<LedgerDetail> toLedgerDetail(List list){
        List<LedgerDetail> ledgerDetails = new ArrayList<LedgerDetail>(list.size());
        for (Object obj:list) {
            FeeType feeType = (FeeType) obj;
            LedgerDetail ledgerDetail = new LedgerDetail();
            ledgerDetail.setFeeType(feeType);
            ledgerDetail.setAmount(0.00);
            ledgerDetail.setInput(0.00);
            ledgerDetail.setTaxRate(0.00);
            ledgerDetails.add(ledgerDetail);
        }
        return ledgerDetails;
    }

    /**
     * 获取去项目的下拉列表
     * @param commModel
     * @return
     */
    @RequestMapping(value = "/getDispFeeType", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String  getProjectManagement(CommModel commModel){
        String where = " and status = 1 and dispatch =1";
        List<FeeType> rows = feeTypeSerivce.selectList(new CommModel(),where,"");
        List<TreeModel> treeModels = new ArrayList<TreeModel>();
        for (FeeType customer:rows ){
            TreeModel treeModel = new TreeModel();
            treeModel.setId(customer.getId());
            treeModel.setText(customer.getName());
            treeModels.add(treeModel);
        }
        return returnJson(treeModels);
    }
}
