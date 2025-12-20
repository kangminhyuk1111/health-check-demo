package com.healthcheckdemo.health.adapter.out.persistence;

import com.healthcheckdemo.health.application.port.out.KafkaConnectionPort;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Component
public class KafkaConnectionAdapter implements KafkaConnectionPort {

    private final String bootstrapServers;

    public KafkaConnectionAdapter(@Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    @Override
    public boolean isConnected() {
        try {
            Properties props = new Properties();
            props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

            AdminClient client = AdminClient.create(props);
            client.describeCluster().clusterId().get(5, TimeUnit.SECONDS);
            client.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getConnectionName() {
        return "Kafka Broker";
    }
}