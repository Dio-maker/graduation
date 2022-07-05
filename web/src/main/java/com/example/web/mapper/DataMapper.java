package com.example.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataMapper {
    @Select("select *from data_01 where date=#{date}")
    List<Map<String,Object>> getData01(String date);

    @Select("select provinceShortName as name,confirmedCount as value from data_02 where date=#{date}")
    List<Map<String,Object>> getData02(String date);

    @Select("select *from data_03")
    List<Map<String,Object>> getData03();

    @Select("select confirmedCount as value,provinceShortName as name from data_04 where date=#{date}")
    List<Map<String,Object>> getData04(String date);

    @Select("select cityName as name,confirmedCount as value from data_05 where date=#{date} and provinceShortName='江西' order by confirmedCount desc ")
    List<Map<String,Object>> getData05(String date);

    @Select("select provinceShortName,confirmedCount,deadCount,curedCount  from data_02 where date=#{date} order by confirmedCount asc")
    List<Map<String,Object>> getData06(String date);

    @Select("select *from data_01 where date=#{date}")
    List<Map<String,Object>> getData07(String date);

}
