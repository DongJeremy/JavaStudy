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
  <div class="timo-detail-page">
    <div class="timo-detail-title">基本信息</div>
    <table class="layui-table timo-detail-table">
        <tbody>
          <tr>
            <th width='100px'>用户编号</th>
            <td>${employee.id}</td>
            <th width='100px'>用户账号</th>
            <td>${employee.name}</td>
          </tr>
          <tr>
            <th>年龄</th>
            <td>${employee.age}</td>
            <th>地址</th>
            <td>${employee.address}</td>
          </tr>
          <tr>
            <th>所在部门</th>
            <td colspan="4">${employee.department.name}</td>
          </tr>
          <tr>
            <th>电话号码</th>
            <td>${employee.tel}</td>
            <th>工资</th>
            <td>${employee.salary}</td>
          </tr>
        </tbody>
    </table>
  </div>

  <jsp:include page="../common/footer.jsp"/>
</body>
</html>