<%--
  Created by IntelliJ IDEA.
  User: sundezeng
  Date: 2018/12/3
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>

<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <script src="/Content/scripts/jquery/jquery-1.10.2.min.js"></script>
    <link href="/Content/styles/font-awesome.min.css" rel="stylesheet" />
    <link href="/Content/scripts/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet" />
    <script src="/Content/scripts/plugins/jquery-ui/jquery-ui.min.js"></script>
    <link href="/Content/scripts/bootstrap/bootstrap.min.css" rel="stylesheet" />
    <link href="/Content/scripts/bootstrap/bootstrap.extension.css" rel="stylesheet" />
    <script src="/Content/scripts/bootstrap/bootstrap.min.js"></script>
    <script src="/Content/scripts/plugins/datepicker/WdatePicker.js"></script>
    <link href="/Content/scripts/plugins/tree/tree.css" rel="stylesheet"/>
    <link href="/Content/scripts/plugins/datetime/pikaday.css" rel="stylesheet"/>
    <link href="/Content/scripts/plugins/wizard/wizard.css" rel="stylesheet"/>
    <link href="/Content/styles/jet-ui.css" rel="stylesheet"/>
    <script src="/Content/scripts/plugins/tree/tree.js"></script>
    <script src="/Content/scripts/plugins/validator/validator.js"></script>
    <script src="/Content/scripts/plugins/datepicker/DatePicker.js"></script>
    <script src="/Content/scripts/utils/jet-ui.js"></script>
    <script src="/Content/scripts/utils/jet-form.js"></script>
    <script src="/Content/scripts/utils/jquery.jqprint-0.3.js"></script>
    <script src="/Content/scripts/plugins/jquery.md5.js"></script>
    <script src="/Content/scripts/utils/jet-pinyin.js"></script>
    <%--???????????????--%>
    <script src="/Content/lodopPrint/LodopFuncs.js"></script>
    <object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
        <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
    </object>


    <meta name=ProgId content=Excel.Sheet>
    <meta name=Generator content="Microsoft Excel 15">
</head>
<style>
    tr
    {mso-height-source:auto;
        mso-ruby-visibility:none;}
    col
    {mso-width-source:auto;
        mso-ruby-visibility:none;}
    br
    {mso-data-placement:same-cell;}
    ruby
    {ruby-align:left;}
    .style0
    {mso-number-format:General;
        text-align:general;
        vertical-align:bottom;
        white-space:nowrap;
        mso-rotate:0;
        mso-background-source:auto;
        mso-pattern:auto;
        color:black;
        font-size:11.0pt;
        font-weight:400;
        font-style:normal;
        text-decoration:none;
        font-family:??????;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        border:none;
        mso-protection:locked visible;
        mso-style-name:??????;
        mso-style-id:0;}
    td
    {mso-style-parent:style0;
        padding-top:1px;
        padding-right:1px;
        padding-left:1px;
        mso-ignore:padding;
        color:black;
        font-size:11.0pt;
        font-weight:400;
        font-style:normal;
        text-decoration:none;
        font-family:??????;
        mso-generic-font-family:auto;
        mso-font-charset:134;
        mso-number-format:General;
        text-align:general;
        vertical-align:bottom;
        border:none;
        mso-background-source:auto;
        mso-pattern:auto;
        mso-protection:locked visible;
        white-space:nowrap;
        mso-rotate:0;}
    .xl65
    {mso-style-parent:style0;
        font-size:9.0pt;}
    .xl66
    {mso-style-parent:style0;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border:.5pt solid windowtext;}
    .xl67
    {mso-style-parent:style0;
        font-size:10.0pt;
        text-align:center;
        vertical-align:middle;
        border:.5pt solid windowtext;}
    .xl68
    {mso-style-parent:style0;
        font-size:10.0pt;
        border:.5pt solid windowtext;}
    .xl69
    {mso-style-parent:style0;
        font-size:12.0pt;
        font-weight:700;
        text-align:center;
        vertical-align:middle;
        border:.5pt solid windowtext;}
</style>
<script>

    var keyValue = request('keyValue');
    //
    //        //???????????????
    $(function () {
        $.ajax({
            url: "/shipment/getShipmentPrint.action",
            data: {id:keyValue},
            async:false,
            type: "post",
            dataType: "json",
            success: function (data) {
                $("#shipmentTable").html(data.obj);
            },
        });
    });
    var LODOP; //?????????????????????
    function AcceptClick(){
        LODOP=getLodop();
        LODOP.PRINT_INIT("???????????????");
        LODOP.SET_PRINT_PAGESIZE(2,2970,2100,"A4");
        LODOP.SET_PRINT_STYLEA(0,"TextNeatRow",true);
        LODOP.ADD_PRINT_HTM(0,-18,"75%",1200,document.documentElement.innerHTML);
        LODOP.PREVIEW();//????????????
        LODOP.PRINT_DESIGN();//????????????
    }
</script>

<body link="#0563C1" vlink="#954F72">


<table id = "shipmentTable"  border=0 cellpadding=0 cellspacing=0 width=574 style='border-collapse:
 collapse;table-layout:fixed;width:431pt'>

</table>

</body>

</html>
