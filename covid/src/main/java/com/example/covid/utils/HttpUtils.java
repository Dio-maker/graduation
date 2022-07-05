package com.example.covid.utils;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class  HttpUtils {
    private static PoolingHttpClientConnectionManager cm;
    private static RequestConfig config;
    private static List<String> userAgent;
    private static List<HttpHost> hosts;

    static {
        hosts=new ArrayList<>();
        //hosts.add(new HttpHost("119.123.175.200",9797));
        //hosts.add(new HttpHost("222.74.73.202",42055));
        //创建连接池
        cm=new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(10);
        config=RequestConfig.custom()
                .setSocketTimeout(10000)//连接建立后，request没有回应的timeout
                .setConnectTimeout(10000)//客户端和服务器建立连接的timeout
                .setConnectionRequestTimeout(10000)//从连接池获取连接的timeout
                //.setProxy(hosts.get(new Random().nextInt(hosts.size())))
                .build();
        userAgent=new ArrayList<>();
        userAgent.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.82 Safari/537.36");
        userAgent.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:97.0) Gecko/20100101 Firefox/97.0");
        userAgent.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:97.0) Gecko/20100101 Firefox/97.0");
    }

    public static String getHtml(String url){
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent",userAgent.get(new Random().nextInt(userAgent.size())));
        httpGet.setConfig(config);
        CloseableHttpResponse response=null;
        try {
            response=httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode()==200){
                if (response.getEntity()!=null){
                    return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
