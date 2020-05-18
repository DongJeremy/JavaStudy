<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/header.jsp"/>
</head>
<body class="animated fadeIn timo-layout-page">
  <div class="page-loading">
    <div class="rubik-loader"></div>
  </div>

  <div class="animated fadeIn">
    <form action="" method="post" class="layui-form layui-form-pane">
      <div class="layui-form-item">
        <label for="password0" class="layui-form-label"> 原密码 </label>
        <div class="layui-input-block">
          <input type="password" id="password0" name="password0" lay-verify="required" lay-vertype="tips"
            autocomplete="off" class="layui-input"> 
          <input type="hidden" name="id" value="${id}">
        </div>
      </div>
      <div class="layui-form-item">
        <label for="password1" class="layui-form-label"> 输入新密码 </label>
        <div class="layui-input-block">
          <input type="password" id="password1" name="password1" lay-verify="required" lay-vertype="tips"
            autocomplete="off" class="layui-input"> 
        </div>
      </div>
      <div class="layui-form-item">
        <label for="password2" class="layui-form-label"> 再次输入新密码 </label>
        <div class="layui-input-block">
          <input type="password" id="password2" name="password2" lay-verify="required" lay-vertype="tips"
            autocomplete="off" class="layui-input"> 
        </div>
      </div>
      <div class="layui-form-item">
        <div class="layui-input-block" style="margin-left: 0; text-align: center;">
          <button type="button" class="layui-btn layui-btn-normal btn-w100" lay-submit="" lay-filter="reset-password">确定</button>
        </div>
      </div>
    </form>
  </div>

  <jsp:include page="../common/footer.jsp"/>
  <script>
    layui.use(['form', 'layer'], function () {
        $ = layui.jquery;
        var form = layui.form
         , layer = layui.layer;
        var index = parent.layer.getFrameIndex(window.name);
        form.on('submit(reset-password)', function (form) {
            var data = form.field;
            if (data.password1!=data.password2) {
                layer.msg("密码两次输入不一致", {icon: 6});
                return false;
            }
            $.post('<%=basePath%>api/user/' + data.id + "/reset", form.field, function (result) {
                handlerResult(result, function (data) {
                    layer.msg("重置成功", {icon: 6});
                    parent.layer.close(index);
                });
            });
            return false;
        });
    });
  </script>
</body>
</html>