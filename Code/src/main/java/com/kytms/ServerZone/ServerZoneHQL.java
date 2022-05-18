package com.kytms.ServerZone;

import com.kytms.ServerZone.service.impl.ServerZoneServiceImpl;
import org.apache.log4j.Logger;

/**
 * @Author:sundezeng
 * @Date:2018/11/14
 * 服务区域
 */
public abstract class ServerZoneHQL {

    private final Logger log = Logger.getLogger(ServerZoneServiceImpl.class);//输出Log日志

    public static final String ServerZone_LIST = "SELECT a.id,b.name,c.name,a.type,a.mileage,a.minMoney,a.songhf,a.weightPrices," +
            " a.volumePrices,1,a.create_Name,a.create_Time,a.modify_Name,a.modify_Time,a.status as st " +
            " FROM JC_SERVER_ZONE a left join a.organization b LEFT JOIN a.zone c  WHERE 1=1 ";
    public static final String ServerZone_COUNT = "SELECT COUNT(*) FROM JC_SERVER_ZONE a left join a.organization b LEFT JOIN a.zone c  WHERE 1=1";

}
