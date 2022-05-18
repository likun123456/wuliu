package com.kytms.vehicleHead;

/**
 * 辽宁捷畅物流有限公司  信息技术中心
 * 孙德增
 * 创建时间： 2018/4/3.
 */
public abstract class VehicleHeadSQL {
   public static  final String VH_LIST="SELECT jvh.id,jvh.status,jrn.name,org.name,org1.name,jvh.code,dri.name,jvh.name,jvh.size,jvh.totalMass,jvh.pullTotalMass,jvh.vehicleWeight," +
           "jvh.persionNumber,jvh.vin,jvh.engineVin,jvh.displacement,jvh.emiStandard,jvh.maxPs,jvh.maxTorque,jvh.maxKm,jvh.gearBoxType,jvh.tireNumber," +
           "jvh.tireType,jvh.oilSize,jvh.oilType,jvh.price,jvh.buyTime,jvh.buyAddress,jvh.supplierName,jvh.supplierIphone,jvh.source,jvh.mileage, " +
           "jvh.create_Name,jvh.create_Time,jvh.modify_Name,jvh.modify_Time,jvh.description " +
           " from JC_VEHICLE_HEAD jvh left join jvh.jcRegistration jrn left join jvh.organization org left join jvh.organization1 org1 left join jvh.drivers dri  " +
           " WHERE 1=1 ";
    public static final String VH_COUNT="SELECT COUNT(*) from JC_VEHICLE_HEAD jvh left join jvh.jcRegistration jrn left join jvh.organization org left join jvh.organization1 org1 left join jvh.drivers dri WHERE 1=1";
}
