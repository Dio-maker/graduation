package com.example.covid.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeanCity {
    private Integer provinceId;
    private String provinceShortName;
    private String  cityName;
    private Integer currentConfirmedCount;
    private Integer confirmedCount;
    private Integer suspectedCount;
    private Integer curedCount;
    private Integer deadCount;
    private Integer locationId;
    private String currentConfirmedCountStr;
    private String date;
}
