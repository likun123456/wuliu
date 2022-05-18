package com.kytms.core.exception;

import org.apache.log4j.Logger;

/**
 * 自定义异常，消息传递
 *
 * @author nidaye
 */
public class MessageException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private final Logger log = Logger.getLogger(MessageException.class);//输出Log日志


    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
