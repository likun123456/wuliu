package com.kytms.system.action;

import com.kytms.core.action.BaseAction;
import com.kytms.core.entity.Menu;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.ReturnModel;
import com.kytms.core.model.TreeModel;
import com.kytms.core.utils.StringUtils;
import com.kytms.system.service.MenuService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 菜单控制器
 *
 * @author 奇趣源码
 * @create 2017-11-19
 */
@Controller
@RequestMapping("/menu")
public class MenuAciton extends BaseAction {
    private final Logger log = Logger.getLogger(MenuAciton.class);//输出Log日志
    private MenuService<Menu> menuService;
    @Resource
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }





    @RequestMapping(value = "/saveMenu",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String saveMenu(Menu menu){
        ReturnModel returnModel = getReturnModel();
        returnModel.setObj("保存成功");
        returnModel.codeUniqueAndNull(menu,menuService);//验证CODE
        if (StringUtils.isEmpty(menu.getName())){
            returnModel.addError("name","名称不能为空");
        }
        if (StringUtils.isEmpty(menu.getUrl())){
            returnModel.addError("url","URL不能为空");
        }
        boolean result = returnModel.isResult();
        if (result){
            if(menu.getMenu() == null || StringUtils.isEmpty(menu.getMenu().getId()) ){
                Menu rood = menuService.selectBean("root");
                menu.setMenu(rood);
            }
            menuService.saveBean(menu);
        }
        return returnJson(returnModel);
    }

    /**
     * 获得系统菜单树数据-OK
     * @return
     */
    @RequestMapping(value = "/getMenuTree",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getMenuTree(){
        List<TreeModel> mneuTree = menuService.getMneuTree();
        return returnJson(mneuTree);
    }

    /**
     * 系统菜单的Grid-OK
     * @return
     */
    @RequestMapping(value = "/getSysMenuGrid", produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getSysMenuGrid(CommModel comm){
        List<Menu> systemMenus = menuService.selectMenuListMain(comm);
        return returnJson(systemMenus);
    }
}
