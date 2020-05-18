<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1"></meta>
<meta http-equiv="X-UA-Compatible" content="IE=edge"></meta>
<meta name="renderer" content="webkit" />
<title>JLZMSYS管理系统</title>
<link rel="icon" type="image/x-icon" href="<%=basePath%>static/favicon.ico">
<link rel="stylesheet" href="<%=basePath%>webjars/layui/css/layui.css">
<link rel="stylesheet" href="<%=basePath%>webjars/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="<%=basePath%>static/css/zadmin.css" media="all">
<link rel="stylesheet" href="<%=basePath%>static/css/dtree.css">
<link rel="stylesheet" href="<%=basePath%>static/css/font.css">
<link rel="stylesheet" href="<%=basePath%>static/css/formSelects-v4.css">
<link rel="stylesheet" href="<%=basePath%>static/css/animate.min.css">