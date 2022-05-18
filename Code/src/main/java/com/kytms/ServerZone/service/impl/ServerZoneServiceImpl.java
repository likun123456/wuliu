package com.kytms.ServerZone.service.impl;

import com.kytms.ServerZone.ServerZoneHQL;
import com.kytms.ServerZone.dao.ServerZoneDao;
import com.kytms.ServerZone.dao.impl.ServerZoneDaoImpl;
import com.kytms.ServerZone.service.ServerZoneService;
import com.kytms.core.entity.DeliveryPrice;
import com.kytms.core.entity.ServerZone;
import com.kytms.core.entity.Zone;
import com.kytms.core.entity.ZoneStoreroom;
import com.kytms.core.exception.AppBugException;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SessionUtil;
import com.kytms.core.utils.StringUtils;
import com.kytms.deliveryPrice.dao.DeliveryPriceDao;
import com.kytms.zone.dao.ZoneDao;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "ServerZoneService")
public class ServerZoneServiceImpl extends BaseServiceImpl<ServerZone> implements ServerZoneService<ServerZone> {
    private final Logger log = Logger.getLogger(ServerZoneServiceImpl.class);//输出Log日志
    private ServerZoneDao<ServerZone> serverZoneDao;
    private ZoneDao<Zone> zoneZoneDao;
    private DeliveryPriceDao<DeliveryPrice> deliveryPriceDao;

    @Resource(name = "DeliveryPriceDao")
    public void setDeliveryPriceDao(DeliveryPriceDao<DeliveryPrice> deliveryPriceDao) {
        this.deliveryPriceDao = deliveryPriceDao;
    }

    @Resource(name = "ZoneDao")
    public void setZoneZoneDao(ZoneDao<Zone> zoneZoneDao) {
        this.zoneZoneDao = zoneZoneDao;
    }

    @Resource(name = "ServerZoneDao")
    public void setServerZoneDao(ServerZoneDao<ServerZone> serverZoneDao) {
        this.serverZoneDao = serverZoneDao;
        super.setBaseDao(serverZoneDao);
    }

    public JgGridListModel getList(CommModel commModel) {
        String where = "";
        if (StringUtils.isNotEmpty(commModel.getStatus())){
            where = " and a.status = "+commModel.getStatus();
        }
        if(StringUtils.isNotEmpty(commModel.getOther())){
            if(commModel.getOther().equals("0")){
                where+=" and a.type=0 and b.id='"+ commModel.getId()+"'";
            }
        }else if(StringUtils.isNotEmpty(commModel.getId())){
            where+=" and b.id='"+ commModel.getId()+"'";
        }else{
            where+=" and b.id='"+SessionUtil.getOrgId()+"'";
        }
        String orderBY = " ORDER BY  a.create_Time desc";
        return super.getListByPageToHql(ServerZoneHQL.ServerZone_LIST,ServerZoneHQL.ServerZone_COUNT,commModel,where,orderBY);
    }

    public void saveServerZone(ServerZone serverZone) {
        ServerZone serverZone1 = serverZoneDao.savaBean(serverZone);

    }

    public void lxupload(File f) {
        FileInputStream fis;
        Zone zone = null;
        try {
            fis = new FileInputStream(f);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fis);
            HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
            List<ServerZone> list = new ArrayList<ServerZone>();
            HSSFRow row = sheetAt.getRow(0);//第一行对象
            int lastRowNum = sheetAt.getLastRowNum(); //行数据
            short lastCellNum = row.getLastCellNum(); //列数据
            for (int j = 1; j < lastRowNum; j++) {
                HSSFRow row1 = sheetAt.getRow(j);
                HSSFCell psqu = row1.getCell(0);//获取区域名称
               //  = psqu.getStringCellValue().trim();
                String trim=  psqu.getStringCellValue().replace("","");//去掉所有的单元格
               // HSSFCell ditu = row1.getCell(4);//地图公里数
                HSSFCell songfei = row1.getCell(1);//送货费
                HSSFCell zuidi = row1.getCell(2);//最低收费
                HSSFCell zldj = row1.getCell(3);//重量单价
                HSSFCell tjdj = row1.getCell(4);//体积单价
                String hql ="from JC_ZONE where name='"+trim+"'";
                List<Zone> serverZones = zoneZoneDao.executeQueryHql(hql);
                if(serverZones == null || serverZones.size()  != 1){
                    throw  new MessageException(psqu + "没有这个区域");
                }
                Zone zone1 = serverZones.get(0);
                  String hql1="from JC_SERVER_ZONE where JC_ZONE_ID='"+zone1.getId()+"'";
                List<ServerZone> serverZones1 = serverZoneDao.executeQueryHql(hql1);
                 if(serverZones1.size()==0){
                     ServerZone serverZone = new ServerZone();
                     serverZone.setZone(zone1);
                     serverZone.setType(1);
                     serverZone.setOrganization(SessionUtil.getOrg());
//                     serverZone.setMileage(ditu.getNumericCellValue());
                     serverZone.setSonghf(songfei.getNumericCellValue());
                     serverZone.setWeightPrices(zldj.getNumericCellValue());
                     serverZone.setVolumePrices(tjdj.getNumericCellValue());
                     serverZone.setMinMoney(zuidi.getNumericCellValue());
                     ServerZone serverZone1 = serverZoneDao.savaBean(serverZone);
//                     if("上海分中心".equals(SessionUtil.getOrgName())){
//                         for (int i = 0; i <5 ; i++) {
//                             DeliveryPrice deliveryPrice = new DeliveryPrice();
//                             deliveryPrice.setZlinterval(Integer.toString(i));
//                             deliveryPrice.setServerZone(serverZone1);
//                             HSSFCell cell = row1.getCell(i + 6);
//                             double numericCellValue = cell.getNumericCellValue();
//                             deliveryPrice.setPirce(numericCellValue);
//                             deliveryPriceDao.savaBean(deliveryPrice);
//                         }
//                     }
//                     if("广州分中心".equals(SessionUtil.getOrgName())){
//                         for (int k = 0; k <6 ;k++) {
//                             DeliveryPrice deliveryPrice1 = new DeliveryPrice();
//                             deliveryPrice1.setZlinterval(Integer.toString(k+5));
//                             deliveryPrice1.setServerZone(serverZone1);
//                             HSSFCell cell = row1.getCell(k + 6);
//                             double numericCellValue = cell.getNumericCellValue();
//                             deliveryPrice1.setPirce(numericCellValue);
//                             deliveryPriceDao.savaBean(deliveryPrice1);
//                         }
//                     }
                 }
                }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
