package com.zerobase.m1.location;

import java.util.Date;
import lombok.Setter;
import lombok.Getter;
public class LocationDTO {
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private float lnt;
    @Getter
    @Setter
    private float lat;

    @Getter
    @Setter
    private Date inquiryDate;
}
