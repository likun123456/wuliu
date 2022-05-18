package com.kytms.shipment;/**
 * Created by 陈小龙 on 2018/3/21
 */

/**
 * 奇趣源码商城 www.qiqucode.com
 * 陈小龙
 * 运单SQL
 *
 * @author
 * @create 2018-03-13
 */
public abstract class outLedgerSql {
    /**
     * 运单列表SQL
     */
    public static final String LEDGER_LIST= "SELECT ledgerDetails.id,org.name,ship.code,ship.time,carr.name," +
           "ledgerDetails.cost,ledgerDetails.is_inoutcome,feeType.name,ledgerDetails.amount,ledgerDetails.taxRate,ledgerDetails.input,ledgerDetails.outcome," +
            "ledgerDetails.create_Name,ledgerDetails.create_Time " +
            " FROM JC_LEDGER_DETAIL ledgerDetails " +
            " left join ledgerDetails.shipment ship " +
            " left join ship.organization org " +
            " LEFT JOIN ledgerDetails.carrier carr " +
            "left join ledgerDetails.feeType feeType "+

            " WHERE 1=1 ";

    public static final String LEDGER_LIST_COUNT = "SELECT COUNT(*)  FROM JC_SHIPMENT ship  " +
            "left join ship.ledgerDetails ledgerDetails " +
            " left join ship.organization org " +
            " LEFT JOIN ledgerDetails.carrier carr " +
            "WHERE 1=1";

    /**
     * -----------------------------------------------------------------------------------------------------------------------------------------
     * 下面是车辆到站页面查询
     */

    public static final String SHIPMENT_LIST2= "SELECT ship.id,org.name,ship.code,ship.time,forg.name,norg.name,ntorg.name,torg.name,ship.orderCode,ship.relatebill1,ship.status,ship.operationPattern, " +
            "carr.name,ship.carrierType,ship.liense,ship.outDriver,ship.carriageIsExceed,ship.tan,ship.number,ship.weight,ship.volume,ship.value, " +
            "ship.create_Name,ship.create_Time,ship.modify_Name,ship.modify_Time,ship.isAbnormal,ship.status as st" +
            " FROM JC_SHIPMENT ship " +
            " left join ship.organization org  LEFT JOIN ship.carrier carr left join ship.fromOrganization forg  " +
            " left join ship.toOrganization torg left join ship.newOrganization norg left join ship.nextOrganization ntorg left join ship.berthStand bs left join bs.organization bo WHERE 1=1 ";

    public static final String SHIPMENT_COUNT2 = "SELECT COUNT(*)  FROM JC_SHIPMENT ship " +
            "left join ship.organization org  LEFT JOIN ship.carrier carr  left join ship.fromOrganization forg " +
            " left join  ship.toOrganization torg left join ship.newOrganization norg left join ship.nextOrganization ntorg left join ship.berthStand bs left join bs.organization bo WHERE 1=1 ";

    /**
     * 运单配载用的分段订单
     */
   public static final String SHIP_LEDLIST =


 "SELECT a.id,a.code,aor.relatebill1,case when aor.costomerType=1 then c.name  when aor.costomerType=0 then aor.costomerName end, " +
            " aor.time,sze.name,eze.name,alps.contactperson,sum(lls.amount),lps.name,a.number,a.volume,a.weight,a.value," +
         "a.status,a.description,a.number as nn,a.volume as vv,a.weight as ww" +
            " FROM JC_LED a left join a.organization b  " +
            "left join a.order aor" +
            " left join aor.startZone sz " +
            "left join sz.zone sze" +
            " left join aor.endZone ez " +
            "left join ez.zone eze " +
            " LEFT JOIN aor.customer c" +
           " left join aor.ledgerDetails lls " +
           "left join a.ledProducts lps " +
          " left join a.ledReceivingParties alps" +
           " WHERE 1=1 and alps.type =1 ";


    public static final String SHIP_LEDCOUNT = "SELECT COUNT(*) FROM JC_LED a" +
            " left join a.organization b left join a.order aor  " +
            "left join aor.startZone sz" +
            " left join sz.zone sze " +
            "left join aor.endZone ez " +
            "left join ez.zone eze" +
            " LEFT JOIN aor.customer c " +
            " left join aor.ledgerDetails lls " +
            "left join a.ledProducts lps " +
            " left join a.ledReceivingParties alps" +
            " WHERE 1=1 and alps.type =1";
}
