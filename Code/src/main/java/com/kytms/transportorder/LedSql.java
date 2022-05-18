package com.kytms.transportorder;

/**
 * @Author:sundezeng
 * @Date:2018/10/24
 * 获得运单里的分段订单
 */
public abstract class LedSql {

    /**
     * 分段订单
     */
    public static final String SHIP_LEDLIST = "SELECT a.id,b.name,a.code,a.time,a.relatebill1,a.factLeaveTime,a.factArriveTime,a.costomerType,c.name," +
            " a.status,a.costomerIsExceed,a.salePersion,a.transportPro,a.isBack,a.backNumber," +
            " a.description,a.create_Name,a.create_Time,a.modify_Name,a.modify_Time " +
            " FROM JC_LED a " +
            "left join a.organization b " +
            " left join a.shipments ship " +
            " LEFT JOIN a.customer c " +
            "Left JOIN a.ledProducts f" +
            "WHERE 1=1 ";
    public static final String SHIP_LEDCOUNT = "SELECT COUNT(*) FROM JC_LED a" +
            " left join a.organization b " +
            "left join a.shipments ship " +
            " LEFT JOIN a.customer c " +
            "LEFT JOIN a.ledProducts f" +
            " WHERE 1=1";

    /**
     * 货品到站用的
     * */

    public static final String DZ_LEDLIST = "SELECT a.id,b.name,a.code,a.time,a.relatebill1,a.costomerType," +
            " case when a.costomerType=1 then c.name when a.costomerType=0 then a.costomerName end," +
            " a.status,als.name,a.number,a.weight,a.volume,a.costomerIsExceed,torg.name,a.salePersion,a.transportPro,a.isBack,a.backNumber," +
            " a.factLeaveTime,a.factArriveTime,a.description,a.create_Name,a.create_Time,a.modify_Name,a.modify_Time " +
            " FROM JC_LED a " +
            "left join a.organization b  " +
            "left join a.shipment ship " +
            "left join a.ledProducts als " +
            " LEFT JOIN a.customer c" +
            " Left JOIN a.ledProducts f" +
            " left join a.toOrganization torg " +
            "left join a.endZone ez " +
            "WHERE 1=1 ";
    public static final String DZ_LEDCOUNT = "SELECT COUNT(*) FROM JC_LED a" +
            " left join a.organization b" +
            " left join a.shipment ship " +
            " left join a.ledProducts als " +
            " LEFT JOIN a.customer c" +
            " LEFT JOIN a.ledProducts f " +
            "left join a.toOrganization torg " +
            "left join a.endZone ez " +
            "WHERE 1=1";

    /***
     * 在库查询用,待运、待提、
     */
    public static final String TY_LEDLIST = "SELECT a.id,aor.id,b.name,aor.code,aor.time,aor.relatebill1,aor.costomerType," +
            "lps.name,aor.status,aor.costomerIsExceed,aor.transportPro,aor.isBack,aor.backNumber," +
            "f.name,a.number,a.weight,a.volume,aor.salePersion," +
            "aor.description,aor.create_Name,aor.create_Time,aor.modify_Name,aor.modify_Time" +
            " FROM JC_LED a " +
            "left join a.order aor " +
            "left join a.organization b" +
            " LEFT JOIN aor.customer c" +
           " left JOIN a.ledProducts f " +
            "left join aor.orderReceivingParties lps" +
            " WHERE 1=1 and lps.type =0";
    public static final String TY_LEDCOUNT = "SELECT COUNT(*) FROM JC_LED a " +
            "left join a.order aor " +
            "left join a.organization b" +
            " LEFT JOIN aor.customer c" +
            " left JOIN a.ledProducts f " +
            "left join aor.orderReceivingParties lps" +
            " WHERE 1=1 and lps.type =0";
  /***
   * 在库查询用，自提、派送、中转
   * */

  public static final String ZPZ_LEDLIST = "SELECT a.id,aor.id,b.name,aor.code,aor.time,aor.relatebill1,aor.costomerType," +
          "lps.name,aor.status,aor.costomerIsExceed,aor.transportPro,aor.isBack,aor.backNumber," +
          "f.name,aor.number,aor.weight,aor.volume,aor.salePersion," +
          "aor.description,aor.create_Name,aor.create_Time,aor.modify_Name,aor.modify_Time,aor.status as st" +
          " FROM JC_LED a " +
          "left join a.order aor " +
          "left join a.organization b " +
          "Left JOIN aor.orderProducts f " +
          "left join aor.toOrganization torg " +
          "left join aor.endZone ez " +
          "left join aor.orderReceivingParties lps " +
          "WHERE 1=1 and lps.type =0";
    public static final String ZPZ_LEDCOUNT = "SELECT COUNT(*) FROM JC_LED a" +
            " left join a.order aor" +
            " left join a.organization b " +
            " Left JOIN aor.orderProducts f " +
            " left join aor.toOrganization torg " +
            " left join aor.endZone ez " +
            "left join aor.orderReceivingParties lps " +
            "WHERE 1=1 and lps.type =0 ";
    /**
     * 签收用
     */
    public static final String QS_LEDLIST = "SELECT a.id,forg.name,torg.name,a.code,a.time,a.relatebill1,a.costomerType," +
            "lps.name,1,2,a.status,a.handoverType,a.costomerIsExceed,a.transportPro,a.isBack,a.backNumber,a.number,a.weight,a.volume,a.salePersion," +
            "a.description,a.create_Name,a.create_Time,a.modify_Name,a.modify_Time,a.status as st" +
            " FROM JC_LED a " +
            "left join a.organization b  " +
            "left join a.shipments ship " +
            "Left JOIN a.ledProducts f " +
            "left join a.formOrganization forg " +
            "left join a.toOrganization torg " +
            "left join a.endZone ez " +
            "left join a.ledReceivingParties lps  " +
            "WHERE 1=1 and lps.type =0";
    public static final String QS_LEDCOUNT = "SELECT COUNT(*) FROM JC_LED a" +
            " left join a.organization b " +
            "left join a.shipments ship " +
            "LEFT JOIN a.ledProducts f " +
            "left join a.formOrganization forg " +
            "left join a.toOrganization torg " +
            "left join a.endZone ez " +
            "left join a.ledReceivingParties lps  WHERE 1=1 and lps.type =0";


}
