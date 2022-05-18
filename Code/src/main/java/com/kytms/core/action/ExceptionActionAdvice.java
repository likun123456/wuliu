package com.kytms.core.action;/**
 * Created by nidaye on 2018/10/16.
 */

import com.kytms.core.exception.AppBugException;
import com.kytms.core.exception.MessageException;
import com.kytms.core.model.ReturnModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 全局异常拦截
 *
 * @author
 * @create 2018-10-16
 */
@ControllerAdvice
@ResponseBody
public class ExceptionActionAdvice extends BaseAction{

    private final Logger logger = LoggerFactory.getLogger(ExceptionActionAdvice.class);
    @ExceptionHandler(Exception.class)
    public String ExceptionHandler(Exception ex){
        logger.error(ex.getMessage());
        logger.error(ex.getLocalizedMessage());
        ex.printStackTrace();
        return null;
    }

    @ExceptionHandler(RuntimeException.class)
    public String RuntimeExceptionHandler(RuntimeException ex){
        logger.error(ex.getMessage());
        logger.error(ex.getLocalizedMessage());
        ex.printStackTrace();
        return null;
    }
    /**
     * 全局自定义异常捕获
     * @param ex
     * @return
     */
    @ExceptionHandler(AppBugException.class)
    public String AppBugExceptionHandler(AppBugException ex){
        return this.returnFalseModel(ex);
    }

    /**
     * 全局自定义异常捕获
     * @param ex
     * @return
     */
    @ExceptionHandler(MessageException.class)
    public String MessageExceptionHandler(MessageException ex){
        return this.returnFalseModel(ex);
    }

    /**
     * 返回参数封装
     * @param ex
     * @return
     */
    private String returnFalseModel(RuntimeException ex){
        ReturnModel returnModel = getReturnModel();
        returnModel.setResult(false);
        returnModel.setType(returnModel.STRING_FALSE);
        returnModel.setObj(ex.getMessage());
        return  returnModel.toJsonString();
    }
}
