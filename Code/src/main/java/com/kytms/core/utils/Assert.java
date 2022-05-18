package com.kytms.core.utils;

import com.kytms.core.exception.AppBugException;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * 断言工具类
 *
 * @author 奇趣源码
 * @create 2017-11-15
 */
public abstract class Assert {
    /**
     * 不是真 抛出异常
     *
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new AppBugException(message);
        }
    }
    /**
     * 不是真 抛出异常
     * @param expression
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    /**
     * 如果是空 抛出异常
     * @param object
     * @param message
     */
    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new AppBugException(message);
        }
    }

    /**
     * 对象为空 抛出异常
     * @param object
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }
    /**
     *  不能为空
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {
        if (object != null) {
            throw new AppBugException(message);
        }
    }
    /**
     * 不能为空
     * @param object
     */
    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     * 判定Sting 长度 如果为空 抛出异常
     * @param text
     * @param message
     */
    public static void hasLength(String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new AppBugException(message);
        }
    }

    /**
     * 判定Sting 长度 如果为空 抛出异常
     * @param text
     */
    public static void hasLength(String text) {
        hasLength(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }
    /**
     * 是否是文本
     * @param text
     * @param message
     */
    public static void hasText(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new AppBugException(message);
        }
    }
    /**
     * 是否是文本
     * @param text
     */
    public static void hasText(String text) {
        hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }
}
