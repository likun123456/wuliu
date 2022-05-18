<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/report/runqianReport4.tld" prefix="report" %>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.runqian.report4.usermodel.Context"%>

<html>
<body topmargin=0 leftmargin=0 rightmargin=0 bottomMargin=0>
<%
    request.setCharacterEncoding( "GBK" );
    String report = request.getParameter( "raq" );
    String reportFileHome=Context.getInitCtx().getMainDir();
    StringBuffer param=new StringBuffer();

    //��֤�������Ƶ�������
    int iTmp = 0;
    if( (iTmp = report.lastIndexOf(".raq")) <= 0 ){
        report = report + ".raq";
        iTmp = 0;
    }

    Enumeration paramNames = request.getParameterNames();
    if(paramNames!=null){
        while(paramNames.hasMoreElements()){
            String paramName = (String) paramNames.nextElement();
            String paramValue=request.getParameter(paramName);
            if(paramValue!=null){
                //�Ѳ���ƴ��name=value;name2=value2;.....����ʽ
                param.append(paramName).append("=").append(paramValue).append(";");
            }
        }
    }

    //���´����Ǽ����������Ƿ�����Ӧ�Ĳ���ģ��
    String paramFile = report.substring(0,iTmp)+"_arg.raq";
    File f=new File(application.getRealPath(reportFileHome+ File.separator +paramFile));

%>
<jsp:include page="toolbar.jsp" flush="false" />
<table id=rpt align=center><tr><td>
        <%	//���´����Ǽ����������Ƿ�����Ӧ�Ĳ���ģ��
	if( f.exists() ) {
	%>
    <table id=param_tbl><tr><td>
        <report:param name="form1" paramFileName="<%=paramFile%>"
                      needSubmit="no"
                      params="<%=param.toString()%>"

        />
    </td>
        <td><a href="javascript:_submit( form1 )"><img src="../images/query.jpg" border=no style="vertical-align:middle"></a></td>
    </tr></table>
        <% }
%>
    <%--<script language=javascript>--%>
    <%--function printpreview(){--%>
    <%--// ��ӡҳ��Ԥ��--%>
    <%--wb.execwb(7,1);--%>

    <%--}--%>
    <%--function printit(){--%>

    <%--if (confirm('ȷ����ӡ��')){--%>
    <%--wb.execwb(6,1);--%>
    <%--}--%>
    <%--}--%>

    <%--</script>--%>

    <table align=center>
        <tr><td>
            <report:html name="report1" reportFileName="<%=report%>"
                         funcBarLocation="top"
                         needPageMark="yes"
                         generateParamForm="no"
                         needDirectPrint="yes"
                         params="<%=param.toString()%>"
                         exceptionPage="/reportJsp/myError2.jsp"
                         needPrint="yes"
            />
        </td></tr>
        <%--<div class="noprint" style="width:640px;height:20px;margin:100px auto 0 auto;font-size:12px;text-align:right;">--%>

        <%--<OBJECT id="wb" height="0" width="0" classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" name="wb"></OBJECT>--%>

        <%--<input value="��ӡ" type="button" onclick="javascript:printit();" />--%>

        <%--<input type=button name=button_setup value="��ӡҳ������" onclick="javascript:printsetup();">--%>

        <%--<input type=button name=button_show value="��ӡԤ��" onclick="javascript:printpreview();">--%>

        <%--<input type=button name=button_fh value="�ر�" onclick="javascript:window.close();">--%>

        <%--</div>--%>
    </table>



    <script language="javascript">
        //���÷�ҳ��ʾֵ
        document.getElementById( "t_page_span" ).innerHTML=report1_getTotalPage();
        document.getElementById( "c_page_span" ).innerHTML=report1_getCurrPage();
    </script>
</body>
</html>
