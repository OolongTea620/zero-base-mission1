package com.zerobase.m1.wifi;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WifiDTO {

    private long id;
    @SerializedName("X_SWIFI_MGR_NO")
    private String mrgNo;
    @SerializedName("X_SWIFI_WRDOFC")
    private String district;
    @SerializedName("X_SWIFI_MAIN_NM")
    private String name;
    @SerializedName("X_SWIFI_ADRES1")
    private String address1;
    @SerializedName("X_SWIFI_ADRES2")
    private String address2;
    @SerializedName("X_SWIFI_INSTL_FLOOR")
    private String instFloor;
    @SerializedName("X_SWIFI_INSTL_TY")
    private String instType;
    @SerializedName("X_SWIFI_INSTL_MBY")
    private String instGvn;
    @SerializedName("X_SWIFI_SVC_SE")
    private String svcType;
    @SerializedName("X_SWIFI_CMCWR")
    private String netType;
    @SerializedName("X_SWIFI_CNSTC_YEAR")
    private String inst_date;
    @SerializedName("X_SWIFI_INOUT_DOOR")
    private String inOutYn;
    @SerializedName("X_SWIFI_REMARS3")
    private String X_SWIFI_REMARS3;
    @SerializedName("LNT")
    private String x;
    @SerializedName("LAT")
    private String y;
    @SerializedName("WORK_DTTM")
    private String work_date;
    private String distance;
}