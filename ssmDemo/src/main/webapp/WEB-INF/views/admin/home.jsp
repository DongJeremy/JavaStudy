<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="common/header.jsp"/>
</head>
<body>
  <div class="animated fadeIn timo-layout-page">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <blockquote class="layui-elem-quote custom-quote">欢迎：<span><shiro:principal property="username" /></span>！当前时间:
          <c:set var="now" value="<%=new java.util.Date()%>" />
          <fmt:formatDate type="both" value="${now}" />
        </blockquote>
      </div>
    </div>
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md3 layui-col-sm6 layui-col-xs12">
          <div class="widget-small widget-user">
            <i class="icon fa fa-users fa-3x"></i>
            <div class="widget-small-info">
              <h4>用户</h4>
              <span>12</span>
            </div>
          </div>
        </div>
        <div class="layui-col-md3 layui-col-sm6 layui-col-xs12">
            <div class="widget-small widget-visit">
              <i class="icon fa fa-line-chart fa-3x"></i>
              <div class="widget-small-info">
                <h4>访问</h4>
                <span>1,200</span>
              </div>
            </div>
        </div>
        <div class="layui-col-md3 layui-col-sm6 layui-col-xs12">
              <div class="widget-small widget-message">
                  <i class="icon fa fa-comments-o fa-3x"></i>
                  <div class="widget-small-info">
                      <h4>信息</h4>
                      <span>41</span>
                  </div>
              </div>
        </div>
        <div class="layui-col-md3 layui-col-sm6 layui-col-xs12">
              <div class="widget-small widget-like">
                  <i class="icon fa fa-star fa-3x"></i>
                  <div class="widget-small-info">
                      <h4>收藏</h4>
                      <span>306</span>
                  </div>
              </div>
        </div>
    </div>
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header">项目信息</div>
          <div class="layui-card-body">
            <div id="main" style="width: 100%; height: 400px;"></div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <jsp:include page="common/footer.jsp"/>
  <script src="<%=basePath%>webjars/echarts/echarts.min.js"></script>
  <script type="application/javascript">
        layui.use('element', function(){
            layui.$("#main").css("width", (window.innerWidth - 100) +'px');
         	// 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
         	// 指定图表的配置项和数据
            option = {
                title: {
                	text: '近七天登陆次数统计图',
                    left: 'center'
                },
                tooltip: {
                	trigger: 'axis',
                    axisPointer: { // 坐标轴指示器，坐标轴触发有效
                        type: 'none' // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                xAxis: {
                    type: 'category',
                    data:  getWeekList()
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: [],
                    type: 'line'
                }]
            };
            layui.$.get('admin/weekLoginCount', function (data) {
            	option.series[0].data = data;
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            });
        });
  </script>
</body>
</html>