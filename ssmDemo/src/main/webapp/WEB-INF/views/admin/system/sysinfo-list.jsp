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
      <span><i class="fa fa-bars"></i> 系统信息</span> <i class="layui-icon layui-icon-refresh refresh-btn"></i>
    </div>
    <div class="layui-card-body">
      <div class="timo-table-wrap">
        <div class="layui-col-md6">
          <div class="layui-card">
            <div class="layui-card-header">CPU</div>
            <div class="layui-card-body">
              <table class="layui-table" lay-skin="line">
                <thead>
                  <tr>
                    <th>属性</th>
                    <th>值</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>核心数</td>
                    <td>${server.cpu.cpuNum}</td>
                  </tr>
                  <tr>
                    <td>用户使用率</td>
                    <td>${server.cpu.used}%</td>
                  </tr>
                  <tr>
                    <td>系统使用率</td>
                    <td>${server.cpu.sys}%</td>
                  </tr>
                  <tr>
                    <td>当前空闲率</td>
                    <td>${server.cpu.free}%</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="layui-col-md6">
          <div class="layui-card">
            <div class="layui-card-header">内存</div>
            <div class="layui-card-body">
              <table class="layui-table" lay-skin="line">
                <thead>
                  <tr>
                    <th>属性</th>
                    <th>内存</th>
                    <th>JVM</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>总内存</td>
                    <td>${empty server.mem.total ? "0" : server.mem.total}GB</td>
                    <td>${empty server.jvm.total ? "0" : server.mem.total}MB</td>
                  </tr>
                  <tr>
                    <td>已用内存</td>
                    <td>${empty server.mem.used ? "0" : server.mem.used}GB</td>
                    <td>${empty server.jvm.used ? "0" : server.mem.used}MB</td>
                  </tr>
                  <tr>
                    <td>剩余内存</td>
                    <td>${empty server.mem.free ? "0" : server.mem.free}GB</td>
                    <td>${empty server.jvm.free ? "0" : server.mem.free}MB</td>
                  </tr>
                  <tr>
                    <td>使用率</td>
                    <td class="${server.mem.usage>80? 'text-danger':''}">${server.mem.usage}%</td>
                    <td class="${server.jvm.usage>80? 'text-danger':''}">${server.jvm.usage}%</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <div class="layui-col-md12">
          <div class="layui-card">
            <div class="layui-card-header">服务器信息</div>
            <div class="layui-card-body">
              <table class="layui-table" lay-skin="line">
                <tr>
                  <td>服务器名称</td>
                  <td>${empty server.sys.computerName ? "RuoYi" : server.sys.computerName}</td>
                  <td>操作系统</td>
                  <td>${empty server.sys.osName ? "Linux" : server.sys.osName}</td>
                </tr>
                <tr>
                  <td>服务器IP</td>
                  <td>${empty server.sys.computerIp ? "127.0.0.1" : server.sys.computerIp}</td>
                  <td>系统架构</td>
                  <td>${empty server.sys.osArch ? "amd64" : server.sys.osArch}</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
        <div class="layui-col-md12">
          <div class="layui-card">
            <div class="layui-card-header">Java虚拟机信息</div>
            <div class="layui-card-body">
              <table class="layui-table" lay-skin="line">
                <tr>
                  <td>Java名称</td>
                  <td>${empty server.jvm.name ? "Java" : server.jvm.name}</td>
                  <td>Java版本</td>
                  <td>${empty server.jvm.version ? "1.8.0" : server.jvm.version}</td>
                </tr>
                <tr>
                  <td>启动时间</td>
                  <td>${empty server.jvm.startTime ? "2019-04-08 00:00:00" : server.jvm.startTime}</td>
                  <td>运行时长</td>
                  <td>${empty server.jvm.runTime ? "0天0时0分0秒" : server.jvm.runTime}</td>
                </tr>
                <tr>
                  <td colspan="1">安装路径</td>
                  <td colspan="3">${server.jvm.home}</td>
                </tr>
                <tr>
                  <td colspan="1">项目路径</td>
                  <td colspan="3">${server.sys.userDir}</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
        <div class="layui-col-md12">
          <div class="layui-card">
            <div class="layui-card-header">磁盘状态</div>
            <div class="layui-card-body">
              <table class="layui-table" lay-skin="line">
                <thead>
                  <tr>
                    <th>盘符路径</th>
                    <th>文件系统</th>
                    <th>盘符类型</th>
                    <th>总大小</th>
                    <th>可用大小</th>
                    <th>已用大小</th>
                    <th>已用百分比</th>
                  </tr>
                </thead>
                <tbody>
                <c:forEach items="${server.sysFiles}" var="sysFile">
                <tr>
                  <td>${empty sysFile.dirName ? 'C:\\' : sysFile.dirName}</td>
                  <td>${empty sysFile.sysTypeName ? "NTFS" : sysFile.sysTypeName}</td>
                  <td>${empty sysFile.typeName ? "local" : sysFile.typeName}</td>
                  <td>${empty sysFile.total ? "0GB" : sysFile.total}</td>
                  <td>${empty sysFile.free ? "0GB" : sysFile.free}</td>
                  <td>${empty sysFile.used ? "0GB" : sysFile.used}</td>
                  <td>${sysFile.usage}%</td>
                </tr>
                </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <jsp:include page="../common/footer.jsp"/>
</body>
</html>