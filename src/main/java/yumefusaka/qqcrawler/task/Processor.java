package yumefusaka.qqcrawler.task;

// task/Processor.java

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;
import yumefusaka.qqcrawler.pojo.Entity.User;
import yumefusaka.qqcrawler.service.serviceImpl.UserServiceImpl;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Component
public class Processor implements PageProcessor {

    @Autowired
    private Processor processor;

    @Autowired
    private UserServiceImpl userService;

    private Processor(){
        Login();
    }

    private static Set<Cookie> cookies;
    private Site site = Site.me().setRetryTimes(3).setSleepTime(0).setTimeOut(3000).setCharset("UTF-8").setUserAgent(
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36");

    @Override
    public Site getSite() {
        for(org.openqa.selenium.Cookie cookie:cookies) {
            site.addCookie(cookie.getName().toString(), cookie.getValue().toString());
        }
        return site;
    }

    public void Login() {
        System.setProperty("webdriver.chrome.driver",
                "E:\\Tool\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://qun.qq.com/member.html#gid=940118924");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.switchTo().frame("login_frame");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nick_1336679971")));
        element.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            cookies=driver.manage().getCookies();
            driver.close();
        }
    }



    @Override
    public void process(Page page){
        System.out.println(page);
        String rawText = page.getRawText();
        JSONObject jsonObject = JSONObject.parseObject(rawText);
        JSONArray mems = jsonObject.getJSONArray("mems");
        for(Object mem:mems){
            JSONObject memJson = (JSONObject) mem;
            Map<String,Object> map = JSON.parseObject(memJson.toJSONString(),Map.class);
            saveInfo(map);
        }
    }
    public void saveInfo(Map<String, Object> map) {
        User user = User.builder()
                .nickName(map.get("nick").toString())
                .qq(map.get("uin").toString())
                .gender(map.get("g").toString())
                .groupName(map.get("card").toString())
                .enterTime(map.get("join_time").toString())
                .qYear(Integer.valueOf(map.get("qage").toString()))
                .build();
        if(Objects.equals(user.getGender(), "1")){
            user.setGender("男");
        }
        if(Objects.equals(user.getGender(), "-1")){
            user.setGender("女");
        }
        if(Objects.equals(user.getGender(), "0")){
            user.setGender("未知");
        }

        long time = Long.parseLong(user.getEnterTime()) * 1000;
        DateTime dateTime = DateUtil.date(time);
        String formattedDate = DateUtil.format(dateTime, "yyyy-MM-dd HH:mm:ss");
        user.setEnterTime(formattedDate);

        userService.mySave(user);
    }

    private String url = "https://qun.qq.com/cgi-bin/qun_mgr/search_group_members";

    @Autowired
    private Pipeline pipeline;

    private static final int[] start = {0,21,42,63,84};

    private static final int[] end = {20,41,62,83,89};

    @Scheduled(initialDelay = 1000,fixedDelay = 100*1000)
    public void start(){
        for(int i=0;i<start.length;i++){
            Request request = new Request(url);
            request.setMethod(HttpConstant.Method.POST);
            Map<String,Object> params=new HashMap<>();
            params.put("gc","940118924");
            params.put("st",start[i]);
            params.put("end",end[i]);
            params.put("sort","0");
            params.put("bkn","2007973527");
            request.setRequestBody(HttpRequestBody.form(params,"utf-8"));
            Spider.create(processor)
                    .addRequest(request)
                    .thread(1)
                    .run();
        }
    }
}