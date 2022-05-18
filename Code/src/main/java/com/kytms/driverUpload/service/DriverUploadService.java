package com.kytms.driverUpload.service;

import com.kytms.core.entity.DriverUpload;
import com.kytms.core.model.TreeModel;
import com.kytms.core.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface DriverUploadService<DriverUpload> extends BaseService<DriverUpload> {
   File driverUpload(MultipartFile file,String path, String driverId) throws  Exception;
    List<com.kytms.core.entity.DriverUpload> showCredentials(String driverId) throws  Exception;
}
