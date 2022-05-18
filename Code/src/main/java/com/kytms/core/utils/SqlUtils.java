package com.kytms.core.utils;

import com.kytms.core.constants.Symbol;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * SQL工具类
 *
 * @author 奇趣源码
 * @create 2018-01-18
 */
public abstract class SqlUtils {
    /**
     * 将数组字符串切割成in函数 如：'234','3123','123123'
     * @param split
     * @return
     */
    public static String splitForIn(String[] split){
        StringBuilder sb = new StringBuilder();
        for (String str:split) {
            sb.append(Symbol.DOT).append(str).append(Symbol.DOT).append(Symbol.COMMA);
        }
        sb.deleteCharAt(sb.lastIndexOf(Symbol.COMMA));
        return sb.toString();
    }
}
