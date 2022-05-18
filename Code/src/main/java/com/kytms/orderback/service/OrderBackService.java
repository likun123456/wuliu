package com.kytms.orderback.service;

import com.kytms.core.exception.MessageException;
import com.kytms.core.model.CommModel;
import com.kytms.core.model.JgGridListModel;
import com.kytms.core.service.BaseService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 *
 * @author
 * @create 2018-01-19
 */
public interface OrderBackService<OrderBack> extends BaseService<OrderBack> {
    JgGridListModel getOrderBackList(CommModel commModel);
    void receive(CommModel commModel) throws MessageException;
    void submit(CommModel commModel) throws MessageException;

    File driverUpload(MultipartFile file, String path, String driverId,String url) throws IOException;

    List<Map> getImages(String id);

    JgGridListModel getOrderYQS(CommModel commModel);
}
