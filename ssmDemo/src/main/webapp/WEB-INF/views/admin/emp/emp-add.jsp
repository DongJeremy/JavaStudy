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
  <div class="layui-form timo-compile">
    <form action="" method="post">
      <div class="layui-form-item">
        <label for="username" class="layui-form-label">姓名</label>
        <div class="layui-input-inline">
          <input type="text" id="name" name="name" value="${employee.name}" lay-verify="required" lay-vertype="tips"
            placeholder="姓名" autocomplete="off" class="layui-input">
        </div>
      </div>
      <div class="layui-form-item">
        <label for="address" class="layui-form-label">地址 </label>
        <div class="layui-input-inline">
          <input type="text" id="address" name="address" value="${employee.address}" lay-verify="required"
            lay-vertype="tips" placeholder="地址" autocomplete="off" class="layui-input">
        </div>
      </div>
      <div class="layui-form-item">
        <label for="age" class="layui-form-label">年龄</label>
        <div class="layui-input-inline">
          <input type="text" id="age" name="age" value="${employee.age}" lay-verify="required" lay-vertype="tips"
            placeholder="年龄" autocomplete="off" class="layui-input">
        </div>
      </div>
      <div class="layui-form-item">
        <label for="tel" class="layui-form-label">手机</label>
        <div class="layui-input-inline">
          <input type="text" id="tel" name="tel" value="${employee.tel}" lay-verify="required" lay-vertype="tips"
            autocomplete="off" placeholder="手机" class="layui-input">
        </div>
      </div>
      <div class="layui-form-item">
        <label for="tel" class="layui-form-label">工资</label>
        <div class="layui-input-inline">
          <input type="text" id="salary" name="salary" value="${employee.salary}" lay-verify="required"
            lay-vertype="tips" autocomplete="off" placeholder="工资" class="layui-input">
        </div>
      </div>
      <div class="layui-form-item">
        <label class="layui-form-label">部门</label>
        <div class="layui-input-inline">
          <select name="department" lay-verify="tips">
            <option value="">请选择</option>
            <c:forEach items="${departmentList}" var="department">
              <option value="${department.id}"
                <c:if test="${department.id == employee.department.id}">selected = "selected"</c:if>>${department.name}</option>
            </c:forEach>
          </select>
        </div>
      </div>
      <div class="layui-form-item timo-finally">
        <input type="hidden" name="id" value="${employee.id}">
        <button class="layui-btn" lay-submit=""
            <c:if test="${employee == null}" var="flag" scope="session">lay-filter="add"</c:if>
            <c:if test="${not flag}">lay-filter="edit"</c:if>>
            <c:if test="${employee == null}" var="flag" scope="session"><i class="fa fa-check-circle"></i> 增加</c:if>
            <c:if test="${not flag}"><i class="fa fa-check-circle"></i> 修改</c:if></button>
        <button class="layui-btn btn-secondary close-frame-popup"><i class="fa fa-times-circle"></i> 关闭</button>
      </div>
    </form>
  </div>

  <jsp:include page="../common/footer.jsp"/>
  <script>
    layui.config({
        base: '<%=basePath%>static/js/'
    }).extend({
        formSelects: 'formSelects-v4'
    }).use(['form', 'layer', 'formSelects'], function () {
        $ = layui.jquery;
        //var index = parent.layer.getFrameIndex(window.name);
        var form = layui.form
          , layer = layui.layer
          , formSelects = layui.formSelects;
        form.on('submit(add)', function (form) {
            form.field.role = formSelects.value('role-select', 'val');
            ajaxJsonRequest("POST", '<%=basePath%>api/employee/', JSON.stringify(form.field), function (result) {
                handlerResult(result, addDone)
            });
            return false;
        });

        //监听提交
        form.on('submit(edit)', function (form) {
            form.field._method = 'PUT';
            form.field.role = formSelects.value('role-select', 'val');
            ajaxJsonRequest("PUT", '<%=basePath%>api/employee/', JSON.stringify(form.field), function(result) {
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