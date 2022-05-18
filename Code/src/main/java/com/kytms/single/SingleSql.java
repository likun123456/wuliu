package com.kytms.single;

import com.kytms.single.service.impl.SingleServiceImpl;
import org.apache.log4j.Logger;

/**
 * 派车单列表SQL
 * @Author:sundezeng
 * @Date:2018/10/29
 */
public abstract class SingleSql {
    private final Logger log = Logger.getLogger(SingleSql.class);//输出Log日志

    /**
     * 派车单列表SQL
     */
    public static final String SINGLE_LIST= "SELECT a.id,a.status,a.code,a.dateBilling,a.status,a.number,a.weight,a.volume,a.toSendInfo," +
            " a.carrierType,b.name,a.isOverdueCarrier,d.code,a.vehicleHead,a.driver,a.driverIphone,a.reBubbleRatio,a.agent,a.accountType,a.planStartTime," +
            " a.planEndTime,a.planCilckStartTime,a.planCilckEndTime,a.isAbnormail,a.create_Name,a.create_Time,a.modify_Name,a.modify_Time  " +
            " FROM JC_SINGLE a LEFT JOIN a.carrier b left join a.organization c left join a.vehicle d  WHERE 1=1 ";

    public static final String SINGLE_COUNT = "SELECT COUNT(*) FROM JC_SINGLE a LEFT JOIN a.carrier b " +
            " left join a.organization c left join a.vehicle d  WHERE 1=1 ";


}
