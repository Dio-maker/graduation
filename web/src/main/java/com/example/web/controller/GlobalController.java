package com.example.web.controller;

import com.example.web.bean.Resulet;
import com.example.web.mapper.DataMapper;
import com.example.web.mapper.GlobalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("globalCovid")
public class GlobalController {

    @Autowired
    private GlobalMapper mapper;

    //top1
    @RequestMapping("getGlobal01")
    public Resulet getGlobal01() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String newDate = dateFormat.format(date);
        List<Map<String, Object>> global01 = mapper.getGlobal01(newDate);

        if (global01 == null) {
            return Resulet.fail("load data01 failed!");
        }
        Map<String, Object> dataNow = global01.get(0);

/*
        for (String s:dataNow.keySet()){
            System.out.println(s);
            System.out.println(dataNow.get(s));
        }*/

            return Resulet.success(dataNow);

    }

    //top2
    @RequestMapping("getGlobal02")
    public Resulet getData02(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String newDate = dateFormat.format(date);
        List<Map<String, Object>> global02 = mapper.getGlobal02(newDate);
        if (global02 ==null){
            return Resulet.fail("load data02 failed!");
        }
        /*Map<String, Object> map = new HashMap<>(1);
        map.put("name","南海诸岛");
        map.put("value",0);
        global02 .add(map);*/
        return Resulet.success(global02 );
    }
    //l2
    @RequestMapping("getGlobal03")
    public Resulet getData03(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String newDate = dateFormat.format(date);
        List<Map<String, Object>> global03 = mapper.getGlobal03(newDate);
        if (global03 ==null){
            return Resulet.fail("load data02 failed!");
        }
        List<Map<String, Object>> list = global03.subList(0, 7);
        /*Map<String, Object> map = new HashMap<>(1);
        map.put("name","南海诸岛");
        map.put("value",0);
        global02 .add(map);*/
        return Resulet.success(list );
    }
    //r2
    @RequestMapping("getGlobal04")
    public Resulet getData04(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String newDate = dateFormat.format(date);
        List<Map<String, Object>> global04 = mapper.getGlobal04(newDate);
        if (global04 ==null){
            return Resulet.fail("load data02 failed!");
        }

        /*Map<String, Object> map = new HashMap<>(1);
        map.put("name","南海诸岛");
        map.put("value",0);
        global02 .add(map);*/
        return Resulet.success(global04 );
    }

    //l1
    @RequestMapping("getGlobal05")
    public Resulet getData05(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String newDate = dateFormat.format(date);
        List<Map<String, Object>> global05 = mapper.getGlobal05(newDate);
        if (global05 ==null){
            return Resulet.fail("load data02 failed!");
        }

        /*Map<String, Object> map = new HashMap<>(1);
        map.put("name","南海诸岛");
        map.put("value",0);
        global02 .add(map);*/
        return Resulet.success(global05 );
    }

    //r1
    @RequestMapping("getGlobal06")
    public Resulet getData06(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String newDate = dateFormat.format(date);
        List<Map<String, Object>> global06 = mapper.getGlobal06(newDate);
        if (global06 ==null){
            return Resulet.fail("load data02 failed!");
        }


        /*Map<String, Object> map = new HashMap<>(1);
        map.put("name","南海诸岛");
        map.put("value",0);
        global02 .add(map);*/
        return Resulet.success(global06 );
    }

    @RequestMapping("getGlobal07")
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
        List<Map<String, Object>> data01 = mapper.getGlobal07(newDate);
        List<Map<String, Object>> data02 = mapper.getGlobal07(newDate2);
        if (data01==null){
            return Resulet.fail("load data07 failed!");
        }
        Map<String, Object> dataNow = data01.get(0);
        Map<String, Object> dataBefore = data02.get(0);
        Map<String, Object> NB=new HashMap<>(dataNow.size());

        Long CurrNB=(Long) dataNow.get("currentConfirmedCount")-(Long)dataBefore.get("currentConfirmedCount");
        Long ConNB=(Long) dataNow.get("confirmedCount")-(Long)dataBefore.get("confirmedCount");

        Long CuredNB=(Long) dataNow.get("curedCount")-(Long)dataBefore.get("curedCount");
        Long DeadNB=(Long) dataNow.get("deadCount")-(Long)dataBefore.get("deadCount");
        NB.put("currNB",CurrNB);
        NB.put("conNB",ConNB);
        NB.put("curedNB",CuredNB);
        NB.put("deadNB",DeadNB);

        return Resulet.success(NB);
    }
}
