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
  <div class="animated fadeIn layui-card">
    <div class="layui-card-header timo-card-header">
      <span><i class="fa fa-bars"></i> 在线账户管理</span> <i class="layui-icon layui-icon-refresh refresh-btn"></i>
    </div>
    <div class="layui-card-body">
      <div class="timo-table-wrap">
        <table class="layui-hide timo-table" id="user-table" lay-filter="allAttr"></table>
      </div>
    </div>
  </div>

  <script type="text/html" id="column-toolbar">
    {{#  if(d.id !== "${pageContext.request.session.id}" ) { }}
        <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="kickout">下线</a>
    {{#  } }}
  </script>

  <jsp:include page="../common/footer.jsp"/>
  <script>
  layui.use(['table', 'element', 'form'], function () {
      var table = layui.table;
      var $ = layui.$;

      table.render({
          elem: '#user-table'
          , url: '<%=basePath%>api/user/onlinelist'
          , page: true
          , limits:[10,20,50]
          , cols: [
              [
                {type: 'numbers', title: '序号', width: "5%"}
                , {field: 'username', title: '用户名', width: "13%", templet: function (d) {
                        var username = d.username;
                        if (d.id === "${pageContext.request.session.id}") {
                            username += "<span class=\"layui-badge-rim layui-bg-blue\">当前登录</span>";
                        }
                        return username;
                    }
                }
                , {field: 'id', title: 'SESSION_ID', width: "25%"}
                , {field: 'startTimestamp', title: '登录时间', templet: function(d) { return dateFormat("yyyy-MM-dd hh:mm:ss", new Date(d.startTimestamp))}, width: "15%"}
                , {field: 'lastAccessTime', title: '最后访问时间', templet: function(d) { return dateFormat("yyyy-MM-dd hh:mm:ss", new Date(d.lastAccessTime))}, width: "15%"}
                , {field: 'ip', title: 'IP', width: "12%"}
                , {field: 'status', title: '状态', width: "5%"}
                , {title: '操作', align: 'center', toolbar: '#column-toolbar'}
              ]
          ]
      });

      // 行点击事件
      table.on('tool', function (obj) {
          var data = obj.data;
          var event = obj.event;
          if (event === 'kickout') {
              $.post('<%=basePath%>api/user/kickout', {"sessionId": data.id}, function (data) {
                  handlerResult(data, kickoutDone);
              });
          }
      });

      function kickoutDone(data) {
          parent.layer.msg("该用户已被强制下线!", {icon: 6});
          table.reload('user-table');
      }

  });
    </script>
</body>
</html>