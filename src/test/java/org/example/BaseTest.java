package org.example;

import io.qameta.allure.junit5.AllureJunit5;
import org.example.actors.Actor;
import org.example.config.AppConfig;
import org.example.config.ConfigurationManager;
import org.example.model.request.LoginRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.TimeUnit;


@ExtendWith(AllureJunit5.class)
@Timeout(value = 30, unit = TimeUnit.SECONDS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected AppConfig config = ConfigurationManager.INSTANCE.getConfig();
    protected Actor admin;

    @BeforeAll
    public void globalSetUp() {
        LoginRequest credentials = new LoginRequest(config.auth().email(), config.auth().password());
        admin = new Actor(credentials).login();
    }
}
