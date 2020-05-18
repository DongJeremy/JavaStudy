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
<body>
  <div class="page-loading">
    <div class="rubik-loader"></div>
  </div>
  <div class="animated fadeIn timo-compile">
    <form action="" method="post" class="layui-form">
      <input type="hidden" name="id" value="${id}">
      <div class="layui-form-item">
        <label class="layui-form-label">原密码</label>
        <div class="layui-input-inline">
          <input class="layui-input" type="password" name="original" placeholder="请输入原来密码">
        </div>
      </div>
      <div class="layui-form-item">
        <label class="layui-form-label">新密码</label>
        <div class="layui-input-inline">
          <input class="layui-input" type="password" name="password" placeholder="请输入新密码">
        </div>
      </div>
      <div class="layui-form-item">
        <label class="layui-form-label">确认密码</label>
        <div class="layui-input-inline">
          <input class="layui-input" type="password" name="confirm" placeholder="再一次输入密码">
        </div>
      </div>
      <div class="layui-form-item timo-finally">
        <button class="layui-btn ajax-submit" lay-filter="reset-password" lay-submit=""><i class="fa fa-check-circle"></i> 保存</button>
        <button class="layui-btn btn-secondary close-popup"><i class="fa fa-times-circle"></i> 关闭</button>
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
            if (data.password!=data.confirm) {
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