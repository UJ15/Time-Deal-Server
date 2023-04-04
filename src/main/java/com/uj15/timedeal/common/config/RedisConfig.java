package com.uj15.timedeal.common.config;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://192.168.1.8:6379");

        return Redisson.create(config);
    }

    @Bean
    public RLock redissonLock() {
        return redissonClient().getLock("orderCreateLock"); // 사용할 락의 이름을 설정합니다.
    }

    @Bean
    public RTopic redissonTopic() {
        return redissonClient().getTopic("order-create-topic"); // 사용할 토픽의 이름을 설정합니다.
    }

}