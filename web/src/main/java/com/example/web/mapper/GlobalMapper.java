package com.example.web.mapper;

import com.example.web.bean.Resulet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Mapper
public interface GlobalMapper {
    @Select("select *from global01 where date=#{date}")
    List<Map<String,Object>> getGlobal01(String date);

    @Select("select provinceName,currentConfirmedCount,confirmedCount,curedCount,deadCount from global02 where date=#{date}")
    List<Map<String,Object>> getGlobal02(String date);

    @Select("select provinceName,currentConfirmedCount,confirmedCount,curedCount,deadCount from global02 where date=#{date} order by confirmedCount desc")
    List<Map<String,Object>> getGlobal03(String date);

    @Select("select provinceName,currentConfirmedCount from global02 where currentConfirmedCount>1000000 and date=#{date} ")
    List<Map<String,Object>> getGlobal04(String date);

    @Select("select * from global01 ")
    List<Map<String,Object>> getGlobal05(String date);

    @Select("select continents,sum(confirmedCount) as confirmedCount from global02 where date=#{date} group by continents")
    List<Map<String,Object>> getGlobal06(String date);

    @Select("select * from global01 where date=#{date}")
    List<Map<String,Object>> getGlobal07(String date);

}
