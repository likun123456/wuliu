package com.kytms.presco;

/**
 * 预录单HQL
 * */

public abstract class PrescoSql {

    /**
     * 预录单列表SQL
     */
    public static final String PRESCO_LIST = "SELECT a.id,b.name,a.status,e.name,a.FH_name,a.FH_address,a.weight,a.number,a.volume,a.FH_person,a.FH_iphone," +
            " a.code,a.costomerType,a.relatebill1,a.dateAccepted,a.SH_name,a.SH_person,a.SH_iphone," +
            " a.SH_address,a.description,a.create_Name,a.create_Time,a.modify_Name,a.modify_Time,a.status as st " +
            " FROM JC_PRESCO a left join a.organization b LEFT JOIN a.customer c" +
            "  left join a.serverZone d left join d.zone e   WHERE 1=1 ";
    public static final String PRESCO_COUNT = "SELECT COUNT(*)  FROM JC_PRESCO a left join a.organization b" +
            "  LEFT JOIN a.customer c left join a.serverZone d left join d.zone e   WHERE 1=1";

}
