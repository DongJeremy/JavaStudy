<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="common/header.jsp"/>
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	localStorage.setItem('basePath', basePath)
</script>
</head>
<body class="layui-layout-body">
  <div class="layui-layout layui-layout-admin">
    <!-- 导航栏 -->
    <div class="layui-header">
      <!-- logo 区域 -->
      <a href="admin#" class="layui-logo">
        <span class="layui-logo-mini">JLZMSYS</span>
        <span class="layui-logo-lg">JLZMSYS 后台</span>
      </a>
      <a class="side-toggle layui-layout-left" href="#"> 
        <i class="layui-icon layui-icon-shrink-right"></i> 
        <i class="layui-icon layui-icon-spread-left"></i>
      </a>
      <ul class="layui-nav layui-layout-right">
        <li class="layui-nav-item">
          <a href="admin#"> 
            <i class="fa fa-bell-o fa-lg"></i>
          </a>
        </li>
        <li class="layui-nav-item">
          <a class="timo-screen-full" href="#"> 
            <i class="fa layui-icon layui-icon-screen-full"></i>
          </a>
        </li>
        <li class="layui-nav-item timo-nav-user">
          <a class="timo-header-nickname">JLZMSYS</a>
          <div class="layui-nav-child">
            <div class="timo-nav-child-box">
              <div>
                <a class="open-popup" data-title="个人信息" data-url="<%=basePath%>admin/userInfo" data-size="680,464">
                  <!-- -->
                  <i class="fa fa-user-o"></i> 个人信息
                </a>
              </div>
              <div>
                <a class="open-popup" data-title="修改密码" data-url="<%=basePath%>admin/editpass" data-size="456,296">
                  <!-- -->
                  <i class="fa fa-lock" style="font-size: 17px; width: 12px;"></i>修改密码
                </a>
              </div>
              <div>
                <a onclick="logout()"><i class="fa fa-power-off"></i>退出登录</a>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>

    <!-- 左侧导航区域 -->
    <div class="layui-side layui-bg-black">
      <div class="layui-side-scroll">
        <div class="layui-side-user">
          <img class="layui-side-user-avatar" data-size="680,464" src="<%=basePath%>static/images/logo.png" alt="logo" />
          <div>
            <p class="layui-side-user-name">超级管理员</p>
            <p class="layui-side-user-designation">在线</p>
          </div>
        </div>
        <!-- 导航区域 -->
        <ul class="layui-nav layui-nav-tree" lay-filter="layui-nav-side">
          <li class="layui-nav-item layui-this">
            <a lay-url="home"> 
              <i class="layui-icon layui-icon-home"></i>&emsp;<span class="layui-nav-title">主页</span>
            </a>
          </li>
          <c:forEach items="${menus}" var="menu">
          <li class="layui-nav-item">
            <a lay-id="${menu.url}" lay-url="${menu.url}">
              <i class="layui-icon iconfont ${menu.icon}" ></i>&emsp;<span class="layui-nav-title">${menu.menuName}</span>
            </a>
            <c:if test="${menu.children!=null && fn:length(menu.children)>0 }">
            <dl class="layui-nav-child">
              <c:forEach items="${menu.children}" var="child">
              <dd>
                <a lay-url="${child.url}" href="javascript:;"><span class="layui-nav-title">${child.menuName}</span></a>
              </dd>
              </c:forEach>
            </dl>
            </c:if>
          </li>
          </c:forEach>
        </ul>
      </div>
    </div>
    
    <div class="layui-body layui-tab" lay-unauto lay-allowclose="true" lay-filter="iframe-tabs">
      <!-- 标签栏 -->
      <ul class="layui-tab-title">
        <li lay-id="home" lay-url="home" class="layui-this">
          <i class="layui-icon layui-icon-home"></i> 主页  
        </li>
      </ul>
      <!-- 内容区域-->
      <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
          <iframe src="admin/home" class="layui-layout-iframe" style="border: none;"></iframe>
        </div>
      </div> 
    </div>

    <!-- 底部固定区域 -->
    <div class="layui-footer">copyright © 2019 Z-Admin all rights reserved.</div>

    <!-- 移动端遮罩 -->
    <div class="site-mobile-shade"></div>
  </div>

  <jsp:include page="common/footer.jsp"/>
  <script>
    function logout() {
        sessionStorage.removeItem("tabs");
        sessionStorage.removeItem("currentTabId");
        $.post('logout', function(result) {
            if (result.code == 0) {
                window.location.href = '<%=basePath%>login';
            }
        }, 'json');
    }
  </script>
</body>
</html>