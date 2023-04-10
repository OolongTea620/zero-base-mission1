<%@ page import="com.zerobase.m1.location.LocationService" %>
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
  String historyId = request.getParameter("historyId");
  LocationService locationService = new LocationService();
  int affected = locationService.delete(historyId);
  String message = "";
  if (affected > 0 ) {
    message = "정상적으로 삭제했습니다";
  }else {
    message = "삭제된 히스토리가 없습니다";
  }
%>
<h1>
<%=message%>
</h1>
<p style="align-items: center"><a href="history.jsp">히스토리 목록 가기</a> </p>
</body>
</html>
