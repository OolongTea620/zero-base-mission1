package com.zerobase.m1.wifi;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WifiResponseDTO {
    @SerializedName("TbPublicWifiInfo")
    private TbPublicWifiInfoDTO tbPublicWifiInfoDTO;
}
