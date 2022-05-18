主要功能：
基础数据，订单管理，客户管理，订单签收，库存查询，运单管理，站点管理。财务核算
    报表中心，异常处理，系统管理等

技术架构：
SpringMVC+Hibernat+Jquery + Boostrap

安装必要环境或软件
Jdk 1.8 、 Mysql 5.7 、 N avicat 、 IntelliJ IDEA 、 M av en 、 Tomcat 7 以上

### 建立wuliu数据库
1. 打开Mysql管理工
2. 执行wuliu.sql脚本

### 运行软件

1. 使用idea导入源码，更新jar包
2. 找到 db.properties 配置文件
3. 修改db.url 中的ip地址和端口和数据库名称(端口默认是**3306**，数据库默认是**wuliu**)
4. 修改db.username 数据库用户名
5. 修改db.password 数据库密码
6. 使用tomcat运行

访问 http://localhost:8080/jsp/login/index.jsp
> 默认的管理员帐号为test 密码为123456










