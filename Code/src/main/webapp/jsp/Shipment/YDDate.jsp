<%--
  Created by IntelliJ IDEA.
  User: sundezeng
  Date: 2019/4/17
  Time: 11:43
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
    <script src="/Content/scripts/plugins/jquery.md5.js"></script>
    <script src="/Content/scripts/utils/jet-pinyin.js"></script>
</head>
<body>
<script>
    var keyValue = request('keyValue');
    var shipment = request('shipment');
    //???????????????
    $(function () {
    });
    //????????????
    function AcceptClick(win) {
        if ($("#time").val() == null || $("#time").val() == undefined ||$("#time").val() ==""){
            ValidationMessage($("#time"),"???????????????")
            return
        }
        $.SaveFormAsync({
            url: "/shipment/updateYDTime.action",
            param:{id:keyValue,date:$("#time").val(),shipment:shipment},
            loading: "??????????????????...",
            success: function (data) {
                if(data.result){
                    dialogMsg('????????????', 1);
                    win.reload();
                    dialogClose();//????????????
                }else {
                    dialogMsg(data.obj, 0);
                }
            }
        })
    }
</script>
<table class="form">
    <tr >
        <th style="padding-top: 60px;" class="formTitle">???????????????<font face="??????"></font></th>
        <td class="formValue" style="padding-top: 60px">
            <input id="time" type="text"  placeholder="????????????" readonly="readonly"  class="form-control input-wdatepicker" isvalid="yes"  checkexpession="NotNull"  onfocus="WdatePicker()"/>
    </tr>
</table>
</body>
</html>