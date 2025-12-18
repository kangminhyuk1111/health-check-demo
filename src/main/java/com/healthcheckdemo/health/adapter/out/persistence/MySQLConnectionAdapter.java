package com.healthcheckdemo.health.adapter.out.persistence;

import com.healthcheckdemo.health.application.port.out.DatabaseConnectionPort;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class MySQLConnectionAdapter implements DatabaseConnectionPort {

    private final DataSource dataSource;

    public MySQLConnectionAdapter(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean isConnected() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(5);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getConnectionName() {
        return "MySQL Database";
    }
}
