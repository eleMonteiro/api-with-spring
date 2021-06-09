package com.example.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.flywaydb.core.Flyway;
import org.springframework.core.io.ClassPathResource;

public class ConfigurationInitializer {
    
    private static final Logger LOG = Logger.getLogger("ControraApplication");

    public static void executeMigrations() {
        LOG.info("<================ Executing Migrations - Begin ================>");
        try {
            Flyway flyway = Flyway.configure().configuration(getApplicationProperties()).load();
            flyway.migrate();
        } catch(Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        } finally {
            LOG.info("<================ Executing Migrations - End ================>");
        }
    }

    private static Properties getApplicationProperties() throws IOException {
        Properties properties = new Properties();
        File applicationProperties = new ClassPathResource("application.properties").getFile();
        LOG.info("Read property file: " + applicationProperties.getPath());
        properties.load(new FileInputStream(applicationProperties));
        return properties;
    }

}
