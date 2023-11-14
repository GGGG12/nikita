package ru.kapustin.soapdemo.integration;

import org.testcontainers.containers.PostgreSQLContainer;

public class AbstactContainerBaseTest {

    static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;

    static {
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer("postgres:latest");

        POSTGRE_SQL_CONTAINER.start();

    }
}
