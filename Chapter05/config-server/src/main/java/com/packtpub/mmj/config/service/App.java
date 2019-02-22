package com.packtpub.mmj.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 * @author Sourabh Sharma
 */
@SpringBootApplication
@EnableConfigServer
public class App {

  @Autowired
  JdbcTemplate jdbcTemplate;

  /**
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void insertDataAfterStartup() {
    Resource resource = new ClassPathResource("data-config.sql");
    ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
    databasePopulator.execute(jdbcTemplate.getDataSource());
  }
}
