package com.twsz;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author wangjun
 * @date 18-2-9 上午11:11
 * @description
 * @modified by
 */

@SpringBootApplication(exclude = MybatisAutoConfiguration.class)
public class TwszUserApiApplication {
    private static Log logger = LogFactory.getLog(TwszUserApiApplication.class);

    public static void main(String args[]) {
        SpringApplication.run(TwszUserApiApplication.class, args);
    }
}
