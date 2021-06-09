package com.example.api;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import com.example.api.config.DBIntegration;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ConfigIntegration {

    private static List<Class> entities;

    @LocalServerPort
    protected int porta;

    @BeforeClass
    public void generalSetUp() {
        RestAssured.port = porta;
    }

    @Autowired
    private DBIntegration dbIntegration;
    private static DBIntegration staticDbIntegration;

    @PostConstruct
    protected void init() {
        staticDbIntegration = dbIntegration;
    }

    @AfterAll
    public static void cleanUponFinish() {
        clearTables();
    }

    protected static void clearTables() {
        staticDbIntegration.cleanTables(entities);
    }

    protected void registryEntity(Class clazz) {
        if(isNull(entities))
            entities = new ArrayList<>();
        entities.add(clazz);
    }

    protected void registryEntities(List<Class> classes) {
        if(isNull(entities))
            entities = new ArrayList<>();
        entities.addAll(classes);
    }
}
