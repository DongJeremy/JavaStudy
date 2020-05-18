<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"></meta>
<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
<meta name="renderer" content="webkit" />
<title>登录后台管理系统</title>
<link rel="icon" href="static/favicon.ico" type="image/x-icon" >
<link rel="stylesheet" href="<%=basePath%>webjars/layui/css/layui.css">
<link rel="stylesheet" href="<%=basePath%>webjars/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=basePath%>static/css/zadmin.css" media="all">
<link rel="stylesheet" href="<%=basePath%>static/css/login.css">
</head>
<body class="layui-layout-login">
    <div class="login-bg">
        <div class="cover"></div>
    </div>
    <div class="login-content ${isCaptcha?'captcha':''}">
        <h1 class="login-box-title"><i class="fa fa-fw fa-user"></i>登录</h1>
        <form class="layui-form">
            <div class="layui-form-item">
                <label class="layui-icon layui-icon-username" for="username"></label>
                <input class="layui-input" type="text" name="username" id="username" placeholder="用户名" 
                    lay-verify="required" lay-vertype="tips">
            </div>
            <div class="layui-form-item">
                <label class="layui-icon layui-icon-password" for="password"></label>
                <input class="layui-input" type="password" name="password" id="password" placeholder="密码" 
                    lay-verify="required" lay-vertype="tips">
            </div>
            <c:if test="${isCaptcha}">
            <div class="layui-form-item captcha-item">
                <label class="layui-icon layui-icon-vercode"></label>
                <input class="layui-input" type="text" name="captcha" autocomplete="off" placeholder="验证码">
                <img class="captcha-img" src="<%=basePath%>captcha" />
            </div>
            </c:if>
            <div class="layui-form-item">
                <input type="checkbox" name="rememberMe" title="记住我" lay-skin="primary">
                <a class="layui-layout-right forget-password" href="javascript:alert('请联系超级管理员！')">忘记密码?</a>
            </div>
            <button lay-submit lay-filter="login" class="layui-btn layui-btn-fluid ajax-login"><i class="fa fa-sign-in fa-lg fa-fw"></i> 登录</button>
        </form>
    </div>
    <jsp:include page="admin/common/footer.jsp"/>
    <script type="text/javascript">
        function renderDone(data) {
            var winObj = window.open(data, 'newwindow', 'height=500, width=500, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
            var loop = setInterval(function() {
                if(winObj.closed) {
                    clearInterval(loop);
                    window.location.href = "<%=basePath%>admin/main";
                }
            }, 1000);
        }
        
        // 如果在 iframe 中, 则跳出 iframe
        if (self !== top) {
            top.location.href = location.href;
        }
        $(function () {
            layui.use(['form'], function () {
                // 操作对象
                var form = layui.form
                     , $ = layui.jquery;
                $(document).on('click', '.captcha-img', function () {
                    var src = this.src.split("?")[0];
                    this.src = src + "?" + Math.random();
                });
                form.on('submit(login)', function (data) {
                    $.post('<%=basePath%>login', data.field, function (result) {
                        if(result.code !== 200){
                            $('.captcha-img').click();
                        }
                        handlerResult(result, loginDone);
                    });
                    return false;
                });
            });
        });


        function generateCaptcha(obj) {
            $(obj).attr("src", "captcha?timestamp=" + (new Date()).valueOf());
        }

        function loginDone(obj) {
            layer.msg("登陆成功, 即将跳转到首页!", {
                icon: 1,
                time: 500
            }, function () {
                location.href = '<%=basePath%>admin';
            });
        }
    </script>
</body>
</html>