package com.example.covid.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.covid.bean.BeanCity;
import com.example.covid.bean.BeanCovid;
import com.example.covid.utils.DateUtils;
import com.example.covid.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = CovidApplication.class)
@Component
public class CrawlData {
    @Autowired
    private KafkaTemplate kafka;

    @Scheduled(cron = "1-2 0/5 * * * ? ")
    //@Test
    //@Scheduled(cron = "0 0 8 * * * ?")//开启定时任务，每日八点刷新数据
    public void Crawling(){
        System.out.println("  ");
        System.out.println("已更新！");
        String html = HttpUtils.getHtml("https://ncov.dxy.cn/ncovh5/view/pneumonia?link=&share=&source=");

        Document document = Jsoup.parse(html);
        String text = document.select("#getAreaStat").toString();
        String reg="\\[(.*)\\]";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(text);
        String s="";
        if (matcher.find()){
            s = matcher.group();
        }
        String time=getTime();
        List<BeanCovid> array = JSON.parseArray(s, BeanCovid.class);
        for (BeanCovid b:array){
            b.setDate(time);
            setCitys(b);

            JSONObject jsonObject = JSON.parseObject(setStatisticsData(b.getStatisticsData()));
            b.setStatisticsData(jsonObject.getString("data"));
            String jsonString = JSON.toJSONString(b);
            kafka.send("covid2022",b.getLocationId(),jsonString);

        }

    }

    private String setStatisticsData(String statisticsDataUrl) {
        return HttpUtils.getHtml(statisticsDataUrl);
    }

    private void setCitys(BeanCovid bean) {
        List<BeanCity> beanCities = JSON.parseArray(bean.getCities(), BeanCity.class);
        for (BeanCity beanCity:beanCities){
            beanCity.setProvinceId(bean.getLocationId());
            beanCity.setDate(bean.getDate());
            beanCity.setProvinceShortName(bean.getProvinceShortName());
            String jsonString = JSON.toJSONString(beanCity);
            kafka.send("covid2022",beanCity.getProvinceId(),jsonString);
        }
    }

    public String getTime(){
        return DateUtils.formatDate(System.currentTimeMillis(), "yyyy-MM-dd");
    }
}
