<%@ page contentType="text/html;charset=gb2312" %>
<%@ taglib uri="/WEB-INF/report/runqianReport4.tld" prefix="report"%>
<%@ page session="true"  import="java.lang.StringBuffer,com.runqian.report4.usermodel.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.runqian.report4.view.*"%>
<%@ page import="com.runqian.base4.util.*"%>

<html>
<head>
<title>�Ƿ��ӡ</title>
<LINK rel="stylesheet" type="text/css" href="styles.css">
<style type="text/css">
<!--
.style1 {
    font-size: 18px;
    color: #0000FF;
}
-->
</style>
<style>
a{TEXT-DECORATION:none}.style3 {
    font-size: 16px;
    font-family: "����";
    color: #0000FF;
}
.style4 {color: #666666}
.style5 {font-size: 14pt}
.style6 {color: #0000FF}
.style7 {color: #000000}
.style15 {font-family: "������κ"}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312"></head>
<body onload="directPrint()">
<p align="center" class="style4 style1"><strong><span class="style6"><span class="style7"><span class="style15">�Ƿ��ӡ?</span></span></span></strong></p>
<div align="center">
  <%
    //��JSP������ʽΪ��report={�޲���������}{����1(����1=value1;����2=value2;...)}{����2(����1=value1;����2=value2;...)}...prompt=yes
    request.setCharacterEncoding( "GBK" );
      String report = request.getParameter( "report" );
      if( report == null || report.trim().length() == 0 ) throw new Exception( "�����뱨���ļ�����������report={�޲���������}{����1(����1=value1;����2=value2;...)}{����2(����1=value1;����2=value2;...)}..." );
    String prompt = request.getParameter( "prompt" );
    
      String appmap = request.getContextPath();
      String serverPort = String.valueOf( request.getServerPort() );
    String serverName = request.getServerName();
    String appRoot = "http://" + serverName + ":" + serverPort + appmap;
    //String appRoot = appmap;
%>
  
  <object classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"    codebase="<%=appRoot%>/j2re-1_4_1-windows-i586-i.exe#Version=1,4,1,0"    width="40" height="16" id="report1_directPrintApplet" name="report1_directPrintApplet" style="vertical-align:middle">    
          <param name="name" value="report1_directPrintApplet">    
          <param name="code" value="com.runqian.report4.view.applet.DirectPrintApplet.class">    
          <param name="archive" value="<%=appmap%>/runqianReport4Applet.jar">    
          <param name="type" value="application/x-java-applet;version=1.4">    
          <param name="appRoot" value="<%=appRoot%>">    
          <param name="dataServlet" value="/reportServlet?action=25">    
        
          <param name="srcType" value="file">    
          <param name="fontFace" value="����">    
          <param name="fontSize" value="18pt">    
          <param name="fontColor" value="#0000FF">    
          <param name="backColor" value="#12632256">    
          <param name="label" value="��ӡ">    
          <param name="needPrintPrompt" value="<%=prompt%>">
          <param name="needSelectPrinter" value="yes">
          <param name="needSetPageSize" value="no">
          
  </object>
       <a href="javascript:window.close()" class="style3 style5">ȡ��</a>
    <script language=javascript>
    //�˺������صĸ�ʽΪ��"{�޲���������}{����1(����1=value1;����2=value2;...)}{����2(����1=value1;����2=value2;...)}..."
    function runqian_getPrintReports() {
        //���ڴ˺�������ϴ�ӡǰ��Ҫ�Ĵ�����󷵻���Ҫ��ӡ�ı���
        return "<%=report%>";
    }

    function directPrint(){
			report1_directPrintApplet.print();
	}
  </script>


  
</div>
<input type="button" name="DP" value="DP" onclick="directPrint()">
</body>
</html>