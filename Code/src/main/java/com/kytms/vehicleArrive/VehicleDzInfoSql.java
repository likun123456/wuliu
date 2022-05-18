package com.kytms.vehicleArrive;

/**
 * 车辆到站记录
 * @Author:sundezeng
 * @Date:2018/10/29
 */
public abstract class VehicleDzInfoSql {

    /**
     * 车辆到站记录列表SQL
     */
    public static final String VEHICLEDZ_LIST= "SELECT ship.id,org.name,ship.code,ship.time,forg.name,norg.name,ntorg.name,torg.name,ship.orderCode,ship.relatebill1,ship.status,ship.operationPattern, " +
            "carr.name,ship.carrierType,ship.liense,ship.outDriver,ship.carriageIsExceed,ship.tan,ship.number,ship.weight,ship.volume,ship.value, " +
            "ship.create_Name,ship.create_Time,ship.modify_Name,ship.modify_Time,ship.isAbnormal,ship.status as st" +
            " FROM JC_VEHICLE_ARRIVE jva LEFT JOIN jva.shipment ship left join jva.organization org  LEFT JOIN ship.carrier carr " +
            " left join ship.fromOrganization forg  left join  ship.toOrganization torg " +
            " left join ship.newOrganization norg left join ship.nextOrganization ntorg WHERE 1=1 ";

    public static final String VEHICLEDZ_COUNT = "SELECT COUNT(*) FROM JC_VEHICLE_ARRIVE jva LEFT JOIN jva.shipment ship " +
            " left join jva.organization org  LEFT JOIN ship.carrier carr " +
            " left join ship.fromOrganization forg  left join  ship.toOrganization torg " +
            " left join ship.newOrganization norg left join ship.nextOrganization ntorg  WHERE 1=1 ";


}
