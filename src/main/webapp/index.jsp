<%@ page import="com.zerobase.m1.wifi.WifiService" %>
<%@ page import="com.zerobase.m1.wifi.WifiDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.zerobase.m1.location.LocationService" %>
<%@ page import="com.zerobase.m1.location.LocationDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <title>와이파이 정보 구하기</title>
</head>
<body>
<%
    WifiService wifiService = new WifiService();
    LocationService locationService = new LocationService();
    List<WifiDTO> wifiList = new ArrayList<>();
    String lnt = request.getParameter("lnt");
    String lat = request.getParameter("lat");
    if (lnt == null && lat == null ) {

    }else if (lnt.equals("0.0000000") && lat.equals("0.0000000")) {

    }
    else {
        // 조회 내역 저장
        wifiList = wifiService.getWifiList(lnt, lat, 1);
        // 위치 내역 저장
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setLnt(Float.valueOf(lnt));
        locationDTO.setLat(Float.valueOf(lat));
        locationService.insert(locationDTO);
    }
%>
<h1>
    <%= "와이파이 정보 구하기" %>
</h1>

<div class="nav">
    <a href="index.jsp">홈</a> |
    <a href="history.jsp">위치 히스토리 목록</a> |
    <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
    <a href="my-wifi-list.jsp">북마크 보기</a> |
    <a href="wifi-group-list.jsp">북마크 그룹 관리</a>
</div>

<div class="nav">
    <form name="location">
        LAT <input type="text" name="lat" id="lat" value="0.0000000">
        LNT <input type="text" name="lnt" id="lnt" value="0.0000000">
        <button type="button" name="get-location-btn" id="get-location-btn">내 위치 가져오기</button>
        <button type="submit" value="name">근처 WIFI 정보 찾기</button>
    </form>
</div>


<div class="public-wifi-list">
    <table class="table table-bordered">
        <thead class="table-light align-middle">
            <th>거리</th>
            <th>관리번호</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>도로명주소</th>
            <th>상세주소</th>
            <th>설치위치(층)</th>
            <th>설치유형</th>
            <th>설치기관</th>
            <th>서비스구분</th>
            <th>망종류</th>
            <th>설치년도</th>
            <th>실내외구분</th>
            <th>WIFI접속<br/>환경</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>작업일자</th>
        </thead>

        <tbody>
        <%
            if (wifiList.size() == 0) {
        %>
        <tr>
            <td class="align-middle" colspan="17">위치 정보를 입력한 뒤에 조회해 주세요</td>
        </tr>
        <%
            } else {
            for (WifiDTO wifi : wifiList) {
        %>
        <tr>
            <td><%=String.format("%6s",wifi.getDistance())%></td>
            <td><%=wifi.getMrgNo()%></td>
            <td><%=wifi.getDistrict()%></td>
            <td>
                <a href="<%=wifi.getId()%>"></a>
                <%=wifi.getName()%>
            </td>
            <td><%=wifi.getAddress1()%></td>
            <td><%=wifi.getAddress2()%></td>
            <td><%=wifi.getInstFloor()%></td>
            <td><%=wifi.getInstType()%></td>
            <td><%=wifi.getInstGvn()%></td>
            <td><%=wifi.getSvcType()%></td>
            <td><%=wifi.getNetType()%></td>
            <td><%=wifi.getInst_date()%></td>
            <td><%=wifi.getInOutYn()%></td>
            <td><%=wifi.getX_SWIFI_REMARS3()%></td>
            <td><%=wifi.getX()%></td>
            <td><%=wifi.getY()%></td>
            <td><%=wifi.getInst_date()%></td>
        </tr>
        <%
            }
        }
        %>
        </tbody>
    </table>
</div>
<br/>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp"></script>
<script src="js/getLocation.js"></script>
</body>
</html>