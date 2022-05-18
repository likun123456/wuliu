package com.kytms.weizhitms.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Organization;
import com.kytms.core.model.CommModel;
import com.kytms.core.utils.SessionUtil;
import com.kytms.transportorder.service.TransportOrderService;
import com.kytms.weizhitms.datasource.TMSDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author:sundezeng
 * @Date:2019/3/14
 */
@Controller
@RequestMapping("/tmsaction")
public class TmsAction extends BaseAction {

    @RequestMapping(value = "/getfxm", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getfxm(CommModel commModel){
        TMSDataSource instance = TMSDataSource.getInstance();
        // xsName ---> 注册公司名称对应的是销售名称
        //yyName --->项目名称对应的是 运营名称
        String sql = "select a.id,a.NAME,a.COMPANY_ID,b.NAME 'xsName',a.PLAT_FORM_ID,c.NAME 'ptName'," +
                " a.PROGRAMME_ID,d.name 'yyName',a.BRANCH_ID, e.name 'fgsName' from TMS_ORGANIZATION a  " +
                " LEFT JOIN TMS_ORGANIZATION b on b.id = a.COMPANY_ID\n" +
                " LEFT JOIN TMS_ORGANIZATION c on c.id = a.PLAT_FORM_ID\n" +
                " LEFT JOIN TMS_ORGANIZATION d on d.id = a.PROGRAMME_ID\n" +
                " LEFT JOIN TMS_ORGANIZATION e on e.id = a.BRANCH_ID\n" +
                " where a.DESCRIPTION = '上海瑾霜供应链管理有限公司' ";
        List<Map> maps = instance.exeQuerySql(sql);
        return returnJson(maps);
    }

    @RequestMapping(value = "/getFylxList", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getFylxList(String lx){
        String lxmc = null;
        if(lx.equals("收入")){
            lxmc ="INCOME";
        }else{
            lxmc ="COST";
        }
        TMSDataSource instance = TMSDataSource.getInstance();
        String sql = "select  a.id,a.name,case when a.IDENTIFIES ='COST' THEN '成本'" +
                "  when a.IDENTIFIES ='DIFFCOST' THEN '调整成本'" +
                "  when a.IDENTIFIES ='INCOME' then '收入'" +
                " when a.IDENTIFIES = 'DIFFINCOME' THEN '调整收入' end 'fylx' " +
                " from TMS_FEE_TYPE a  where a.IDENTIFIES ='"+ lxmc+"' and  a.IDENTIFIES not in('DIFFCOST','DIFFINCOME')";
        List<Map> maps = instance.exeQuerySql(sql);
        return returnJson(maps);
    }

}
