package com.kytms.orderback;

/**
 * @Author:sundezeng
 * @Date:2019/3/19
 */
public abstract class OrderBackHQL {

    /**
     * 订单列表SQL
     */
    public static final String ORDER_BACK_LIST = "SELECT  job.id,a.id,job.isUpload,b.name,a.code,a.relatebill1,a.relatebill2,a.time,a.status,a.costomerType," +
            "a.isTake,case when a.costomerType = 1 then c.name when a.costomerType =0 then a.costomerName end as khmc," +
            "a.feeType,szz.name,ton.name,ezz.name,a.handoverType," +
            " f.name,(select sum(leds.amount) from JC_LEDGER_DETAIL leds left join leds.order ord where ord.id =a.id)," +
            " a.number,a.weight,a.volume,a.jzWeight,a.factArriveTime,a.factArriveTime,1," +
            "a.costomerIsExceed,a.salePersion,a.transportPro,a.isBack,a.backNumber,zs.name,a.orderMileage,a.description," +
            "a.create_Name,a.create_Time,a.modify_Name,a.modify_Time,a.status as st"+
            " FROM JC_ORDER_BACK job" +
            " left join job.order a" +
            " LEFT JOIN a.organization b" +
            " LEFT JOIN a.customer c" +
            " LEFT JOIN a.orderProducts f" +
            " LEFT JOIN a.zoneStoreroom zs " +
            " LEFT JOIN a.toOrganization ton"+
            " LEFT JOIN a.startZone sz"+
            " LEFT JOIN sz.zone szz"+
            " LEFT JOIN a.endZone ez"+
            " LEFT JOIN ez.zone ezz"+
            " LEFT JOIN a.ledgerDetails led"+
            " WHERE 1=1 ";
    public static final String ORDER_BACK_COUNT = "SELECT COUNT(*)" +
            " FROM JC_ORDER_BACK job left join job.order a" +
            " LEFT JOIN a.organization b" +
            " LEFT JOIN a.customer c" +
            " LEFT JOIN a.orderProducts f" +
            " LEFT JOIN a.zoneStoreroom zs " +
            " LEFT JOIN a.toOrganization ton"+
            " LEFT JOIN a.startZone sz"+
            " LEFT JOIN sz.zone szz"+
            " LEFT JOIN a.endZone ez"+
            " LEFT JOIN ez.zone ezz"+
            " WHERE 1=1";
}
