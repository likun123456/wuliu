<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width" />
    <title>系统登录</title>
    <link href="/Content/css/login.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all" />
    <link href="/Content/css/demo.css" rel="stylesheet" rev="stylesheet" type="text/css" media="all" />
    <script type="text/javascript" src="/Content/js/jquery1.42.min.js"></script>
    <script type="text/javascript" src="/Content/js/jquery.SuperSlide.js"></script>
    <script type="text/javascript" src="/Content/js/Validform_v5.3.2_min.js"></script>
    <script src="/Content/scripts/jquery/jquery-1.10.2.min.js"></script>
    <link href="/Content/styles/jet-login.css" rel="stylesheet" />
    <link href="/Content/styles/font-awesome.min.css" rel="stylesheet" />
    <script src="/Content/scripts/plugins/jquery.md5.js"></script>
    <script src="/Content/scripts/plugins/cookie/jquery.cookie.js"></script>
    <script src="/Content/scripts/plugins/dialog/dialog.js"></script>
    <script src="/Content/scripts/utils/jet-ui.js"></script>
    <script src="/Content/scripts/plugins/validator/validator.js"></script>
    <script src="/Content/scripts/plugins/tipso.min.js"></script>
    <script src="/Content/scripts/utils/jet-form.js"></script>

    <script>
//        $(function(){
//
//            $(".i-text").focus(function(){
//                $(this).addClass('h-light');
//            });
//
//            $(".i-text").focusout(function(){
//                $(this).removeClass('h-light');
//            });
//
//            $("#username").focus(function(){
//                var username = $(this).val();
//                if(username=='输入账号'){
//                    $(this).val('');
//                }
//            });
//
//            $("#username").focusout(function(){
//                var username = $(this).val();
//                if(username==''){
//                    $(this).val('输入账号');
//                }
//            });
//
//
//            $("#password").focus(function(){
//                var username = $(this).val();
//                if(username=='输入密码'){
//                    $(this).val('');
//                }
//            });
//
//
//            $("#yzm").focus(function(){
//                var username = $(this).val();
//                if(username=='输入验证码'){
//                    $(this).val('');
//                }
//            });
//
//            $("#yzm").focusout(function(){
//                var username = $(this).val();
//                if(username==''){
//                    $(this).val('输入验证码');
//                }
//            });
//
//
//
//            $(".registerform").Validform({
//                tiptype:function(msg,o,cssctl){
//                    var objtip=$(".error-box");
//                    cssctl(objtip,o.type);
//                    objtip.text(msg);
//                },
//                ajaxPost:true
//            });
//
//        });

        var getcontentPath ="http://"+window.location.host+"/";
        var contentPath =getcontentPath;// 获取应用路径
        var isIE = !!window.ActiveXObject;
        var isIE6 = isIE && !window.XMLHttpRequest;
        if (isIE6) {
            window.location.href = contentPath + "jsp/error/errorBrowser.jsp";//错误页面
        }
        //回车键
        document.onkeydown = function (e) {
            if (!e) e = window.event;
            if ((e.keyCode || e.which) == 13) {
                var btlogin = document.getElementById("btnlogin");
                btnlogin.click();
            }
        }
        //初始化
        $(function () {
            $(".wrap").css("margin-top", ($(window).height() - $(".wrap").height()) / 2 - 35);
            $(window).resize(function (e) {
                $(".wrap").css("margin-top", ($(window).height() - $(".wrap").height()) / 2 - 35);
                e.stopPropagation();
            });
            //错误提示
            //是否自动登录
            if (top.$.cookie('learn_autologin') == 1) {
                $("#autologin").attr("checked", 'true');
                $("#username").val(top.$.cookie('learn_username'));
                $("#password").val(top.$.cookie('learn_password'));
                CheckLogin(1);
            }
            //设置下次自动登录
            $("#autologin").click(function () {
                if (!$(this).attr('checked')) {
                    $(this).attr("checked", 'true');
                    top.$.cookie('learn_autologin', 1, { path: "/", expires: 7 });
                } else {
                    $(this).removeAttr("checked");
                    top.$.cookie('learn_autologin', '', { path: "/", expires: -1 });
                    top.$.cookie('learn_username', '', { path: "/", expires: -1 });
                    top.$.cookie('learn_password', '', { path: "/", expires: -1 });
                }
            });
            //主题风格
            var learn_UItheme = top.$.cookie('learn_UItheme')
            if (learn_UItheme) {
                $("#UItheme").val(learn_UItheme);
            }
            //登录按钮事件
            $("#btnlogin").click(function () {
                var $username = $("#username");
                var $password = $("#password");
                if ($username.val() == "") {
                    $username.focus();
                    formMessage('请输入账户');
                    return false;
                } else if ($password.val() == "") {
                    $password.focus();
                    formMessage('请输入密码');
                    return false;
                } else {
                    CheckLogin(0);
                }
            })
            //切换登录表单

            window.onload = function () {
                if (window.parent.window != window) {
                    window.top.location = "/jsp/login/index.jsp";
                }
            }
        })
        //登录验证
        function CheckLogin(autologin) {
            $("#btnlogin").addClass('active').attr('disabled', 'disabled');
            $("#btnlogin").find('span').hide();
            var username = $.trim($("#username").val());
            var password = $.trim($("#password").val());
            $.ajax({
                url: contentPath + "login/login.action",
                data: { code: $.trim(username), password:  $.md5($.trim(password))},
                type: "post",
                dataType: "JSON",
                success: function (data) {
                    if (data.type) {
                        window.location.href = contentPath + 'jsp/home/adminDefault.jsp';
                    }
                    else{
                        alert(data.obj, 0)
                        //dialogAlert(data.obj, 0);
                        $("#btnlogin").removeClass('active').removeAttr('disabled');
                        $("#btnlogin").find('span').show();
                    }
                }
            });
//            window.location.href = contentPath + 'jsp/home/adminDefault.jsp';
        }
        //提示信息
        function formMessage(msg, type) {
            $('.login_tips').parents('dt').remove();
            var _class = "login_tips";
            if (type == 1) {
                _class = "login_tips-succeed";
            }
            $('.form').prepend('<dt><div class="' + _class + '"><i class="fa fa-question-circle"></i>' + msg + '</div></dt>');
        }



    </script>


</head>

<body>


<div class="banner">

    <div class="login-aside">
        <div id="loginform" class="container" style="opacity:0.95 ">
        <fieldset class="box">
            <legend>欢迎登陆<a href="http://www.qiqucode.com">奇趣源码商城</a>物流系统</legend>
            <dl class="form">
                <dd>
                    <input id="username" type="text" class="px" placeholder="账户"></dd>
                <dd>
                    <input id="password" type="password" class="px" placeholder="密码"></dd>
                <dd>
                    <span class="y"><a href="#" onclick="alert('请访问：www.qiqucode.com')">忘记密码了？</a></span>
                    <label style="cursor: pointer;">
                        <input id="autologin" type="checkbox" class="pc" style="margin-bottom: 4.5px; margin-right: 0px; vertical-align: middle;">
                        <span>下次自动登录</span>
                    </label>
                </dd>
                <dd class="btn">
                    <button id="btnlogin" type="button" class="pn"><span>登录</span></button>
                </dd>
            </dl>
        </fieldset>
    </div>

    </div>

    <div class="bd">
        <ul>
            <li style="background:url(/Content/111.jpg)   center 0 no-repeat;"><a target="_blank"></a></li>
            <%--<li style="background:url(../RoadVehicles/111.jpg) #BCE0FF center 0 no-repeat;"><a target="_blank" href="#"></a></li>--%>
        </ul>
    </div>

    <div class="hd"><ul></ul></div>
</div>
<div class="copyright" style="margin-left: 10px">
    ${Company}


</body>
</html>
