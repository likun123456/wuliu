package com.kytms.transportorder;

/**
 * 奇趣源码商城 www.qiqucode.com
 * <p>
 * 订单SQL
 *
 * @author 奇趣源码
 * @create 2018-03-13
 */
public abstract class OrderSql {
    /**
     * 订单列表SQL
     */
    public static final String ORDER_LIST = "SELECT  a.id,b.name,a.code,a.relatebill1,a.relatebill2,a.time,a.status,a.costomerType," +
            "a.isTake,case when a.costomerType = 1 then c.name when a.costomerType =0 then a.costomerName end as khmc," +
            "a.feeType,a.is_inoutcome,szz.name,ton.name,ezz.name,a.handoverType," +
            " f.name,(select sum(leds.amount) from JC_LEDGER_DETAIL leds left join leds.order ord where ord.id =a.id)," +
            " a.number,a.weight,a.volume,a.jzWeight,a.factArriveTime,a.factArriveTime,1," +
            "a.costomerIsExceed,a.salePersion,a.transportPro,a.isBack,a.backNumber,zs.name,a.orderMileage,a.description," +
            "a.create_Name,a.create_Time,a.modify_Name,a.modify_Time,a.relatebill3,a.isAbnormal,a.status as st,a.isAbnormal as abn "+
            " FROM JC_ORDER a" +
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
    public static final String ORDER_COUNT = "SELECT COUNT(*)" +
            " FROM JC_ORDER a" +
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

    /**
     * 分段订单
     */
    public static final String LED_LIST = "SELECT a.id,aa.id,b.name,a.code,a.time,a.relatebill1,a.relatebill2,a.costomerType," +
            "c.name,1,2,a.status,a.costomerIsExceed,a.salePersion,a.transportPro,a.isBack,a.backNumber,a.number,a.weight,a.volume," +
            "a.description,a.create_Name,a.create_Time,a.modify_Name,a.modify_Time" +
            " FROM JC_LED a left join a.order aa" +
            " left join a.organization b" +
            " LEFT JOIN a.ledReceivingParties c" +
            " left JOIN a.ledProducts f" +
            " WHERE 1=1 and c.type=0";
    public static final String LEDCOUNT = "SELECT COUNT(*) FROM JC_LED a " +
            " left join a.order aa" +
            " left join a.organization b " +
            " LEFT JOIN a.ledReceivingParties c" +
            " LEFT JOIN a.ledProducts f" +
            " WHERE 1=1 and c.type=0";
    /**
     * 核销用
     */
    public static final String OO_LIST = "SELECT a.id,b.name,a.code,a.time,a.relatebill1,a.costomerType,c.name,1,2," +
            "a.status,a.costomerIsExceed,a.salePersion,a.transportPro,a.isBack,a.backNumber," +
            "a.description,a.create_Name,a.create_Time,a.modify_Name,a.modify_Time" +
            " FROM JC_LED a left join a.organization b" +
            " LEFT JOIN a.customer c" +
            " left JOIN a.ledProducts f" +
            " WHERE 1=1 ";
    public static final String OO_COUNT = "SELECT COUNT(*) FROM JC_LED a" +
            " left join a.organization b" +
            " LEFT JOIN a.customer c" +
            " LEFT JOIN a.ledProducts f" +
            " WHERE 1=1";

    /**
     * 收入确认List
     */
    public static final String SRQR_LIST = "SELECT d.id,b.name,a.code,a.relatebill1,a.time,d.affirm_Time,d.status,d.is_inoutcome,a.costomerType,c.name," +
            " f.name,d.amount,d.input,d.income,a.description,a.create_Name,a.create_Time,a.modify_Name,a.modify_Time" +
            " FROM JC_ORDER a left join a.organization b" +
            " LEFT JOIN a.customer c" +
            " left JOIN a.ledgerDetails d left join d.feeType f" +
            " WHERE 1=1 ";
    public static final String SRQR_COUNT = "SELECT COUNT(*) FROM JC_ORDER a left join a.organization b" +
            " LEFT JOIN a.customer c" +
            " left JOIN a.ledgerDetails d left join d.feeType f" +
            " WHERE 1=1 ";

}
