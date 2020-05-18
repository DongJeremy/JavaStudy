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
      <span><i class="fa fa-bars"></i> 系统日志</span>
    </div>
    <div class="layui-card-body">
      <div class="layui-row timo-card-screen put-row">
        <div class="pull-right screen-btn-group">
          <div class="btn-group-right">
            <button class="layui-btn open-popup" data-type="getCheckData" id="del_btn">
              <i class="fa fa-trash"></i> 清空日志
            </button>
          </div>
        </div>
      </div>
      <div class="timo-table-wrap">
        <table class="layui-hide timo-table" id="login-log-table" lay-filter="allAttr"></table>
      </div>
    </div>
  </div>

  <jsp:include page="../common/footer.jsp"/>
  <script>
    layui.use(['table'], function () {
        var table = layui.table;
        var $ = layui.$;
  
        table.render({
            elem: '#login-log-table'
           , url: '<%=basePath%>api/syslog/list'
          , page: true
          , id : 'logTable'
          , limits: [10,20,50]
          , cols: [
              [
                  {field: 'username', title: '用户名', width: "8%"}
                  , {field: 'operation', title: '描述', width: "8%"}
                  , {field: 'time', title: '耗时(毫秒)', width: "8%"}
                  , {field: 'method', title: '请求方法', width: "25%"}
                  , {field: 'params', title: '请求参数'}
                  , {field: 'ip', title: 'IP', width: "10%"}
                  , {field: 'createTime', title: '操作时间', templet: function(d) { return dateFormat("yyyy-MM-dd hh:mm:ss", new Date(d.createTime))}, width: "15%"}
              ]
          ]
      });

      $("#del_btn").on("click", function() {
          layer.confirm('是否清空日志?', function(index) {
              ajaxJsonRequest("POST", '<%=basePath%>api/syslog/clear', null, function(data) {
                  layer.close(index);
                  handlerResult(data, function() {
                      parent.layer.msg("删除成功", {icon: 6});
                      table.reload('logTable');
                  });
              });
          });
      });
  });
  </script>
</body>
</html>