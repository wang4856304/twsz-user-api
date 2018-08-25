package com.twsz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.twsz.config.ProfileConfig;
import com.twsz.exception.BusinessException;
import com.twsz.service.SendNoticeService;
import com.twsz.utils.HttpClientUtil;
import com.twsz.utils.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

@Service
public class SendNoticeServiceImpl implements SendNoticeService {


    /**
     * 钉钉报警url
     */
    private static final String noticeUrl = "https://oapi.dingtalk.com/robot/send?access_token=526178eafd026cddff81c2ca2fb62493e4e49e1368db008cca7aea03f13f8dab";
    @Value("${mobile}")
    private String mobile;
    @Value("${project}")
    private String project;
    @Value("${env}")
    private String env;

    private static final String delimiter = ",";

    @Override
    public void sendTextNotice(String msg) {
        JSONObject reqJson = new JSONObject();
        reqJson.put("msgtype", "text");
        JSONObject contentJson = new JSONObject();
        if (StringUtils.isEmpty(mobile)) {
            throw new BusinessException("请配置报警电话");
        }
        String ipAddr = NetUtil.getLocalHostLANAddress();
        List<String> mobileList = Arrays.asList(mobile.split(delimiter));
        String mobiles = "";
        for (String mobile: mobileList) {
            mobiles = mobiles + "@" + mobile;
        }
        msg = msg + "\n" +mobiles;
        contentJson.put("content", "[" + ipAddr + "_" + project + "_" + env + "]\n" + msg);
        reqJson.put("text", contentJson);

        JSONObject atJson = new JSONObject();
        atJson.put("atMobiles", mobileList);
        atJson.put("isAtAll", false);
        reqJson.put("at", atJson);
        HttpClientUtil.httpPostRequest(noticeUrl, reqJson.toJSONString());
    }

    @Override
    public void sendTextNotice(Exception e) {
        sendTextNotice(e.getMessage());
    }
}
