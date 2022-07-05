package com.example.covid.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeanGlobal {
    private Integer currentConfirmedCount;
    private Integer confirmedCount;
    private Integer curedCount;
    private Integer deadCount;
    private Integer locationId;
    private String continents;
    private String provinceName;
    private String Date;

}
