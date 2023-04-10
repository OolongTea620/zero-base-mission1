package com.zerobase.m1.wifi;

import com.google.gson.annotations.SerializedName;
import com.zerobase.m1.util.ResultDTO;
import java.util.List;
import lombok.Data;

@Data
public class TbPublicWifiInfoDTO {
    @SerializedName("list_total_count")
    private int totalCount;

    @SerializedName("RESULT")
    private ResultDTO ResultDTO;

    @SerializedName("row")
    private List<WifiDTO> wifiDTOList;
}
