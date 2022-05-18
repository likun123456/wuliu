package com.kytms.vehicleArrive.service;

import com.kytms.core.entity.Shipment;
import com.kytms.core.entity.VehicleArrive;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;

public interface VehicleArriveService<VehicleArrive> extends BaseService<VehicleArrive> {
    JgGridListModel getList(CommModel commModel);

    VehicleArrive saveVehicleArrive(VehicleArrive vehicleArrive);

    void saveShippingTime(VehicleArrive vehicleArrive);

    void saveArriveTime(VehicleArrive vehicleArrive);

    JgGridListModel getDZShipmentInfo(CommModel commModel);
}
