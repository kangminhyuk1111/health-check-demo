package com.healthcheckdemo.health.application.port.out;

public interface ConnectionPort {
    boolean isConnected();
    String getConnectionName();
}
