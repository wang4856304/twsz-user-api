package com.twsz.controller;

import com.alibaba.fastjson.JSONObject;
import com.twsz.dao.TestDao;
import com.twsz.dao.user.UserSeqDao;
import com.twsz.entity.Response;
import com.twsz.entity.bo.UserBo;
import com.twsz.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjun
 * @date 18-2-9 下午3:33
 * @description
 * @modified by
 */

@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserSeqDao userSeqDao;

    @Autowired
    private TestDao testDao;

    @RequestMapping("/test")
    public Object test(@RequestBody JSONObject jsonObject) {
        return buildResponse(new Response());
    }

    @RequestMapping("/hello")
    public Object hello(@RequestParam String name) {
        /*Integer seq = testDao.insert("1");

        Map<String, Object> map = new HashMap<>();
        map.put("userCode", 1);
        testDao.insertByMap(map);*/

        UserBo userBo = new UserBo();
        userBo.setUserCode("1");
        testDao.insertByObj(userBo);
        return buildResponse(new Response());
    }
}
