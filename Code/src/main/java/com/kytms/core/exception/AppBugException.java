package com.kytms.core.exception;

import com.kytms.core.entity.ZoneStoreroom;
import org.apache.log4j.Logger;

/**
 * 自定义异常，当程序出现BUG的情况下，那么抛出该异常
 */
public class AppBugException extends RuntimeException {
    private final Logger log = Logger.getLogger(AppBugException.class);//输出Log日志

    public AppBugException() {
    }

    public AppBugException(String message) {
        super(message);
    }

    public AppBugException(Throwable cause) {
        super(cause);
    }

    public AppBugException(String message, Throwable cause) {
        super(message, cause);
    }
}
