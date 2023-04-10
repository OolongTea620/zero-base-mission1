package com.zerobase.m1.location;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationService {
    public int insert(LocationDTO locationDTO){

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
                + "into main.location_history(x, y, inquiry_at)\n"
                + "values (?,?,CURRENT_TIMESTAMP);";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, locationDTO.getLnt()+"");
            preparedStatement.setString(2, locationDTO.getLat()+"");

            int affected = preparedStatement.executeUpdate();
            if (affected > 0 ){
                System.out.println("위치 기록 성공");
            } else {
                System.out.println("위치 기록 실패");
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
    public List<LocationDTO> getList(){
        List<LocationDTO> locationDTOS = null;

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
            String sql = "select *\n"
                + "from main.location_history\n"
                + "order by inquiry_at desc;";

            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            locationDTOS = new ArrayList<>();
            while(rs.next()) {
                LocationDTO locationDTO = new LocationDTO();
                locationDTO.setId(rs.getLong("id"));
                locationDTO.setLnt(rs.getFloat("x"));
                locationDTO.setLat(rs.getFloat("y"));
                locationDTO.setInquiryDate(rs.getDate("inquiry_at"));
                locationDTOS.add(locationDTO);
            }
            return locationDTOS;
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
    public int delete(String historyId) {
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
            String sql = "delete FROM main.location_history\n"
                + "where id = ?;";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, historyId);

            int affected = preparedStatement.executeUpdate();
            if (affected > 0) {
                System.out.println("위치 기록 삭제 성공 ");
            }else {
                System.out.println("위치 기록 삭제 실패 ");
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
}
