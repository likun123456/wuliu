package com.kytms.driverUpload.service.Impl;

import com.kytms.core.entity.Driver;
import com.kytms.core.entity.DriverUpload;
import com.kytms.core.service.impl.BaseServiceImpl;
import com.kytms.core.utils.SpringUtils;
import com.kytms.driverUpload.dao.DriverUploadDao;
import com.kytms.driverUpload.dao.Impl.DriverUploadDaoImpl;
import com.kytms.driverUpload.service.DriverUploadService;
import com.kytms.driverset.dao.Impl.DriverDaoImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service(value = "DriverUploadService")
public class DriverUploadServiceImpl extends BaseServiceImpl<DriverUpload> implements DriverUploadService<DriverUpload> {
    private final Logger log = Logger.getLogger(DriverUploadServiceImpl.class);//输出Log日志
    private DriverUploadDao<DriverUpload> driverUploadDao;

    @Resource(name = "DriverUploadDao")
    public void setDriverUploadDao(DriverUploadDao<DriverUpload> driverUploadDao) {
        this.driverUploadDao = driverUploadDao;
        super.setBaseDao(driverUploadDao);
    }


    public File driverUpload(MultipartFile file,String path, String driverId) throws Exception {
        File targetFile=null;
        String fileName=file.getOriginalFilename();//获取文件名加后缀
        String sqlPath="images/driverUpload/";
            if (fileName != null && fileName != "") {
                //文件后缀
                String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                if(!(fileF.equals(".JPG")|| fileF.equals(".GIF")||fileF.equals(".PNG")||fileF.equals(".jpg")||fileF.equals(".jpeg"))){
                    throw new Exception("请上传图片格式！");
                }
                //新的文件名
                String newFileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileF;
                sqlPath=sqlPath+newFileName;
                targetFile = new File(path);
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }

                FileOutputStream imgOut = new FileOutputStream(new File(targetFile, newFileName));
                imgOut.write(file.getBytes());//返回一个字节数组文件的内容】
                imgOut.close();

                DriverUpload du= new DriverUpload();
                DriverDaoImpl driverDao = SpringUtils.getBean(DriverDaoImpl.class);
                Driver driver=driverDao.selectBean(driverId);
                du.setDriver(driver);
                du.setPath(sqlPath);
                du.setNewName(newFileName);
                du.setOldName(fileName);
                driverUploadDao.savaBean(du);

        }
        return targetFile;
    }


    public List<DriverUpload> showCredentials(String driverId) throws Exception {

        List<DriverUpload> dulist=driverUploadDao.executeQueryHql("from JC_DRIVERUPLOAD where JC_DRIVER_ID='"+driverId+"'");

        return dulist;
    }

}
