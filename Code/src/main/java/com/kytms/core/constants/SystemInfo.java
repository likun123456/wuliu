package com.kytms.core.constants;

/**
 * 奇趣源码商城 www.qiqucode.com
 * 奇趣源码
 * 系统全局变量
 *
 * @author
 * @create 2017-11-17
 */
public abstract class SystemInfo {
    /**Java 运行时环境版本 */
    public static final String JAVA_VERSION;
    /** 用户的当前工作目录*/
    public static final String JAVA_USER_DIR;
    /** 用户的主目录*/
    public static final String JAVA_USER_HOME;
    /** 用户的账户名称*/
    public static final String JAVA_USER_NAME;
    /** 行分隔符（在 UNIX 系统中是“/n”）*/
    public static final String JAVA_LINE_SEPARATOR;
    /** 路径分隔符（在 UNIX 系统中是“:”）*/
    public static final String JAVA_PATH_SEPARATOR;
    /** 文件分隔符（在 UNIX 系统中是“/”）*/
    public static final String JAVA_FILE_SEPARATOR;
    /** 操作系统的版本 */
    public static final String JAVA_OS_VERSION;
    /**操作系统的架构*/
    public static final String JAVA_OS_ARCH;
    /**操作系统名称*/
    public static final String JAVA_OS_NAME;
    /**默认的临时文件路径*/
    public static final String JAVA_IO_TEMDIR;
    /**类路径*/
    public static final String JAVA_CLASS_PATH;
    static {
        JAVA_VERSION=System.getProperty("java.version");
        JAVA_USER_DIR=System.getProperty("user.dir");
        JAVA_USER_HOME=System.getProperty("user.home");
        JAVA_USER_NAME=System.getProperty("user.name");
        JAVA_LINE_SEPARATOR=System.getProperty("line.separator");
        JAVA_PATH_SEPARATOR=System.getProperty("path.separator");
        JAVA_FILE_SEPARATOR=System.getProperty("file.separator");
        JAVA_OS_VERSION=System.getProperty("os.version");
        JAVA_OS_ARCH = System.getProperty("os.arch");
        JAVA_OS_NAME= System.getProperty("os.name");
        JAVA_IO_TEMDIR= System.getProperty("java.io.tmpdir");
        JAVA_CLASS_PATH= System.getProperty("java.class.path");
    }
}
