package com.example.covid.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.covid.CovidApplication;
import com.example.covid.bean.BeanCovid;
import com.example.covid.bean.BeanGlobal;
import com.example.covid.utils.DateUtils;
import com.example.covid.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = CovidApplication.class)
@Component
public class CrawWorld {
    @Autowired
    private KafkaTemplate kafka;

    @Scheduled(cron = "1-2 0/5 * * * ? ")
    // @Test
    //@Scheduled(cron = "0 0 8 * * * ?")//开启定时任务，每日八点刷新数据
    //@Test
    public void Crawling(){

        String html = HttpUtils.getHtml("http://api.tianapi.com/ncovabroad/index?key=ba0b406c9a477bc62d30007b65cc0606");

        String reg="\\[(.*)\\]";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(html);
        String s="";
        if (matcher.find()){
            s = matcher.group();
        }

        String time=getTime();
        List<BeanGlobal> array = JSON.parseArray(s, BeanGlobal.class);

        for (BeanGlobal b:array){
                b.setDate(time);
                String jsonString = JSON.toJSONString(b);
                kafka.send("covidglobal",b.getLocationId(),jsonString);
            }


        System.out.println("已更新国外数据！");

    }
    public String getTime(){
        return DateUtils.formatDate(System.currentTimeMillis(), "yyyy-MM-dd");
    }
}
