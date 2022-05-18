package com.kytms.core.utils;

/**
 * 奇趣源码商城 www.qiqucode.com
 *
 * String工具类
 *
 * @author 奇趣源码
 * @create 2017-11-15
 */
public abstract class StringUtils {
    public static final String SPLIT_SQL = ",";
    /**
     * 是否是文本
     * @param str
     * @return
     */
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是文本
     * @param str
     * @return
     */
    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }
    /**
     * 判定字符串长度 不为空和0
     * @param str
     * @return
     */
    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * 判定字符串 为空和0
     * @param str
     * @return
     */
    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }
    /**
     * 校验一个字符串是否不为null,或除空格外长度为0
     * @param str 字符串值
     * @return
     */
    public static Boolean isNotEmpty(String str) {
        if(null != str && 0 != str.trim().length() && !"NULL".equalsIgnoreCase(str) ) {
            return true;
        }
        return false;
    }

    /**
     * 检验一个字符串是否为null.
     * @param str
     * @return
     */
    public static Boolean isEmpty(String str) {
        return !isNotEmpty(str);
    }
    /**
     * 将字符串转为double，出错则返回0.0
     * @param str
     * @return
     */
    public static Double stringToDouble(String str) {
        try {
            Double d = 0.0;
            if(isNotEmpty(str)) {
                d = Double.parseDouble(str);
            } else {
                d = 0.0;
            }
            return d;
        } catch(NumberFormatException ex) {
            return 0.0;
        }

    }
    /**
     * 移除多个逗号分割的逗号字符   错误情况例如： ,1,2    1,,2   1,2,  经处理后返回  1,2
     * @param value
     * @return
     */
    public static String replaceSplit(String value){
        if(StringUtils.isEmpty(value)){
            return "";
        }
        while(value.indexOf(",,")>=0){
            value=value.replaceAll(",,", ",");
        }
        if(value.indexOf(",")==0){
            value=value.substring(1);
        }
        if(value.length()>0 && value.lastIndexOf(",")==(value.length()-1)){
            value=value.substring(0,value.length()-1);
        }
        return value;
    }
    /**
     * 字符串转Unicode
     * @param str
     * @return
     */
    public static String enUnicode(String str){
        str = (str == null ? "" : str);
        String tmp;
        StringBuffer sb = new StringBuffer(1000);
        char c;
        int i, j;
        sb.setLength(0);
        for (i = 0; i < str.length(); i++)
        {
            c = str.charAt(i);
            sb.append("\\u");
            j = (c >>>8); //取出高8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);
            j = (c & 0xFF); //取出低8位
            tmp = Integer.toHexString(j);
            if (tmp.length() == 1)
                sb.append("0");
            sb.append(tmp);

        }
        return (new String(sb));
    }
    /**
     * Unicode转字符串
     * @param str 待转字符串
     * @return 普通字符串
     */
    public static String deUnicode(String str)
    {
        str = (str == null ? "" : str);
        if (str.indexOf("\\u") == -1)//如果不是unicode码则原样返回
            return str;

        StringBuffer sb = new StringBuffer(1000);

        for (int i = 0; i < str.length();)
        {
            String strTemp = str.substring(i, i + 6);
            String value = strTemp.substring(2);
            int c = 0;
            for (int j = 0; j < value.length(); j++)
            {
                char tempChar = value.charAt(j);
                int t = 0;
                switch (tempChar)
                {
                    case 'a':
                        t = 10;
                        break;
                    case 'b':
                        t = 11;
                        break;
                    case 'c':
                        t = 12;
                        break;
                    case 'd':
                        t = 13;
                        break;
                    case 'e':
                        t = 14;
                        break;
                    case 'f':
                        t = 15;
                        break;
                    default:
                        t = tempChar - 48;
                        break;
                }

                c += t * ((int) Math.pow(16, (value.length() - j - 1)));
            }
            sb.append((char) c);
            i = i + 6;
        }
        return sb.toString();
    }
    /**
     * 转化字符串为十六进制编码
     * @param s
     * @param mark 是否启用16进制标记
     * @return
     */
    public static String enHex(String s,Boolean mark) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return (mark==false?"":"0x") + str;// 0x表示十六进制
    }

    /**
     * 转换十六进制编码为字符串
     * @param s
     * @return
     */
    public static String deHex(String s) {
        if ("0x".equals(s.substring(0, 2))) {
            s = s.substring(2);
        }
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            s = new String(baKeyword, "utf-8");// UTF-16le:Not
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public static String BySqlIn(String[] id) {
        StringBuilder sb = new StringBuilder(id.length*33);
        for (int i = 0; i <id.length ; i++) {
            sb.append("'"+id[i]+"',");
        }
        sb.deleteCharAt(sb.lastIndexOf(SPLIT_SQL));
        return sb.toString();
    }
    public static String BySqlIn(String ids) {
        String[] id = ids.split(SPLIT_SQL);
        return BySqlIn(id);
    }

    /**
     * 判定两个字符串是否相等
     * @return
     */
    public static boolean strEquals(String str1,String str2){
        if (str1 == null && str2 == null){
            return true;
        }
        if (str1 !=null && str2 == null){
            return false;
        }
        if (str1 ==null && str2 != null){
            return false;
        }
        return str1.equals(str2);
    }
}
