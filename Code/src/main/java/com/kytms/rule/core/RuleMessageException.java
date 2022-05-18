package com.kytms.rule.core;

/**
 * QQ 165149324
 * 奇趣源码
 *
 * @author
 * @create 2019-08-02
 */
public class RuleMessageException extends Exception {


    public RuleMessageException() {
    }

    public RuleMessageException(String message) {
        super(message);
    }

    public RuleMessageException(Throwable cause) {
        super(cause);
    }

    public RuleMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
