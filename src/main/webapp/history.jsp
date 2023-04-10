<%@ page import="com.zerobase.m1.location.LocationService" %>
<%@ page import="com.zerobase.m1.location.LocationDTO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
  <title>와이파이 정보 구하기</title>
</head>
<body>
<h1>
  <%= "위치 히스토리 목록" %>
</h1>

<%
  LocationService locationService = new LocationService();
  List<LocationDTO> histories = locationService.getList();
%>

<div class="nav">
  <a href="index.jsp">홈</a> |
  <a href="history.jsp">위치 히스토리 목록</a> |
  <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
  <a href="hello-servlet">북마크 보기</a> |
  <a href="hello-servlet">북마크 그룹 관리</a>
</div>

<div class="public-wifi-list">
  <table class="table table-bordered">
    <thead class="table-light align-middle">
    <th>ID</th>
    <th>X좌표</th>
    <th>Y좌표</th>
    <th>조회일자</th>
    <th>비고</th>
    </thead>
    <tbody>
    <%
      if (histories.size() == 0) {
    %>
    <tr class="align-middle">
      <td colspan="5">정보 위치가 없습니다.</td>
    </tr>
    <%
    } else {
      for (LocationDTO history  : histories) {
    %>
    <tr>
      <td><%=history.getId()%></td>
      <td><%=history.getLnt()%></td>
      <td><%=history.getLat()%></td>
      <td><%=history.getInquiryDate()%></td>
      <td>
        <a href="delete-history.jsp?historyId=<%=history.getId()%>">삭제</a>
      </td>
    </tr>
    <%
        }
      }
    %>
    </tbody>
  </table>
</div>
<br/>
</body>
</html>