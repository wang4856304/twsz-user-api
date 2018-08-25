package com.twsz.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * redis配置
 */
@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisClusterConfig {

    private String clusterNodes;
    private String password;
    private int expireSeconds = 120;
    private int commandTimeout = 10000;

    private static final String DELIMITER = ",";
    private static final String HOST_PORT_REGEX = ":";

    /*@Bean
    public JedisCluster getJedisCluster() {
        String[] nodes = clusterNodes.split(DELIMITER);
        Set<HostAndPort> nodeSet = new HashSet<HostAndPort>();
        for (String node: nodes) {
            String[] hostAndPorts = node.split(HOST_PORT_REGEX);
            nodeSet.add(new HostAndPort(hostAndPorts[0].trim(), Integer.valueOf(hostAndPorts[1].trim())));
        }
        return new JedisCluster(nodeSet, commandTimeout, 5000, 1, password, new GenericObjectPoolConfig());
    }*/
}
