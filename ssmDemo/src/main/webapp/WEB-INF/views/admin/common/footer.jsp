<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<script src="<%=basePath%>webjars/jquery/jquery.min.js"></script>
<script src="<%=basePath%>webjars/layui/layui.all.js"></script>
<script src="<%=basePath%>static/js/main.js"></script>