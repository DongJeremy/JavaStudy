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
      <span><i class="fa fa-bars"></i> 账户管理</span> <i class="layui-icon layui-icon-refresh refresh-btn"></i>
    </div>
    <div class="layui-card-body">
      <div class="timo-table-wrap">
        <table class="layui-hide timo-table" id="user-table" lay-filter="allAttr"></table>
      </div>
    </div>
  </div>

  <script type="text/html" id="toolbar">
    <a class="layui-btn layui-btn-xs layui-btn-blue" lay-event="add">新增</a>
  </script>
  
  <script type="text/html" id="column-toolbar">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-xs" lay-event="reset">重置密码</a>
    <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
  </script>

    <script type="text/html" id="statusTpl">
        <!-- 不能禁用自己 -->
        {{#  if('<shiro:principal property="username"/>' == d.username) { }}
            <input type="checkbox" disabled name="status" value="{{d.id}}" lay-skin="switch" lay-text="正常|锁定" lay-filter="status" {{ d.status == 1 ? 'checked' : '' }}>
        {{#  } }}
        {{#  if('<shiro:principal property="username"/>' !== d.username) { }}
          <input type="checkbox" name="status" value="{{d.id}}" lay-skin="switch" lay-text="正常|锁定" lay-filter="status" {{ d.status == 1 ? 'checked' : '' }}>
        {{#  } }}
    </script>

  <jsp:include page="../common/footer.jsp"/>
  <script>
        layui.use(['table', 'element', 'form'], function () {
            var table = layui.table;
            var $ = layui.$;
            var form = layui.form;

            table.render({
                elem: '#user-table'
                , url: '<%=basePath%>api/user/list'
                , page: true
                , toolbar: '#toolbar'
                , limits:[10,20,50]
                , cols: [
                    [
                        {type: 'numbers', title: '序号', width: "5%"}
                        ,{field: 'userId', title: 'ID', width: "10%", hide: true}
                        , {field: 'username', title: '用户名', width: "25%"}
                        , {field: 'email', title: '邮箱', width: "15%"}
                        , {field: 'lastLoginTime', title: '最后登陆时间', templet: function(d) { return dateFormat("yyyy-MM-dd hh:mm:ss", new Date(d.lastLoginTime))}, width: "15%"}
                        , {field: 'status', title: '状态', templet: "#statusTpl", width: "15%"}
                        , {title: '操作', fixed: 'right', align: 'center', toolbar: '#column-toolbar'}
                    ]
                ]
            });

            // 账号状态(正常/锁定)点击事件
            form.on('switch(status)', function(data){
                if (data.elem.checked) {
                    ajaxJsonRequest("POST", '<%=basePath%>api/user/' + data.value + '/enable', null, function (data) {
                        handlerResult(data, enableDone)
                    });
                } else {
                    ajaxJsonRequest("POST", '<%=basePath%>api/user/' + data.value + '/disable', null, function (data) {
                        handlerResult(data, disableDone)
                    });
                }
            });

            // 工具条点击事件
            table.on('toolbar', function (obj) {
                var data = obj.data;
                var event = obj.event;

                if (event === 'add') {
                    add();
                }
            });
            
            // 行点击事件 重置密码
            table.on('tool', function (obj) {
                var data = obj.data;
                var event = obj.event;
                if (event === 'edit') {
                    edit(data.id);
                } else if (event === 'reset') {
                    layer.open({
                        content: '<%=basePath%>admin/user/' + data.id + "/reset",
                        title: "重置密码",
                        area: ['500px', '300px'],
                        type: 2,
                        maxmin: true,
                        shadeClose: true,
                        end: function () {
                            $(".layui-laypage-btn")[0].click()
                        }
                    });
                } else if (event === 'del') {
                    del(obj);
                }
            });

            function disableDone(data) {
                parent.layer.msg("禁用成功", {icon: 6});
            }

            function enableDone(data) {
                parent.layer.msg("激活成功", {icon: 6});
            }

            function add() {
                layer.open({
                    content: "<%=basePath%>admin/empChangeView",
                    title: "新增用户",
                    area: ['500px', '480px'],
                    type: 2,
                    maxmin: true,
                    shadeClose: true,
                    end: function () {
                        $(".layui-laypage-btn")[0].click()
                    }
                });
            }

            function edit(id) {
                layer.open({
                    content: '<%=basePath%>admin/empChangeView/' + id,
                    title: "编辑",
                    area: ['500px', '450px'],
                    type: 2,
                    maxmin: true,
                    shadeClose: true,
                    end: function () {
                        table.reload('user-table');
                    }
                });
            }

            function del(obj) {
                layer.confirm("确定删除用户吗?", {icon: 3, title: '提示'},
                    function (index) {//确定回调
                        var id = obj.data.id;
                        ajaxJsonRequest("DELETE", '<%=basePath%>api/user/' + id, null, function (data) {
                              layer.close(index);
                              handlerResult(data, deleteDone)
                          });
                    }, function (index) {//取消回调
                        layer.close(index);
                    }
                );
            }

            function deleteDone(data) {
                parent.layer.msg("删除成功", {icon: 6});
                table.reload('user-table');
            }

        });
    </script>
</body>
</html>