package com.example.covid.bean;


import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeanCovid {
     private String provinceName;//省会
     private String provinceShortName;//简称
     private Integer currentConfirmedCount;//现确认数
     private Integer confirmedCount;//以确诊人数
     private Integer suspectedCount;
     private Integer curedCount;//治愈人数
     private Integer deadCount;//死亡人数
     private String comment;
     private Integer locationId;
     private String statisticsData;
     private String cities;//城市
     private String date;
}
