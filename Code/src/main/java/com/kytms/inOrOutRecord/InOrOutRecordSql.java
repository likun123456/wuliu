package com.kytms.inOrOutRecord;

/**
 * @Author:sundezeng
 * @Date:2018/10/25
 * 出入库
 */
public abstract class InOrOutRecordSql {

    /**
     * 出入库列表SQL
     */
    public static final String INOROUT_LIST = "SELECT a.id,b.name,c.name,d.code,a.type,a.time,a.number,a.volume,a.weight," +
            " a.description,a.create_Name,a.create_Time,a.modify_Name,a.modify_Time " +
            " FROM JC_INOROUTRECORD a left join a.organization b LEFT JOIN a.zoneStoreroom c left join a.led d  WHERE 1=1 ";

    public static final String INOROUT_COUNT = "SELECT COUNT(*) FROM  JC_INOROUTRECORD a left join a.organization b " +
            "LEFT JOIN a.zoneStoreroom c left join a.led d  WHERE 1=1";



}
