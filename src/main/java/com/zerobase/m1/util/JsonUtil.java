package com.zerobase.m1.util;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.zerobase.m1.wifi.WifiResponseDTO;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    static Gson gson = new Gson();
    public JsonUtil(){

    }
    public WifiResponseDTO JsonToWifiResponseDTO(String jsonText) {
        WifiResponseDTO map = null;
        try {
            map = this.gson.fromJson(jsonText, WifiResponseDTO.class);
        }catch (JsonIOException | JsonSyntaxException jioe){
            jioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return map;
        }
    }

    public Map<String, List<Map<String,String>>> JsonToMap(String jsonText) {
        Map<String,List<Map<String,String>>> map = null;
        try {
            map = this.gson.fromJson(jsonText, Map.class);
        }catch (JsonIOException | JsonSyntaxException jioe){
            jioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            return map;
        }
    }

}
