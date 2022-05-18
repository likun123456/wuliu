package com.kytms.settlement;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/6/11.
 * 结算表
 */
public abstract  class  SettlementHQL {
    public static final String SETTLEENT_LIST="SELECT  slt.id,'',pmt.name,slt.name,slt.code,slt.time,slt.way,slt.jsStatus,slt.beginTime,slt.endTime," +
            " slt.description,slt.create_Name,slt.create_Time,slt.modify_Name,slt.modify_Time" +
            " FROM JC_SETTLEMENT slt left join slt.projectManagement pmt left join slt.organization org  where 1=1 ";


    public static final String SETTLEENT_COUNT= "SELECT COUNT(*) FROM JC_SETTLEMENT slt left join slt.projectManagement pmt left join slt.organization org WHERE 1=1 ";
}
