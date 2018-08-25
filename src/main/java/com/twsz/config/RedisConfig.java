package com.twsz.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "spring.redis.noCluster")
@Configuration
public class RedisConfig {

    private int database;
    private String host;
    private int port;
    private String password;
    private int maxActive;
    private int maxWait = 5000;
    private int maxIdle;
    private int minIdle;
    private int timeout;
    private boolean testOnBorrow = true;

    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
        return jedisPool;
    }
}
