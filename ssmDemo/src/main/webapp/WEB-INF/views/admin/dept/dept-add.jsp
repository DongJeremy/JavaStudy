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
  <div class="layui-form animated fadeIn timo-compile">
    <form action="" method="post">
      <div class="layui-form-item">
        <label for="deptName" class="layui-form-label"> 部门名称 </label>
        <div class="layui-input-inline">
          <input type="text" id="name" name="name" value="${department.name}" lay-verify="required" lay-vertype="tips" autocomplete="off" placeholder="请输入部门名称" class="layui-input">
        </div>
      </div>
      <div class="layui-form-item">
        <label for="description" class="layui-form-label"> 部门描述 </label>
        <div class="layui-input-inline">
          <input type="text" id="description" name="description" value="${department.description}" lay-verify="required" lay-vertype="tips" autocomplete="off" placeholder="请输入部门描述" class="layui-input">
        </div>
      </div>
      <div class="layui-form-item timo-finally">
        <input type="hidden" name="id" value="${department.id}">
        <button class="layui-btn" lay-submit=""
           <c:if test="${department == null}" var="flag" scope="session">lay-filter="add"</c:if>
           <c:if test="${not flag}">lay-filter="edit"</c:if>>
           <c:if test="${department == null}" var="flag" scope="session">增加</c:if>
           <c:if test="${not flag}">修改</c:if></button>
        <button class="layui-btn btn-secondary close-frame-popup"><i class="fa fa-times-circle"></i> 关闭</button>
      </div>
    </form>
  </div>

  <jsp:include page="../common/footer.jsp"/>
  <script>
        layui.use(['form', 'layer'], function () {
            var $ = layui.jquery,
                form = layui.form,
                layer = layui.layer;
            var index = parent.layer.getFrameIndex(window.name);
            $("#parentName").on("click",function(){
                $(this).toggleClass("layui-form-selected");
                $("#parentTreeDiv").toggleClass("layui-show layui-anim layui-anim-upbit");
            });
            //监听提交
            form.on('submit(add)', function (form) {
                ajaxJsonRequest("POST", '<%=basePath%>api/department', JSON.stringify(form.field), function (result) {
                    handlerResult(result, addDone)
                });
                return false;
            });
            //监听提交
            form.on('submit(edit)', function (form) {
                ajaxJsonRequest("PUT", '<%=basePath%>api/department', JSON.stringify(form.field), function(result) {
                    handlerResult(result, editDone)
                });
                return false;
            });
            function addDone(data) {
                parent.layer.close(index);
                layer.msg("添加成功", {
                    icon : 6
                });
            }
            function editDone(data) {
                parent.layer.close(index);
                layer.msg("修改成功", {
                    icon : 6
                });
            }
        });
  </script>
</body>
</html>