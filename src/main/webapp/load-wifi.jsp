<%@ page import="com.zerobase.m1.wifi.WifiService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>와이파이 정보 구하기</title>
</head>
<style>
  h1 { text-align: center; }
  p { text-align: center; }
</style>
<body>
<%
    WifiService wifiService = new WifiService();
    int affected = wifiService.insertBulk();
%>
<h1>
    <%= affected %>개의 WIFI 정보를 정상적으로 저장하였습니다.
</h1>
<p style="align-items: center"><a href="index.jsp">홈으로 가기</a> </p>
</body>
</html>
