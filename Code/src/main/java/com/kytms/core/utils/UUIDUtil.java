package com.kytms.core.utils;

import java.util.UUID;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * UUID生成工具
 *
 * @author 奇趣源码
 * @create 2017-11-19
 */
public abstract class UUIDUtil {
    /**
     * 获得一个32位UUID
     * @return
     */
    public static String getUuidTo32() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid.toString();
    }
}
