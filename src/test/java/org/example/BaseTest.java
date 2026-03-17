package org.example;

import io.qameta.allure.junit5.AllureJunit5;
import org.example.actors.Actor;
import org.example.config.AppConfig;
import org.example.config.ConfigurationManager;
import org.example.model.CredentialsDTO;
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
        CredentialsDTO credentials = new CredentialsDTO(config.getUserEmail(), config.getUserPassword());
        admin = new Actor(config, credentials).login();
    }
}
