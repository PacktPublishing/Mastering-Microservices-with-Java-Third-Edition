package com.packtpub.mmj.conductor;

import com.packtpub.mmj.conductor.client.worker.TaskSampleWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Sourabh Sharma
 */
@SpringBootApplication
@EnableEurekaClient
public class ConductorApp implements CommandLineRunner {
  private static final Logger LOG = LoggerFactory.getLogger(ConductorApp.class);

  @Autowired
  private TaskSampleWorker taskSampleWorker;

  @Value("${spring.application.name:conductor-service}")
  public String appName;

  /**
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(ConductorApp.class, args);
  }

  @Override
  public void run(final String... arg0) throws Exception {
    LOG.info("\n\n{} is starting Task A polling...", appName);
    taskSampleWorker.initConductorPolling();
  }
}