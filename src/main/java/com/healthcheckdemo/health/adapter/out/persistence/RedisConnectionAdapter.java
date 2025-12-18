package com.healthcheckdemo.health.adapter.out.persistence;

import com.healthcheckdemo.health.application.port.out.RedisConnectionPort;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisConnectionAdapter implements RedisConnectionPort {

    private final RedisConnectionFactory redisConnectionFactory;

    public RedisConnectionAdapter(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public boolean isConnected() {
        try {
            redisConnectionFactory.getConnection().ping();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getConnectionName() {
        return "Redis";
    }
}
