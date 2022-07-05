package com.example.web.controller;

import com.example.web.bean.Resulet;
import com.example.web.mapper.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("covid2022")
public class CovidController {
    @Autowired
    private DataMapper dataMapper;

    //c1
    @RequestMapping("getData01")
    public Resulet getData01(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String newDate = dateFormat.format(date);
        List<Map<String, Object>> data01 = dataMapper.getData01(newDate);
        if (data01==null){
            return Resulet.fail("load data01 failed!");
        }
        Map<String, Object> dataNow = data01.get(0);
        return Resulet.success(dataNow);
    }


    //c2
    @RequestMapping("getData02")
    public Resulet getData02(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String newDate = dateFormat.format(date);
        List<Map<String, Object>> data02 = dataMapper.getData02(newDate);
        if (data02==null){
            return Resulet.fail("load data02 failed!");
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("name","南海诸岛");
        map.put("value",0);
        data02.add(map);
        return Resulet.success(data02);
    }
    //l2
    @RequestMapping("getData03")
    public Resulet getData03(){
        List<Map<String, Object>> data03 = dataMapper.getData03();
        if (data03==null){
            return Resulet.fail("load data03 failed!");
        }
        List<Map<String, Object>> subList = data03.subList(data03.size()-7,data03.size());
        return Resulet.success(subList);
    }
    //l1
    @RequestMapping("getData04")
    public Resulet getData04(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String newDate = dateFormat.format(date);
        List<Map<String, Object>> data04 = dataMapper.getData04(newDate);
        if (data04==null){
            return Resulet.fail("load data04 failed!");
        }
        return Resulet.success(data04);
    }
    @RequestMapping("getData05")
    public Resulet getData05(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String newDate = dateFormat.format(date);
        List<Map<String, Object>> data05 = dataMapper.getData05(newDate);
        if (data05==null){
            return Resulet.fail("load data05 failed!");
        }
        List<Map<String, Object>> dataNow = data05.subList(0,5);
        return Resulet.success(dataNow);
    }


    @RequestMapping("getData06")
    public Resulet getData06(){
        //System.out.println("--------");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String newDate = dateFormat.format(date);

        List<Map<String, Object>> data06 = dataMapper.getData06(newDate);
        if (data06==null){
            return Resulet.fail("load data06 failed!");
        }

       /*List<Map<String, Object>> list = data06.subList(0, 7);
        for (Map<String,Object> map:list) {
            for (String s:map.keySet()){
                System.out.println(s);
                System.out.println(map.get(s));
            }
        }*/
        return Resulet.success(data06);
    }


    @RequestMapping("getData07")
    public Resulet getData07(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间
        String newDate = dateFormat.format(dNow);
        String newDate2 = dateFormat.format(dBefore);
        System.out.println(newDate);
        System.out.println(newDate2);
        List<Map<String, Object>> data01 = dataMapper.getData07(newDate);
        List<Map<String, Object>> data02 = dataMapper.getData07(newDate2);
        if (data01==null){
            return Resulet.fail("load data07 failed!");
        }
        Map<String, Object> dataNow = data01.get(0);
        Map<String, Object> dataBefore = data02.get(0);
        Map<String, Object> NB=new HashMap<>(dataNow.size());

        Long CurrNB=(Long) dataNow.get("currentConfirmedCount")-(Long)dataBefore.get("currentConfirmedCount");
        Long ConNB=(Long) dataNow.get("confirmedCount")-(Long)dataBefore.get("confirmedCount");
        Long SuspNB=(Long) dataNow.get("suspectedCount")-(Long)dataBefore.get("suspectedCount");
        Long CuredNB=(Long) dataNow.get("curedCount")-(Long)dataBefore.get("curedCount");
        Long DeadNB=(Long) dataNow.get("deadCount")-(Long)dataBefore.get("deadCount");
        NB.put("currNB",CurrNB);
        NB.put("conNB",ConNB);
        NB.put("suspNB",SuspNB);
        NB.put("curedNB",CuredNB);
        NB.put("deadNB",DeadNB);

        System.out.println(NB.get("currNB"));
        System.out.println(NB.get("conNB"));
        System.out.println(NB.get("suspNB"));
        System.out.println(NB.get("curedNB"));
        System.out.println(NB.get("deadNB"));
        return Resulet.success(NB);
    }
}
