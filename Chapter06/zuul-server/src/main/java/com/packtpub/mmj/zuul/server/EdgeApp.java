package com.packtpub.mmj.zuul.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Sourabh Sharma
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class EdgeApp {

//  @LoadBalanced
//  @Bean
//  RestTemplate restTemplate() {
//    return new RestTemplate();
//  }

  public static void main(String[] args) {
    SpringApplication.run(EdgeApp.class, args);
  }
}

//@Component
//class DiscoveryClientSample implements CommandLineRunner {
//
//  @Autowired
//  private DiscoveryClient discoveryClient;
//
//  @Override
//  public void run(String... strings) throws Exception {
//    System.out.println(discoveryClient.description());
//    discoveryClient.getInstances("restaurant-service")
//        .forEach((ServiceInstance serviceInstance) -> {
//          System.out.println("Instance --> " + serviceInstance.getServiceId()
//              + "\nServer: " + serviceInstance.getHost() + ":" + serviceInstance.getPort()
//              + "\nURI: " + serviceInstance.getUri() + "\n\n\n");
//        });
//  }
//}