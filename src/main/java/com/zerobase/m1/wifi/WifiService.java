package com.zerobase.m1.wifi;

import com.zerobase.m1.util.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WifiService {
    private String apiKey;
    private String kakaoAPIKey;
    public static int totalCount;
    public int curPage;
    public WifiService() {
        setApiKey("4656565145726c613535566a456679"); //  소스코드 올릴 때는 빈 문자열로 변환해서 올림
        setKakaoAPIKey("754cda31dcea8a4c0d47ca7baa840ba5"); // 카카오 지도 Resg API key
        totalCount = 0;
        this.curPage = 1;
    }

    /**
     *
     * @param lnt 경도 (lnt x 좌표)
     * @param lat 위도 (lat y 좌표)
     * @return
     */
    public String getdistrictByGPS(String lnt, String lat){
        String district = "";
        OkHttpClient client = null;
        String resString = null;
        try{
            client = new OkHttpClient();
            String url = "https://dapi.kakao.com/v2/local/geo/coord2address.json?"
                + "x="+lnt
                + "&y="+lat;

            Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "KakaoAK " + getKakaoAPIKey())
                .build();
            try (Response response = client.newCall(request).execute()) {
                resString = response.body().string();
            }

            JsonUtil jsonUtil = new JsonUtil();
            Map<String,List<Map<String,String>>> resMap = jsonUtil.JsonToMap(resString);
            List<Map<String,String>> al = resMap.get("documents");
            System.out.println(resString);
            System.out.println(al.get(0).get("road_address").toString());

            if (resString == null) {
                throw new NullPointerException("no Response Data");
            }

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            totalCount = 0;
        }
        return district;
    }

    /**
     * @return (DB에 저장되어 있는)총 와이파이 수
     */
    public void getWifiCount(){
        HttpRequest httpRequest = null;
        JsonUtil jsonUtil = null;
        String response = null;
        int count = 0;
        try{
            httpRequest = new HttpRequest();
            String url = "http://openapi.seoul.go.kr:8088/"
                + this.getApiKey()
                + "/json/TbPublicWifiInfo/1/1/";
            response = httpRequest.get(url);
            if (response == null) {
                throw new NullPointerException("no Response Data");
            }
            jsonUtil = new JsonUtil();
            WifiResponseDTO resDTO = jsonUtil.JsonToWifiResponseDTO(response);
            count = resDTO.getTbPublicWifiInfoDTO().getTotalCount();
            totalCount = count;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            totalCount = 0;
        }
    }
    public List<WifiDTO> getWifiAPIData(String startIdx,String endIdx){
        List<WifiDTO> wifiDTOS = null;
        HttpRequest httpRequest = null;
        JsonUtil jsonUtil = null;
        String response = null;
        try{
            httpRequest = new HttpRequest();
            String url = String.format("http://openapi.seoul.go.kr:8088/%s/json/TbPublicWifiInfo/%s/%s/",getApiKey(), startIdx, endIdx);
            response = httpRequest.get(url);
            if (response == null) {
                throw new NullPointerException("no Response Data");
            }
            jsonUtil = new JsonUtil();
            WifiResponseDTO resDTO = jsonUtil.JsonToWifiResponseDTO(response);
            wifiDTOS = resDTO.getTbPublicWifiInfoDTO().getWifiDTOList();
            return wifiDTOS;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            wifiDTOS = null;
            return wifiDTOS;
        } catch ( Exception ee) {
            ee.printStackTrace();
            wifiDTOS = null;
            return wifiDTOS;
        }
    }

    public void insert(WifiDTO wifiDTO) {
        String url = "jdbc:sqlite:C:\\Users\\he959\\IdeaProjects\\m1\\zerobaseM1.sqlite";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(url);
            String sql = "insert\n"
                + "into main.wifi(mng_code,district, name, addr1, addr2, addr_floor, "
                + "inst_type, svc_type, net_type, inst_year, inst_io, conn_env, inst_nby, lnt, lat, worked_date)\n"
                + "VALUES (?,?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            int affected = 0;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,wifiDTO.getMrgNo());
            preparedStatement.setString(2, wifiDTO.getDistrict());
            preparedStatement.setString(3, wifiDTO.getName());
            preparedStatement.setString(4, wifiDTO.getAddress1());
            preparedStatement.setString(5,wifiDTO.getAddress2());
            preparedStatement.setString(6, wifiDTO.getInstFloor());
            preparedStatement.setString(7, wifiDTO.getInstType());
            preparedStatement.setString(8, wifiDTO.getSvcType());
            preparedStatement.setString(9, wifiDTO.getNetType());
            preparedStatement.setString(10, wifiDTO.getInst_date());
            preparedStatement.setString(11, wifiDTO.getInOutYn());
            preparedStatement.setString(12, wifiDTO.getX_SWIFI_REMARS3());
            preparedStatement.setString(13, wifiDTO.getInstGvn());
            preparedStatement.setString(14, wifiDTO.getY());
            preparedStatement.setString(15, wifiDTO.getX());
            preparedStatement.setString(16, wifiDTO.getWork_date());
            affected += preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println(" 저장 성공 ");
            }else {
                System.out.println(" 저장 실패 ");
            }
            // 5. 삽입 결과 확인
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
            // 6. 객체 연결 해제 (close)
        } finally {
            try {
                if (rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection!= null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public int insertBulk(){
        this.getWifiCount();
        int curTotalcount = WifiService.totalCount;
        int affected = 0;


        String url = "jdbc:sqlite:C:\\Users\\he959\\IdeaProjects\\m1\\zerobaseM1.sqlite";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(url);
            String sql = "insert\n"
                + "into main.wifi(mng_code,district, name, addr1, addr2, addr_floor, "
                + "inst_type, svc_type, net_type, inst_year, inst_io, conn_env, inst_nby, lnt, lat, worked_date)\n"
                + "VALUES (?,?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            int start = 1;
            int end = start - 1 + 20;
            for (start = 1; end <= curTotalcount; start = start + 20) {
                end = start - 1 + 20;
                List<WifiDTO> wifiDTOS = getWifiAPIData(start+"",end+"");
                preparedStatement = connection.prepareStatement(sql);
                for (WifiDTO wifiDTO : wifiDTOS) {
                    try {
                        preparedStatement.setString(1,wifiDTO.getMrgNo());
                        preparedStatement.setString(2, wifiDTO.getDistrict());
                        preparedStatement.setString(3, wifiDTO.getName());
                        preparedStatement.setString(4, wifiDTO.getAddress1());
                        preparedStatement.setString(5,wifiDTO.getAddress2());
                        preparedStatement.setString(6, wifiDTO.getInstFloor());
                        preparedStatement.setString(7, wifiDTO.getInstType());
                        preparedStatement.setString(8, wifiDTO.getSvcType());
                        preparedStatement.setString(9, wifiDTO.getNetType());
                        preparedStatement.setString(10, wifiDTO.getInst_date());
                        preparedStatement.setString(11, wifiDTO.getInOutYn());
                        preparedStatement.setString(12, wifiDTO.getX_SWIFI_REMARS3());
                        preparedStatement.setString(13, wifiDTO.getInstGvn());
                        preparedStatement.setString(14, wifiDTO.getY());
                        preparedStatement.setString(15, wifiDTO.getX());
                        preparedStatement.setString(16, wifiDTO.getWork_date());
                        affected += preparedStatement.executeUpdate();
                    }catch (SQLException sqlException) {
                        continue;
                    }
                }
            }
            if (affected > 0) {
                System.out.println("성공");
            }else {
                System.out.println("실패");
            }
            return affected;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try{
                if (rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection!= null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 서울시 WIFI 리스트 정보 가져오기
     * @param lnt x좌표 (경도)
     * @param lat y좌표 (위도)
     * @param enterPage 이동하려는 페이지
     * @return 와이파이 리스트 (20개)
     * */
    public List<WifiDTO> getWifiList (String lnt, String lat, int enterPage) {
        List<WifiDTO> wifiDTOS = null;
        int limit = 20;
        int offset = (enterPage - 1) * 20;

        String url = "jdbc:sqlite:C:\\Users\\he959\\IdeaProjects\\m1\\zerobaseM1.sqlite";
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        // sql injection 공격 방지
        String memberTypeValue = "email";
        try {
            connection = DriverManager.getConnection(url);
            String sql = "select *, sqrt(power(lat - ?,2) + power(lnt - ?,2)) as distance\n"
                + "from main.wifi\n"
                + "order by distance asc\n"
                + "limit ? offset ?;";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, lat);
            preparedStatement.setString(2, lnt);
            preparedStatement.setString(3, limit+"");
            preparedStatement.setString(4, offset+"");

            rs = preparedStatement.executeQuery();


            wifiDTOS = new ArrayList<>();
            while(rs.next()) {
                WifiDTO wifiDTO = new WifiDTO();
                wifiDTO.setId(rs.getLong("id"));
                wifiDTO.setDistance(rs.getString("distance"));
                wifiDTO.setMrgNo(rs.getString("mng_code"));
                wifiDTO.setName(rs.getString("name"));
                wifiDTO.setDistrict(rs.getString("district"));
                wifiDTO.setAddress1(rs.getString("addr1"));
                wifiDTO.setAddress2(rs.getString("addr2"));
                wifiDTO.setInstFloor(rs.getString("addr_floor"));
                wifiDTO.setInstType(rs.getString("inst_type"));
                wifiDTO.setNetType(rs.getString("net_type"));
                wifiDTO.setInst_date(rs.getString("inst_year"));
                wifiDTO.setInOutYn(rs.getString("inst_io"));
                wifiDTO.setX(rs.getString("lnt"));
                wifiDTO.setY(rs.getString("lat"));
                wifiDTO.setWork_date(rs.getString("worked_date"));
                wifiDTO.setX_SWIFI_REMARS3(rs.getString("conn_env"));
                wifiDTO.setSvcType(rs.getString("svc_type"));
                wifiDTO.setInstGvn(rs.getString("inst_nby"));
                wifiDTOS.add(wifiDTO);
            }
            return wifiDTOS;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try{
                if (rs != null && !rs.isClosed()){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()){
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection!= null && !connection.isClosed()){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String getApiKey() {
        return apiKey;
    }
    private void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private void setKakaoAPIKey(String apiKey) { this.kakaoAPIKey = apiKey;}
    private String getKakaoAPIKey() { return kakaoAPIKey; }


}