package com.kytms.verification;

/**
 * @Author:sundezeng
 * @Date:2018/11/3
 * 核销HQL
 */
public abstract class VerificationSql {

    /**
     * 核销收入HQL
     */

    public static final String VERIFICATION_LIST= "SELECT a.id,b.name,ccr.name,c.code,a.hxStatus,c.time,c.relatebill1,1,1,a.zMoney,a.hxMoney," +
            " a.whxMoney,a.zInput,a.hxCount,a.tzSource,org.name,torg.name,c.id" +
            " FROM JC_VERIFICATION_ZB a  " +
            "left join a.organization b " +
            "left join a.order c " +
            " left join c.organization org " +
            "left join c.toOrganization torg " +
            "left join c.orderReceivingParties ccr " +
            " WHERE 1=1 and ccr.type = 0";

    public static final String VERIFICATION_COUNT = "SELECT COUNT(*) FROM JC_VERIFICATION_ZB a  left join a.organization b " +
            " left join a.order c left join c.organization org " +
            "left join c.toOrganization torg left join c.orderReceivingParties ccr WHERE 1=1 and ccr.type = 0";

    /**
     * 核销成本HQL
     */

    public static final String VERIFICATIONCB_LIST= "SELECT a.id,b.name,c.liense,car.name,c.outDriver,c.code,a.hxStatus,c.time,c.relatebill1,1,1,a.zMoney,a.hxMoney," +
            " a.whxMoney,a.zInput,a.hxCount,a.tzSource,org.name,torg.name,a.description,a.create_Name," +
            "a.create_Time,a.modify_Name,a.modify_Time,c.id" +
            " FROM JC_VERIFICATION_ZB a " +
            " left join a.organization b " +
            " left join a.shipment c " +
            " left join c.organization org " +
            " left join c.toOrganization torg left join c.carrier car" +
            " WHERE 1=1 ";

    public static final String VERIFICATIONCB_COUNT = "SELECT COUNT(*) FROM JC_VERIFICATION_ZB a  left join a.organization b " +
            " left join a.shipment c left join c.organization org left join c.toOrganization torg WHERE 1=1 ";
    /**
     * 核销提派HQL
     */

    public static final String VERIFICATIONTP_LIST= "SELECT a.id,b.name,c.vehicleHead,c.code,a.hxStatus,c.dateBilling,1,1,a.zMoney,a.hxMoney," +
            " a.whxMoney,a.zInput,a.hxCount,a.tzSource,c.id" +
            " FROM JC_VERIFICATION_ZB a  left join a.organization b left join a.single c WHERE 1=1 ";

    public static final String VERIFICATIONTP_COUNT = "SELECT COUNT(*) FROM JC_VERIFICATION_ZB a  left join a.organization b left join a.single c WHERE 1=1 ";

    /**
     * 核销明细HQL
     */
    public static final String RECORD_LIST= "SELECT a.id,a.zmoney,a.yhxmoney,a.hxmoney,a.whxmoney,a.operator,a.hxTime,a.hxtype " +
            " FROM JC_VERIFICATION_RECORD a  left join a.verificationZb b WHERE 1=1 ";

    public static final String RECORD_COUNT = "SELECT COUNT(*) FROM JC_VERIFICATION_RECORD a  left join a.verificationZb b WHERE 1=1";


    /**
     * 显示台账HQL
     */
    public static final String LEDGERD_LIST= "SELECT a.id,c.name,a.amount,a.taxRate,a.input" +
            " FROM JC_LEDGER_DETAIL a  left join a.order b left join a.feeType c WHERE 1=1 ";

    public static final String LEDGERD_COUNT = "SELECT COUNT(*) FROM JC_LEDGER_DETAIL a  left join a.order b left join a.feeType c WHERE 1=1";






}
